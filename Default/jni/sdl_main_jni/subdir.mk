################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl_main_jni/sdl_main.c 

OBJS += \
./jni/sdl_main_jni/sdl_main.o 

C_DEPS += \
./jni/sdl_main_jni/sdl_main.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl_main_jni/%.o: ../jni/sdl_main_jni/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


