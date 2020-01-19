package skara;

public class BreadWoman extends Thread {

    @Override
    public void run() {
        while(true){
            try {
                CurPur.getInstance().kneadBread();
            } catch (InterruptedException e) {
                System.out.println("skara.Bread Woman has been interrupted");
            }
        }
    }
}
