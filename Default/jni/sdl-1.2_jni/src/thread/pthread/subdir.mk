################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/thread/pthread/SDL_syscond.c \
../jni/sdl-1.2_jni/src/thread/pthread/SDL_sysmutex.c \
../jni/sdl-1.2_jni/src/thread/pthread/SDL_syssem.c \
../jni/sdl-1.2_jni/src/thread/pthread/SDL_systhread.c 

OBJS += \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_syscond.o \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_sysmutex.o \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_syssem.o \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_systhread.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_syscond.d \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_sysmutex.d \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_syssem.d \
./jni/sdl-1.2_jni/src/thread/pthread/SDL_systhread.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/thread/pthread/%.o: ../jni/sdl-1.2_jni/src/thread/pthread/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


