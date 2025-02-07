package twisk.simulation;

import twisk.monde.*;

public class Simulation {
    /**
     * Constructeur sans argument
     */
    public Simulation(){

    }

    /**
     * Affiche le monde sur la sortie standard
     * @param monde
     */
    public void simuler(Monde monde){
        System.out.println(monde.toString());
    }
}
