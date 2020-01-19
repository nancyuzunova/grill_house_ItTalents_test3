package skara;

public class SaladMan extends Thread {

    @Override
    public void run() {
        while(true){
            try {
                CurPur.getInstance().makeSalad();
            } catch (InterruptedException e) {
                System.out.println("skara.SaladMan has been interrupted");
            }
        }
    }
}
