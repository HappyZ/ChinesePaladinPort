################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../sdlports/pal_s60-10.cpp \
../sdlports/rixplay.cpp 

C_SRCS += \
../sdlports/battle.c \
../sdlports/ending.c \
../sdlports/fight.c \
../sdlports/font.c \
../sdlports/game.c \
../sdlports/getopt.c \
../sdlports/global.c \
../sdlports/input.c \
../sdlports/input_PSP.c \
../sdlports/itemmenu.c \
../sdlports/magicmenu.c \
../sdlports/main.c \
../sdlports/map.c \
../sdlports/midi.c \
../sdlports/palcommon.c \
../sdlports/palette.c \
../sdlports/play.c \
../sdlports/private.c \
../sdlports/res.c \
../sdlports/rngplay.c \
../sdlports/scene.c \
../sdlports/script.c \
../sdlports/sound.c \
../sdlports/text.c \
../sdlports/ui.c \
../sdlports/uibattle.c \
../sdlports/uigame.c \
../sdlports/util.c \
../sdlports/video.c \
../sdlports/yj1.c 

OBJS += \
./sdlports/battle.o \
./sdlports/ending.o \
./sdlports/fight.o \
./sdlports/font.o \
./sdlports/game.o \
./sdlports/getopt.o \
./sdlports/global.o \
./sdlports/input.o \
./sdlports/input_PSP.o \
./sdlports/itemmenu.o \
./sdlports/magicmenu.o \
./sdlports/main.o \
./sdlports/map.o \
./sdlports/midi.o \
./sdlports/pal_s60-10.o \
./sdlports/palcommon.o \
./sdlports/palette.o \
./sdlports/play.o \
./sdlports/private.o \
./sdlports/res.o \
./sdlports/rixplay.o \
./sdlports/rngplay.o \
./sdlports/scene.o \
./sdlports/script.o \
./sdlports/sound.o \
./sdlports/text.o \
./sdlports/ui.o \
./sdlports/uibattle.o \
./sdlports/uigame.o \
./sdlports/util.o \
./sdlports/video.o \
./sdlports/yj1.o 

C_DEPS += \
./sdlports/battle.d \
./sdlports/ending.d \
./sdlports/fight.d \
./sdlports/font.d \
./sdlports/game.d \
./sdlports/getopt.d \
./sdlports/global.d \
./sdlports/input.d \
./sdlports/input_PSP.d \
./sdlports/itemmenu.d \
./sdlports/magicmenu.d \
./sdlports/main.d \
./sdlports/map.d \
./sdlports/midi.d \
./sdlports/palcommon.d \
./sdlports/palette.d \
./sdlports/play.d \
./sdlports/private.d \
./sdlports/res.d \
./sdlports/rngplay.d \
./sdlports/scene.d \
./sdlports/script.d \
./sdlports/sound.d \
./sdlports/text.d \
./sdlports/ui.d \
./sdlports/uibattle.d \
./sdlports/uigame.d \
./sdlports/util.d \
./sdlports/video.d \
./sdlports/yj1.d 

CPP_DEPS += \
./sdlports/pal_s60-10.d \
./sdlports/rixplay.d 


# Each subdirectory must supply rules for building sources it contributes
sdlports/%.o: ../sdlports/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

sdlports/%.o: ../sdlports/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O2 -g -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


