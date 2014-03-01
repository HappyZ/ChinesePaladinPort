################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../a/libmad/bit.c \
../a/libmad/decoder.c \
../a/libmad/fixed.c \
../a/libmad/frame.c \
../a/libmad/huffman.c \
../a/libmad/layer12.c \
../a/libmad/layer3.c \
../a/libmad/music_mad.c \
../a/libmad/stream.c \
../a/libmad/synth.c \
../a/libmad/timer.c 

OBJS += \
./a/libmad/bit.o \
./a/libmad/decoder.o \
./a/libmad/fixed.o \
./a/libmad/frame.o \
./a/libmad/huffman.o \
./a/libmad/layer12.o \
./a/libmad/layer3.o \
./a/libmad/music_mad.o \
./a/libmad/stream.o \
./a/libmad/synth.o \
./a/libmad/timer.o 

C_DEPS += \
./a/libmad/bit.d \
./a/libmad/decoder.d \
./a/libmad/fixed.d \
./a/libmad/frame.d \
./a/libmad/huffman.d \
./a/libmad/layer12.d \
./a/libmad/layer3.d \
./a/libmad/music_mad.d \
./a/libmad/stream.d \
./a/libmad/synth.d \
./a/libmad/timer.d 


# Each subdirectory must supply rules for building sources it contributes
a/libmad/%.o: ../a/libmad/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


