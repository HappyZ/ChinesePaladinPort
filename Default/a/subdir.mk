################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../a/pal_s60-10.cpp \
../a/rixplay.cpp 

C_SRCS += \
../a/battle.c \
../a/ending.c \
../a/fight.c \
../a/font.c \
../a/game.c \
../a/getopt.c \
../a/global.c \
../a/input.c \
../a/input_PSP.c \
../a/itemmenu.c \
../a/magicmenu.c \
../a/main.c \
../a/map.c \
../a/midi.c \
../a/palcommon.c \
../a/palette.c \
../a/play.c \
../a/private.c \
../a/res.c \
../a/rngplay.c \
../a/scene.c \
../a/script.c \
../a/sound.c \
../a/text.c \
../a/ui.c \
../a/uibattle.c \
../a/uigame.c \
../a/util.c \
../a/video.c \
../a/yj1.c 

OBJS += \
./a/battle.o \
./a/ending.o \
./a/fight.o \
./a/font.o \
./a/game.o \
./a/getopt.o \
./a/global.o \
./a/input.o \
./a/input_PSP.o \
./a/itemmenu.o \
./a/magicmenu.o \
./a/main.o \
./a/map.o \
./a/midi.o \
./a/pal_s60-10.o \
./a/palcommon.o \
./a/palette.o \
./a/play.o \
./a/private.o \
./a/res.o \
./a/rixplay.o \
./a/rngplay.o \
./a/scene.o \
./a/script.o \
./a/sound.o \
./a/text.o \
./a/ui.o \
./a/uibattle.o \
./a/uigame.o \
./a/util.o \
./a/video.o \
./a/yj1.o 

C_DEPS += \
./a/battle.d \
./a/ending.d \
./a/fight.d \
./a/font.d \
./a/game.d \
./a/getopt.d \
./a/global.d \
./a/input.d \
./a/input_PSP.d \
./a/itemmenu.d \
./a/magicmenu.d \
./a/main.d \
./a/map.d \
./a/midi.d \
./a/palcommon.d \
./a/palette.d \
./a/play.d \
./a/private.d \
./a/res.d \
./a/rngplay.d \
./a/scene.d \
./a/script.d \
./a/sound.d \
./a/text.d \
./a/ui.d \
./a/uibattle.d \
./a/uigame.d \
./a/util.d \
./a/video.d \
./a/yj1.d 

CPP_DEPS += \
./a/pal_s60-10.d \
./a/rixplay.d 


# Each subdirectory must supply rules for building sources it contributes
a/%.o: ../a/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

a/%.o: ../a/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


