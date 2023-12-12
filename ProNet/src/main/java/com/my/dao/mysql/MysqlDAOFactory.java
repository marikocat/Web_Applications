package com.my.dao.mysql;

import com.my.dao.AccountDAO;
import com.my.dao.DAOFactory;
import com.my.dao.ServiceDAO;
import com.my.dao.UserDAO;

/**
 * Implementation of DAOFactory for DBMS MySQL
 */
public class MysqlDAOFactory extends DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return new MysqlUserDAO();
    }

    @Override
    public AccountDAO getAccountDAO() {
        return new MysqlAccountDAO();
    }

    @Override
    public ServiceDAO getServiceDAO() {
        return new MysqlServiceDAO();
    }
}
