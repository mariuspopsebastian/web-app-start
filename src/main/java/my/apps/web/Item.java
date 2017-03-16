package my.apps.web;

/**
 * @author flo
 * @since 02/03/2017.
 */
public class Item {
    private String nume;
    private int cantitate;

    public Item(String nume, int cantitate) {
        this.nume = nume;
        this.cantitate = cantitate;
    }

    public String getNume() {
        return nume;
    }

    public int getCantitate() {
        return cantitate;
    }
}
