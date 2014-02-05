################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/audio/SDL_audio.c \
../jni/sdl-1.2_jni/src/audio/SDL_audiocvt.c \
../jni/sdl-1.2_jni/src/audio/SDL_audiodev.c \
../jni/sdl-1.2_jni/src/audio/SDL_mixer.c \
../jni/sdl-1.2_jni/src/audio/SDL_mixer_MMX.c \
../jni/sdl-1.2_jni/src/audio/SDL_mixer_MMX_VC.c \
../jni/sdl-1.2_jni/src/audio/SDL_mixer_m68k.c \
../jni/sdl-1.2_jni/src/audio/SDL_wave.c 

OBJS += \
./jni/sdl-1.2_jni/src/audio/SDL_audio.o \
./jni/sdl-1.2_jni/src/audio/SDL_audiocvt.o \
./jni/sdl-1.2_jni/src/audio/SDL_audiodev.o \
./jni/sdl-1.2_jni/src/audio/SDL_mixer.o \
./jni/sdl-1.2_jni/src/audio/SDL_mixer_MMX.o \
./jni/sdl-1.2_jni/src/audio/SDL_mixer_MMX_VC.o \
./jni/sdl-1.2_jni/src/audio/SDL_mixer_m68k.o \
./jni/sdl-1.2_jni/src/audio/SDL_wave.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/audio/SDL_audio.d \
./jni/sdl-1.2_jni/src/audio/SDL_audiocvt.d \
./jni/sdl-1.2_jni/src/audio/SDL_audiodev.d \
./jni/sdl-1.2_jni/src/audio/SDL_mixer.d \
./jni/sdl-1.2_jni/src/audio/SDL_mixer_MMX.d \
./jni/sdl-1.2_jni/src/audio/SDL_mixer_MMX_VC.d \
./jni/sdl-1.2_jni/src/audio/SDL_mixer_m68k.d \
./jni/sdl-1.2_jni/src/audio/SDL_wave.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/audio/%.o: ../jni/sdl-1.2_jni/src/audio/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


