package org.Exchanger.storage;

import org.Exchanger.entity.ExchangeRate;
import org.Exchanger.errors.ApplicationException;
import org.Exchanger.errors.StorageException;
import org.Exchanger.errors.UniqueException;
import org.Exchanger.utils.mapper.ExchangerMapper;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO extends DAO implements ExchangerStorage{

    public List<ExchangeRate> getAll(CurrencyStorage currencyStorage) throws ApplicationException {
        try {
            List<ExchangeRate> currencies = new ArrayList<>();

            String query = "SELECT * FROM exchangerate";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                currencies.add(ExchangerMapper.getExchangeRate(resultSet, currencyStorage));
            }

            return currencies;
        } catch (SQLException e){
            throw new StorageException();
        }
    }

    public ExchangeRate get(String baseCurrencyCode, String targetCurrencyCode, CurrencyStorage currencyStorage) throws ApplicationException {
        try {
            String query = """
                    SELECT exchangerate.*, bc.code, tc.code FROM exchangerate
                        JOIN currencies bc ON exchangerate.basecurrencyid = bc.id
                        JOIN currencies tc ON exchangerate.targetcurrencyid = tc.id
                        where bc.code = ? and tc.code = ?;""";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            return ExchangerMapper.getExchangeRate(resultSet, currencyStorage);
        } catch (SQLException e){
            throw new StorageException();
        }
    }

    public void insert(String baseCurrencyCode, String targetCurrencyCode, double rate) throws ApplicationException {
        try {
            String query = """
                    INSERT INTO exchangerate (basecurrencyid, targetcurrencyid, rate) \
                    SELECT
                        base.id AS basecurrencyid,
                        target.id AS targetcurrencyid,
                        ? AS rate
                    FROM currencies base, currencies target
                    WHERE base.code = ? AND target.code = ?""";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setDouble(1, rate);
            preparedStatement.setString(2, baseCurrencyCode);
            preparedStatement.setString(3, targetCurrencyCode);

            int prep = preparedStatement.executeUpdate();
            System.out.println(prep);
        } catch (SQLException e) {
            SQLiteErrorCode exMessage = ((SQLiteException) e).getResultCode();
            String ex = exMessage.message;
            if (ex.equals("A UNIQUE constraint failed")) {
                throw new UniqueException();
            }
            throw new StorageException();
        }
    }

    public void update(String baseCurrencyCode, String targetCurrencyCode, double rate) throws ApplicationException {
        try {
            String query = """
                    UPDATE exchangerate SET rate = ?\s
                    WHERE basecurrencyid = (SELECT id FROM currencies WHERE code = ?)
                      AND targetcurrencyid = (SELECT id FROM currencies WHERE code = ?)""";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setDouble(1, rate);
            preparedStatement.setString(2, baseCurrencyCode);
            preparedStatement.setString(3, targetCurrencyCode);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException();
        }
    }
}
