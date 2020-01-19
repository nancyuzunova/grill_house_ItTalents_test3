package skara;

import db.DbDAO;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CurPur {

    public static final int MY_SHOP_ID = 4;
    private static CurPur mInstance;

    public void stealMeat() {
        Meat meat = getRandomMeat();
        Pot pot = getProperPot(meat);
        if(!pot.isEmpty()){
            pot.takeMeat();
        }
        else{
            System.out.println("Bau bau.. I don't have luck...");
        }
    }

    public class Entity{
        private int shopId;
        private Bread breadType;
        private Meat meatType;
        private Salad garnishType;
        private LocalDate date;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public Bread getBreadType() {
            return breadType;
        }

        public void setBreadType(Bread breadType) {
            this.breadType = breadType;
        }

        public Meat getMeatType() {
            return meatType;
        }

        public void setMeatType(Meat meatType) {
            this.meatType = meatType;
        }

        public Salad getGarnishType() {
            return garnishType;
        }

        public void setGarnishType(Salad garnishType) {
            this.garnishType = garnishType;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

    private double money;
    private ArrayList<Pot> pots;
    private ArrayList<Basket> baskets;
    private ArrayList<Bowl> bowls;
    private BlockingQueue<Portion> orders;
    private Map<LocalDateTime, Portion> helperNotebook;
    private File notebook = new File("notebook.txt");

    private CurPur() {
        this.pots = new ArrayList<Pot>();
        this.pots.add(new Pot(Meat.MEATBALL));
        this.pots.add(new Pot(Meat.PLESKAVITSA));
        this.pots.add(new Pot(Meat.STACK));

        this.baskets = new ArrayList<Basket>();
        this.baskets.add(new Basket(Bread.WHITE));
        this.baskets.add(new Basket(Bread.WHOLE_GRAIN));

        this.bowls = new ArrayList<Bowl>();
        this.bowls.add(new Bowl(Salad.CABBAGE_CARROTS));
        this.bowls.add(new Bowl(Salad.SNOW_WHITE));
        this.bowls.add(new Bowl(Salad.LUTENICA));
        this.bowls.add(new Bowl(Salad.RUSSIAN));
        this.bowls.add(new Bowl(Salad.TOMATO_CUCUMBER));

        this.orders = new ArrayBlockingQueue<Portion>(100);
        this.helperNotebook = new HashMap<>();
    }

    public synchronized static CurPur getInstance() {
        if (mInstance == null) {
            mInstance = new CurPur();
        }
        return mInstance;
    }

    public void bakeMeat() throws InterruptedException {
        Meat meat = getRandomMeat();
        for(Pot pot : pots){
            if(pot.getType() == meat){
                pot.addMeat(meat);
            }
        }
    }

    public Meat getRandomMeat() {
        return Meat.values()[new Random().nextInt(Meat.values().length)];
    }

    public void kneadBread() throws InterruptedException {
        Bread bread = getRandomBread();
        for(Basket basket : baskets){
            if(basket.getType() == bread){
                basket.addBread(bread);
            }
        }
    }

    public Bread getRandomBread() {
        if(new Random().nextBoolean()){
            return Bread.WHITE;
        }
        else{
            return Bread.WHOLE_GRAIN;
        }
    }

    public void makeSalad() throws InterruptedException {
        Salad salad = getRandomSalad();
        for(Bowl bowl : bowls){
            if(bowl.getType() == salad){
                bowl.addSalad(salad);
            }
        }
    }

    public Salad getRandomSalad() {
        return Salad.values()[new Random().nextInt(Salad.values().length)];
    }

    public void addOrder(Portion order) {
        try {
            this.orders.put(order);
        } catch (InterruptedException e) {
            System.out.println("Ordering interrupted");
        }
    }

    public Portion getAnOrder() {
        try {
            return this.orders.take();
        } catch (InterruptedException e) {
            System.out.println("Getting order has been interrupted");
        }
        return null;
    }

    public double makePortion(Portion order) {
        Basket basket = getProperBasket(order.getBread());
        Pot pot = getProperPot(order.getMeat());
        Bowl bowl = getProperBowl(order.getSalad());
        if(basket != null && pot != null && bowl != null) {
            basket.takeBread();
            pot.takeMeat();
            bowl.takeSalad();
        }
        return order.getPrice();
    }

    private Bowl getProperBowl(Salad salad) {
        for(Bowl bowl : bowls){
            if(salad == bowl.getType()){
                return bowl;
            }
        }
        return null;
    }

    private Pot getProperPot(Meat meat) {
        for(Pot pot : pots){
            if(meat == pot.getType()){
                return pot;
            }
        }
        return null;
    }

    private Basket getProperBasket(Bread bread) {
        for(Basket basket : baskets){
            if(bread == basket.getType()){
                return basket;
            }
        }
        return null;
    }

    public void getMoney(double price) {
        this.money += price;
    }

    public synchronized void writeSale(Portion order, double price) {
        try(PrintWriter pr = new PrintWriter(new FileOutputStream(notebook, true))){
            pr.println(order.getBread() + ", " + order.getMeat() + ", " + order.getSalad() + " - " +
                    price + ", " + LocalDateTime.now());
        } catch (FileNotFoundException e) {
            System.out.println("Error! Writing to file failed! " + e.getMessage());
        }
        this.helperNotebook.put(LocalDateTime.now(), order);
    }

    public synchronized void readNotebook() {
        try(Scanner scanner = new Scanner(new FileInputStream(notebook))){
            //reading the file
            while(scanner.hasNextLine()){
                scanner.nextLine();
            }
            notebook.delete();
            //using map for helping me write to DB because splitting string would take me more time
            Entity entity = new Entity();
            for(Map.Entry<LocalDateTime, Portion> e  : this.helperNotebook.entrySet()){
                entity.setDate(e.getKey().toLocalDate());
                entity.setBreadType(e.getValue().getBread());
                entity.setGarnishType(e.getValue().getSalad());
                entity.setMeatType(e.getValue().getMeat());
                entity.setShopId(MY_SHOP_ID);
                DbDAO.getInstance().insertIntoSalesTable(entity);
            }
            this.helperNotebook.clear();
        } catch (FileNotFoundException e) {
            System.out.println("Error! Reading from file failed! " + e.getMessage());
        } catch (SQLException e){
            System.out.println("Error! Writing do DB failed! " + e.getMessage());
        }
    }
}
