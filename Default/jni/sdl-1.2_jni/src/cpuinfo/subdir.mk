################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/cpuinfo/SDL_cpuinfo.c 

OBJS += \
./jni/sdl-1.2_jni/src/cpuinfo/SDL_cpuinfo.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/cpuinfo/SDL_cpuinfo.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/cpuinfo/%.o: ../jni/sdl-1.2_jni/src/cpuinfo/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


