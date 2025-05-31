package twiskIG.outils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import twiskIG.exceptions.ArcImpossibleException;
import twiskIG.mondeIG.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilisation de la librairie org.json car c'est celle que l'on a utilisé
 * pour le projet de collection en IG
 */
public class LecteurJson {

    /**
     * Créer un fichier Json représentant le mondeIG
     * @param mondeIG
     * @param fichierChemin
     * @throws IOException
     */
    public static void sauvegarder(MondeIG mondeIG, String fichierChemin) throws IOException {
        JSONObject root = new JSONObject();
        JSONArray etapes = new JSONArray();
        //On parcourt toutes les étapes du monde pour créer leurs objets et les ajouter
        for (EtapeIG etape : mondeIG.getEtapes()) {
            JSONObject objetEtape = new JSONObject();
            //récupération des attributs communs entre Etape et Guichet
            objetEtape.put("identifiant", etape.getIdentifiant());
            objetEtape.put("nom", etape.getNom());
            objetEtape.put("posX", etape.getX());
            objetEtape.put("posY", etape.getY());
            objetEtape.put("largeur", etape.getLargeur());
            objetEtape.put("hauteur", etape.getHauteur());
            objetEtape.put("estEntree", etape.estEntree());
            objetEtape.put("estSortie", etape.estSortie());
            //Pour les attributs différents on sépare les activités et les guichets
            if (etape.estUneActivite()) {
                objetEtape.put("type", "Activite");
                ActiviteIG activite = (ActiviteIG) etape;
                objetEtape.put("delai", activite.getDelai());
                objetEtape.put("ecart", activite.getEcart());
                objetEtape.put("estActiviteRestreinte", activite.estUneActiviteRestreinte());
            } else if (etape.estUnGuichet()) {
                objetEtape.put("type", "Guichet");
                GuichetIG guichet = (GuichetIG) etape;
                objetEtape.put("nbJetons", guichet.getnbJetons());
            }

            etapes.put(objetEtape);
        }
        root.put("etapes", etapes);

        //On parcourt tous les arcs
        JSONArray arcs = new JSONArray();
        for (ArcIG arc : mondeIG.getArcs()) {
            JSONObject arcJson = new JSONObject();
            //Récupération des points de contrôles de l'arc
            PointDeControleIG depart = arc.getDepart();
            PointDeControleIG arrivee = arc.getArrivee();
            //récupération des étapes de l'arc
            EtapeIG etapeDepart = depart.getEtape();
            EtapeIG etapeArrivee = arrivee.getEtape();
            //On récupère l'indice des points parmi les 4 points sur chaque étapes
            int indiceDepart = etapeDepart.getPointsDeControle().indexOf(depart);
            int indiceArrivee = etapeArrivee.getPointsDeControle().indexOf(arrivee);
            arcJson.put("EtapeDepart", etapeDepart.getIdentifiant());
            arcJson.put("IndiceDepart", indiceDepart);
            arcJson.put("EtapeArrivee", etapeArrivee.getIdentifiant());
            arcJson.put("IndiceArrivee", indiceArrivee);
            arcs.put(arcJson);
        }
        root.put("arcs", arcs);

        try (FileWriter writer = new FileWriter(fichierChemin)) {
            writer.write(root.toString());
        }
    }

    /**
     * Permet de charger un monde depuis un fichier Json
     * @param fichierChemin
     * @return
     * @throws IOException
     */
    public static MondeIG charger(String fichierChemin) throws IOException {

        FileReader reader = new FileReader(fichierChemin);
        JSONObject root = new JSONObject(new JSONTokener(reader));

        MondeIG mondeIG = new MondeIG();
        FabriqueIdentifiant.getInstance().resetIdentifiant();
        Map<String, EtapeIG> Idetapes = new HashMap<>();
        JSONArray etapes = root.getJSONArray("etapes");
        for (int i = 0; i < etapes.length(); i++) {
            JSONObject etape = etapes.getJSONObject(i);
            String identifiant = etape.getString("identifiant");
            String nom = etape.getString("nom");
            int posX = etape.getInt("posX");
            int posY = etape.getInt("posY");
            int largeur = etape.getInt("largeur");
            int hauteur = etape.getInt("hauteur");
            boolean estEntree = etape.getBoolean("estEntree");
            boolean estSortie = etape.getBoolean("estSortie");
            String type = etape.getString("type");
            EtapeIG etapeIG = null;
            if (type.equals("Activite")) {
                int delai = etape.getInt("delai");
                int ecart = etape.getInt("ecart");
                boolean restreint = etape.getBoolean("estActiviteRestreinte");
                ActiviteIG activiteIG = new ActiviteIG(nom,largeur,hauteur,identifiant);
                activiteIG.setDelai(delai);
                activiteIG.setEcart(ecart);
                activiteIG.setEstActiviteRestreinte(restreint);
                activiteIG.setEntree(estEntree);
                activiteIG.setSortie(estSortie);
                activiteIG.setX(posX);
                activiteIG.setY(posY);
                etapeIG = activiteIG;

            }else if (type.equals("Guichet")) {
                int nbJetons = etape.getInt("nbJetons");
                GuichetIG guichet = new GuichetIG(nom,largeur,hauteur,identifiant);
                guichet.setnbJetons(nbJetons);
                guichet.setX(posX);
                guichet.setY(posY);
                guichet.setEntree(estEntree);
                guichet.setSortie(estSortie);
                etapeIG = guichet;
            }

            Idetapes.put(identifiant,etapeIG);
            mondeIG.ajouterEtape(etapeIG);


        }

        JSONArray arcs = root.getJSONArray("arcs");
        for (int i = 0; i < arcs.length(); i++) {
            JSONObject arc = arcs.getJSONObject(i);
            String idDepart    = arc.getString("EtapeDepart");
            int indiceDepart   = arc.getInt("IndiceDepart");
            String idArrivee    = arc.getString("EtapeArrivee");
            int indiceArrivee   = arc.getInt("IndiceArrivee");
            EtapeIG etapeDepart  = Idetapes.get(idDepart);
            EtapeIG etapeArrivee  = Idetapes.get(idArrivee);
            PointDeControleIG pointDepart = etapeDepart.getPointsDeControle().get(indiceDepart);
            PointDeControleIG pointArrivee = etapeArrivee.getPointsDeControle().get(indiceArrivee);
            try{
                mondeIG.ajouter(pointArrivee,pointDepart);
            } catch (ArcImpossibleException e) {
                throw new RuntimeException(e);
            }

        }
        return mondeIG;
    }

}

