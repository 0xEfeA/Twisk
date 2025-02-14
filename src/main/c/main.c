#include "../ressources/codeC/def.h"
#include <stddef.h>


int main(int argc, char *argv[]) {
  // Instanciations des paramètres du monde de test
  int nbEtapes = 3;
  int nbClients = 5;
  int nbGuichets =0;
  int* tabJetonsGuichet = NULL ;
  // Récupération du tableau contenant les pid des processus retourné par start_simulation
  int* tab = start_simulation(nbEtapes,nbGuichets, nbClients, tabJetonsGuichet);
  // Affichage pid
  printf("Les clients :");
  for (int i = 0; i < nbClients; i++) {
    printf(" ,%d ",tab[i]);
  }
  printf("\n");
// free pour éviter fuite mémoire
free(tab);
  void nettoyage();
  return 0;
}