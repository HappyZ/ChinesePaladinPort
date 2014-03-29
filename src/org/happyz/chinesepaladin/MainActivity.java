/*
Simple DirectMedia Layer
Java source code (C) 2009-2011 Sergii Pylypenko
  
This software is provided 'as-is', without any express or implied
warranty.  In no event will the authors be held liable for any damages
arising from the use of this software.

Permission is granted to anyone to use this software for any purpose,
including commercial applications, and to alter it and redistribute it
freely, subject to the following restrictions:
  
1. The origin of this software must not be misrepresented; you must not
   claim that you wrote the original software. If you use this software
   in a product, an acknowledgment in the product documentation would be
   appreciated but is not required. 
2. Altered source versions must be plainly marked as such, and must not be
   misrepresented as being the original software.
3. This notice may not be removed or altered from any source distribution.
*/

/*
 * 2012/7 Modified by AKIZUKI Katane
 * 2013/9 Modified by Martin Dieter
 * 2014/3 Modified by HappyZ
 */


package org.happyz.chinesepaladin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.media.AudioManager;

public class MainActivity extends Activity implements View.OnClickListener {
	
	public static MainActivity instance = null;
	public static DrawerLayout mDrawer = null;
	public static FrameLayout mFrame = null;
	public static ListView mSettings = null;
	public static MainView mView = null;
	
	private Button videoDepth;
	private Button screenRatio;
	private Button smoothVideo;
	private Button nevershow;
	private Button gallery;
	private Button about;
	private Button run;
	
	private String[] settingTitles;
	
	boolean _isPaused = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// window feature
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// initiate the instance
		instance = this;
		// load global settings
		Settings.LoadGlobals(this);
		// run configuration
		runAppLaunchConfig();
	}
	
	/**
	 * Configuration Screen
	 * @author HappyZ
	 */
	public void runAppLaunchConfig() {
		checkGameDirectory(); // if failed, exit app
		Settings.LoadLocals(this); // load local settings
		if(!Locals.AppLaunchConfigUse){
			runApp();
		}else{
			//AppLaunchConfigView view = new AppLaunchConfigView(this);
			setContentView(R.layout.cp_config);
			initialConfigView();
		}
	}
	
	/**
	 * Check the validity of the directory (and files)
	 * @author HappyZ
	 */
	private void checkGameDirectory() {
		String curDirPath = Globals.CurrentDirectoryPath;
		String path = Environment.getExternalStorageDirectory().getPath() + Globals.CURRENT_DIRECTORY_PATH_TEMPLATE;
		if (curDirPath == null || curDirPath.equals("")){
			// create directory if not exist
			File dir = new File(path);
			dir.mkdirs();
			Settings.setupCurrentDirectory(); // setup again
		}
		curDirPath = Globals.CurrentDirectoryPath;
		if(curDirPath != null && !curDirPath.equals("")){
			if (!checkGameFiles()) unZipFile();
			Toast.makeText(instance, "Game files have been validated", Toast.LENGTH_SHORT).show();
		} else { // exit app since failed to create a directory
			AlertDialog.Builder d = new AlertDialog.Builder(this);
			d.setTitle(getResources().getString(R.string.error));
			d.setMessage(getResources().getString(R.string.error_create_dir) + path);
			d.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			d.setCancelable(false);
			d.create().show();
		}
	}

	/**
	 * Validate the Files
	 * @author HappyZ
	 * @return boolean check existence of all required files
	 */
	private boolean checkGameFiles() {
		for(String fileName : Globals.APP_NEED_FILENAME_ARRAY){
			String[] itemNameArray = fileName.split("\\|"); // support "|" (or) files
			boolean flag = false;
			for(String itemName : itemNameArray){
				File file = new File(Globals.CurrentDirectoryPath + "/" + itemName.trim());
				if(file.exists() && file.canRead()){
					flag = true;
					break;
				}
			}
			if (!flag) return false;
		}
		return true;
	}

	/**
	 * Run the app
	 * @author HappyZ
	 */
	public void runApp() {
		Settings.LoadLocals(this); // load local settings
		if (!checkGameFiles())
			unZipFile();
		if (mView == null) {
			settingTitles = getResources().getStringArray(R.array.settings);
			mDrawer = new DrawerLayout(this);
			mFrame = new FrameLayout(this);
			mSettings = new ListView(this);
			mView = new MainView(this);
			// MainView Added to Frame
			mFrame.addView(mView);
			// Frame Added to Drawer
			mDrawer.addView(mFrame, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// Settings Added to Drawer
			DrawerLayout.LayoutParams mSettingsLP = new DrawerLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			mSettingsLP.gravity = Gravity.START;
			mSettings.setLayoutParams(mSettingsLP);
			mSettings.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, settingTitles));
			mDrawer.addView(mSettings);
			// Initialize DrawerLayout view
			setContentView(mDrawer);
		}
		mView.setFocusableInTouchMode(true);
		mView.setFocusable(true);
		mView.requestFocus();
		System.gc();
	}

	/**
	 * Unzip the game files
	 * @author HappyZ
	 */
	private void unZipFile(){
		int size = 4096; // buffer 4kb
		String strEntry;
		try{
			BufferedOutputStream dest = null; 
			InputStream fis = instance.getResources().openRawResource(R.raw.cp);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				try {
					Log.i("Unzip: ","="+ entry);
					int count; 
					byte data[] = new byte[size];
					strEntry = entry.getName();

					File entryFile = new File(Globals.CurrentDirectoryPath + "/" + strEntry);
					File entryDir = new File(entryFile.getParent());
					if (!entryDir.exists()) {
						entryDir.mkdirs();
					}
					if (!entryFile.exists()){
						FileOutputStream fos = new FileOutputStream(entryFile);
						dest = new BufferedOutputStream(fos, size);
						while ((count = zis.read(data, 0, size)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			zis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Implement onClickListener for all buttons
	 * @author HappyZ
	 */
	public void onClick(View arg) {
		switch (arg.getId()) {
		case R.id.cp_video_depth:
			if(Globals.VIDEO_DEPTH_BPP_ITEMS.length >= 2){
				String[] bppItems = new String[Globals.VIDEO_DEPTH_BPP_ITEMS.length];
				for(int i = 0; i < bppItems.length; i ++){
					bppItems[i] = "" + Globals.VIDEO_DEPTH_BPP_ITEMS[i] + "bpp";
				}
				// new dialog
				AlertDialog.Builder d = new AlertDialog.Builder(instance);
				d.setTitle(getResources().getString(R.string.video_depth));
				d.setItems(bppItems, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Locals.VideoDepthBpp = Globals.VIDEO_DEPTH_BPP_ITEMS[which];
						videoDepth.setText(getResources().getString(R.string.video_depth)
								+"\n"+getResources().getString(R.string.current)+Locals.VideoDepthBpp+"bpp");
						Settings.SaveLocals(instance);
					}
				});
				d.setNegativeButton(getResources().getString(R.string.cancel), null);
				d.setCancelable(true);
				d.create().show();
			}else{
				Toast.makeText(instance, getResources().getString(R.string.no_other_options), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.cp_screen_ratio:
			if(Globals.VIDEO_RATIO_ITEMS.length >= 2){
				String[] ratioItems = new String[Globals.VIDEO_RATIO_ITEMS.length + 1];
				ratioItems[0] = getResources().getString(R.string.swap);
				for(int i = 1; i < ratioItems.length; i++){
					int w = Globals.VIDEO_RATIO_ITEMS[i-1][0];
					int h = Globals.VIDEO_RATIO_ITEMS[i-1][1];
					if(w > 0 && h > 0){
						ratioItems[i] = "" + w + ":" + h;
					} else {
						ratioItems[i] = getResources().getString(R.string.full);
					}
				}
				// new dialog
				AlertDialog.Builder d = new AlertDialog.Builder(instance);
				d.setTitle(getResources().getString(R.string.screen_ratio));
				d.setItems(ratioItems, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which != 0){
							Locals.VideoXRatio = Globals.VIDEO_RATIO_ITEMS[which-1][0];
							Locals.VideoYRatio = Globals.VIDEO_RATIO_ITEMS[which-1][1];
						}else{
							int tmp = Locals.VideoXRatio;
							Locals.VideoXRatio = Locals.VideoYRatio;
							Locals.VideoYRatio = tmp;
						}
						instance.screenRatio.setText(getResources().getString(R.string.screen_ratio)
								+"\n"+getResources().getString(R.string.current)
								+((Locals.VideoXRatio > 0 && Locals.VideoYRatio > 0)?(Locals.VideoXRatio+":"+Locals.VideoYRatio):(getResources().getString(R.string.full))));
						Settings.SaveLocals(instance);
					}
				});
				d.setNegativeButton(getResources().getString(R.string.cancel), null);
				d.setCancelable(true);
				d.create().show();
			}
			break;
		case R.id.cp_smooth_video:
			Locals.VideoSmooth = !Locals.VideoSmooth;
			Settings.SaveLocals(instance);
			this.smoothVideo.setText(getResources().getString(R.string.smooth_video)
					+"\n"+getResources().getString(R.string.current)
					+(Locals.VideoSmooth?"Yes":"No"));
			break;
		case R.id.cp_never_show:
			// new dialog
			AlertDialog.Builder d = new AlertDialog.Builder(instance);
			d.setTitle(getResources().getString(R.string.never_show));
			d.setMessage(getResources().getString(R.string.never_show_disc));
			d.setNegativeButton(getResources().getString(R.string.no),  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Locals.AppLaunchConfigUse = true;
					instance.nevershow.setText(getResources().getString(R.string.never_show)
							+"\n"+getResources().getString(R.string.current)
							+(Locals.AppLaunchConfigUse?"No":"Yes"));
					Settings.SaveLocals(instance);
				}
			});
			d.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Locals.AppLaunchConfigUse = false;
					instance.nevershow.setText(getResources().getString(R.string.never_show)
							+"\n"+getResources().getString(R.string.current)
							+(Locals.AppLaunchConfigUse?"No":"Yes"));
					Settings.SaveLocals(instance);
				}
			});
			d.setCancelable(true);
			d.create().show();
			break;
		case R.id.cp_run:
			runApp();
			break;
		default:
			Toast.makeText(instance, getResources().getString(R.string.coming_soon),Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Initialize the Configuration View
	 * @author HappyZ
	 */
	private void initialConfigView(){
		// video depth
		this.videoDepth = ((Button) findViewById(R.id.cp_video_depth));
		this.videoDepth.setText(getResources().getString(R.string.video_depth)
				+"\n"+getResources().getString(R.string.current)+Locals.VideoDepthBpp+"bpp");
		this.videoDepth.setOnClickListener(this);
		// screen ratio
		this.screenRatio = ((Button) findViewById(R.id.cp_screen_ratio));
		this.screenRatio.setText(getResources().getString(R.string.screen_ratio)
				+"\n"+getResources().getString(R.string.current)
				+((Locals.VideoXRatio > 0 && Locals.VideoYRatio > 0)?(Locals.VideoXRatio+":"+Locals.VideoYRatio):(getResources().getString(R.string.full))));
		this.screenRatio.setOnClickListener(this);
		// smooth video
		this.smoothVideo = ((Button) findViewById(R.id.cp_smooth_video));
		this.smoothVideo.setText(getResources().getString(R.string.smooth_video)
				+"\n"+getResources().getString(R.string.current)
				+(Locals.VideoSmooth?"Yes":"No"));
		this.smoothVideo.setOnClickListener(this);
		// never show
		this.nevershow = ((Button) findViewById(R.id.cp_never_show));
		this.nevershow.setText(getResources().getString(R.string.never_show)
				+"\n"+getResources().getString(R.string.current)
				+(Locals.AppLaunchConfigUse?"No":"Yes"));
		this.nevershow.setOnClickListener(this);
		// gallery
		this.gallery = ((Button) findViewById(R.id.cp_gallery));
		this.gallery.setOnClickListener(this);
		// about
		this.about = ((Button) findViewById(R.id.cp_about));
		this.about.setOnClickListener(this);
		// run game
		this.run = ((Button) findViewById(R.id.cp_run));
		this.run.setOnClickListener(this);
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
    @Override
	public boolean onPrepareOptionsMenu( Menu menu ) {
		if(mView != null){
			return mView.onPrepareOptionsMenu(menu);
		}
		return true;
	}
	
    @Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		if(mView != null){
			return mView.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	protected void onPause() {
		if( !Globals.APP_CAN_RESUME ) {
			if (mView != null) {
				mView.exitApp();
				try {
					wait(3000);
				} catch(InterruptedException e){}
			}
		}
		_isPaused = true;
		if( mView != null )
			mView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if( mView != null ) {
			mView.onResume();
		}
		_isPaused = false;
	}

	@Override
	protected void onDestroy() {
		if( mView != null ){
			mView.exitApp();
			try {
				wait(3000);
			} catch(InterruptedException e){}
		}
		super.onDestroy();
		System.exit(0);
	}
}

