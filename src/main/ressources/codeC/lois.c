#include "lois.h"
#include <stdlib.h>
#include <math.h>
#include <unistd.h>
#include <time.h>

void delaiUniforme(int temps, int ecart) {
    srand(time(NULL));
    int bi = temps - ecart;
    if (bi < 0) bi = 0;
    int bs = temps + ecart;
    int n  = bs - bi + 1;
    int nbSec = (rand() / (double)RAND_MAX) * n;
    nbSec += bi;
    usleep(nbSec * 1000000);
}

void delaiGauss(double moyenne, double ecartType) {
srand(time(NULL));
    double U1 = rand()/RAND_MAX;
    double U2 = rand() /RAND_MAX;
    double produit = sqrt(-2.0*log(U1)) * cos(2*3.14*U2);
    double X  = moyenne + ecartType * produit;
    if (X < 0) {
        X = 1;
    }
    int nbSec = (int)(X + 0.5);
    usleep(nbSec * 1000000);
}

void delaiExponentiel(double lambda) {
srand(time(NULL));
    double U = rand() / (double)RAND_MAX;
    double X = -log( U) / lambda;
    if (X < 0) {
        X = 1;
    }
    int nbSec = (int)(X + 0.5);
    usleep(nbSec * 1000000);
}
