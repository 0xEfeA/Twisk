package twiskIG.mondeIG;

public class ArcIG {
    private final PointDeControleIG depart;
    private final PointDeControleIG arrivee;

    public ArcIG(PointDeControleIG depart, PointDeControleIG arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.estSelectionne = false;
    }

    public PointDeControleIG getDepart() {
        return depart;
    }

    public PointDeControleIG getArrivee() {
        return arrivee;
    }

    public boolean relie(PointDeControleIG p1, PointDeControleIG p2) {
        return (depart.equals(p1) && arrivee.equals(p2)) || (depart.equals(p2) && arrivee.equals(p1));
    }

    private boolean estSelectionne;

    public boolean estSelectionne() {
        return estSelectionne;
    }

    public void toggleSelection() {
        estSelectionne = !estSelectionne;
    }



    @Override
    public String toString() {
        return "ArcIG{" +
                "depart=(" + depart.getX() + "," + depart.getY() + ")" +
                ", arrivee=(" + arrivee.getX() + "," + arrivee.getY() + ")" +
                '}';
    }
}
