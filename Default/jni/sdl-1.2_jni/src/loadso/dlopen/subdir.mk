################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/loadso/dlopen/SDL_sysloadso.c 

OBJS += \
./jni/sdl-1.2_jni/src/loadso/dlopen/SDL_sysloadso.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/loadso/dlopen/SDL_sysloadso.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/loadso/dlopen/%.o: ../jni/sdl-1.2_jni/src/loadso/dlopen/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


