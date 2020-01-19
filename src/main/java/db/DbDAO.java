package db;

import skara.Bread;
import skara.CurPur;

import java.sql.*;

public class DbDAO {

    private static DbDAO mInstance;

    private DbDAO() {
    }

    public synchronized static DbDAO getInstance() {
        if (mInstance == null) {
            mInstance = new DbDAO();
        }
        return mInstance;
    }

    public void insertIntoSalesTable(CurPur.Entity entity) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "INSERT INTO sales (shop_id, bread_type_id, meat_type_id, garnish_type_id, date_created) VALUES (?,?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        int breadID = entity.getBreadType() == Bread.WHITE ? 1 : 2;
        int meatID = 0;
        switch(entity.getMeatType()){
            case MEATBALL: meatID = 1; break;
            case PLESKAVITSA: meatID = 2; break;
            case STACK: meatID = 3; break;
        };
        int garnishID = 0;
        switch (entity.getGarnishType()){
            case RUSSIAN: garnishID = 1; break;
            case LUTENICA: garnishID = 2; break;
            case SNOW_WHITE: garnishID = 3; break;
            case CABBAGE_CARROTS: garnishID = 4; break;
            case TOMATO_CUCUMBER: garnishID = 5; break;
        }
        statement.setInt(1, entity.getShopId());
        statement.setInt(2, breadID);
        statement.setInt(3, meatID);
        statement.setInt(4, garnishID);
        statement.setDate(5, Date.valueOf(entity.getDate()));
        statement.executeUpdate();
        statement.close();
    }

    public void printNumberOfSales() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT COUNT(*) AS total FROM sales WHERE shop_id = " + CurPur.MY_SHOP_ID + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println("Total sales = " + set.getInt("total"));
        }
        set.close();
        statement.close();
    }

    public void printMostSoldMeat() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT s.shop_id, m.name, COUNT(*) AS meat FROM test3_java.sales AS s" +
                "JOIN meat_types AS m ON (s.meat_type_id = m.id)" +
                "GROUP BY s.meat_type_id HAVING shop_id = 4 ORDER BY meat DESC LIMIT 1;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println("Most sold meat : " + set.getString("name"));
        }
        set.close();
        statement.close();
    }

    public void printShopsWithTheirSales() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT s.shop_id, sh.name, COUNT(*) AS total_sales FROM test3_java.sales AS s JOIN shops AS sh ON (s.shop_id = sh.id) GROUP BY s.shop_id;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println(set.getString("name") + " - " + set.getInt("total_sales") + " sales");
        }
        set.close();
        statement.close();
    }

    public void printNumberOfPleskavitsa() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT COUNT(*) AS total FROM sales WHERE meat_type_id = 2;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println("Total pleskavitsa = " + set.getInt("total"));
        }
        set.close();
        statement.close();
    }

    public void printMostSoldSalad() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT g.name, COUNT(*) AS total FROM sales AS s " +
                "JOIN garnish_types AS g ON s.garnish_type_id = g.id " +
                "GROUP BY garnish_type_id ORDER BY total DESC LIMIT 1;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println("Most sold salad : " + set.getString("name"));
        }
        set.close();
        statement.close();
    }

    public void printLeastSoldSalad() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT g.name, COUNT(*) AS total FROM sales AS s " +
                "JOIN garnish_types AS g ON s.garnish_type_id = g.id " +
                "WHERE s.shop_id = 4" +
                "group by garnish_type_id" +
                "ORDER BY total LIMIT 1;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println("Least sold salad : " + set.getString("name"));
        }
        set.close();
        statement.close();
    }

    public void printTotal() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT COUNT(bread_type_id) AS stock, b.price FROM test3_java.sales AS s JOIN bread_types AS b ON s.bread_type_id = b.id WHERE s.shop_id = 4 GROUP BY s.bread_type_id" +
                "UNION " +
                "SELECT COUNT(garnish_type_id) AS stock, g.price FROM test3_java.sales AS s JOIN garnish_types AS g ON s.garnish_type_id = g.id WHERE s.shop_id = 4 GROUP BY s.garnish_type_id" +
                "UNION " +
                "SELECT COUNT(meat_type_id) AS stock, m.price FROM test3_java.sales AS s JOIN meat_types AS m ON s.meat_type_id = m.id WHERE s.shop_id = 4 GROUP BY s.meat_type_id;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        double total = 0;
        while(set.next()){
            int num = set.getInt("stock");
            double price = set.getDouble("price");
            total += num*price;
        }
        System.out.println("Total : " + total);
        set.close();
        statement.close();
    }

    public void printShopWithMostWholeGrains() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT sh.name, COUNT(*) AS breads FROM sales AS s  " +
                "JOIN shops AS sh ON s.shop_id = sh.id WHERE bread_type_id = 2 " +
                "GROUP BY shop_id ORDER BY breads DESC LIMIT 1;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            System.out.println("Shop with most whole grains : " + set.getString("name"));
        }
        set.close();
        statement.close();
    }

    public void printSoldGramsOfSalad() throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        String sql = "SELECT g.name, COUNT(garnish_type_id) * 0.2 AS sold FROM sales AS s " +
                "JOIN garnish_types AS g ON s.garnish_type_id = g.id " +
                "WHERE shop_id = 4 " +   // for my shop
                "GROUP BY s.garnish_type_id;";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        while (set.next()){
            System.out.println(set.getString("name") + " - " + set.getDouble("sold") + " kg");
        }
        set.close();
        statement.close();
    }
}
