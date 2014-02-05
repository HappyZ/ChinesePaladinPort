################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/video/SDL_RLEaccel.c \
../jni/sdl-1.2_jni/src/video/SDL_blit.c \
../jni/sdl-1.2_jni/src/video/SDL_blit_0.c \
../jni/sdl-1.2_jni/src/video/SDL_blit_1.c \
../jni/sdl-1.2_jni/src/video/SDL_blit_A.c \
../jni/sdl-1.2_jni/src/video/SDL_blit_N.c \
../jni/sdl-1.2_jni/src/video/SDL_bmp.c \
../jni/sdl-1.2_jni/src/video/SDL_cursor.c \
../jni/sdl-1.2_jni/src/video/SDL_gamma.c \
../jni/sdl-1.2_jni/src/video/SDL_pixels.c \
../jni/sdl-1.2_jni/src/video/SDL_stretch.c \
../jni/sdl-1.2_jni/src/video/SDL_surface.c \
../jni/sdl-1.2_jni/src/video/SDL_video.c \
../jni/sdl-1.2_jni/src/video/SDL_yuv.c \
../jni/sdl-1.2_jni/src/video/SDL_yuv_mmx.c \
../jni/sdl-1.2_jni/src/video/SDL_yuv_sw.c 

OBJS += \
./jni/sdl-1.2_jni/src/video/SDL_RLEaccel.o \
./jni/sdl-1.2_jni/src/video/SDL_blit.o \
./jni/sdl-1.2_jni/src/video/SDL_blit_0.o \
./jni/sdl-1.2_jni/src/video/SDL_blit_1.o \
./jni/sdl-1.2_jni/src/video/SDL_blit_A.o \
./jni/sdl-1.2_jni/src/video/SDL_blit_N.o \
./jni/sdl-1.2_jni/src/video/SDL_bmp.o \
./jni/sdl-1.2_jni/src/video/SDL_cursor.o \
./jni/sdl-1.2_jni/src/video/SDL_gamma.o \
./jni/sdl-1.2_jni/src/video/SDL_pixels.o \
./jni/sdl-1.2_jni/src/video/SDL_stretch.o \
./jni/sdl-1.2_jni/src/video/SDL_surface.o \
./jni/sdl-1.2_jni/src/video/SDL_video.o \
./jni/sdl-1.2_jni/src/video/SDL_yuv.o \
./jni/sdl-1.2_jni/src/video/SDL_yuv_mmx.o \
./jni/sdl-1.2_jni/src/video/SDL_yuv_sw.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/video/SDL_RLEaccel.d \
./jni/sdl-1.2_jni/src/video/SDL_blit.d \
./jni/sdl-1.2_jni/src/video/SDL_blit_0.d \
./jni/sdl-1.2_jni/src/video/SDL_blit_1.d \
./jni/sdl-1.2_jni/src/video/SDL_blit_A.d \
./jni/sdl-1.2_jni/src/video/SDL_blit_N.d \
./jni/sdl-1.2_jni/src/video/SDL_bmp.d \
./jni/sdl-1.2_jni/src/video/SDL_cursor.d \
./jni/sdl-1.2_jni/src/video/SDL_gamma.d \
./jni/sdl-1.2_jni/src/video/SDL_pixels.d \
./jni/sdl-1.2_jni/src/video/SDL_stretch.d \
./jni/sdl-1.2_jni/src/video/SDL_surface.d \
./jni/sdl-1.2_jni/src/video/SDL_video.d \
./jni/sdl-1.2_jni/src/video/SDL_yuv.d \
./jni/sdl-1.2_jni/src/video/SDL_yuv_mmx.d \
./jni/sdl-1.2_jni/src/video/SDL_yuv_sw.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/video/%.o: ../jni/sdl-1.2_jni/src/video/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


