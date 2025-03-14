all: libTwisk.so main

client.o: client.c ../ressources/codeC/def.h
	gcc -Wall -fPIC -c client.c

main.o: main.c ../ressources/codeC/def.h
	gcc -Wall -c main.c

libTwisk.so: client.o ../ressources/codeC/programmeC.o
	gcc -shared -o libTwisk.so client.o ../ressources/codeC/programmeC.o

main: main.o libTwisk.so
	gcc -Wall -L. main.o -o main -lTwisk

clean:
	rm -f *.o *.so main
