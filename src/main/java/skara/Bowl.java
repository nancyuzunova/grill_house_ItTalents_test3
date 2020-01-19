package skara;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Bowl {

    public static final int SALAD_CAPACITY_GRAMS = 5000;
    public static final int GRAMS_FOR_EACH_CUTTING = 500;
    public static final int GRAMS_SALAD_PER_PORTION = 200;
    private BlockingQueue<Salad> salad;
    private Salad type;

    public Bowl(Salad type) {
        this.salad = new ArrayBlockingQueue<Salad>(SALAD_CAPACITY_GRAMS, true);
        this.type = type;
    }

    public Salad getType() {
        return type;
    }

    public void addSalad(Salad salad) throws InterruptedException {
        if(salad == this.type){
            Thread.sleep(salad.getTimeForCooking()*1000);
            for (int i = 0; i < GRAMS_FOR_EACH_CUTTING; i++) {
                this.salad.put(salad);
            }
        }
    }

    public void takeSalad() {
        for (int i = 0; i < GRAMS_SALAD_PER_PORTION; i++) {
            try {
                this.salad.take();
            } catch (InterruptedException e) {
                System.out.println("Taking salad has been interrupted");
            }
        }
    }
}
