import org.junit.Test;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    @Test
    public void testnbClient() {
        GestionnaireClients gestionnaire = new GestionnaireClients();
        gestionnaire.setClient(1,2,3,4);
        assertEquals(4,gestionnaire.getNombreClients(),"Le nombre de client dans le gestionnaire doit être 4");
    }

    @Test
    public void testNumero() {
        GestionnaireClients gestionnaire = new GestionnaireClients();
        gestionnaire.setClient(1,2,3,4);
        Client client = gestionnaire.getClient(1);
        assertEquals(1, client.getNumeroClient(),"Le numéro du client 1 doit être 1");
    }
    @Test
    public void testRang() {
        GestionnaireClients gestionnaire = new GestionnaireClients();
        gestionnaire.setClient(1,2,3,4);
        Client client = gestionnaire.getClient(2);
        client.allerA(null, 55);
        assertEquals(55,client.getRang(),"Le client doit être rang 55");
    }


}