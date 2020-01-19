package skara;

import java.util.Random;

public class DogSparky extends Thread {

    @Override
    public void run() {
        try {
            while(true){
                Thread.sleep(5000);
                int chance = new Random().nextInt(20);
                if(chance == 0){
                    CurPur.getInstance().stealMeat();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Sparky has been interrupted");
        }
    }
}
