################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/events/SDL_active.c \
../jni/sdl-1.2_jni/src/events/SDL_events.c \
../jni/sdl-1.2_jni/src/events/SDL_expose.c \
../jni/sdl-1.2_jni/src/events/SDL_keyboard.c \
../jni/sdl-1.2_jni/src/events/SDL_mouse.c \
../jni/sdl-1.2_jni/src/events/SDL_quit.c \
../jni/sdl-1.2_jni/src/events/SDL_resize.c 

OBJS += \
./jni/sdl-1.2_jni/src/events/SDL_active.o \
./jni/sdl-1.2_jni/src/events/SDL_events.o \
./jni/sdl-1.2_jni/src/events/SDL_expose.o \
./jni/sdl-1.2_jni/src/events/SDL_keyboard.o \
./jni/sdl-1.2_jni/src/events/SDL_mouse.o \
./jni/sdl-1.2_jni/src/events/SDL_quit.o \
./jni/sdl-1.2_jni/src/events/SDL_resize.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/events/SDL_active.d \
./jni/sdl-1.2_jni/src/events/SDL_events.d \
./jni/sdl-1.2_jni/src/events/SDL_expose.d \
./jni/sdl-1.2_jni/src/events/SDL_keyboard.d \
./jni/sdl-1.2_jni/src/events/SDL_mouse.d \
./jni/sdl-1.2_jni/src/events/SDL_quit.d \
./jni/sdl-1.2_jni/src/events/SDL_resize.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/events/%.o: ../jni/sdl-1.2_jni/src/events/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


