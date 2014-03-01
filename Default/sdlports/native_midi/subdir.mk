################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../sdlports/native_midi/native_midi_common.c \
../sdlports/native_midi/native_midi_macosx.c \
../sdlports/native_midi/native_midi_win32.c 

OBJS += \
./sdlports/native_midi/native_midi_common.o \
./sdlports/native_midi/native_midi_macosx.o \
./sdlports/native_midi/native_midi_win32.o 

C_DEPS += \
./sdlports/native_midi/native_midi_common.d \
./sdlports/native_midi/native_midi_macosx.d \
./sdlports/native_midi/native_midi_win32.d 


# Each subdirectory must supply rules for building sources it contributes
sdlports/native_midi/%.o: ../sdlports/native_midi/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


