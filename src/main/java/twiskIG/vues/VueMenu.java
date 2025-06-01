package twiskIG.vues;

import javafx.application.Platform;
import javafx.scene.control.*;
import twisk.outils.ThreadsManager;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;

import java.util.Optional;

public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;
    private SimulationIG simulation;

    public VueMenu(MondeIG monde, SimulationIG sim) {
        this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        this.monde = monde;
        this.simulation = sim;
        this.monde.ajouterObservateur(this);

        Menu menuFichier = new Menu("Fichier");
        MenuItem initialise = new MenuItem("New Monde");
        initialise.setOnAction(event ->monde.supprimerToutesLesEtapes());
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(event -> {
            ThreadsManager.getInstance().detruireTout();
            Platform.exit();
        });

        MenuItem sauvegarder = new MenuItem("Sauvegarder");
        sauvegarder.setOnAction(new EcouteurSauvegarder(monde));
        MenuItem charger = new MenuItem("Charger");
        charger.setOnAction(new EcouteurCharger(monde));
        menuFichier.getItems().addAll(sauvegarder,charger,initialise,quitter);

        Menu menuEdition = new Menu("Édition");
        MenuItem supprimer = new MenuItem("Supprimer");
        supprimer.setOnAction(event -> {
            monde.supprimerEtapesSelectionnees();
            monde.supprimerArcs();
        });

        MenuItem renommer = new MenuItem("Renommer");
        renommer.setOnAction(event -> monde.renommerSelection());

        MenuItem effacer = new MenuItem("Effacer");
        effacer.setOnAction(event ->monde.deselectionnerAll());

        menuEdition.getItems().addAll(supprimer, renommer,effacer);

         Menu menuMonde = new Menu("Monde");
         MenuItem entree = new MenuItem("Entrée");
         entree.setOnAction(event->monde.marquerCommeEntree());
         MenuItem sortie = new MenuItem("Sortie");
         sortie.setOnAction(event->monde.marquerCommeSortie());

         menuMonde.getItems().addAll(entree,sortie);

         Menu parametre = new Menu("Paramètres");
         MenuItem delai = new MenuItem("Délai");
         delai.setOnAction(event->monde.setDelai());
         MenuItem ecart = new MenuItem("Ecart");
         ecart.setOnAction(event->monde.setEcart());
        MenuItem nbjetons = new MenuItem("NbJetons");
        nbjetons.setOnAction(event->monde.setNbJetons());
        MenuItem nbClients = new MenuItem("NbClients");
        nbClients.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Paramètre - Nombre de clients");
            dialog.setHeaderText("Définir le nombre de clients pour la simulation");
            dialog.setContentText("Nombre de clients :");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(input -> {
                try {
                    int nbClientsValue = Integer.parseInt(input.trim());
                    simulation.setNbClients(nbClientsValue);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Entrée invalide");
                    alert.setContentText("Veuillez entrer un nombre entier valide.");
                    alert.showAndWait();
                }
            });
        });

        parametre.getItems().addAll(delai, ecart, nbjetons, nbClients);

        Menu loiArrivee = new Menu("Loi arrivee");
        MenuItem loiUniforme = new MenuItem("Loi uniforme");
        MenuItem loiGaussienne = new MenuItem("Loi Gaussienne");
        MenuItem loiExponentiel = new MenuItem("Loi Exponentiel");
        loiUniforme.setOnAction(event -> monde.setNomLoiArrivee("delaiUniforme"));
        loiGaussienne.setOnAction(event -> monde.setNomLoiArrivee("delaiGauss"));
        loiExponentiel.setOnAction(event -> monde.setNomLoiArrivee("delaiExponentiel"));
        loiArrivee.getItems().addAll(loiUniforme,loiGaussienne,loiExponentiel);
        this.getMenus().addAll(menuFichier, menuEdition,menuMonde,parametre,loiArrivee);
        this.reagir();
    }

    @Override
    public void reagir() {
        if (monde.getEtapesSelectionnees().size() == 1) {
            this.getMenus().get(1).getItems().get(1).setDisable(false); //renommer
            this.getMenus().get(3).getItems().getFirst().setDisable(false); //delai
            this.getMenus().get(3).getItems().get(1).setDisable(false); //ecart
        } else {
            this.getMenus().get(1).getItems().get(1).setDisable(true);
            this.getMenus().get(3).getItems().getFirst().setDisable(true);
            this.getMenus().get(3).getItems().get(1).setDisable(true);
            this.getMenus().get(3).getItems().get(2).setDisable(true); //nbjetons
        }
        if (monde.getEtapesSelectionnees().size() == 1 && monde.getEtapesSelectionnees().getFirst().estUnGuichet()) {
            this.getMenus().get(3).getItems().get(2).setDisable(false);
            this.getMenus().get(3).getItems().getFirst().setDisable(true);
            this.getMenus().get(3).getItems().get(1).setDisable(true);
        }

    }

}
