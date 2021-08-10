package ie.lukeandella.wedding.pojos;

public class GiftForDisplay {

    private Gift g;
    private boolean available;
    private boolean resByThisUser;

    public GiftForDisplay(Gift g, boolean available, boolean resByThisUser) {
        this.g = g;
        this.available = available;
        this.resByThisUser = resByThisUser;
    }

    public Gift getG() {
        return g;
    }

    public void setG(Gift g) {
        this.g = g;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isResByThisUser() {
        return resByThisUser;
    }

    public void setResByThisUser(boolean resByThisUser) {
        this.resByThisUser = resByThisUser;
    }

    @Override
    public String toString() {
        return "GiftForDisplay{" +
                "g=" + g.getId() +
                ", available=" + available +
                ", resByThisUser=" + resByThisUser +
                '}';
    }
}
