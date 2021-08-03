package ie.lukeandella.wedding.pojos;

public class GiftForDisplay {

    private Gift g;
    private boolean res;

    public GiftForDisplay(Gift g, boolean res) {
        this.g = g;
        this.res = res;
    }

    public Gift getG() {
        return g;
    }

    public void setG(Gift g) {
        this.g = g;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }
}
