################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../a/adplug/binfile.cpp \
../a/adplug/binio.cpp \
../a/adplug/dosbox_opl.cpp \
../a/adplug/emuopl.cpp \
../a/adplug/fprovide.cpp \
../a/adplug/player.cpp \
../a/adplug/rix.cpp \
../a/adplug/surroundopl.cpp 

C_SRCS += \
../a/adplug/fmopl.c 

OBJS += \
./a/adplug/binfile.o \
./a/adplug/binio.o \
./a/adplug/dosbox_opl.o \
./a/adplug/emuopl.o \
./a/adplug/fmopl.o \
./a/adplug/fprovide.o \
./a/adplug/player.o \
./a/adplug/rix.o \
./a/adplug/surroundopl.o 

C_DEPS += \
./a/adplug/fmopl.d 

CPP_DEPS += \
./a/adplug/binfile.d \
./a/adplug/binio.d \
./a/adplug/dosbox_opl.d \
./a/adplug/emuopl.d \
./a/adplug/fprovide.d \
./a/adplug/player.d \
./a/adplug/rix.d \
./a/adplug/surroundopl.d 


# Each subdirectory must supply rules for building sources it contributes
a/adplug/%.o: ../a/adplug/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

a/adplug/%.o: ../a/adplug/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


