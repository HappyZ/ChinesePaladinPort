/*
 * 2012/7 Created by AKIZUKI Katane
 * 2014/4 Modified by HappyZ
 */

package org.happyz.chinesepaladin;

import android.content.Context;
//import android.view.MotionEvent;

import java.lang.String;

import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

class TouchMode implements DifferentTouchInput.OnInputEventListener {
	public static TouchMode getTouchMode(String name, MainView mainView) {
//		if(name.equals("Touch")) return new TouchTouchMode(mainView);
//		if(name.equals("TrackPad")) return new TrackPadTouchMode(mainView);
		if(name.equals("GamePad")) return new GamePadTouchMode(mainView);
		return new InvalidTouchMode(mainView);
	}
	
//	private class OnButtonTouchListener implements View.OnTouchListener {
//		//private MainView mMainView;
//		private int mKey;
//		
//		public OnButtonTouchListener(MainView mainView, int key) {
//			//mMainView = mainView;
//			mKey = key;
//		}
//		
//		public boolean onTouch(View v, MotionEvent event){
//			v.onTouchEvent(event);
//			switch(event.getAction()){
//				case MotionEvent.ACTION_DOWN:
//					mMainView.nativeKey( mKey, 1 );
//					break;
//				case MotionEvent.ACTION_UP:
//					mMainView.nativeKey( mKey, 0 );
//					break;
//			}
//			return true;
//		}
//	}
	
//	private Button newButtonToLayout(LinearLayout layout, int key) {
//		Button button = new Button(mMainView.getActivity());
//		button.setOnTouchListener(new OnButtonTouchListener(mMainView, key));
//		layout.addView(button, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//		return button;
//	}
	
	public TouchMode(MainView mainView) {
		mMainView = mainView;
		mLayoutLeft = new LinearLayout(mMainView.getActivity());
		mLayoutLeft.setOrientation(LinearLayout.VERTICAL);
		mButtonLeftArray = new Button[Globals.BUTTON_LEFT_MAX];
//		int len = mButtonLeftArray.length;
//		for(int i = 0; i < len; i++){
//			mButtonLeftArray[i] = newButtonToLayout(mLayoutLeft, Globals.BUTTON_LEFT_KEY_ARRAY[i]);
//		}
		
		mLayoutRight = new LinearLayout(mMainView.getActivity());
		mLayoutRight.setOrientation(LinearLayout.VERTICAL);
		mButtonRightArray = new Button[Globals.BUTTON_RIGHT_MAX];
//		len = mButtonRightArray.length;
//		for(int i = 0; i < len; i++){
//			mButtonRightArray[i] = newButtonToLayout(mLayoutRight, Globals.BUTTON_RIGHT_KEY_ARRAY[i]);
//		}
		
		mLayoutTop = new LinearLayout(mMainView.getActivity());
		mLayoutTop.setOrientation(LinearLayout.HORIZONTAL);
		mButtonTopArray = new Button[Globals.BUTTON_BOTTOM_MAX];
//		for(int i = 0; i < mButtonTopArray.length; i ++){
//			mButtonTopArray[i] = newButtonToLayout(mLayoutTop, Globals.BUTTON_TOP_KEY_ARRAY[i]);
//		}
		
		mLayoutBottom = new LinearLayout(mMainView.getActivity());
		mLayoutBottom.setOrientation(LinearLayout.HORIZONTAL);
		mButtonBottomArray = new Button[Globals.BUTTON_BOTTOM_MAX];
//		for(int i = 0; i < mButtonBottomArray.length; i ++){
//			mButtonBottomArray[i] = newButtonToLayout(mLayoutBottom, Globals.BUTTON_BOTTOM_KEY_ARRAY[i]);
//		}
	}
	
	protected MainView getMainView() {
		return mMainView;
	}
	
	protected void setXMargin(int margin) {
		mXMargin = margin;
	}
	
	protected void setYMargin(int margin) {
		mYMargin = margin;
	}
	
	protected int getScreenX() {
		return mScreenX;
	}
	
	protected int getScreenY() {
		return mScreenY;
	}
	
	protected int getScreenWidth() {
		return mScreenWidth;
	}
	
	protected int getScreenHeight() {
		return mScreenHeight;
	}
	
	void setup() {
		mMainView.setMouseCursorRGB(0, 0, 0, 255, 255, 255);

		mMainView.addView(mLayoutLeft);
		mMainView.addView(mLayoutRight);
		mMainView.addView(mLayoutTop);
		mMainView.addView(mLayoutBottom);
	}
	
	void cleanup() {
		mMainView.removeView(mLayoutLeft);
		mMainView.removeView(mLayoutRight);
		mMainView.removeView(mLayoutTop);
		mMainView.removeView(mLayoutBottom);
		
		mMainView.setMouseCursorRGB(0, 0, 0, 255, 255, 255);
	}
	
	@SuppressWarnings("unused")
	void update() {
		int i;
		
		int xnum = (Globals.BUTTON_USE && Globals.ButtonLeftEnabled ? 1 : 0) + (Globals.BUTTON_USE && Globals.ButtonRightEnabled ? 1 : 0) + mXMargin + Locals.VideoXMargin;
		int wsum = mMainView.getDisplayWidth() - mMainView.getGLViewWidth();
		int w1 = (xnum > 0 ? (wsum / xnum) : 0);
		int lw = (Globals.BUTTON_USE && Globals.ButtonLeftEnabled ? w1 : 0);
		int rw = (Globals.BUTTON_USE && Globals.ButtonRightEnabled ? w1 : 0);
		
		int ynum = (Globals.BUTTON_USE && Globals.ButtonTopEnabled ? 1 : 0) + (Globals.BUTTON_USE && Globals.ButtonBottomEnabled ? 1 : 0) + mYMargin + Locals.VideoYMargin;
		int hsum = mMainView.getDisplayHeight() - mMainView.getGLViewHeight();
		int h1 = (ynum > 0 ? (hsum / ynum) : 0);
		int th = (Globals.BUTTON_USE && Globals.ButtonTopEnabled ? h1 : 0);
		int bh = (Globals.BUTTON_USE && Globals.ButtonBottomEnabled ? h1 : 0);
		
		mScreenX = lw;
		mScreenY = th;
		mScreenWidth  = mMainView.getDisplayWidth()  - (lw + rw);
		mScreenHeight = mMainView.getDisplayHeight() - (th + bh);
		
		int lx = 0;
		int ly = 0;
		int lh = mMainView.getDisplayHeight();
		if(lw > 0 && lh > 0){
			for(i = 0; i < Globals.ButtonLeftNum && i < mButtonLeftArray.length; i ++){
				mButtonLeftArray[i].setVisibility(View.VISIBLE);
				
				String name = null;
				Integer sdlKey = (Integer)Globals.SDLKeyAdditionalKeyMap.get(Integer.valueOf(Globals.BUTTON_LEFT_KEY_ARRAY[i]));
				if(sdlKey != null){
					name = Globals.SDLKeyFunctionNameMap.get(sdlKey);
				}
				mButtonLeftArray[i].setText(name);
			}
			for(; i < mButtonRightArray.length; i ++){
				mButtonLeftArray[i].setVisibility(View.GONE);
			}
			mLayoutLeft.setVisibility(View.VISIBLE);
			LayoutParams params = new LayoutParams(lw, lh);
			params.setMargins(lx,ly,0,0);
			mLayoutLeft.setLayoutParams(new RelativeLayout.LayoutParams(params));
		} else {
			mLayoutLeft.setVisibility(View.GONE);
		}
		
		int rx = mMainView.getDisplayWidth() - rw;
		int ry = 0;
		int rh = mMainView.getDisplayHeight();
		if(rw > 0 && rh > 0){
			for(i = 0; i < Globals.ButtonRightNum && i < mButtonRightArray.length; i ++){
				mButtonRightArray[i].setVisibility(View.VISIBLE);
				
				String name = null;
				Integer sdlKey = Globals.SDLKeyAdditionalKeyMap.get(Integer.valueOf(Globals.BUTTON_RIGHT_KEY_ARRAY[i]));
				if(sdlKey != null){
					name = Globals.SDLKeyFunctionNameMap.get(sdlKey);
				}
				mButtonRightArray[i].setText(name);
			}
			for(; i < mButtonRightArray.length; i ++){
				mButtonRightArray[i].setVisibility(View.GONE);
			}
			mLayoutRight.setVisibility(View.VISIBLE);
			LayoutParams params = new LayoutParams(rw, rh);
			params.setMargins(rx,ry,0,0);
			mLayoutRight.setLayoutParams(new RelativeLayout.LayoutParams(params));
		} else {
			mLayoutRight.setVisibility(View.GONE);
		}
		
		int tx = mScreenX;
		int ty = 0;
		int tw = mScreenWidth;
		if(tw > 0 && th > 0){
			for(i = 0; i < Globals.ButtonTopNum && i < mButtonTopArray.length; i ++){
				mButtonTopArray[i].setVisibility(View.VISIBLE);
				
				String name = null;
				Integer sdlKey = (Integer)Globals.SDLKeyAdditionalKeyMap.get(Integer.valueOf(Globals.BUTTON_TOP_KEY_ARRAY[i]));
				if(sdlKey != null){
					name = Globals.SDLKeyFunctionNameMap.get(sdlKey);
				}
				mButtonTopArray[i].setText(name);
			}
			for(; i < mButtonTopArray.length; i ++){
				mButtonTopArray[i].setVisibility(View.GONE);
			}
			mLayoutTop.setVisibility(View.VISIBLE);
			LayoutParams params = new LayoutParams(tw, th);
			params.setMargins(tx,ty,0,0);
			mLayoutTop.setLayoutParams(new RelativeLayout.LayoutParams(params));
		} else {
			mLayoutTop.setVisibility(View.GONE);
		}
		
		int bx = mScreenX;
		int by = mMainView.getDisplayHeight() - bh;
		int bw = mScreenWidth;
		if(bw > 0 && bh > 0){
			for(i = 0; i < Globals.ButtonBottomNum && i < mButtonBottomArray.length; i ++){
				mButtonBottomArray[i].setVisibility(View.VISIBLE);
				
				String name = null;
				Integer sdlKey = (Integer)Globals.SDLKeyAdditionalKeyMap.get(Integer.valueOf(Globals.BUTTON_BOTTOM_KEY_ARRAY[i]));
				if(sdlKey != null){
					name = Globals.SDLKeyFunctionNameMap.get(sdlKey);
				}
				mButtonBottomArray[i].setText(name);
			}
			for(; i < mButtonBottomArray.length; i ++){
				mButtonBottomArray[i].setVisibility(View.GONE);
			}
			mLayoutBottom.setVisibility(View.VISIBLE);
			LayoutParams params = new LayoutParams(bw, bh);
			params.setMargins(bx,by,0,0);
			mLayoutBottom.setLayoutParams(new RelativeLayout.LayoutParams(params));
		} else {
			mLayoutBottom.setVisibility(View.GONE);
		}
		
		int x;
		int y;
		
		if(Locals.VideoXPosition == 0){
			x = mScreenX + ((mScreenWidth - getMainView().getGLViewWidth())  / 2);
		} else if(Locals.VideoXPosition < 0){
			x = mScreenX;
		} else {
			x = mScreenX + (mScreenWidth - getMainView().getGLViewWidth());
		}
		if(Locals.VideoYPosition == 0){
			y = mScreenY + ((mScreenHeight - getMainView().getGLViewHeight())  / 2);
		} else if(Locals.VideoYPosition < 0){
			y = mScreenY;
		} else {
			y = mScreenY + (mScreenHeight - getMainView().getGLViewHeight());
		}
		
		getMainView().setGLViewPos(x, y);
	}
	
	public void onKeyEvent(int keyCode, int pressed){}
	
	public void onMotionEvent(int x, int y, int action, int pointerId, int pressure, int radius, int touchCount){}
	
	public void onMouseButtonEvent(int buttonId, int pressed){}
	
	// Declarations
	private LinearLayout mLayoutLeft = null;
	private Button[] mButtonLeftArray = null;
	
	private LinearLayout mLayoutRight = null;
	private Button[] mButtonRightArray = null;
	
	private LinearLayout mLayoutTop = null;
	private Button[] mButtonTopArray = null;
	
	private LinearLayout mLayoutBottom = null;
	private Button[] mButtonBottomArray = null;
	
	private int mXMargin = 0;
	private int mYMargin = 0;
	
	private int mScreenX = 0;
	private int mScreenY = 0;
	private int mScreenWidth  = 0;
	private int mScreenHeight = 0;

	private MainView mMainView = null;
}

class GamePadTouchMode extends TouchMode {
	class ArrowButton extends View {
		public ArrowButton(Context c) {
			super(c);
			mPath = new Path();
			mTouchPath = new Path();
			mPaint = new Paint();
			setBackgroundColor(Color.TRANSPARENT);
		}
		
		@Override
		protected void onSizeChanged (int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			
			int x = w / 2;
			int y = h / 2;
			int r = w / 2 - 2;
			
			mPath.reset();
			
			mPath.addCircle( x, y, r, Path.Direction.CW );
			
			mPath.moveTo(x-(r/6), y-(r/2));
			mPath.lineTo(x, y-(r*3/4));
			mPath.lineTo(x+(r/6), y-(r/2));
			//mPath.close();
			
			mPath.moveTo(x-(r/6), y+(r/2));
			mPath.lineTo(x, y+(r*3/4));
			mPath.lineTo(x+(r/6), y+(r/2));
			//mPath.close();
			
			mPath.moveTo(x-(r/2), y-(r/6));
			mPath.lineTo(x-(r*3/4), y);
			mPath.lineTo(x-(r/2), y+(r/6));
			//mPath.close();
			
			mPath.moveTo(x+(r/2), y-(r/6));
			mPath.lineTo(x+(r*3/4), y);
			mPath.lineTo(x+(r/2), y+(r/6));
			//mPath.close();
			
			setButtonState(mButtonState);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			int a = Globals.GamePadOpacity * 255 / 100;
			
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setARGB(a, 243, 152, 0);
			canvas.drawPath(mTouchPath, mPaint);
		
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(4);
			mPaint.setARGB(a, 0, 0, 0);
			canvas.drawPath(mPath, mPaint);
	
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(2);
			mPaint.setARGB(a, 255, 255, 255);
			canvas.drawPath(mPath, mPaint);
		}
		
		public int getButtonState() {
			return mButtonState;
		}
		
		public void setButtonState(int buttonState) {
			mButtonState = buttonState;
			
			int x = getWidth() / 2;
			int y = getHeight() / 2;
			int r = getWidth() / 2 - 2;
			
			mTouchPath.reset();
			
			if((buttonState & BUTTON_UP) != 0){
				mTouchPath.addCircle( x, y-(r*5/8), (r*3/8), Path.Direction.CW );
			}
			if((buttonState & BUTTON_DOWN) != 0){
				mTouchPath.addCircle( x, y+(r*5/8), (r*3/8), Path.Direction.CW );
			}
			if((buttonState & BUTTON_LEFT) != 0){
				mTouchPath.addCircle( x-(r*5/8), y, (r*3/8), Path.Direction.CW );
			}
			if((buttonState & BUTTON_RIGHT) != 0){
				mTouchPath.addCircle( x+(r*5/8), y, (r*3/8), Path.Direction.CW );
			}
			
			//Log.i("setButtonState", "buttonState:" + buttonState);
			
			invalidate();
		}

		public final static int BUTTON_NONE  = 0;
		public final static int BUTTON_UP    = 1 << Globals.GAMEPAD_BUTTON_ARROW_UP_INDEX;
		public final static int BUTTON_RIGHT = 1 << Globals.GAMEPAD_BUTTON_ARROW_RIGHT_INDEX;
		public final static int BUTTON_DOWN  = 1 << Globals.GAMEPAD_BUTTON_ARROW_DOWN_INDEX;
		public final static int BUTTON_LEFT  = 1 << Globals.GAMEPAD_BUTTON_ARROW_LEFT_INDEX;
		
		private Path mPath;
		private Path mTouchPath;
		private Paint mPaint;
		
		private int mButtonState = 0;
	}
	
	class ActionButton extends View {
		public ActionButton(Context c)
		{
			super(c);
			
			mPath = new Path();
			mTouchPath = new Path();
			
			mPaint = new Paint();
			setBackgroundColor(Color.TRANSPARENT);
		}
		
		@Override
		protected void onSizeChanged (int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			
			int x = w / 2;
			int y = h / 2;
			int r = w / 2 - 2;
			
			mPath.reset();
			
			mPath.addCircle( x, y, r, Path.Direction.CW );
			
			mPath.addCircle( x, y-(r*5/8), (r*3/8), Path.Direction.CW );
			mPath.addCircle( x, y+(r*5/8), (r*3/8), Path.Direction.CW );
			mPath.addCircle( x-(r*5/8), y, (r*3/8), Path.Direction.CW );
			mPath.addCircle( x+(r*5/8), y, (r*3/8), Path.Direction.CW );
			
			setButtonState(mButtonState);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			int a = Globals.GamePadOpacity * 255 / 100;
			
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setARGB(a, 243, 152, 0);
			//mPaint.setARGB(80, 100, 100, 100);
			canvas.drawPath(mTouchPath, mPaint);
			
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(4);
			mPaint.setARGB(a, 0, 0, 0);
			canvas.drawPath(mPath, mPaint);
			
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(2);
			mPaint.setARGB(a, 255, 255, 255);
			canvas.drawPath(mPath, mPaint);
		}
		
		public int getButtonState() {
			return mButtonState;
		}
		
		public void setButtonState(int buttonState) {
			mButtonState = buttonState;
			
			int x = getWidth() / 2;
			int y = getHeight() / 2;
			int r = getWidth() / 2 - 2;
			
			mTouchPath.reset();
			
			if((buttonState & BUTTON_UP) != 0){
				mTouchPath.addCircle( x, y-(r*5/8), (r*3/8), Path.Direction.CW );
			}
			if((buttonState & BUTTON_DOWN) != 0){
				mTouchPath.addCircle( x, y+(r*5/8), (r*3/8), Path.Direction.CW );
			}
			if((buttonState & BUTTON_LEFT) != 0){
				mTouchPath.addCircle( x-(r*5/8), y, (r*3/8), Path.Direction.CW );
			}
			if((buttonState & BUTTON_RIGHT) != 0){
				mTouchPath.addCircle( x+(r*5/8), y, (r*3/8), Path.Direction.CW );
			}
			
			//Log.i("setButtonState", "buttonState:" + buttonState);
			
			invalidate();
		}
		
		public final static int BUTTON_NONE  = 0;
		public final static int BUTTON_UP    = 1 << Globals.GAMEPAD_BUTTON_ACTION_UP_INDEX;
		public final static int BUTTON_RIGHT = 1 << Globals.GAMEPAD_BUTTON_ACTION_RIGHT_INDEX;
		public final static int BUTTON_DOWN  = 1 << Globals.GAMEPAD_BUTTON_ACTION_DOWN_INDEX;
		public final static int BUTTON_LEFT  = 1 << Globals.GAMEPAD_BUTTON_ACTION_LEFT_INDEX;
		
		private Path mPath;
		private Path mTouchPath;
		private Paint mPaint;
		
		private int mButtonState = 0;
	}
	
	public GamePadTouchMode(MainView mainView) {
		super(mainView);
		
		mArrowButton  = new ArrowButton(mainView.getActivity());
		mActionButton = new ActionButton(mainView.getActivity());
		
		mActionButtonPointerIdAry = new int[Globals.GAMEPAD_BUTTON_ACTION_NUM];
		for(int i = 0; i < mActionButtonPointerIdAry.length; i ++){
			mActionButtonPointerIdAry[i] = -1;
		}
	}
	
	@Override
	void setup() {
		super.setup();
		
		mIsMouseShowed = getMainView().isMouseCursorShowed();
		getMainView().showMouseCursor(false);

//		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		getMainView().addView(mArrowButton);
		getMainView().addView(mActionButton);
	}
	
	@Override
	void cleanup() {
		getMainView().removeView(mArrowButton);
		getMainView().removeView(mActionButton);
		
		if(!getMainView().isMouseCursorShowed()){
			getMainView().showMouseCursor(mIsMouseShowed);
		}
		
		super.cleanup();
	}
	
	@Override
	void update() {
		super.update();
		
		int buttonSize = (getScreenWidth() / 2) * Globals.GamePadSize / 100;
		
		mArrowButtonWidth  = mArrowButtonHeight  = buttonSize;
		mActionButtonWidth = mActionButtonHeight = buttonSize;
		
		mArrowButtonX = getScreenX();
		mArrowButtonY = getScreenY() + ((getScreenHeight() - mArrowButtonHeight) * Globals.GamePadPosition / 100);

		LayoutParams params = new LayoutParams(mArrowButtonWidth, mArrowButtonHeight);
		params.setMargins(mArrowButtonX,mArrowButtonY,0,0);
//		mArrowButton.setPadding(mArrowButtonX,mArrowButtonY,0,0);
		mArrowButton.setLayoutParams(new RelativeLayout.LayoutParams(params));
		
		mActionButtonX = getScreenX() + getScreenWidth() - mActionButtonWidth;
		mActionButtonY = getScreenY() + ((getScreenHeight() - mActionButtonHeight) * Globals.GamePadPosition / 100);

		params = new LayoutParams(mActionButtonWidth, mActionButtonHeight);
		params.setMargins(mActionButtonX, mActionButtonY, 0, 0);
//		mActionButton.setPadding(mArrowButtonX,mArrowButtonY,0,0);
		mActionButton.setLayoutParams(new RelativeLayout.LayoutParams(params));
		
		mArrowButton.invalidate();
		mActionButton.invalidate();
		
		//Log.i("TouchMode", "Screen: x=" + getScreenX() + " y=" + getScreenY());
		//Log.i("TouchMode", "ArrowButton: x=" + mArrowButtonX + " y=" + mArrowButtonY);
	}
	
	@Override
	public void onKeyEvent(int keyCode, int pressed) {
		getMainView().nativeKey( keyCode, pressed );
	}
	
	@Override
	public void onMotionEvent(int x, int y, int action, int pointerId, int pressure, int radius, int touchCount) {
		//Log.i("onMotionEvent", "action:" + action + " x=" + x + " y=" + y);
		
		int arrowButtonState  = mArrowButtonState;
		
		if(action == Mouse.SDL_FINGER_DOWN){
			if(x >= mArrowButtonX && x < mArrowButtonX + mArrowButtonWidth /*&& y >= mArrowButtonY && y < mArrowButtonY + mArrowButtonHeight*/){
				if(mArrowButtonPointerId < 0){
					mArrowButtonPointerId = pointerId;
					
					int ax = x - mArrowButtonX;
					int ay = y - mArrowButtonY;
					
					if(Globals.GamePadArrowButtonAsAxis){
						arrowButtonState = 0;
						if(ax < mArrowButtonWidth * 1 / 3){
							arrowButtonState |= ActionButton.BUTTON_LEFT;
						} else if(ax >= mArrowButtonWidth * 2 / 3){
							arrowButtonState |= ActionButton.BUTTON_RIGHT;
						}
						if(ay < mArrowButtonHeight * 1 / 3){
							arrowButtonState |= ActionButton.BUTTON_UP;
						} else if(ay >= mArrowButtonHeight * 2 / 3){
							arrowButtonState |= ActionButton.BUTTON_DOWN;
						}
					} else {
						if((ax + ay) < mArrowButtonWidth){
							if(ax <= ay){
								arrowButtonState = ActionButton.BUTTON_LEFT;
							} else {
								arrowButtonState = ActionButton.BUTTON_UP;
							}
						} else {
							if(ax >= ay){
								arrowButtonState = ActionButton.BUTTON_RIGHT;
							} else {
								arrowButtonState = ActionButton.BUTTON_DOWN;
							}
						}
					}
				}
			} else if(x >= mActionButtonX && x < mActionButtonX + mActionButtonWidth &&
					  y >= mActionButtonY && y < mActionButtonY + mActionButtonHeight){
				int ax = x - mActionButtonX;
				int ay = y - mActionButtonY;
				if((ax + ay) < mActionButtonWidth){
					if(ax <= ay){
						if(mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_LEFT_INDEX] < 0){
							mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_LEFT_INDEX] = pointerId;
							mActionButton.setButtonState(mActionButton.getButtonState() | ActionButton.BUTTON_LEFT);
							getMainView().nativeKey( Globals.GAMEPAD_BUTTON_ACTION_KEY_ARRAY[Globals.GAMEPAD_BUTTON_ACTION_LEFT_INDEX], 1 );
						}
					} else {
						if(mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_UP_INDEX] < 0){
							mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_UP_INDEX] = pointerId;
							mActionButton.setButtonState(mActionButton.getButtonState() | ActionButton.BUTTON_UP);
							getMainView().nativeKey( Globals.GAMEPAD_BUTTON_ACTION_KEY_ARRAY[Globals.GAMEPAD_BUTTON_ACTION_UP_INDEX], 1 );
						}
					}
				} else {
					if(ax >= ay){
						if(mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_RIGHT_INDEX] < 0){
							mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_RIGHT_INDEX] = pointerId;
							mActionButton.setButtonState(mActionButton.getButtonState() | ActionButton.BUTTON_RIGHT);
							getMainView().nativeKey( Globals.GAMEPAD_BUTTON_ACTION_KEY_ARRAY[Globals.GAMEPAD_BUTTON_ACTION_RIGHT_INDEX], 1 );
						}
					} else {
						if(mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_DOWN_INDEX] < 0){
							mActionButtonPointerIdAry[Globals.GAMEPAD_BUTTON_ACTION_DOWN_INDEX] = pointerId;
							mActionButton.setButtonState(mActionButton.getButtonState() | ActionButton.BUTTON_DOWN);
							getMainView().nativeKey( Globals.GAMEPAD_BUTTON_ACTION_KEY_ARRAY[Globals.GAMEPAD_BUTTON_ACTION_DOWN_INDEX], 1 );
						}
					}
				}
			}
		} else if(action == Mouse.SDL_FINGER_MOVE){
			if(pointerId == mArrowButtonPointerId && Globals.GamePadArrowButtonAsAxis){
				int ax = x - mArrowButtonX;
				int ay = y - mArrowButtonY;
				
				arrowButtonState = 0;
				if(ax < mArrowButtonWidth * 1 / 3){
					arrowButtonState |= ActionButton.BUTTON_LEFT;
				} else if(ax >= mArrowButtonWidth * 2 / 3){
					arrowButtonState |= ActionButton.BUTTON_RIGHT;
				}
				if(ay < mArrowButtonHeight * 1 / 3){
					arrowButtonState |= ActionButton.BUTTON_UP;
				} else if(ay >= mArrowButtonHeight * 2 / 3){
					arrowButtonState |= ActionButton.BUTTON_DOWN;
				}
			}
		} else if(action == Mouse.SDL_FINGER_UP){
			if(pointerId == mArrowButtonPointerId){
				arrowButtonState = ArrowButton.BUTTON_NONE;
				mArrowButtonPointerId = -1;
			}
			for(int i = 0; i < mActionButtonPointerIdAry.length; i ++){
				if(pointerId == mActionButtonPointerIdAry[i]){
					mActionButton.setButtonState(mActionButton.getButtonState() & (~(1 << i)));
					getMainView().nativeKey( Globals.GAMEPAD_BUTTON_ACTION_KEY_ARRAY[i], 0 );
					mActionButtonPointerIdAry[i] = -1;
				}
			}
		}
		
		if(arrowButtonState != mArrowButtonState){
			mArrowButton.setButtonState(arrowButtonState);
			
			int diff  = arrowButtonState ^ mArrowButtonState;
			int state = arrowButtonState;
			
			for(int i = 0; diff != 0 ; diff>>= 1, state>>= 1, i ++){
				if( (diff & 1) != 0 ){
					getMainView().nativeKey( Globals.GAMEPAD_BUTTON_ARROW_KEY_ARRAY[i], ((state & 1) != 0 ? 1 : 0) );
				}
			}
			
			mArrowButtonState = arrowButtonState;
			
			//Log.i("onMotionEvent", "arrowButtonState" + mArrowButtonState);
		}
	}
	
	@Override
	public void onMouseButtonEvent(int buttonId, int pressed) {}
	
	// Declarations
	private boolean mIsMouseShowed = true;
	
	private ArrowButton mArrowButton = null;
	private int mArrowButtonX = 0;
	private int mArrowButtonY = 0;
	private int mArrowButtonWidth  = 0;
	private int mArrowButtonHeight = 0;
	private int mArrowButtonPointerId = -1;
	private int mArrowButtonState = ArrowButton.BUTTON_NONE;
	
	private ActionButton mActionButton = null;
	private int mActionButtonX = 0;
	private int mActionButtonY = 0;
	private int mActionButtonWidth  = 0;
	private int mActionButtonHeight = 0;
	private int[] mActionButtonPointerIdAry = null;
}


//class TrackPadTouchMode extends TouchMode implements View.OnTouchListener {
//	private int mFirstPointerId = -1;
//	private int mFirstPointX = 0;
//	private int mFirstPointY = 0;
//	private int mFirstMousePointX = 0;
//	private int mFirstMousePointY = 0;
//	
//	private int mSecondPointerId = -1;
//	
//	private boolean mDirectTouchMode = false;
//	private boolean mDirectTouchDown = false;
//
//	private Button mRightClickButton = null;
//	private Button mLeftClickButton = null;
//	
//	public TrackPadTouchMode(MainView mainView) {
//		super(mainView);
//		
//		setXMargin(1);
//		
//		mRightClickButton = new Button(mainView.getActivity());
//		mRightClickButton.setText("R");
//		mRightClickButton.setOnTouchListener(this);
//		
//		mLeftClickButton = new Button(mainView.getActivity());
//		mLeftClickButton.setText("L");
//		mLeftClickButton.setOnTouchListener(this);
//	}
//	
//	public boolean onTouch(View v, MotionEvent event) {
//		if(v == mLeftClickButton || v == mRightClickButton){
//			v.onTouchEvent(event);
//			
//			int buttonId = (v == mLeftClickButton ? MotionEvent.BUTTON_PRIMARY : MotionEvent.BUTTON_SECONDARY);
//			switch(event.getAction()){
//				case MotionEvent.ACTION_DOWN:
//					getMainView().nativeMouseButtonsPressed( buttonId, 1 );
//					break;
//				case MotionEvent.ACTION_UP:
//					getMainView().nativeMouseButtonsPressed( buttonId, 0 );
//					break;
//				default:
//					break;
//			}
//		}
//		return true;
//	}
//	
//	@Override
//	void setup() {
//		super.setup();
//		
//		getMainView().setMouseCursorRGB(120, 120, 120, 255, 255, 255);
//		
//		getMainView().addView(mLeftClickButton);
//		getMainView().addView(mRightClickButton);
//	}
//	
//	@Override
//	void cleanup() {
//		getMainView().removeView(mLeftClickButton);
//		getMainView().removeView(mRightClickButton);
//		
//		super.cleanup();
//	}
//	
//	@Override
//	void update() {
//		super.update();
//		
//		int x;
//		int y;
//		
//		x = getScreenX();
//		if(Locals.VideoYPosition == 0){
//			y = getScreenY() + ((getScreenHeight() - getMainView().getGLViewHeight())  / 2);
//		} else if(Locals.VideoYPosition < 0){
//			y = getScreenY();
//		} else {
//			y = getScreenY() + (getScreenHeight() - getMainView().getGLViewHeight());
//		}
//		
//		getMainView().setGLViewPos(x, y);
//		
//		int rx, ry, rw, rh;
//		int lx, ly, lw, lh;
//		
//		if(getMainView().getGLViewWidth() + (getMainView().getGLViewWidth() / 18) < getScreenWidth()){
//			rx = getMainView().getGLViewX() + getMainView().getGLViewWidth();
//			ry = getScreenY();
//			rw = getScreenX() + getScreenWidth() - rx;
//			rh = (getScreenHeight() / 8) * 1;
//		} else {
//			rw = getMainView().getGLViewWidth() / 18;
//			rh = (getScreenHeight() / 8) * 1;
//			rx = getScreenX() + getScreenWidth() - rw;
//			ry = getScreenY();
//		}
//		
//		lx = rx;
//		ly = ry + rh;
//		lw = rw;
//		lh = (getScreenHeight() / 8) * 3;
//
//		LayoutParams params = new LayoutParams(rw, rh);
//		params.setMargins(rx,ry,0,0);
//		mRightClickButton.setLayoutParams(new RelativeLayout.LayoutParams(params));
//		params = new LayoutParams(lw, lh);
//		params.setMargins(lx,ly,0,0);
//		mLeftClickButton.setLayoutParams(new RelativeLayout.LayoutParams(params));
//	}
//
//	@Override
//	public void onKeyEvent(int keyCode, int pressed) {
//		getMainView().nativeKey( keyCode, pressed );
//	}
//	
//	@Override
//	public void onMotionEvent(int x, int y, int action, int pointerId, int pressure, int radius, int touchCount) {
//		if(action == Mouse.SDL_FINGER_DOWN){
//			if(mFirstPointerId < 0){
//				mFirstPointerId = pointerId;
//				mFirstPointX = x;
//				mFirstPointY = y;
//				mFirstMousePointX = getMainView().getMousePointX();
//				mFirstMousePointY = getMainView().getMousePointY();
//				if(mDirectTouchMode && x >= getMainView().getGLViewX() && x < getMainView().getGLViewX() + getMainView().getGLViewWidth() && y >= getMainView().getGLViewY() && y < getMainView().getGLViewY() + getMainView().getGLViewHeight()){
//					mDirectTouchDown = true;
//				}
//			} else if(mSecondPointerId < 0){
//				mSecondPointerId = pointerId;
//				onMouseButtonEvent( MotionEvent.BUTTON_PRIMARY, 1 );
//			}
//		}
//		
//		if(pointerId == mFirstPointerId){
//			if(mDirectTouchDown){
//				int wx = x - getMainView().getGLViewX();
//				int wy = y - getMainView().getGLViewY();
//				
//				getMainView().setMousePoint(wx, wy);
//				getMainView().nativeMotionEvent( wx, wy );
//				
//				switch(action){
//					case Mouse.SDL_FINGER_DOWN:
//						onMouseButtonEvent(MotionEvent.BUTTON_PRIMARY, 1);
//						break;
//					case Mouse.SDL_FINGER_UP:
//						onMouseButtonEvent(MotionEvent.BUTTON_PRIMARY, 0);
//						break;
//				}
//			} else {
//				int wx = mFirstMousePointX + (x - mFirstPointX);
//				int wy = mFirstMousePointY + (y - mFirstPointY);
//				if(wx < 0){
//					wx = 0;
//				} else if(wx >= getMainView().getGLViewWidth()){
//					wx = getMainView().getGLViewWidth() - 1;
//				}
//				if(wy < 0){
//					wy = 0;
//				} else if(wy >= getMainView().getGLViewHeight()){
//					wy = getMainView().getGLViewHeight() - 1;
//				}
//			
//				getMainView().setMousePoint( wx, wy );
//				getMainView().nativeMotionEvent( wx, wy );
//			}
//		}
//		
//		if(action == Mouse.SDL_FINGER_UP){
//			if(pointerId == mFirstPointerId){
//				mFirstPointerId = -1;
//				if(mDirectTouchDown){
//					mDirectTouchDown = false;
//				}
//			} else if(pointerId == mSecondPointerId){
//				mSecondPointerId = -1;
//				onMouseButtonEvent( MotionEvent.BUTTON_PRIMARY, 0 );
//			}
//		}
//	}
//	
//	@Override
//	public void onMouseButtonEvent(int buttonId, int pressed) {
//		int action = (pressed == 0 ? MotionEvent.ACTION_UP : MotionEvent.ACTION_DOWN);
//		MotionEvent event = MotionEvent.obtain( 0L, 0L, action, 0.0f, 0.0f, 0 );
//		
//		switch(buttonId){
//			case MotionEvent.BUTTON_PRIMARY:
//				onTouch(mLeftClickButton, event);
//				break;
//			case MotionEvent.BUTTON_SECONDARY:
//				onTouch(mRightClickButton, event);
//				break;
//			default:
//				getMainView().nativeMouseButtonsPressed( buttonId, pressed );
//				break;
//		}
//	}
//}

class InvalidTouchMode extends TouchMode {
	private boolean mIsMouseShowed = true;
	
	public InvalidTouchMode(MainView mainView) {
		super(mainView);
	}
	
	@Override
	void setup() {
		super.setup();
		mIsMouseShowed = getMainView().isMouseCursorShowed();
		getMainView().showMouseCursor(false);
	}
	
	@Override
	void cleanup() {
		if(!getMainView().isMouseCursorShowed()){
			getMainView().showMouseCursor(mIsMouseShowed);
		}
		super.cleanup();
	}
	
	@Override
	public void onKeyEvent(int keyCode, int pressed) {
		getMainView().nativeKey( keyCode, pressed );
	}
}
//
//class TouchTouchMode extends TouchMode {
//	private int mFirstPointerId = -1;
//	private boolean mRightClickMode = false;
//
//	public TouchTouchMode(MainView mainView) {
//		super(mainView);
//	}
//	
//	@Override
//	public void onKeyEvent(int keyCode, int pressed) {
//		getMainView().nativeKey( keyCode, pressed );
//	}
//	
//	@Override
//	public void onMotionEvent(int x, int y, int action, int pointerId, int pressure, int radius, int touchCount) {
//		if(mFirstPointerId < 0 && touchCount == 1 && action == Mouse.SDL_FINGER_DOWN){
//			mFirstPointerId = pointerId;
//		}
//		
//		if(pointerId == mFirstPointerId){
//			int wx = x - getMainView().getGLViewX();
//			int wy = y - getMainView().getGLViewY();
//			
//			getMainView().setMousePoint(wx, wy);
//			getMainView().nativeMotionEvent( wx, wy );
//			
//			//Log.i("onMotionEvent", "action:" + action + " x=" + wx + " y=" + wy);
//			
//			switch(action){
//				case Mouse.SDL_FINGER_DOWN:
//					onMouseButtonEvent(MotionEvent.BUTTON_PRIMARY, 1);
//					break;
//				case Mouse.SDL_FINGER_UP:
//					onMouseButtonEvent(MotionEvent.BUTTON_PRIMARY, 0);
//					mFirstPointerId = -1;
//					break;
//			}
//		}
//	}
//
//	@Override
//	public void onMouseButtonEvent(int buttonId, int pressed) {
//		getMainView().nativeMouseButtonsPressed( (mRightClickMode ? MotionEvent.BUTTON_SECONDARY : buttonId), pressed );
//		if(pressed == 0){
//			mRightClickMode = false;
//			getMainView().setMouseCursorRGB(0, 0, 0, 255, 255, 255);
//		}
//	}
//	
//}
