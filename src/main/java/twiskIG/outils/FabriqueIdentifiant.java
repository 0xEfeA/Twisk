package twiskIG.outils;

public class FabriqueIdentifiant {
    private static FabriqueIdentifiant instance;
    private int noEtape = 0;

    /**
     * Private constructor
     */
    private FabriqueIdentifiant() {}

    /**
     * Returns the single instance of FabriqueIdentifiant.
     * @return The singleton instance of FabriqueIdentifiant.
     */
    public static FabriqueIdentifiant getInstance() {
        if (instance == null) {
            instance = new FabriqueIdentifiant();
        }
        return instance;
    }

    /**
     * Generates a new unique Etape identifier.
     * @return A unique string identifier for Etape.
     */
    public String getIdentifiantEtape() {
        return String.valueOf(noEtape++);
    }

    public void resetIdentifiant() {
        this.noEtape = 0;
    }
}
