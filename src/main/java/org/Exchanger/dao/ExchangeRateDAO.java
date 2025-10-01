package org.Exchanger.dao;

import org.Exchanger.dto.CurrencyDTO;
import org.Exchanger.dto.ExchangeRateDTO;
import org.Exchanger.util.ParserDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO extends DAO{

    public List<ExchangeRateDTO> getAllRates() throws SQLException{
        List<ExchangeRateDTO> currencies = new ArrayList<>();

        String query = "SELECT * FROM exchangerate";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            currencies.add(ParserDB.getExchangeRate(resultSet));
        }

        return currencies;
    }

    public ExchangeRateDTO get(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency) throws SQLException{
        String query = "SELECT * FROM exchangerate WHERE basecurrencyid = ? AND targetcurrencyid = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setInt(1, baseCurrency.getId());
        preparedStatement.setInt(2, targetCurrency.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        return ParserDB.getExchangeRate(resultSet);
    }

    public ExchangeRateDTO get(ExchangeRateDTO exchangeRate) throws SQLException{
        String query = "SELECT * FROM exchangerate WHERE basecurrencyid = ? AND targetcurrencyid = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
        preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        return ParserDB.getExchangeRate(resultSet);
    }

    public ExchangeRateDTO getLast() throws SQLException{
        String query = "SELECT * FROM exchangerate order by id desc limit 1";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return ParserDB.getExchangeRate(resultSet);
    }

    public void insert(ExchangeRateDTO exchangeRate) throws SQLException{
        String query = "INSERT INTO exchangerate(basecurrencyid, targetcurrencyid, rate) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
        preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());
        preparedStatement.setDouble(3, exchangeRate.getRate());

        preparedStatement.executeUpdate();
    }

    public void update(ExchangeRateDTO exchangeRate) throws SQLException{
        String query = "UPDATE exchangerate SET rate = ? WHERE basecurrencyid = ? AND targetcurrencyid = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setDouble(1, exchangeRate.getRate());
        preparedStatement.setInt(2, exchangeRate.getBaseCurrency().getId());
        preparedStatement.setInt(3, exchangeRate.getTargetCurrency().getId());

        preparedStatement.executeUpdate();
    }

    public boolean isContains(ExchangeRateDTO exchangeRate) throws SQLException{
        String query = "SELECT * FROM exchangerate WHERE basecurrencyid = ? AND targetcurrencyid = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

        preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
        preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
