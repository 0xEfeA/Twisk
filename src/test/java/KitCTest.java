
import org.junit.jupiter.api.*;
import twisk.outils.KitC;

import java.nio.file.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class KitCTest {
    private static final Path TWISK_DIR = Paths.get("/tmp/twisk");
    private static final Path CLIENT_FILE = TWISK_DIR.resolve("client.c");
    private KitC kitC;

    @BeforeEach
    void setUp() {
        kitC = new KitC();
        kitC.creerEnvironnement();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up: delete created files and directory
        Files.deleteIfExists(CLIENT_FILE);
        Files.deleteIfExists(TWISK_DIR.resolve("programmeC.o"));
        Files.deleteIfExists(TWISK_DIR.resolve("def.h"));
        Files.deleteIfExists(TWISK_DIR);
    }

    @Test
    void testCreerEnvironnement() {
        assertTrue(Files.exists(TWISK_DIR), "Le répertoire /tmp/twisk devrait exister");
        assertTrue(Files.exists(TWISK_DIR.resolve("programmeC.o")), "Le fichier programmeC.o devrait être copié");
        assertTrue(Files.exists(TWISK_DIR.resolve("def.h")), "Le fichier def.h devrait être copié");
    }

    @Test
    void testCreerFichier() throws IOException {
        String codeC = "int main() { return 0; }";
        kitC.creerFichier(codeC);

        assertTrue(Files.exists(CLIENT_FILE), "Le fichier client.c devrait être créé");
        String contenu = Files.readString(CLIENT_FILE);
        assertEquals(codeC, contenu, "Le fichier client.c devrait contenir le bon code");
    }
}
