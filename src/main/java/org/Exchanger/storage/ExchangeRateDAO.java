package org.Exchanger.storage;

import org.Exchanger.dto.ExchangeRateGetDTO;
import org.Exchanger.dto.ExchangeRatePostDTO;
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

    public List<ExchangeRatePostDTO> getAll() throws ApplicationException {
        try {
            List<ExchangeRatePostDTO> currencies = new ArrayList<>();

            String query = "SELECT * FROM exchangerate";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                currencies.add(ExchangerMapper.getExchangeRate(resultSet));
            }

            return currencies;
        } catch (SQLException e){
            throw new StorageException();
        }
    }

    public ExchangeRatePostDTO get(ExchangeRateGetDTO rate) throws ApplicationException {
        try {
            String query = """
                    SELECT exchangerate.*, bc.code, tc.code FROM exchangerate
                        JOIN currencies bc ON exchangerate.basecurrencyid = bc.id
                        JOIN currencies tc ON exchangerate.targetcurrencyid = tc.id
                        where bc.code = ? and tc.code = ?;""";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setString(1, rate.getBaseCurrency());
            preparedStatement.setString(2, rate.getTargetCurrency());

            ResultSet resultSet = preparedStatement.executeQuery();

            return ExchangerMapper.getExchangeRate(resultSet);
        } catch (SQLException e){
            throw new StorageException();
        }
    }

    public void insert(ExchangeRateGetDTO exchangeRate) throws ApplicationException {
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

            preparedStatement.setDouble(1, exchangeRate.getRate());
            preparedStatement.setString(2, exchangeRate.getBaseCurrency());
            preparedStatement.setString(3, exchangeRate.getTargetCurrency());

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

    public void update(ExchangeRateGetDTO exchangeRate) throws ApplicationException {
        try {
            String query = """
                    UPDATE exchangerate SET rate = ?\s
                    WHERE basecurrencyid = (SELECT id FROM currencies WHERE code = ?)
                      AND targetcurrencyid = (SELECT id FROM currencies WHERE code = ?)""";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            preparedStatement.setDouble(1, exchangeRate.getRate());
            preparedStatement.setString(2, exchangeRate.getBaseCurrency());
            preparedStatement.setString(3, exchangeRate.getTargetCurrency());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException();
        }
    }
}
