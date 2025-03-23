#include "../ressources/codeC/def.h"

#define sasEntree 0
#define sasSortie 5
#define activiteRestreinte 4
#define activite 2
#define guichet 1
#define guichet2 3

#define num_sem_guichet 1
#define num_sem_guichet2 1

#define numero_etape 0


void simulation (int ids){


  entrer(sasEntree);
  delai(4,1);


  //guichet
  transfert(sasEntree,guichet);
  P(ids,num_sem_guichet);
  delai(4,1);

  //activité
  transfert(guichet,activite);



  delai(5, 1);
  V(ids,num_sem_guichet);
  /**
*  Instruction à ajouter pour nouvelle activité (question 3 Séance 4) :
*  transfert(activitePredecesseur, activiteNouvelle);
*  delai(3,1);
*  transfert(activiteNouvelle, Successeurs : {sasSortie ou guichet ou activité});
*/
  /** Ancien code question 3 séance 4
  *  transfert(activite,activite2);
  *  delai(5, 1);
  *  //sasSortie
  *  transfert(activite2,sasSortie);
*/

  /**
* Instructions pour ajouter Activité restreinte (Question 4 séance 4)
  *   transfert(predecesseur, guichetNouveau);
 *     P(ids, num_sem_guichetNouveau);
 *     delai(3,1);
 *     transfert(guichetNouveau, activiteRestreinte);
 *     delai(4,1);
 *      V(ids, num_sem_guichetNouveau);
 *     transfert(activiteRestreinte,successeurs)
*/
  /** Instructions SasEntree et SasSortie (question 5 Séance 4)
* SasEntree: entrer(sasEntree);
* SasSortie : transfert(activite , sasSortie);
*/

 /** Question 6 séance 4
 *  Partie variante du code (dépend du monde):
*     -Les constantes : #define activite...
*     -Les transfert : dépends des liens entre le activité et guichet choisi par celui qui créer le monde
*     -les délais
*
*   Partie fixe du code :
*   - Entrée au début du monde
*   - Sortie à la fin du monde
*/
  transfert(activite, guichet2);
  P(ids, num_sem_guichet2);
  delai(3,1);
  transfert(guichet2, activiteRestreinte);
  delai(4,1);
  V(ids, num_sem_guichet2);

  //sasSortie
  transfert(activiteRestreinte,sasSortie);
    }
