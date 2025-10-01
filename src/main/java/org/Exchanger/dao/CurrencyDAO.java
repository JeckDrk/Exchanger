package org.Exchanger.dao;

import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.util.ParserDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO extends DAO{

    public List<CurrencyDTO> getAll() throws SQLException {
        List<CurrencyDTO> currencies = new ArrayList<>();

        String query = "SELECT * FROM currencies";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            currencies.add(ParserDB.getCurrency(resultSet));
        }

        return currencies;
    }

    public CurrencyDTO get(int id) throws SQLException {
        CurrencyDTO currencyDTO = null;

        String query = "SELECT * FROM currencies WHERE id = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            currencyDTO = ParserDB.getCurrency(resultSet);
        }
        if(currencyDTO == null){
            throw new RuntimeException("Currency not found");
        }

        return currencyDTO;
    }

    public CurrencyDTO get(String code) throws SQLException {
        String query = "SELECT * FROM currencies WHERE code = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setString(1, code);

        ResultSet resultSet = preparedStatement.executeQuery();

        return ParserDB.getCurrency(resultSet);
    }

    public void insert(CurrencyDTO currencyDTO) throws SQLException {
        String query = "INSERT INTO currencies(code, fullname, sign) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setString(1, currencyDTO.getCode());
        preparedStatement.setString(2, currencyDTO.getName());
        preparedStatement.setString(3, currencyDTO.getSign());

        preparedStatement.executeUpdate();
    }

    public boolean isContain(String code) throws SQLException {
        String query = "SELECT * FROM currencies WHERE code = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setString(1, code);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
}
