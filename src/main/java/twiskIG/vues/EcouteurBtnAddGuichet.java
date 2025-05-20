package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twiskIG.mondeIG.MondeIG;

public class EcouteurBtnAddGuichet implements EventHandler<ActionEvent>{
    private MondeIG monde;

    public EcouteurBtnAddGuichet(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent event) {
        monde.ajouter("Guichet");
    }
}


