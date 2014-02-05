################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/SDL.c \
../jni/sdl-1.2_jni/src/SDL_error.c \
../jni/sdl-1.2_jni/src/SDL_fatal.c 

OBJS += \
./jni/sdl-1.2_jni/src/SDL.o \
./jni/sdl-1.2_jni/src/SDL_error.o \
./jni/sdl-1.2_jni/src/SDL_fatal.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/SDL.d \
./jni/sdl-1.2_jni/src/SDL_error.d \
./jni/sdl-1.2_jni/src/SDL_fatal.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/%.o: ../jni/sdl-1.2_jni/src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


