package skara;

public enum Meat {

    MEATBALL(2, 1), PLESKAVITSA(3, 2), STACK(4, 3);

    private int timeForCooking;
    private double price;

    Meat(int timeForCooking, double price) {
        this.timeForCooking = timeForCooking;
        this.price = price;
    }

    //in seconds
    public int getTimeForCooking() {
        return timeForCooking;
    }

    public double getPrice() {
        return price;
    }
}
