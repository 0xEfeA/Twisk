package twiskIG.vues;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import twiskIG.mondeIG.MondeIG;

public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;

    public VueMenu(MondeIG monde) {
        this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        this.monde = monde;
        this.monde.ajouterObservateur(this);

        Menu menuFichier = new Menu("Fichier");
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(event -> Platform.exit());
        menuFichier.getItems().add(quitter);

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

         parametre.getItems().addAll(delai,ecart);

        this.getMenus().addAll(menuFichier, menuEdition,menuMonde,parametre);
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
        }
    }

}
