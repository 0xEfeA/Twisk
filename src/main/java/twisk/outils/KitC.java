package twisk.outils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class KitC {
    /**
     * Function qui creer le répertoire twisk sous /tmp/
     * puis y recopie les deux fichiers programmeC.o et def.h.
     */
    public void creerEnvironnement(){
        Path directory = Paths.get("/tmp/twisk");
        try {
            // création du répertoire twisk sous /tmp.
            // Ne déclenche pas d’erreur si le répertoire existe déjà
            Files.createDirectories(directory);
            // copie des fichiers programmeC.o et def.h sous /tmp/twisk
            String[] liste = {"programmeC.o", "def.h","codeNatif.o","lois.c","lois.h","twisk_simulation_Simulation.h"};
            for (String nom : liste) {
                InputStream src = getClass().getResourceAsStream("/codeC/" + nom);
                if (src == null) {
                    throw new IOException("Fichier introuvable: " + nom);
                }
                Path dest = directory.resolve(nom);
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui creer le fichier client.c
     */
    public void creerFichier(String codeC) {
        Path fichier = Paths.get("/tmp/twisk/client.c");
        try {
            // Écriture du code dans le fichier client.c (écrase s'il existe déjà)
            Files.writeString(fichier, codeC, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compile le fichier client.c en client.o en exécutant la commande GCC.
     */
    public void compiler() {

        ProcessBuilder pb = new ProcessBuilder("gcc", "-Wall", "-ansi", "-pedantic", "-fPIC", "-c", "/tmp/twisk/client.c", "-o", "/tmp/twisk/client.o");
        ProcessBuilder pb2 = new ProcessBuilder("gcc", "-Wall", "-std=c99", "-fPIC", "-c", "/tmp/twisk/lois.c", "-o", "/tmp/twisk/lois.o");

        try {
            pb.inheritIO().start().waitFor();
            pb2.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construction de la bobliothèque libTwisk.so
     */
    public void construireLaBibliotheque() {
        ProcessBuilder pb = new ProcessBuilder("gcc", "-shared", "/tmp/twisk/programmeC.o","/tmp/twisk/codeNatif.o", "/tmp/twisk/client.o","/tmp/twisk/lois.o",  "-o", "/tmp/twisk/libTwisk.so","-lm");
        try {
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
