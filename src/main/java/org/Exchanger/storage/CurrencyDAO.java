package org.Exchanger.storage;

import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.errors.StorageException;
import org.Exchanger.errors.UniqueException;
import org.Exchanger.utils.mapper.CurrencyMapper;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;
import org.Exchanger.entity.Currency;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO extends DAO implements CurrencyStorage{

    public List<Currency> getAll() throws ApplicationException {
        try {
            String query = "SELECT * FROM currencies";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Currency> currencies = new ArrayList<>();

            while (resultSet.next()) {
                currencies.add(CurrencyMapper.getCurrency(resultSet));
            }

            return currencies;
        } catch (SQLException e) {
            throw new StorageException();
        }
    }

    public Currency get(int id) throws ApplicationException {
        try {
            String query = "SELECT * FROM currencies WHERE id = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return CurrencyMapper.getCurrency(resultSet);
        } catch (SQLException e) {
            throw new StorageException();
        }
    }

    public Currency get(String code) throws ApplicationException {
        try {
            String query = "SELECT * FROM currencies WHERE code = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            return CurrencyMapper.getCurrency(resultSet);
        } catch (SQLException e) {
            throw new StorageException();
        }
    }

    public void insert(String code, String name, String sign) throws ApplicationException {
        try {
            String query = "INSERT INTO currencies(code, fullname, sign) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, sign);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SQLiteErrorCode exMessage = ((SQLiteException) e).getResultCode();
            String ex = exMessage.message;
            if (ex.equals("A UNIQUE constraint failed")) {
                throw new UniqueException();
            }
            throw new StorageException();
        }
    }
}
