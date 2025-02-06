package main.java.twisk.simulation;

import main.java.twisk.monde.*;

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
        for (Etape e : monde.getLesEtapes()){
            System.out.println(e.toString());
        }
    }
}
