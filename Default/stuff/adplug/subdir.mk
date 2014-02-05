################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../stuff/adplug/binfile.cpp \
../stuff/adplug/binio.cpp \
../stuff/adplug/dosbox_opl.cpp \
../stuff/adplug/emuopl.cpp \
../stuff/adplug/fprovide.cpp \
../stuff/adplug/player.cpp \
../stuff/adplug/rix.cpp \
../stuff/adplug/surroundopl.cpp 

C_SRCS += \
../stuff/adplug/fmopl.c 

OBJS += \
./stuff/adplug/binfile.o \
./stuff/adplug/binio.o \
./stuff/adplug/dosbox_opl.o \
./stuff/adplug/emuopl.o \
./stuff/adplug/fmopl.o \
./stuff/adplug/fprovide.o \
./stuff/adplug/player.o \
./stuff/adplug/rix.o \
./stuff/adplug/surroundopl.o 

C_DEPS += \
./stuff/adplug/fmopl.d 

CPP_DEPS += \
./stuff/adplug/binfile.d \
./stuff/adplug/binio.d \
./stuff/adplug/dosbox_opl.d \
./stuff/adplug/emuopl.d \
./stuff/adplug/fprovide.d \
./stuff/adplug/player.d \
./stuff/adplug/rix.d \
./stuff/adplug/surroundopl.d 


# Each subdirectory must supply rules for building sources it contributes
stuff/adplug/%.o: ../stuff/adplug/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

stuff/adplug/%.o: ../stuff/adplug/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


