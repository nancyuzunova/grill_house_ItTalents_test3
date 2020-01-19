package skara;

import db.DbDAO;

import java.sql.SQLException;

public class SkaraOwner extends Thread {

    @Override
    public void run() {
        try {
            int days = 0;
            while(true){
                while(days < 7) {
                    Thread.sleep(24 * 1000); //once a day
                    CurPur.getInstance().readNotebook();
                    days++;
                }
                if(days == 7){
                    printStatistics();
                    days = 0;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Skara Owner has been interrupted");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void printStatistics() throws SQLException {
        DbDAO.getInstance().printNumberOfSales();
        DbDAO.getInstance().printMostSoldMeat();
        DbDAO.getInstance().printShopsWithTheirSales();
        DbDAO.getInstance().printNumberOfPleskavitsa();
        DbDAO.getInstance().printMostSoldSalad();
        DbDAO.getInstance().printLeastSoldSalad();
        DbDAO.getInstance().printTotal();
        DbDAO.getInstance().printShopWithMostWholeGrains();
        DbDAO.getInstance().printSoldGramsOfSalad();
        //DbDAO.getInstance().printMostProsperousShop();
    }

}
