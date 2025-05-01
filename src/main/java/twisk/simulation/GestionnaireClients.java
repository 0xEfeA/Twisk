package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireClients  implements Iterable<Client>{
    private ArrayList<Client> listeClients;

    public GestionnaireClients(){
        this.listeClients = new ArrayList<>();
    }

    /**
     * instancie les clients identifiés par leur numéro de processus (numéro de client)
     * @param tabClients
     */
    void setClient(int... tabClients){
        for ( int numerosClient : tabClients ) {
            listeClients.add(new Client(numerosClient));
        }

    }

    /**
     * Iterateur sur les clients
     * @return iterateur
     */
    @Override
    public Iterator<Client> iterator() {
        return listeClients.iterator();
    }

    /**
     * Vide la liste
     */
    public void nettoyer(){
        listeClients.clear();
    }

    /**
     * Met à jour attribut client
     * @param numeroClient
     * @param etape
     * @param rang
     */
    public void allerA(int numeroClient, Etape etape, int rang){
        Client client = listeClients.get(numeroClient);
        client.allerA(etape, rang);
    }
}
