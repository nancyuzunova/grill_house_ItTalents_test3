package skara;

public class Client extends Thread{

    @Override
    public void run() {
        Portion order = createRandomPortion();
        CurPur.getInstance().addOrder(order);
    }

    private Portion createRandomPortion() {
        Bread bread = CurPur.getInstance().getRandomBread();
        Meat meat = CurPur.getInstance().getRandomMeat();
        Salad salad = CurPur.getInstance().getRandomSalad();
        return new Portion(bread, meat,salad);
    }
}
