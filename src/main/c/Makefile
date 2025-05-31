all: libTwisk.so main

client.o: client.c ../ressources/codeC/def.h
	gcc -Wall -fPIC -c client.c

lois.o : lois.c lois.h
	gcc -std=c11 -fPIC -Wall -I. -c lois.c -o lois.o


main.o: main.c ../ressources/codeC/def.h
	gcc -Wall -c main.c

libTwisk.so: client.o ../ressources/codeC/programmeC.o ../ressources/codeC/lois.o
	gcc -shared -o libTwisk.so client.o ../ressources/codeC/programmeC.o ../ressources/codeC/lois.o -lm

main: main.o libTwisk.so
	gcc -Wall -L. main.o -o main -lTwisk -lm

clean:
	rm -f *.o *.so main
