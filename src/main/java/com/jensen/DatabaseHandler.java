package com.jensen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//klass för att kunna hantera databasen
public class DatabaseHandler  {
    private String system = "mysql";
    private final Connection connection;


    public DatabaseHandler() throws SQLException {
        //Här kan du välja om programmet ska använda mysql eller sqlite.
        if(system.equals("mysql")) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schema_name", "root", "jensen123");
        } else {
            connection = DriverManager.getConnection("jdbc:sqlite:webbutiken.db");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}