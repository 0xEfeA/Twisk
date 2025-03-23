#include "../ressources/codeC/def.h"
#define SASENTREE 0
#define SASSORTIE 1
#define ZOO 2
#define GUICHET_TOBOGAN 3
#define TOBOGAN 4
#define PISCINE 5

void simulation(int ids){
entrer(SASENTREE);
delai(4,2);
transfert(SASENTREE,ZOO);

delai(6,3);
transfert(ZOO,GUICHET_TOBOGAN);

P(ids,1);
delai(4,2);
V(ids,1);
transfert(GUICHET_TOBOGAN,TOBOGAN);

delai(4,2);
transfert(TOBOGAN,PISCINE);

delai(5,2);
transfert(PISCINE,SASSORTIE);

}