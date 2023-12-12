package com.my;

import com.my.dao.AccountDAO;
import com.my.dao.DAOException;
import com.my.dao.DAOFactory;
import com.my.dao.UserDAO;
import com.my.dao.mysql.Constants;
import com.my.dao.mysql.Fields;
import com.my.domain.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountDAOTest {
    private final AccountDAO accountDAO;

    public AccountDAOTest() throws Exception {
        DAOFactory.setDAOFactoryFCN("com.my.dao.mysql.MysqlDAOFactory");
        accountDAO = DAOFactory.getInstance().getAccountDAO();
    }

    @Test
    void testCreateAccount() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(1)).thenReturn(100L);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);
        when(pstmt.getGeneratedKeys()).thenReturn(rs);

        long expectedResult = 100;
        long actualResult = accountDAO.createAccount(con, 10);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testReadAccountByUserId() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID)).thenReturn(10L);
        when(rs.getDouble(Fields.BALANCE)).thenReturn(150.0);
        when(rs.getLong(Fields.USER_ID)).thenReturn(5L);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_ACCOUNT_BY_USER_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Account expectedAccount = new Account();
        expectedAccount.setId(10);
        expectedAccount.setBalance(150.0);
        expectedAccount.setUserId(5);

        Account actualAccount = accountDAO.readAccountByUserId(con, 5);

        Assertions.assertTrue(
                new ReflectionEquals(expectedAccount)
                        .matches(actualAccount));
    }

    @Test
    void testUpdateAccount() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_UPDATE_ACCOUNT)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        Account account = new Account();
        account.setBalance(100.0);
        account.setUserId(10);

        boolean actualResult = accountDAO.updateAccount(con, account);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testDeleteAccountByUserId() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_DELETE_ACCOUNT_BY_USER_ID)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        boolean actualResult = accountDAO.deleteAccountByUserId(con, 15);
        Assertions.assertTrue(actualResult);
    }
}