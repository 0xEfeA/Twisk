package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twiskIG.mondeIG.MondeIG;

public class EcouteurBtnAddActivite implements EventHandler<ActionEvent> {
    private MondeIG monde;

    public EcouteurBtnAddActivite(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent event) {
        monde.ajouter("Activité");
    }
}
