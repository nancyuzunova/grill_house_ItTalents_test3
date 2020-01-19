package skara;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Basket {

    public static final int BREAD_CAPACITY = 30;
    private BlockingQueue<Bread> breads;
    private Bread type;

    public Basket(Bread type) {
        this.breads = new ArrayBlockingQueue<Bread>(BREAD_CAPACITY, true);
        this.type = type;
    }

    public Bread getType() {
        return type;
    }

    public void addBread(Bread bread) throws InterruptedException {
        if(bread == this.type){
            Thread.sleep(bread.getTimeForCooking() * 1000);
            this.breads.put(bread);
        }
    }

    public void takeBread() {
        try {
            this.breads.take();
        } catch (InterruptedException e) {
            System.out.println("Taking bread has been interrupted");
        }
    }
}
