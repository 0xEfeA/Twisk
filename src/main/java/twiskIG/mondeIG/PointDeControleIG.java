package twiskIG.mondeIG;

public class PointDeControleIG {
    private int x, y;
    private EtapeIG etape;

    public PointDeControleIG(int x, int y, EtapeIG etape) {
        this.x = x;
        this.y = y;
        this.etape = etape;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public EtapeIG getEtape() { return etape; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

