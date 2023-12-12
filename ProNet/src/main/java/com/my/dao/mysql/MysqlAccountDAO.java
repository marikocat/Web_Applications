package com.my.dao.mysql;

import com.my.dao.AccountDAO;
import com.my.dao.DAOException;
import com.my.domain.Account;

import java.sql.*;

public class MysqlAccountDAO implements AccountDAO {
    @Override
    public long createAccount(Connection con, long userId) throws DAOException {
        long accountId = 0;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            pstmt.setLong(++k, userId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        accountId = rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return accountId;
    }

    @Override
    public Account readAccountByUserId(Connection con, long userId) throws DAOException {
        Account account = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_ACCOUNT_BY_USER_ID)) {
            int k = 0;
            pstmt.setLong(++k, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    account = extractUserAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return account;
    }

    @Override
    public boolean updateAccount(Connection con, Account account) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_UPDATE_ACCOUNT)) {
            int k = 0;
            pstmt.setDouble(++k, account.getBalance());
            pstmt.setLong(++k, account.getUserId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public boolean deleteAccountByUserId(Connection con, long userId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_DELETE_ACCOUNT_BY_USER_ID)) {
            int k = 0;
            pstmt.setLong(++k, userId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return  true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }


    // helper methods
    private Account extractUserAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong(Fields.ID));
        account.setBalance(rs.getDouble(Fields.BALANCE));
        account.setUserId(rs.getLong(Fields.USER_ID));
        return account;
    }
}
