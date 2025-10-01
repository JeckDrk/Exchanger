package org.Exchanger.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAO {
    protected static final Connection CONNECTION;

    static{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            String dbPath = System.getProperty("user.dir");
            CONNECTION = DriverManager.getConnection("jdbc:sqlite::resource:DataBase.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
