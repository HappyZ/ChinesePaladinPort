################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../stuff/native_midi/native_midi_common.c \
../stuff/native_midi/native_midi_macosx.c \
../stuff/native_midi/native_midi_win32.c 

OBJS += \
./stuff/native_midi/native_midi_common.o \
./stuff/native_midi/native_midi_macosx.o \
./stuff/native_midi/native_midi_win32.o 

C_DEPS += \
./stuff/native_midi/native_midi_common.d \
./stuff/native_midi/native_midi_macosx.d \
./stuff/native_midi/native_midi_win32.d 


# Each subdirectory must supply rules for building sources it contributes
stuff/native_midi/%.o: ../stuff/native_midi/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


