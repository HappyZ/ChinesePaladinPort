################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/thread/SDL_thread.c 

OBJS += \
./jni/sdl-1.2_jni/src/thread/SDL_thread.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/thread/SDL_thread.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/thread/%.o: ../jni/sdl-1.2_jni/src/thread/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


