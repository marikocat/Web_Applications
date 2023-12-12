package com.my.dao;

import com.my.domain.Account;

import java.sql.Connection;

public interface AccountDAO {
    /**
     * Method of adding user account to DB.
     * After execution account id set with value from DB.
     * @param con
     * @param userId
     * @return account id.
     */
    long createAccount(Connection con, long userId) throws DAOException;

    /**
     * Method to read user account from DB by user id.
     * @param con
     * @param userId
     * @return Account object with user account data.
     */
    Account readAccountByUserId(Connection con, long userId) throws DAOException;

    /**
     * Method to update user account in DB.
     * @param con
     * @param account
     * @return true if account was updated.
     */
    boolean updateAccount(Connection con, Account account) throws DAOException;

    /**
     * Method to delete user account from DB.
     * @param con
     * @param userId
     * @return true if account was deleted.
     */
    boolean deleteAccountByUserId(Connection con, long userId) throws DAOException;
}
