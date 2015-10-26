OBJECTS = main.o ProblemB.o
CXX = g++
DEBUG = -g
CFLAGS = -Wall -c
LFLAGS = -Wall $(DEBUG)
PROJECT = ProblemB

.PHONY: all paths clean run

all: paths $(OBJECTS)
	$(CXX) $(LFLAGS) $(OBJECTS) -o $(PROJECT) && mv *.o build && mv $(PROJECT) build/debug

main.o:
	$(CXX) $(CFLAGS) source/main.cpp

ProblemB.o:
	$(CXX) $(CFLAGS) source/ProblemB.cpp

paths:
	if [[ ! -d build ]]; then mkdir -p build/debug; fi

clean:
	if [[ -d build ]]; then rm -rf build; fi

run:
	if [[ -d build ]] && [[ -d build/debug ]]; then ./build/debug/$(PROJECT); fi