################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../sdlports/adplug/binfile.cpp \
../sdlports/adplug/binio.cpp \
../sdlports/adplug/dosbox_opl.cpp \
../sdlports/adplug/emuopl.cpp \
../sdlports/adplug/fprovide.cpp \
../sdlports/adplug/player.cpp \
../sdlports/adplug/rix.cpp \
../sdlports/adplug/surroundopl.cpp 

C_SRCS += \
../sdlports/adplug/fmopl.c 

OBJS += \
./sdlports/adplug/binfile.o \
./sdlports/adplug/binio.o \
./sdlports/adplug/dosbox_opl.o \
./sdlports/adplug/emuopl.o \
./sdlports/adplug/fmopl.o \
./sdlports/adplug/fprovide.o \
./sdlports/adplug/player.o \
./sdlports/adplug/rix.o \
./sdlports/adplug/surroundopl.o 

C_DEPS += \
./sdlports/adplug/fmopl.d 

CPP_DEPS += \
./sdlports/adplug/binfile.d \
./sdlports/adplug/binio.d \
./sdlports/adplug/dosbox_opl.d \
./sdlports/adplug/emuopl.d \
./sdlports/adplug/fprovide.d \
./sdlports/adplug/player.d \
./sdlports/adplug/rix.d \
./sdlports/adplug/surroundopl.d 


# Each subdirectory must supply rules for building sources it contributes
sdlports/adplug/%.o: ../sdlports/adplug/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

sdlports/adplug/%.o: ../sdlports/adplug/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


