//
// Copyright (c) 2009, Wei Mingzhi <whistler_wmz@users.sf.net>.
// All rights reserved.
//
// This file is part of SDLPAL.
//
// SDLPAL is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//

#include "main.h"
#include <jni.h>

// jni to get money
JNIEXPORT jint JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeGetMoney ( JNIEnv  env, jobject thiz)
{
	return gpGlobals->dwCash;
}

// jni to set money
JNIEXPORT void JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeSetMoney ( JNIEnv  env, jobject thiz, jint amount)
{
	gpGlobals->dwCash = amount;
}

// jni to get hp
JNIEXPORT jint JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeGetHP ( JNIEnv  env, jobject thiz, jint who)
{
	return gpGlobals->g.PlayerRoles.rgwMaxHP[who];
}

// jni to set hp
JNIEXPORT void JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeSetHP ( JNIEnv  env, jobject thiz, jint who, jint amount)
{
	gpGlobals->g.PlayerRoles.rgwMaxHP[who] = amount;
	gpGlobals->g.PlayerRoles.rgwHP[who] = amount;
}

// jni to get mp
JNIEXPORT jint JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeGetMP ( JNIEnv  env, jobject thiz, jint who)
{
	return gpGlobals->g.PlayerRoles.rgwMaxMP[who];
}

// jni to set mp
JNIEXPORT void JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeSetMP ( JNIEnv  env, jobject thiz, jint who, jint amount)
{
	gpGlobals->g.PlayerRoles.rgwMaxMP[who] = amount;
	gpGlobals->g.PlayerRoles.rgwMP[who] = amount;
}

// jni to cheat
JNIEXPORT jboolean JNICALL
Java_org_happyz_chinesepaladin_MainActivity_nativeCheat ( JNIEnv  env, jobject thiz, jint who, jint what)
{
	switch(what){
	case 0:
		gpGlobals->g.PlayerRoles.rgwMaxHP[who] = 999;
		gpGlobals->g.PlayerRoles.rgwHP[who] = 999;			
		gpGlobals->dwCash -= 500;
		return 1;
	default:
		return 0;
	}
}
