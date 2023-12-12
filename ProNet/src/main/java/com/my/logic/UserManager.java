package com.my.logic;

import com.my.dao.AccountDAO;
import com.my.dao.DAOException;
import com.my.dao.DAOFactory;
import com.my.dao.UserDAO;
import com.my.domain.User;
import com.my.domain.UserDetails;
import com.my.util.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class UserManager {
    private static UserManager instance;

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final DBConnector dbConnector;
    private static final Logger log = LogManager.getLogger(UserManager.class);

    private UserManager() {
        DAOFactory.setDAOFactoryFCN("com.my.dao.mysql.MysqlDAOFactory");
        try {
            userDAO = DAOFactory.getInstance().getUserDAO();
            accountDAO = DAOFactory.getInstance().getAccountDAO();
        } catch (Exception e) {
            log.error("Cannot get access to User DAO");
            throw new IllegalStateException(e);
        }
        dbConnector = DBConnector.getInstance();
    }

    public long createUserWithDetails(User user, UserDetails userDetails) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            long userId = userDAO.createUser(con, user);
            userDetails.setUserId(userId);
            userDAO.addUserDetails(con, userDetails);
            accountDAO.createAccount(con, userId);

            dbConnector.commitTransaction(con);
            return userId;
        } catch (DAOException e) {
            log.error("Cannot create a user with details", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot create a user!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public User findUser(String login) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.readUserByLogin(con, login);
        } catch (DAOException e) {
            log.error("Cannot find a user by login", e);

            throw new LogicException("Cannot find a user!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public User findUserById(long userId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.readUser(con, userId);
        } catch (DAOException e) {
            log.error("Cannot find a user by id", e);

            throw new LogicException("Cannot find a user!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public UserDetails findUserDetails(long userId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.readUserDetails(con, userId);
        } catch (DAOException e) {
            log.error("Cannot find user details by user id", e);

            throw new LogicException("Cannot find user information!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<User> findAllUsersWithDetails() throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.readAllUsersWithDetails(con);
        } catch (DAOException e) {
            log.error("Cannot find users with details", e);

            throw new LogicException("Cannot find users!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<User> findUsersByPage(int offset, int limit) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.readUsersByPage(con, offset, limit);
        } catch (DAOException e) {
            log.error("Cannot find users with details", e);

            throw new LogicException("Cannot find users!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<User> findAllSubscribers() throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.readAllSubscribers(con);
        } catch (DAOException e) {
            log.error("Cannot find users with role 'SUBSCRIBER'", e);

            throw new LogicException("Cannot find subscribers!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public boolean updateUser(User user) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.updateUser(con, user);
        } catch (DAOException e) {
            log.error("Cannot update a user by user id", e);

            throw new LogicException("Cannot update a user!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public boolean updateUserWithDetails(User user, UserDetails userDetails) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            userDAO.updateUser(con, user);
            userDAO.updateUserDetails(con, userDetails);

            dbConnector.commitTransaction(con);
        } catch (DAOException e) {
            log.error("Cannot update a user with details by user id", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot update a user with details!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
        return true;
    }

    public boolean deleteUserById(long userId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            accountDAO.deleteAccountByUserId(con, userId);
            userDAO.deleteUser(con, userId);

            dbConnector.commitTransaction(con);
        } catch (DAOException e) {
            log.error("Cannot delete a user by user id", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot delete a user!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
        return true;
    }

    public long getUsersSize() throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return userDAO.getUsersSize(con);
        } catch (DAOException e) {
            log.error("Cannot get users size", e);

            throw new LogicException("Cannot get an amount of users!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }
}
