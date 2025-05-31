package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import twiskIG.mondeIG.MondeIG;
import twiskIG.outils.LecteurJson;

import java.io.File;
import java.io.IOException;

public class EcouteurSauvegarder implements EventHandler<ActionEvent> {
    private MondeIG monde;

    public EcouteurSauvegarder(MondeIG monde){
        this.monde = monde;
    }




    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Sauvegarder");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json", "*.json"));
        File file = chooser.showSaveDialog(null);
        if(file != null){
            try{
                String path = file.getAbsolutePath();
                if(!path.endsWith(".json")){
                    path += ".json";
                }
                LecteurJson.sauvegarder(monde,path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
