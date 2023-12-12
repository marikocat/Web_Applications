package com.my.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private static DBConnector instance;

    public static synchronized DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    private final DataSource ds;
    private static final Logger log = LogManager.getLogger(DBConnector.class);

    private DBConnector() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/MyDB");
        } catch (NamingException e) {
            log.error("Cannot obtain a data source");
            throw new IllegalStateException(e);
        }
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            log.error("Cannot obtain a connection");
            throw new IllegalStateException(e);
        }
    }

    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error("Cannot close a connection");
                throw new IllegalStateException(e);
            }
        }
    }

    public void disallowAutoCommit(Connection con) {
        try {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("Cannot disallow to auto commit");
            throw new IllegalStateException(e);
        }
    }

    public void commitTransaction(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            log.error("Cannot commit a transaction");
            throw new IllegalStateException(e);
        }
    }

    public void rollbackTransaction(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                log.error("Cannot rollback a transaction");
                throw new IllegalStateException(e);
            }
        }
    }
}