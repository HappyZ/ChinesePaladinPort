LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := $(lastword $(subst /, ,$(LOCAL_PATH)))

ifndef SDL_JAVA_PACKAGE_PATH
	$(Error: Please define SDL_JAVA_PACKAGE_PATH for example "com_example_android")
endif

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../sdl-1.2_jni/include $(LOCAL_PATH)/../stlport/stlport \
	 $(LOCAL_PATH) $(LOCAL_PATH)/../../sdlports/adplug $(LOCAL_PATH)/../../sdlports/libmad \
	 $(LOCAL_PATH)/../../sdlports/
LOCAL_CFLAGS := -g -Wall -O2 -DSDL -DANDROID -DPAL_CLASSIC -DTIMIDITY

GAME_SRCS := ../../sdlports/adplug/*.c ../../sdlports/timidity/*.c ../../sdlports/adplug/*.cpp ../../sdlports/libmad/*.c ../../sdlports/*.c ../../sdlports/*.cpp
LOCAL_SRC_FILES := $(foreach F, $(GAME_SRCS), $(addprefix $(dir $(F)),$(notdir $(wildcard $(LOCAL_PATH)/$(F)))))

LOCAL_SHARED_LIBRARIES := sdl-1.2_jni stlport_shared
LOCAL_LDLIBS := -lGLESv1_CM -ldl -llog -lz -lGLESv1_CM -lstdc++

include $(BUILD_SHARED_LIBRARY)
