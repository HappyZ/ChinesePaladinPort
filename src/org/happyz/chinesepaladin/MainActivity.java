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
 2012/7 Modified by AKIZUKI Katane
 2013/9 Modified by Martin Dieter
 2013/12 Modified by HappyZ
*/


package org.happyz.chinesepaladin;

import java.io.File;

import android.text.InputType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.graphics.Color;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	
	public static MainActivity instance = null;
	public static MainView mView = null;
	
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
				AppLaunchConfigView view = new AppLaunchConfigView(this);
				setContentView(view);
			}
		}
	}

	/**
	 * File Check
	 */
	private boolean checkAppNeedFiles()
	{
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
		    alertDialogBuilder.setPositiveButton(getResources().getString(R.string.download), new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO: implement the download feature
					Toast.makeText(instance, "not implemented", Toast.LENGTH_LONG).show();
					finish();
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

	/**
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
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(getResources().getString(R.string.error));
		alertDialogBuilder.setMessage(getResources().getString(R.string.open_dir_error));
		if(quitting){
		    alertDialogBuilder.setPositiveButton(getResources().getString(R.string.download), new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO: implement the download feature
					Toast.makeText(instance, "not implemented", Toast.LENGTH_LONG).show();
					finish();
				}
			}).setNegativeButton(getResources().getString(R.string.quit), new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int whichButton) {
					finish();
				}
			});
		} else {
		    alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), null);
		}
		alertDialogBuilder.setCancelable(false);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
		return false;
	}
	
	/*private class AppLauncherView extends LinearLayout implements Button.OnClickListener, DialogInterface.OnClickListener, AdapterView.OnItemClickListener
	{
		private MainActivity mActivity;
		
		private LinearLayout mCurDirLayout;
		private TextView mCurDirText;
		private Button mCurDirChgButton;
		private ListView mDirListView;
		
		private File [] mDirFileArray;
		
		public AppLauncherView(MainActivity activity)
		{
			super(activity);
			mActivity = activity;
			
			setOrientation(LinearLayout.VERTICAL);
			{
				mCurDirLayout = new LinearLayout(mActivity);
				{
					LinearLayout txtLayout = new LinearLayout(mActivity);
					txtLayout.setOrientation(LinearLayout.VERTICAL);
					{
						TextView txt1 = new TextView(mActivity);
						txt1.setTextSize(18.0f);
						txt1.setText(getResources().getString(R.string.current_dir));
						txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						
						mCurDirText = new TextView(mActivity);
						mCurDirText.setPadding(5, 0, 0, 0);
						txtLayout.addView(mCurDirText, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					}
					mCurDirLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
					
					mCurDirChgButton = new Button(mActivity);
					mCurDirChgButton.setText(getResources().getString(R.string.change_dir));
					mCurDirChgButton.setOnClickListener(this);
					mCurDirLayout.addView(mCurDirChgButton, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
				}
				addView(mCurDirLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

				mDirListView = new ListView(mActivity);
				mDirListView.setOnItemClickListener(this);
				addView(mDirListView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
			}
			
			loadCurrentDirectory();
		}
		
		public void loadCurrentDirectory()
		{
			if(Globals.CurrentDirectoryPathForLauncher == null || Globals.CurrentDirectoryPathForLauncher.equals("")){
				mCurDirText.setText("");
			} else {
				mCurDirText.setText(Globals.CurrentDirectoryPathForLauncher);

				try {
					File searchDirFile = new File(Globals.CurrentDirectoryPathForLauncher);
				
					mDirFileArray = searchDirFile.listFiles(new FileFilter() {
						public boolean accept(File file) {
							return (!file.isHidden() && file.isDirectory() && file.canRead() && (!Globals.CURRENT_DIRECTORY_NEED_WRITABLE || file.canWrite()));
						}
					});
					
					Arrays.sort(mDirFileArray, new Comparator<File>(){
						public int compare(File src, File target){
							return src.getName().compareTo(target.getName());
						}
					});
				
					String[] dirPathArray = new String[mDirFileArray.length];
					for(int i = 0; i < mDirFileArray.length; i ++){
						dirPathArray[i] = mDirFileArray[i].getName();
					}

					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, dirPathArray);
					mDirListView.setAdapter(arrayAdapter);
				} catch(Exception e){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
					alertDialogBuilder.setTitle(getResources().getString(R.string.error));
					alertDialogBuilder.setMessage(getResources().getString(R.string.open_dir_error) + "\n" + Globals.CurrentDirectoryPathForLauncher);
					alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), null);
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, new String[0]);
					mDirListView.setAdapter(arrayAdapter);
				}
			}
		}
		
		public void onClick(View v)
		{
		}
		
		public void onClick(DialogInterface dialog, int which)
		{
		}
		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
		}
	}
	
	public void runAppLauncher()
	{
		checkCurrentDirectory(false);
		AppLauncherView view = new AppLauncherView(this);
		setContentView(view);
	}*/
	
	private class AppLaunchConfigView extends LinearLayout
	{
		MainActivity mActivity;

		ScrollView mConfView;
		LinearLayout mConfLayout;

		//TextView mExecuteModuleText;
		TextView mVideoDepthText;
		TextView mScreenRatioText;
		TextView mScreenOrientationText;
		
		TextView[] mEnvironmentTextArray;
		Button[]   mEnvironmentButtonArray;
		
		Button mRunButton;
		
		public AppLaunchConfigView(MainActivity activity)
		{
			super(activity);
			mActivity = activity;
			
			setOrientation(LinearLayout.VERTICAL);
			{
				mConfView = new ScrollView(mActivity);
				{
					mConfLayout = new LinearLayout(mActivity);
					mConfLayout.setOrientation(LinearLayout.VERTICAL);
					{
						//Video Depth
						LinearLayout videoDepthLayout = new LinearLayout(mActivity);
						{
							LinearLayout txtLayout = new LinearLayout(mActivity);
							txtLayout.setOrientation(LinearLayout.VERTICAL);
							{
								TextView txt1 = new TextView(mActivity);
								txt1.setTextSize(18.0f);
								txt1.setText(getResources().getString(R.string.video_depth));
								txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
								
								mVideoDepthText = new TextView(mActivity);
								mVideoDepthText.setPadding(5, 0, 0, 0);
								mVideoDepthText.setText("" + Locals.VideoDepthBpp + "bpp");
								txtLayout.addView(mVideoDepthText, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							}
							videoDepthLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
							
							if(Globals.VIDEO_DEPTH_BPP_ITEMS.length >= 2){
								Button btn = new Button(mActivity);
								btn.setText(getResources().getString(R.string.change));
								btn.setOnClickListener(new OnClickListener(){
									public void onClick(View v){
										String[] bppItems = new String[Globals.VIDEO_DEPTH_BPP_ITEMS.length];
										for(int i = 0; i < bppItems.length; i ++){
											bppItems[i] = "" + Globals.VIDEO_DEPTH_BPP_ITEMS[i] + "bpp";
										}
										
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
										alertDialogBuilder.setTitle(getResources().getString(R.string.video_depth));
										alertDialogBuilder.setItems(bppItems, new DialogInterface.OnClickListener(){
											public void onClick(DialogInterface dialog, int which)
											{
												Locals.VideoDepthBpp = Globals.VIDEO_DEPTH_BPP_ITEMS[which];
												mVideoDepthText.setText("" + Locals.VideoDepthBpp + "bpp");
												Settings.SaveLocals(mActivity);
											}
										});
										alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);
										alertDialogBuilder.setCancelable(true);
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
									}
								});
								videoDepthLayout.addView(btn, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							}
						}
						mConfLayout.addView(videoDepthLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						
						//Screen Ratio
						LinearLayout screenRatioLayout = new LinearLayout(mActivity);
						{
							LinearLayout txtLayout = new LinearLayout(mActivity);
							txtLayout.setOrientation(LinearLayout.VERTICAL);
							{
								TextView txt1 = new TextView(mActivity);
								txt1.setTextSize(18.0f);
								txt1.setText(getResources().getString(R.string.aspect_ratio));
								txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
								
								mScreenRatioText = new TextView(mActivity);
								mScreenRatioText.setPadding(5, 0, 0, 0);
								if(Locals.VideoXRatio > 0 && Locals.VideoYRatio > 0){
									mScreenRatioText.setText("" + Locals.VideoXRatio + ":" + Locals.VideoYRatio);
								} else {
									mScreenRatioText.setText(getResources().getString(R.string.full));
								}
								txtLayout.addView(mScreenRatioText, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							}
							screenRatioLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
							
							Button btn1 = new Button(mActivity);
							btn1.setText(getResources().getString(R.string.swap));
							btn1.setOnClickListener(new OnClickListener(){
								public void onClick(View v){
									int tmp = Locals.VideoXRatio;
									Locals.VideoXRatio = Locals.VideoYRatio;
									Locals.VideoYRatio = tmp;
									if(Locals.VideoXRatio > 0 && Locals.VideoYRatio > 0){
										mScreenRatioText.setText("" + Locals.VideoXRatio + ":" + Locals.VideoYRatio);
									} else {
										mScreenRatioText.setText(getResources().getString(R.string.full));
									}
									Settings.SaveLocals(mActivity);
								}
							});
							screenRatioLayout.addView(btn1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							
							if(Globals.VIDEO_RATIO_ITEMS.length >= 2){
								Button btn = new Button(mActivity);
								btn.setText(getResources().getString(R.string.change));
								btn.setOnClickListener(new OnClickListener(){
									public void onClick(View v){
										String[] ratioItems = new String[Globals.VIDEO_RATIO_ITEMS.length];
										for(int i = 0; i < ratioItems.length; i ++){
											int w = Globals.VIDEO_RATIO_ITEMS[i][0];
											int h = Globals.VIDEO_RATIO_ITEMS[i][1];
											if(w > 0 && h > 0){
												ratioItems[i] = "" + w + ":" + h;
											} else {
												ratioItems[i] = getResources().getString(R.string.full);
											}
										}
										
										AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
										alertDialogBuilder.setTitle(getResources().getString(R.string.screen_ratio));
										alertDialogBuilder.setItems(ratioItems, new DialogInterface.OnClickListener(){
											public void onClick(DialogInterface dialog, int which)
											{
												Locals.VideoXRatio = Globals.VIDEO_RATIO_ITEMS[which][0];
												Locals.VideoYRatio = Globals.VIDEO_RATIO_ITEMS[which][1];
												if(Locals.VideoXRatio > 0 && Locals.VideoYRatio > 0){
													mScreenRatioText.setText("" + Locals.VideoXRatio + ":" + Locals.VideoYRatio);
												} else {
													mScreenRatioText.setText(getResources().getString(R.string.full));
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
								screenRatioLayout.addView(btn, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							}
						}
						mConfLayout.addView(screenRatioLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

						//Screen Orientation
						LinearLayout screenOrientationLayout = new LinearLayout(mActivity);
						{
							LinearLayout txtLayout = new LinearLayout(mActivity);
							txtLayout.setOrientation(LinearLayout.VERTICAL);
							{
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
												
						//Smooth Video
						LinearLayout videoSmoothLayout = new LinearLayout(mActivity);
						{
							CheckBox chk = new CheckBox(mActivity);
							chk.setChecked(Locals.VideoSmooth);
							chk.setOnClickListener(new OnClickListener(){
								public void onClick(View v){
									CheckBox c = (CheckBox)v;
									Locals.VideoSmooth = c.isChecked();
									Settings.SaveLocals(mActivity);
								}
							});
							videoSmoothLayout.addView(chk, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							
							LinearLayout txtLayout = new LinearLayout(mActivity);
							txtLayout.setOrientation(LinearLayout.VERTICAL);
							{
								TextView txt1 = new TextView(mActivity);
								txt1.setTextSize(18.0f);
								txt1.setText(getResources().getString(R.string.smooth_video));
								txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
								
								TextView txt2 = new TextView(mActivity);
								txt2.setPadding(5, 0, 0, 0);
								txt2.setText(getResources().getString(R.string.linear_filtering));
								txtLayout.addView(txt2, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
							}
							videoSmoothLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
						}
						mConfLayout.addView(videoSmoothLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

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
							mConfLayout.addView(envLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						}
					}
					mConfView.addView(mConfLayout);
				}
				addView(mConfView, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1) );
				
				View divider = new View(mActivity);
				divider.setBackgroundColor(Color.GRAY);
				addView(divider, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2) );
				
				LinearLayout runLayout = new LinearLayout(mActivity);
				
				//don't ask me again
				LinearLayout askLayout = new LinearLayout(mActivity);
				{
					CheckBox chk = new CheckBox(mActivity);
					chk.setChecked(!Locals.AppLaunchConfigUse);
					chk.setOnClickListener(new OnClickListener(){
						public void onClick(View v){
							CheckBox c = (CheckBox)v;
							Locals.AppLaunchConfigUse = !c.isChecked();
							Settings.SaveLocals(mActivity);
						}
					});
					askLayout.addView(chk, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					
					LinearLayout txtLayout = new LinearLayout(mActivity);
					txtLayout.setOrientation(LinearLayout.VERTICAL);
					{
						TextView txt1 = new TextView(mActivity);
						txt1.setTextSize(16.0f);
						txt1.setText(getResources().getString(R.string.dont_ask_again));
						txtLayout.addView(txt1, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						
						TextView txt2 = new TextView(mActivity);
						txt2.setPadding(5, 0, 0, 0);
						txt2.setText(getResources().getString(R.string.no_applaunchconfig));
						txtLayout.addView(txt2, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					}
					askLayout.addView(txtLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
				}
				runLayout.addView(askLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
				
				mRunButton = new Button(mActivity);
				mRunButton.setText(" " + getResources().getString(R.string.run) + " ");
				mRunButton.setTextSize(24.0f);
				mRunButton.setOnClickListener(new OnClickListener(){
					public void onClick(View v){
						runApp();
					}
				});
				runLayout.addView(mRunButton, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
				
				addView(runLayout, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
			}
		}
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return true;
	}
	
    @Override
	public boolean onPrepareOptionsMenu( Menu menu )
	{
		if(mView != null){
			return mView.onPrepareOptionsMenu(menu);
		}
		return true;
	}
	
    @Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
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
	protected void onDestroy() 
	{
		if( mView != null ){
			mView.exitApp();
			try {
				wait(3000);
			} catch(InterruptedException e){}
		}
		super.onDestroy();
		System.exit(0);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Do nothing here
	}
}

