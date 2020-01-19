package demo;

import db.DbDAO;
import skara.*;

import java.sql.SQLException;

public class Demo {

    public static void main(String[] args) {

        SkaraOwner owner = new SkaraOwner();
        owner.start();
        SkaraMan skaraMan = new SkaraMan();
        skaraMan.start();
        BreadWoman woman = new BreadWoman();
        woman.start();
        SaladMan saladMan = new SaladMan();
        saladMan.start();
        Seller seller = new Seller();
        seller.start();
        for (int i = 0; i < 10; i++) {
            Client client = new Client();
            client.start();
        }
        DogSparky sparky = new DogSparky();
        sparky.start();
    }
}
