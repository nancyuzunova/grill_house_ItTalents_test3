package skara;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Pot {

    public static final int MEAT_CAPACITY = 20;
    private BlockingQueue<Meat> meats;
    private Meat type;

    public Pot(Meat type) {
        this.meats = new ArrayBlockingQueue<Meat>(MEAT_CAPACITY, true);
        this.type = type;
    }

    public Meat getType() {
        return type;
    }

    public void addMeat(Meat meat) throws InterruptedException {
        if(meat == this.type){
            Thread.sleep(meat.getTimeForCooking() * 1000);
            this.meats.put(meat);
        }
    }

    public void takeMeat() {
        try {
            this.meats.take();
        } catch (InterruptedException e) {
            System.out.println("Taking meat has been interrupted");
        }
    }

    public boolean isEmpty() {
        return this.meats.isEmpty();
    }
}
