package com.jensen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//klass för att kunna hantera databasen
public class DatabaseHandler  {
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schema_name", "root", "jensen123");
    public DatabaseHandler() throws SQLException {}

    public Connection getConnection() {
        return connection;
    }
}