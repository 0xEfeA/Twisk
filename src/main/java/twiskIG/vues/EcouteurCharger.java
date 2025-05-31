package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import twisk.monde.Etape;
import twiskIG.exceptions.ArcImpossibleException;
import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.outils.LecteurJson;

import java.io.File;
import java.io.IOException;

public class EcouteurCharger implements EventHandler<ActionEvent> {

    private MondeIG monde;
    public EcouteurCharger(MondeIG monde){
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Charger monde");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try{
                System.out.println(file.getAbsolutePath());
                MondeIG copie = LecteurJson.charger(file.getAbsolutePath());
                this.monde.getEtapes().clear();
                for(EtapeIG etape : copie.getEtapes()){
                    this.monde.ajouterEtape(etape);
                }

                this.monde.getArcs().clear();
                for(ArcIG arc : copie.getArcs()){
                    monde.ajouter(arc.getDepart(),arc.getArrivee());
                }
               monde.notifierObservateurs();

            } catch (IOException | ArcImpossibleException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
