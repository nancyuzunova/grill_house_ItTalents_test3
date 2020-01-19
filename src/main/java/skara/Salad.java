package skara;

public enum Salad {

    RUSSIAN(10, 1.5), LUTENICA(8, 1.1), SNOW_WHITE(4, 1.2), CABBAGE_CARROTS(2, 0.8), TOMATO_CUCUMBER(3, 1.3);

    private int timeForCooking;
    private double price;

    Salad(int timeForCooking, double price) {
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
