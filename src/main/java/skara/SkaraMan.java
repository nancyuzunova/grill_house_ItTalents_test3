package skara;

public class SkaraMan extends Thread{

    @Override
    public void run() {
        while (true){
            try {
                CurPur.getInstance().bakeMeat();
            } catch (InterruptedException e) {
                System.out.println("skara.SkaraMan has been interrupted!");
            }
        }
    }
}
