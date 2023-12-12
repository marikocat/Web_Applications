package com.my.logic;

import com.my.dao.*;
import com.my.domain.Account;
import com.my.domain.TariffPlan;
import com.my.domain.User;
import com.my.domain.UserDetails;
import com.my.util.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class AccountManager {
    private static AccountManager instance;

    public static synchronized AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    private final ServiceDAO serviceDAO;
    private final AccountDAO accountDAO;
    private final DBConnector dbConnector;
    private static final Logger log = LogManager.getLogger(AccountManager.class);

    private AccountManager() {
        DAOFactory.setDAOFactoryFCN("com.my.dao.mysql.MysqlDAOFactory");
        try {
            serviceDAO = DAOFactory.getInstance().getServiceDAO();
        } catch (Exception e) {
            log.error("Cannot get access to Service DAO");
            throw new IllegalStateException(e);
        }
        try {
            accountDAO = DAOFactory.getInstance().getAccountDAO();
        } catch (Exception e) {
            log.error("Cannot get access to Account DAO");
            throw new IllegalStateException(e);
        }
        dbConnector = DBConnector.getInstance();
    }

    public Account findAccountByUserId(long userId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return accountDAO.readAccountByUserId(con, userId);
        } catch (DAOException e) {
            log.error("Cannot find an account by user id", e);

            throw new LogicException("Cannot find an account!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public void updateAccount(Account account) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            accountDAO.updateAccount(con, account);
        } catch (DAOException e) {
            log.error("Cannot update an account by user id", e);

            throw new LogicException("Cannot refill an account balance!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }


    public void updateAccountAndStatus(Account account, TariffPlan tariffPlan) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            accountDAO.updateAccount(con, account);
            serviceDAO.updateTariffPlan(con, tariffPlan);

            dbConnector.commitTransaction(con);
        } catch (DAOException e) {
            log.error("Cannot withdraw an amount from an account balance and update tariff plan status", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot withdraw an amount from an account balance to connect a tariff plan!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }
}
