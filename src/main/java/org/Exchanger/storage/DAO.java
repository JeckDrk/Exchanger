package org.Exchanger.storage;

import org.Exchanger.errors.StorageException;

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
            CONNECTION = DriverManager.getConnection("jdbc:sqlite::resource:DataBase.db");
            //CONNECTION = DriverManager.getConnection("jdbc:sqlite:C:/Users/dames/IdeaProjects/Exchanger/src/main/resources/DataBase.db");
        } catch (SQLException e) {
            throw new StorageException();
        }
    }
}
