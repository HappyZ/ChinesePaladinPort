################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../stuff/pal_s60-10.cpp \
../stuff/rixplay.cpp 

C_SRCS += \
../stuff/battle.c \
../stuff/ending.c \
../stuff/fight.c \
../stuff/font.c \
../stuff/game.c \
../stuff/getopt.c \
../stuff/global.c \
../stuff/input.c \
../stuff/input_PSP.c \
../stuff/itemmenu.c \
../stuff/magicmenu.c \
../stuff/main.c \
../stuff/map.c \
../stuff/midi.c \
../stuff/palcommon.c \
../stuff/palette.c \
../stuff/play.c \
../stuff/private.c \
../stuff/res.c \
../stuff/rngplay.c \
../stuff/scene.c \
../stuff/script.c \
../stuff/sound.c \
../stuff/text.c \
../stuff/ui.c \
../stuff/uibattle.c \
../stuff/uigame.c \
../stuff/util.c \
../stuff/video.c \
../stuff/yj1.c 

OBJS += \
./stuff/battle.o \
./stuff/ending.o \
./stuff/fight.o \
./stuff/font.o \
./stuff/game.o \
./stuff/getopt.o \
./stuff/global.o \
./stuff/input.o \
./stuff/input_PSP.o \
./stuff/itemmenu.o \
./stuff/magicmenu.o \
./stuff/main.o \
./stuff/map.o \
./stuff/midi.o \
./stuff/pal_s60-10.o \
./stuff/palcommon.o \
./stuff/palette.o \
./stuff/play.o \
./stuff/private.o \
./stuff/res.o \
./stuff/rixplay.o \
./stuff/rngplay.o \
./stuff/scene.o \
./stuff/script.o \
./stuff/sound.o \
./stuff/text.o \
./stuff/ui.o \
./stuff/uibattle.o \
./stuff/uigame.o \
./stuff/util.o \
./stuff/video.o \
./stuff/yj1.o 

C_DEPS += \
./stuff/battle.d \
./stuff/ending.d \
./stuff/fight.d \
./stuff/font.d \
./stuff/game.d \
./stuff/getopt.d \
./stuff/global.d \
./stuff/input.d \
./stuff/input_PSP.d \
./stuff/itemmenu.d \
./stuff/magicmenu.d \
./stuff/main.d \
./stuff/map.d \
./stuff/midi.d \
./stuff/palcommon.d \
./stuff/palette.d \
./stuff/play.d \
./stuff/private.d \
./stuff/res.d \
./stuff/rngplay.d \
./stuff/scene.d \
./stuff/script.d \
./stuff/sound.d \
./stuff/text.d \
./stuff/ui.d \
./stuff/uibattle.d \
./stuff/uigame.d \
./stuff/util.d \
./stuff/video.d \
./stuff/yj1.d 

CPP_DEPS += \
./stuff/pal_s60-10.d \
./stuff/rixplay.d 


# Each subdirectory must supply rules for building sources it contributes
stuff/%.o: ../stuff/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

stuff/%.o: ../stuff/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


