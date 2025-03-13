#include "../ressources/codeC/def.h"
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
  // Instanciations des paramètres du monde de test
  int nbEtapes = 6;
  int nbClients = 5;
  int nbGuichets =0;
  int* tabJetonsGuichet = NULL ;
  // Récupération de l'adresse du tableau contenant les pid des processus retourné par start_simulation
  int* tabsimu = start_simulation(nbEtapes,nbGuichets, nbClients, tabJetonsGuichet);
  // Affichage pid
  printf("Les clients :");
  for (int i = 0; i < nbClients; i++) {
    printf(" %d, ",tabsimu[i]);

  }
  printf("\n");

  //Boucle inifinie qui sera fini que quand condition d'arrêt réalisée
  while (1==1) {
    // Récupération des informations des clients
    int* tabclient = ou_sont_les_clients(nbEtapes, nbClients);
    // taille de ségment mémoire d'une étape (nbClient + 1 case qui stock le nombre de client en mémoire)
    int tailleEtapesEnMemoire = nbClients+1;
    for (int i = 0; i < nbEtapes; i++) {
      // On récupère le nombre de client de l'étape i
      int nbClientEtapeI = tabclient[i * tailleEtapesEnMemoire];
      // Selon le numéro l'étape on affiche un nom différent
      if (i == 0) {
        printf("étape %d (entrée) %d clients : ", i, nbClientEtapeI);

      }else if ( i== nbEtapes-1) {
        printf("étape %d (sortie) %d clients : ", i, nbClientEtapeI);

      }else {
        printf("étape %d (Activité) %d clients : ", i, nbClientEtapeI);

      }
      //Affichage des identifiants des clients à l'étape i
      for (int j = 0; j < nbClientEtapeI; j++) {
        int pidClientEtapeI = tabclient[i *tailleEtapesEnMemoire + j + 1];
        printf("%d,",pidClientEtapeI);
      }
      printf("\n");
    }
      if ( tabclient[2 * tailleEtapesEnMemoire]==nbClients) {
        free(tabclient);
        break;
      }
    free(tabclient);
    sleep(1);
  }
// free pour éviter fuite mémoire
free(tabsimu);
   nettoyage();
  return 0;
}