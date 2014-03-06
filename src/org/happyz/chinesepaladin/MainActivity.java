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
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.media.AudioManager;

public class MainActivity extends Activity implements View.OnClickListener {
	
	public static MainActivity instance = null;
	public static MainView mView = null;
	
	private Button videoDepth;
	private Button screenRatio;
	private Button smoothVideo;
	private Button nevershow;
	private Button gallery;
	private Button about;
	private Button run;
	
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
	 */
	public void runAppLaunchConfig() {
		if(checkCurrentDirectory(true)){
			Settings.LoadLocals(this); // load local settings
			if(!Locals.AppLaunchConfigUse){
				runApp();
			}else{
				//AppLaunchConfigView view = new AppLaunchConfigView(this);
				setContentView(R.layout.cp_config);
				initialConfigView();
			}
		}
	}

	/**
	 * File Check
	 */
	private boolean checkAppNeedFiles() {
		String missingFileNames = "";
		int missingCount = 0;
		for(String fileName : Globals.APP_NEED_FILENAME_ARRAY){
			String[] itemNameArray = fileName.split("\\|");
			boolean flag = false;
			for(String itemName : itemNameArray){
				File file = new File(Globals.CurrentDirectoryPath + "/" + itemName.trim());
				if(file.exists() && file.canRead()){
					flag = true;
					break;
				}
			}
			if(!flag){
				missingCount ++;
				missingFileNames += "File " + missingCount + ": " + fileName.replace("|"," or ") + "\n";
			}
		}
		if(missingCount != 0){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setTitle(getResources().getString(R.string.error));
			alertDialogBuilder.setMessage(getResources().getString(R.string.missing_file) + "\n" + missingFileNames);
		    alertDialogBuilder.setPositiveButton(getResources().getString(R.string.fixit), new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int whichButton) {
				    Toast.makeText(instance, R.string.unziping, Toast.LENGTH_SHORT).show();
					unZipFile();
				}
			}).setNegativeButton(getResources().getString(R.string.quit), new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int whichButton) {
					finish();
				}
			});
			alertDialogBuilder.setCancelable(false);
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return false;
		}
		return true;
	}

	/*
	 * Run the app
	 */
	public void runApp() {
		if(checkCurrentDirectory(true)){
			Settings.LoadLocals(this); // load local settings
			if(checkAppNeedFiles()){
				if(mView == null){
					mView = new MainView(this);
					setContentView(mView);
				}
				mView.setFocusableInTouchMode(true);
				mView.setFocusable(true);
				mView.requestFocus();
				System.gc();
			}
		}
	}

	/*
	 * Unzip the game files
	 */
	private void unZipFile(){
		String dirPath = Environment.getExternalStorageDirectory().getPath() + Globals.CURRENT_DIRECTORY_PATH_TEMPLATE + "/";
		File dir = new File(dirPath);
		try{
			if (!dir.exists()) dir.mkdir();
		} catch (Exception e){
			e.printStackTrace();
		}
		int size = 4096;
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

					File entryFile = new File(dirPath + strEntry);
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
	    finish();
	}
	
	
	/**
	 * Check the validity of the directory
	 * @param	quitting	boolean to determine whether quit the app or not if check failed
	 * @return				directory is valid (true) or invalid (false)
	 */
	public boolean checkCurrentDirectory(boolean quitting) {
		String curDirPath = Globals.CurrentDirectoryPath;
		if(curDirPath != null && !curDirPath.equals("")){
			Toast.makeText(instance, "Game Directory is Loaded", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		AlertDialog.Builder d = new AlertDialog.Builder(this);
		d.setTitle(getResources().getString(R.string.error));
		d.setMessage(getResources().getString(R.string.open_dir_error));
		if(quitting){
		    d.setPositiveButton(getResources().getString(R.string.fixit), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(instance, R.string.unziping, Toast.LENGTH_SHORT).show();
					unZipFile();
				}
			}).setNegativeButton(getResources().getString(R.string.quit), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		} else {
		    d.setPositiveButton(getResources().getString(R.string.ok), null);
		}
		d.setCancelable(false);
		d.create().show();
		
		return false;
	}
	
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
	
	/*
		TextView txt1 = new TextView(mActivity);
		txt1.setTextSize(18.0f);
		txt1.setText(getResources().getString(R.string.screen_orientation));
		txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		
		mScreenOrientationText = new TextView(mActivity);
		mScreenOrientationText.setPadding(5, 0, 0, 0);
		switch(Locals.ScreenOrientation){
			case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
				mScreenOrientationText.setText(getResources().getString(R.string.portrait));
				break;
			case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
				mScreenOrientationText.setText(getResources().getString(R.string.landscape));
				break;
			case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
				mScreenOrientationText.setText(getResources().getString(R.string.r_portrait));
				break;
			case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
				mScreenOrientationText.setText(getResources().getString(R.string.r_landscape));
				break;
			default:
				mScreenOrientationText.setText(getResources().getString(R.string.unknown));
				break;
		}
		txtLayout.addView(mScreenOrientationText, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	screenOrientationLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	
	Button btn = new Button(mActivity);
	btn.setText(getResources().getString(R.string.change));
	btn.setOnClickListener(new OnClickListener(){
		public void onClick(View v){
			String[] screenOrientationItems;
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD){
				screenOrientationItems = new String[]{getResources().getString(R.string.portrait), getResources().getString(R.string.landscape), getResources().getString(R.string.r_portrait), getResources().getString(R.string.r_landscape)};
			} else {
				screenOrientationItems = new String[]{getResources().getString(R.string.portrait), getResources().getString(R.string.landscape)};
			}
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
			alertDialogBuilder.setTitle(getResources().getString(R.string.screen_orientation));
			alertDialogBuilder.setItems(screenOrientationItems, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which)
				{
					switch(which){
						case 0:
							Locals.ScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
							mScreenOrientationText.setText(getResources().getString(R.string.portrait));
							break;
						case 1:
							Locals.ScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
							mScreenOrientationText.setText(getResources().getString(R.string.landscape));
							break;
						case 2:
							Locals.ScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
							mScreenOrientationText.setText(getResources().getString(R.string.r_portrait));
							break;
						case 3:
							Locals.ScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
							mScreenOrientationText.setText(getResources().getString(R.string.r_landscape));
							break;
					}
					Settings.SaveLocals(mActivity);
				}
			});
			alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);
			alertDialogBuilder.setCancelable(true);
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
	});
	screenOrientationLayout.addView(btn, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
}
mConfLayout.addView(screenOrientationLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

//Command Options
for(int i = 0; i < Globals.APP_COMMAND_OPTIONS_ITEMS.length; i ++){
	LinearLayout cmdOptLayout = new LinearLayout(mActivity);
	{
		final int index = i;
		
		CheckBox chk = new CheckBox(mActivity);
		chk.setChecked(Locals.AppCommandOptions.indexOf(Globals.APP_COMMAND_OPTIONS_ITEMS[index][1]) >= 0);
		chk.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				CheckBox c = (CheckBox)v;
				if(!c.isChecked()){
					int start = Locals.AppCommandOptions.indexOf(Globals.APP_COMMAND_OPTIONS_ITEMS[index][1]);
					if(start == 0){
						Locals.AppCommandOptions = Locals.AppCommandOptions.replace(Globals.APP_COMMAND_OPTIONS_ITEMS[index][1], "");
					} else if(start >= 0){
						Locals.AppCommandOptions = Locals.AppCommandOptions.replace(" " + Globals.APP_COMMAND_OPTIONS_ITEMS[index][1], "");
					}
				} else {
					if(Locals.AppCommandOptions.equals("")){
						Locals.AppCommandOptions = Globals.APP_COMMAND_OPTIONS_ITEMS[index][1];
					} else {
						Locals.AppCommandOptions += " " + Globals.APP_COMMAND_OPTIONS_ITEMS[index][1];
					}
				}
				Settings.SaveLocals(mActivity);
			}
		});
		cmdOptLayout.addView(chk, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		
		LinearLayout txtLayout = new LinearLayout(mActivity);
		txtLayout.setOrientation(LinearLayout.VERTICAL);
		{
			TextView txt1 = new TextView(mActivity);
			txt1.setTextSize(18.0f);
			txt1.setText(Globals.APP_COMMAND_OPTIONS_ITEMS[index][0]);
			txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			
			TextView txt2 = new TextView(mActivity);
			txt2.setPadding(5, 0, 0, 0);
			txt2.setText(Globals.APP_COMMAND_OPTIONS_ITEMS[index][1]);
			txtLayout.addView(txt2, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		cmdOptLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	}
	mConfLayout.addView(cmdOptLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
}

//Environment
mEnvironmentTextArray = new TextView[Globals.ENVIRONMENT_ITEMS.length];
mEnvironmentButtonArray = new Button[Globals.ENVIRONMENT_ITEMS.length];
for(int i = 0; i < Globals.ENVIRONMENT_ITEMS.length; i ++){
	LinearLayout envLayout = new LinearLayout(mActivity);
	{
		final int index = i;
		String value = Locals.EnvironmentMap.get(Globals.ENVIRONMENT_ITEMS[index][1]);
		
		CheckBox chk = new CheckBox(mActivity);
		chk.setChecked(value != null);
		chk.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				CheckBox c = (CheckBox)v;
				if(!c.isChecked()){
					Locals.EnvironmentMap.remove(Globals.ENVIRONMENT_ITEMS[index][1]);
					mEnvironmentTextArray[index].setText("unset " + Globals.ENVIRONMENT_ITEMS[index][1]);
					mEnvironmentButtonArray[index].setVisibility(View.GONE);
				} else {
					Locals.EnvironmentMap.put(Globals.ENVIRONMENT_ITEMS[index][1], Globals.ENVIRONMENT_ITEMS[index][2]);
					mEnvironmentTextArray[index].setText(Globals.ENVIRONMENT_ITEMS[index][1] + "=" + Globals.ENVIRONMENT_ITEMS[index][2]);
					mEnvironmentButtonArray[index].setVisibility(View.VISIBLE);
				}
				Settings.SaveLocals(mActivity);
			}
		});
		envLayout.addView(chk, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		
		LinearLayout txtLayout = new LinearLayout(mActivity);
		txtLayout.setOrientation(LinearLayout.VERTICAL);
		{
			TextView txt1 = new TextView(mActivity);
			txt1.setTextSize(18.0f);
			txt1.setText(Globals.ENVIRONMENT_ITEMS[index][0]);
			txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			
			mEnvironmentTextArray[i] = new TextView(mActivity);
			mEnvironmentTextArray[i].setPadding(5, 0, 0, 0);
			if(value == null){
				mEnvironmentTextArray[i].setText("unset" + Globals.ENVIRONMENT_ITEMS[index][1]);
			} else {
				mEnvironmentTextArray[i].setText(Globals.ENVIRONMENT_ITEMS[index][1] + "=" + value);
			}
			txtLayout.addView(mEnvironmentTextArray[i], new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		envLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		
		mEnvironmentButtonArray[index] = new Button(mActivity);
		mEnvironmentButtonArray[index].setText("Change");
		mEnvironmentButtonArray[index].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				final EditText ed = new EditText(mActivity);
				ed.setInputType(InputType.TYPE_CLASS_TEXT);
				ed.setText(Locals.EnvironmentMap.get(Globals.ENVIRONMENT_ITEMS[index][1]));
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
				alertDialogBuilder.setTitle(Globals.ENVIRONMENT_ITEMS[index][1]);
				alertDialogBuilder.setView(ed);
				alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton) {
						String newval = ed.getText().toString();
						Locals.EnvironmentMap.put(Globals.ENVIRONMENT_ITEMS[index][1], newval);
						mEnvironmentTextArray[index].setText(Globals.ENVIRONMENT_ITEMS[index][1] + "=" + newval);
						Settings.SaveLocals(mActivity);
					}
				});
				alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);
				alertDialogBuilder.setCancelable(true);
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
		mEnvironmentButtonArray[index].setVisibility(value != null ? View.VISIBLE : View.GONE);
		envLayout.addView(mEnvironmentButtonArray[index], new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	*/
	
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
		if( mView != null )
		{
			mView.onResume();
		}
		_isPaused = false;
	}

	@Override
	protected void onDestroy()  {
		if( mView != null ){
			mView.exitApp();
			try {
				wait(3000);
			} catch(InterruptedException e){}
		}
		super.onDestroy();
		System.exit(0);
	}
/*
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Do nothing here
	}*/
}

