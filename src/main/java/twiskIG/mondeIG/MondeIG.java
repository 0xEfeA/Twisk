package twiskIG.mondeIG;

import twiskIG.exceptions.ArcImpossibleException;
import twiskIG.exceptions.IncorrectDelaiEcartException;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import twiskIG.outils.FabriqueIdentifiant;

import java.util.*;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG> {
    private HashMap<String, EtapeIG> etapes;
    private final ArrayList<ArcIG> arcs;
    private final ArrayList<EtapeIG> etapesSelectionnees = new ArrayList<>();
    private final ArrayList<ArcIG> arcsSelectionnes = new ArrayList<>();
    private String nomLoiArrivee;
    /**
     * Constructor: Initializes the world with a default activity.
     */
    public MondeIG() {
        this.etapes = new HashMap<>();
        //ActiviteIG defaut = new ActiviteIG("Activité 1", 150, 65);
        //this.etapes.put(defaut.getIdentifiant(), defaut);
        this.arcs = new ArrayList<>();
    }
    public void setNomLoiArrivee(String nomLoiArrivee) {
        this.nomLoiArrivee = nomLoiArrivee;
    }
    public String getNomLoiArrivee() {
        return nomLoiArrivee;
    }
    /**
     * Adds an Etape to the world.
     * @param type The type of the Etape to be added .
     */
    public void ajouter(String type) {
        if (type.equals("Activité")) {
            ActiviteIG activite = new ActiviteIG("Activité " + (this.numEtapes() + 1), 150, 65);
            etapes.put(activite.getIdentifiant(), activite);
            System.out.println("Activité ajoutée  " ); //+ activite.getIdentifiant()
            notifierObservateurs();
        }
        if (type.equals("Guichet")) {
            GuichetIG guichet = new GuichetIG("Guichet ", 150, 65);
            etapes.put(guichet.getIdentifiant(), guichet);
            System.out.println("Guichet ajoutée " );
            notifierObservateurs();
        }
    }
    public void ajouterArc(ArcIG arc) {
        this.arcs.add(arc);
    }
    /**
     * Returns an iterator to iterate over all the Etapes.
     * @return an iterator for the stored Etapes.
     */
    @Override
    public Iterator<EtapeIG> iterator() {
        return etapes.values().iterator();
    }

    /**
     * Returns the number of Etapes in the world.
     * @return The total count of Etapes.
     */
    public int numEtapes() {
        return etapes.size();
    }

    /**
     * Fonction pour getter les activités
     */
    public Collection<EtapeIG> getEtapes() {
        return etapes.values();
    }

    /**
     * Ajoute un arc entre deux points de contrôle.
     */
    public void ajouter(PointDeControleIG pt1, PointDeControleIG pt2) throws ArcImpossibleException {
        //vérifier si les points de contrôle sont non nuls
        if (pt1 == null || pt2 == null) {
            throw new ArcImpossibleException("Les points de contrôle sont invalides (l'un ou les deux sont nuls).");
        }

        //vérifier si les points de contrôle appartiennent à la même étape
        if (pt1.getEtape() == pt2.getEtape()) {
            throw new ArcImpossibleException("Les deux points de contrôle appartiennent à la même étape. Impossible de créer un arc entre eux.");
        }

        //vérifier si un arc existe déjà entre ces deux points ou entre n'importe quels points de leurs étapes
        for (ArcIG arc : arcs) {
            if (arc.relie(pt1, pt2)) {
                throw new ArcImpossibleException("L'arc entre ces deux points de contrôle existe déjà.");
            }
        }

        //points de contrôle des étapes
        ArrayList<PointDeControleIG> pointsStep1 = pt1.getEtape().getPointsDeControle();
        ArrayList<PointDeControleIG> pointsStep2 = pt2.getEtape().getPointsDeControle();

        //vérifier si un arc existe déjà entre n'importe quel point de Step1 et Step2 dans la même direction
        for (ArcIG arc : arcs) {
            for (PointDeControleIG p1 : pointsStep1) {
                for (PointDeControleIG p2 : pointsStep2) {
                    if (arc.getDepart().getEtape() == pt1.getEtape() && arc.getArrivee().getEtape() == pt2.getEtape()) {
                        throw new ArcImpossibleException("Un arc existe déjà de cette étape vers l'autre.");
                    }
                }
            }
        }

        if(estAccessibleDepuis(pt1.getEtape(),pt2.getEtape())){
            throw new ArcImpossibleException("Cet arc créer un circuit");
        }

        //ajouter l'arc si aucune exception n'a été levée
        arcs.add(new ArcIG(pt1, pt2));
        pt1.getEtape().setSuccesseurs(pt2.getEtape());
        pt2.getEtape().setPredecesseurs(pt1.getEtape());
    }

    /**
     * Renvoie vrai si candida est accessible depuis la racine
     * @param candidat etape d'arrivée
     * @param racine étape de départ
     * @return Si il existe un chemin entre racine et candidat
     */
    public boolean estAccessibleDepuis(EtapeIG candidat, EtapeIG racine) {
       return estAccessibleRecursif(candidat,racine,new ArrayList<>());
    }

    /**
     * Parcours en profondeur pour estAccessibleDepuis
     * @param candidat
     * @param racine
     * @param visite
     * @return
     */
    private  boolean estAccessibleRecursif(EtapeIG candidat, EtapeIG racine,ArrayList<EtapeIG> visite) {
        if(candidat==racine) {
            return true;
        }
        visite.add(racine);
        for(EtapeIG succs : racine.getSuccesseurs()){
            if(!visite.contains(succs)) {
                boolean accessible = estAccessibleRecursif(candidat,succs,visite);
                if(accessible) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Itérateur permettant de parcourir tous les arcs.
     */
    public Iterator<ArcIG> iteratorarc() {
        return arcs.iterator();
    }

    private PointDeControleIG premierPoint = null;

    public void selectionnerPoint(PointDeControleIG point) {
        if (premierPoint == null) {
            premierPoint = point;  // Premier point sélectionné
        } else {
            if (premierPoint.getEtape() != point.getEtape()) {
                try {
                    ajouter(premierPoint, point);
                } catch (ArcImpossibleException e) {
                    premierPoint = null;
                    afficherMessageErreur("Impossible d'ajouter l'arc : " + e.getMessage());
                    return;
                }
            }
            premierPoint = null;
        }
        notifierObservateurs();
    }

    public ArrayList<ArcIG> getArcs() {
        return arcs;
    }

    private void afficherMessageErreur(String message) {
        // Create an alert dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'ajout d'arc");
        alert.setHeaderText("Impossible d'ajouter l'arc");
        alert.setContentText(message);

        alert.show();

        // Create a PauseTransition to automatically close the alert after 5 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

    public void gererClicEtape(EtapeIG etape) {
        if (etapesSelectionnees.contains(etape)) {
            etapesSelectionnees.remove(etape);
            etape.toggleSelection();
        } else {
            etapesSelectionnees.add(etape);
            etape.toggleSelection();
        }
        notifierObservateurs();
    }

    /**
     * getter pour les etapes selectioné
     */
    public ArrayList<EtapeIG> getEtapesSelectionnees() {
        return etapesSelectionnees;
    }

    /**
     * Fonction pour supprimer les arcs et etapes selectionnée
     */
    public void supprimerEtapesSelectionnees() {
        // Supprimer les arcs associés aux étapes sélectionnées
        arcs.removeIf(arc -> etapesSelectionnees.contains(arc.getDepart().getEtape())
                || etapesSelectionnees.contains(arc.getArrivee().getEtape()));

        for (EtapeIG etape : etapesSelectionnees) {
            etapes.remove(etape.getIdentifiant());
        }

        etapesSelectionnees.clear();

        notifierObservateurs();
    }

    public void supprimerToutesLesEtapes() {
        arcs.clear();
        etapes.clear();

        FabriqueIdentifiant.getInstance().resetIdentifiant();

        notifierObservateurs();
    }



    public void renommerSelection() {
        if (getEtapesSelectionnees().size() == 1) {
            EtapeIG etape = getEtapesSelectionnees().get(0);

            // Boîte de dialogue pour saisir un nouveau nom
            TextInputDialog dialog = new TextInputDialog(etape.getNom());
            dialog.setTitle("Renommer l'activité");
            dialog.setHeaderText("Entrez un nouveau nom pour l'activité");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(nouveauNom -> {
                etape.setNom(nouveauNom);
                notifierObservateurs();
            });
        }
    }


    /**
     * Function pour retourner un etape grace a son identifiant
     * @param id l'identifiant d'etape
     * @return
     */
    public EtapeIG getEtapeById(String id) {
        for (EtapeIG etape : this.getEtapes()) {
            if (etape.getIdentifiant().equals(id)) {
                return etape;
            }
        }
        return null;
    }

    /**
     * Fonction pour deplacer un etape
     * @param id
     * @param x
     * @param y
     */
    public void deplacerEtape(String id, double x, double y) {
        EtapeIG etape = getEtapeById(id);
        if (etape != null) {
            etape.setX((int)x);
            etape.setY((int)y);
        }
        notifierObservateurs();
    }



    public void supprimerArcs() {
        for(ArcIG arc : arcsSelectionnes){
            arcs.remove(arc);
        }
        arcsSelectionnes.clear();
        notifierObservateurs();
    }

    public void gererClicArc(ArcIG arc) {
        if (arcsSelectionnes.contains(arc)) {
            arcsSelectionnes.remove(arc);
            arc.toggleSelection();
        } else {
            arcsSelectionnes.add(arc);
            arc.toggleSelection();
        }
        notifierObservateurs();
    }

    public ArrayList<ArcIG> getArcsSelectionnes() {
        return arcsSelectionnes;
    }

    public void deselectionnerAll(){
        ArrayList<ArcIG> arcsCopy = new ArrayList<>(arcsSelectionnes);
        ArrayList<EtapeIG> etapesCopy = new ArrayList<>(etapesSelectionnees);

        for (ArcIG arc : arcsCopy){
            gererClicArc(arc);
        }

        for(EtapeIG etape: etapesCopy){
            gererClicEtape(etape);
        }
    }

    public void marquerCommeEntree() {
        ArrayList<EtapeIG> etapesCopy = new ArrayList<>(etapesSelectionnees);

        for (EtapeIG etape : etapesCopy) {
            if (etape.estEntree()) {
                etape.setEntree(false);
                afficherInfos(String.format("L'étape '%s' n'est plus une entrée.", etape.getNom()));
            } else if (etape.estSortie()) {
                etape.setSortie(false);
                etape.setEntree(true);
                afficherInfos(String.format("L'étape '%s' est maintenant une entrée.", etape.getNom()));
            } else {
                etape.setEntree(true);
                afficherInfos(String.format("L'étape '%s' est à nouveau une entrée.", etape.getNom()));
            }
        }
        notifierObservateurs();
    }


    public void marquerCommeSortie() {
        ArrayList<EtapeIG> etapesCopy = new ArrayList<>(etapesSelectionnees);

        for (EtapeIG etape : etapesCopy) {
            if (etape.estUnGuichet()) {
                afficherInfos(String.format("L'étape '%s' est un guichet et ne peut pas être une sortie.", etape.getNom()));
                continue;
            }

            if (etape.estSortie()) {
                etape.setSortie(false);
                afficherInfos(String.format("L'étape '%s' n'est plus une sortie.", etape.getNom()));
            } else if (etape.estEntree()) {
                etape.setEntree(false);
                etape.setSortie(true);
                afficherInfos(String.format("L'étape '%s' est maintenant une sortie.", etape.getNom()));
            } else {
                etape.setSortie(true);
                afficherInfos(String.format("L'étape '%s' est à nouveau une sortie.", etape.getNom()));
            }
        }
        notifierObservateurs();
    }




    private void afficherInfos(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Entrées / Sorties");
        alert.setHeaderText("Definition d'Entrées/ Sorties");
        alert.setContentText(message);

        alert.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

    public void setDelai() {
        if (getEtapesSelectionnees().size() == 1) {
            EtapeIG etape = getEtapesSelectionnees().get(0);

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Set Délai d'Étape");
            dialog.setHeaderText("Entrez un nouveau délai pour l'activité");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                try {
                    int delai = Integer.parseInt(input);
                    addDelaiToActivity(delai,etape);
                    notifierObservateurs();
                } catch (NumberFormatException e) {
                    afficherInfos("Entrée invalide, veuillez entrer un nombre.");
                } catch (IncorrectDelaiEcartException e) {
                    afficherInfos(e.getMessage());
                }
            });
        }
    }

    public void setEcart() {
        if (getEtapesSelectionnees().size() == 1) {
            EtapeIG etape = getEtapesSelectionnees().get(0);

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Set Écart d'Étape");
            dialog.setHeaderText("Entrez un nouvel écart pour l'activité");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                try {
                    int ecart = Integer.parseInt(input);
                    addEcartToActivity(ecart,etape);
                    notifierObservateurs();
                } catch (NumberFormatException e) {
                    afficherInfos("Entrée invalide, veuillez entrer un nombre.");
                } catch (IncorrectDelaiEcartException e) {
                    afficherInfos(e.getMessage());
                }
            });
        }
    }


    public void setNbJetons() {
        if (getEtapesSelectionnees().size() == 1) {
            EtapeIG etape = getEtapesSelectionnees().get(0);

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Set nombre de Jetons");
            dialog.setHeaderText("Entrez un nouveau Nombre de Jetons");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                try {
                    int ecart = Integer.parseInt(input);
                    addNombreJetonsToGuichet(ecart,etape);
                    notifierObservateurs();
                } catch (NumberFormatException e) {
                    afficherInfos("Entrée invalide, veuillez entrer un nombre.");
                } catch (IncorrectDelaiEcartException e) {
                    afficherInfos(e.getMessage());
                }
            });
        }
    }


    private void addDelaiToActivity(int delai, EtapeIG etape) throws IncorrectDelaiEcartException {
        if (delai < 1) {
            throw new IncorrectDelaiEcartException("Délai ne peut pas être inférieur à 1");
        }
        for (EtapeIG etapes : this.getEtapes()) {
            if (etapes.estUneActivite() && etapes.identifiant.equals(etape.identifiant)) {
                ((ActiviteIG) etapes).setDelai(delai);
            }
        }
    }

    private void addNombreJetonsToGuichet(int nombre, EtapeIG etape) throws IncorrectDelaiEcartException {
        if (nombre < 1) {
            throw new IncorrectDelaiEcartException("Délai ne peut pas être inférieur à 1");
        }
        for (EtapeIG etapes : this.getEtapes()) {
            if (etapes.estUnGuichet() && etapes.identifiant.equals(etape.identifiant)) {
                ((GuichetIG) etapes).setnbJetons(nombre);
            }
        }
    }

    private void addEcartToActivity(int ecart,EtapeIG etapes) throws IncorrectDelaiEcartException {
        if (ecart < 1) {
            throw new IncorrectDelaiEcartException("Écart ne peut pas être inférieur à 1");
        }
        for (EtapeIG etape : this.getEtapes()) {
            if (etape.estUneActivite()&& etape.identifiant.equals(etapes.identifiant)) {
                ((ActiviteIG) etape).setEcart(ecart);
            }
        }
    }


    public  void ajouterEtape(EtapeIG etape) {
        etapes.put(etape.identifiant, etape);
    }



}
