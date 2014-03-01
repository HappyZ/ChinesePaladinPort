################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../a/native_midi/native_midi_common.c \
../a/native_midi/native_midi_macosx.c \
../a/native_midi/native_midi_win32.c 

OBJS += \
./a/native_midi/native_midi_common.o \
./a/native_midi/native_midi_macosx.o \
./a/native_midi/native_midi_win32.o 

C_DEPS += \
./a/native_midi/native_midi_common.d \
./a/native_midi/native_midi_macosx.d \
./a/native_midi/native_midi_win32.d 


# Each subdirectory must supply rules for building sources it contributes
a/native_midi/%.o: ../a/native_midi/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


