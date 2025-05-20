package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twiskIG.mondeIG.MondeIG;

public class EcouteurBouton implements EventHandler<ActionEvent> {
    private MondeIG monde;

    public EcouteurBouton(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent event) {
        monde.ajouter("Activité");
    }
}
