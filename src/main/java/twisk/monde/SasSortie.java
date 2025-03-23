package twisk.monde;

public class SasSortie extends Activite{
    /**
     * Constructeur
     */
    public SasSortie() {
        super("sasSortie");
    }

    @Override
    public String toC() {
        return "//SasSortie";
    }

    @Override
    public boolean estSasEntree() {
        return false;
    }

    @Override
    public boolean estSasSortie() {
        return true;
    }
}
