package ie.lukeandella.wedding.pojos;

import org.springframework.context.annotation.Bean;

public class Percentage {

    private int p;

    public Percentage(){}

    public Percentage(int p) {
        this.p = p;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }
}
