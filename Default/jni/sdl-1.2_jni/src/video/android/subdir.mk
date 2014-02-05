################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/video/android/SDL_androidinput.c \
../jni/sdl-1.2_jni/src/video/android/SDL_androidvideo-1.2.c \
../jni/sdl-1.2_jni/src/video/android/SDL_androidvideo-1.3-stub.c \
../jni/sdl-1.2_jni/src/video/android/SDL_androidvideo.c \
../jni/sdl-1.2_jni/src/video/android/SDL_pixels.c \
../jni/sdl-1.2_jni/src/video/android/SDL_rect.c \
../jni/sdl-1.2_jni/src/video/android/SDL_renderer_gles.c \
../jni/sdl-1.2_jni/src/video/android/SDL_video.c \
../jni/sdl-1.2_jni/src/video/android/keymap.c 

OBJS += \
./jni/sdl-1.2_jni/src/video/android/SDL_androidinput.o \
./jni/sdl-1.2_jni/src/video/android/SDL_androidvideo-1.2.o \
./jni/sdl-1.2_jni/src/video/android/SDL_androidvideo-1.3-stub.o \
./jni/sdl-1.2_jni/src/video/android/SDL_androidvideo.o \
./jni/sdl-1.2_jni/src/video/android/SDL_pixels.o \
./jni/sdl-1.2_jni/src/video/android/SDL_rect.o \
./jni/sdl-1.2_jni/src/video/android/SDL_renderer_gles.o \
./jni/sdl-1.2_jni/src/video/android/SDL_video.o \
./jni/sdl-1.2_jni/src/video/android/keymap.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/video/android/SDL_androidinput.d \
./jni/sdl-1.2_jni/src/video/android/SDL_androidvideo-1.2.d \
./jni/sdl-1.2_jni/src/video/android/SDL_androidvideo-1.3-stub.d \
./jni/sdl-1.2_jni/src/video/android/SDL_androidvideo.d \
./jni/sdl-1.2_jni/src/video/android/SDL_pixels.d \
./jni/sdl-1.2_jni/src/video/android/SDL_rect.d \
./jni/sdl-1.2_jni/src/video/android/SDL_renderer_gles.d \
./jni/sdl-1.2_jni/src/video/android/SDL_video.d \
./jni/sdl-1.2_jni/src/video/android/keymap.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/video/android/%.o: ../jni/sdl-1.2_jni/src/video/android/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


