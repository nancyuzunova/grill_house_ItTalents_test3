package skara;

public enum Bread {

    WHITE(2, 0.9), WHOLE_GRAIN(5, 1.4);

    private int timeForCooking;
    private double price;

    Bread(int timeForCooking, double price) {
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
