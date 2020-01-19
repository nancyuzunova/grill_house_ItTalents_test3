package skara;

public class Portion {

    private Bread bread;
    private Meat meat;
    private Salad salad;
    private double price;

    public Portion(Bread bread, Meat meat, Salad salad) {
        this.bread = bread;
        this.meat = meat;
        this.salad = salad;
        calculatePrice();
    }

    public double getPrice() {
        return price;
    }

    private void calculatePrice() {
        this.price = bread.getPrice() + meat.getPrice() + salad.getPrice();
    }

    public Bread getBread() {
        return bread;
    }

    public Meat getMeat() {
        return meat;
    }

    public Salad getSalad() {
        return salad;
    }
}
