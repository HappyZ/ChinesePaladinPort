################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../stuff/libmad/bit.c \
../stuff/libmad/decoder.c \
../stuff/libmad/fixed.c \
../stuff/libmad/frame.c \
../stuff/libmad/huffman.c \
../stuff/libmad/layer12.c \
../stuff/libmad/layer3.c \
../stuff/libmad/music_mad.c \
../stuff/libmad/stream.c \
../stuff/libmad/synth.c \
../stuff/libmad/timer.c 

OBJS += \
./stuff/libmad/bit.o \
./stuff/libmad/decoder.o \
./stuff/libmad/fixed.o \
./stuff/libmad/frame.o \
./stuff/libmad/huffman.o \
./stuff/libmad/layer12.o \
./stuff/libmad/layer3.o \
./stuff/libmad/music_mad.o \
./stuff/libmad/stream.o \
./stuff/libmad/synth.o \
./stuff/libmad/timer.o 

C_DEPS += \
./stuff/libmad/bit.d \
./stuff/libmad/decoder.d \
./stuff/libmad/fixed.d \
./stuff/libmad/frame.d \
./stuff/libmad/huffman.d \
./stuff/libmad/layer12.d \
./stuff/libmad/layer3.d \
./stuff/libmad/music_mad.d \
./stuff/libmad/stream.d \
./stuff/libmad/synth.d \
./stuff/libmad/timer.d 


# Each subdirectory must supply rules for building sources it contributes
stuff/libmad/%.o: ../stuff/libmad/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


