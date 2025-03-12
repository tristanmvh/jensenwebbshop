package com.jensen;

import Stock.StockController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        StockController stockController = new StockController();
        stockController.run();
    }
}