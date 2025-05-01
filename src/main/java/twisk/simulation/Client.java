package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    private int numeroClient;
    private int rang;
    private Etape etape;


    public Client(int numero) {
        this.numeroClient = numero;
    }

    /**
     * Met à jour les attributs etape et rang
     * @param etape Nouvelle étape
     * @param rang Nouveau rang
     */
    public void allerA(Etape etape , int rang) {
        this.etape = etape;
        this.rang = rang;
    }

    /**
     * Renvoie numéro client
     * @return numeroclient
     */
    public int getNumeroClient() {
        return numeroClient;
    }

    /**
     * Renvoie le rang du client
     * @return rang
     */
    public int getRang() {
        return rang;
    }
}
