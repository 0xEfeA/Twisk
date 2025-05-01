package twisk.simulation;

import twisk.monde.Etape;

import java.util.HashMap;
import java.util.Iterator;

public class GestionnaireClients  implements Iterable<Client>{
    private HashMap<Integer,Client> listeClients;

    public GestionnaireClients(){
        this.listeClients = new HashMap<Integer,Client>();
    }

    /**
     * instancie les clients identifiés par leur numéro de processus (numéro de client)
     * @param tabClients
     */
    public void setClient(int... tabClients){
        for ( int numerosClient : tabClients ) {
            listeClients.put(numerosClient,new Client(numerosClient));
        }

    }

    /**
     * Iterateur sur les clients
     * @return iterateur
     */
    @Override
    public Iterator<Client> iterator() {
        return listeClients.values().iterator();
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

    /**
     * Renvoie nb clients
     * @return
     */
    public int getNombreClients(){
        return listeClients.size();
    }

    /**
     * Renvoie client à l'indice numeroClient
     * @param numeroClient
     * @return
     */
    public Client getClient(int numeroClient){
        return listeClients.get(numeroClient);
    }

}
