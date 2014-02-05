################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../jni/sdl-1.2_jni/src/stdlib/SDL_getenv.c \
../jni/sdl-1.2_jni/src/stdlib/SDL_iconv.c \
../jni/sdl-1.2_jni/src/stdlib/SDL_malloc.c \
../jni/sdl-1.2_jni/src/stdlib/SDL_qsort.c \
../jni/sdl-1.2_jni/src/stdlib/SDL_stdlib.c \
../jni/sdl-1.2_jni/src/stdlib/SDL_string.c 

OBJS += \
./jni/sdl-1.2_jni/src/stdlib/SDL_getenv.o \
./jni/sdl-1.2_jni/src/stdlib/SDL_iconv.o \
./jni/sdl-1.2_jni/src/stdlib/SDL_malloc.o \
./jni/sdl-1.2_jni/src/stdlib/SDL_qsort.o \
./jni/sdl-1.2_jni/src/stdlib/SDL_stdlib.o \
./jni/sdl-1.2_jni/src/stdlib/SDL_string.o 

C_DEPS += \
./jni/sdl-1.2_jni/src/stdlib/SDL_getenv.d \
./jni/sdl-1.2_jni/src/stdlib/SDL_iconv.d \
./jni/sdl-1.2_jni/src/stdlib/SDL_malloc.d \
./jni/sdl-1.2_jni/src/stdlib/SDL_qsort.d \
./jni/sdl-1.2_jni/src/stdlib/SDL_stdlib.d \
./jni/sdl-1.2_jni/src/stdlib/SDL_string.d 


# Each subdirectory must supply rules for building sources it contributes
jni/sdl-1.2_jni/src/stdlib/%.o: ../jni/sdl-1.2_jni/src/stdlib/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


