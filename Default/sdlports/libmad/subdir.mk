################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../sdlports/libmad/bit.c \
../sdlports/libmad/decoder.c \
../sdlports/libmad/fixed.c \
../sdlports/libmad/frame.c \
../sdlports/libmad/huffman.c \
../sdlports/libmad/layer12.c \
../sdlports/libmad/layer3.c \
../sdlports/libmad/music_mad.c \
../sdlports/libmad/stream.c \
../sdlports/libmad/synth.c \
../sdlports/libmad/timer.c 

OBJS += \
./sdlports/libmad/bit.o \
./sdlports/libmad/decoder.o \
./sdlports/libmad/fixed.o \
./sdlports/libmad/frame.o \
./sdlports/libmad/huffman.o \
./sdlports/libmad/layer12.o \
./sdlports/libmad/layer3.o \
./sdlports/libmad/music_mad.o \
./sdlports/libmad/stream.o \
./sdlports/libmad/synth.o \
./sdlports/libmad/timer.o 

C_DEPS += \
./sdlports/libmad/bit.d \
./sdlports/libmad/decoder.d \
./sdlports/libmad/fixed.d \
./sdlports/libmad/frame.d \
./sdlports/libmad/huffman.d \
./sdlports/libmad/layer12.d \
./sdlports/libmad/layer3.d \
./sdlports/libmad/music_mad.d \
./sdlports/libmad/stream.d \
./sdlports/libmad/synth.d \
./sdlports/libmad/timer.d 


# Each subdirectory must supply rules for building sources it contributes
sdlports/libmad/%.o: ../sdlports/libmad/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


