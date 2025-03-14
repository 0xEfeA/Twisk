package twisk.monde;

public class SasEntree extends Activite{
    /**
     * Constructeur
     */
    public SasEntree() {
        super("sasEntree");
    }

    @Override
    public String toC() {
        StringBuilder sb = new StringBuilder();
        sb.append("// SasEntree\n ");
        //entrer(sasEntree); avec #define sasEntree 0
        sb.append("entrer(").append(nom).append(");\n");
        //transfert(sasEntree,successeur);
        for(Etape etape : this.getSuccesseurs()){
            sb.append("transfert(").append(this.nom).append(",").append(etape.getNom()).append(");\n");
        }
        return sb.toString();
    }
}
