/*
 2012/7 Created by AKIZUKI Katane
 */

package org.happyz.chinesepaladin;

import android.view.MotionEvent;

public class AndroidGingerBreadAPI
{
	public static int MotionEventGetSource(final MotionEvent event)
	{
		return event.getSource();
	}
	
	public static float MotionEventGetAxisValue(final MotionEvent event, final int axis)
	{
		return event.getAxisValue(axis);
	}
}
