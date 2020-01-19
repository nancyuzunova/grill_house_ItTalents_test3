package skara;

public class Seller extends Thread {

    @Override
    public void run() {
        while(true){
            Portion order = CurPur.getInstance().getAnOrder();
            if(order != null) {
                double price = CurPur.getInstance().makePortion(order);
                CurPur.getInstance().getMoney(price);
                CurPur.getInstance().writeSale(order, price);
            }
        }
    }
}
