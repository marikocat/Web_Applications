package com.my.dao.mysql;

import com.my.dao.DAOException;
import com.my.dao.UserDAO;
import com.my.domain.Account;
import com.my.domain.Role;
import com.my.domain.User;
import com.my.domain.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserDAO for DBMS MySQL
 */
public class MysqlUserDAO implements UserDAO {

    @Override
    public long createUser(Connection con, User user) throws DAOException {
        long id = 0;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            pstmt.setString(++k, user.getLogin());
            pstmt.setString(++k, user.getPassword());
            pstmt.setInt(++k, user.getRole().getId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        user.setId(id);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return id;
    }

    @Override
    public User readUser(Connection con, long id) throws DAOException {
        User user = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_USER_BY_ID)) {
            int k = 0;
            pstmt.setLong(++k, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User readUserByLogin(Connection con, String login) throws DAOException {
        User user = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_USER_BY_LOGIN)) {
            int k = 0;
            pstmt.setString(++k, login);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public List<User> readAllSubscribers(Connection con) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(Constants.SQL_GET_ALL_SUBSCRIBERS)) {
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public List<User> readAllUsersWithDetails(Connection con) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Statement stmt = con.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(Constants.SQL_GET_ALL_USERS_WITH_DETAILS)) {
                while (rs.next()) {
                    User user = extractUser(rs);
                    user.setUserDetails(extractUserDetails(rs));
                    user.setAccount(extractUserAccount(rs));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public List<User> readUsersByPage(Connection con, int offset, int limit) throws DAOException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_USERS_BY_PAGE)) {
            int k = 0;
            pstmt.setInt(++k, limit);
            pstmt.setInt(++k, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    User user = extractUser(rs);
                    user.setUserDetails(extractUserDetails(rs));
                    user.setAccount(extractUserAccount(rs));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw  new DAOException(e);
        }
        return users;
    }


    @Override
    public boolean updateUser(Connection con, User user) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_UPDATE_USER)) {
            int k = 0;
            pstmt.setString(++k, user.getLogin());
            pstmt.setString(++k, user.getPassword());
            pstmt.setInt(++k, user.getRole().getId());
            pstmt.setLong(++k, user.getId());
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
    public boolean deleteUser(Connection con, long userId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_DELETE_USER)) {
            int k = 0;
            pstmt.setLong(++k, userId);
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
    public boolean addUserDetails(Connection con, UserDetails userDetails) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_USER_DETAILS)) {
            int k = 0;
            pstmt.setLong(++k, userDetails.getUserId());
            pstmt.setString(++k, userDetails.getFirstName());
            pstmt.setString(++k, userDetails.getLastName());
            pstmt.setString(++k, userDetails.getEmail());
            pstmt.setString(++k, userDetails.getPhoneNumber());
            pstmt.setString(++k, userDetails.getAddress());
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
    public UserDetails readUserDetails(Connection con, long userId) throws DAOException {
        UserDetails userDetails = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_USER_DETAILS_BY_USER_ID)) {
            int k = 0;
            pstmt.setLong(++k, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userDetails = extractUserDetails(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return userDetails;
    }

    @Override
    public boolean updateUserDetails(Connection con, UserDetails userDetails) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_UPDATE_USER_DETAILS)) {
            int k = 0;
            pstmt.setString(++k, userDetails.getFirstName());
            pstmt.setString(++k, userDetails.getLastName());
            pstmt.setString(++k, userDetails.getEmail());
            pstmt.setString(++k, userDetails.getPhoneNumber());
            pstmt.setString(++k, userDetails.getAddress());
            pstmt.setLong(++k, userDetails.getUserId());
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
    public long getUsersSize(Connection con) throws DAOException {
        long size = 0;
        try (Statement stmt = con.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(Constants.SQL_GET_USERS_SIZE)) {
                if (rs.next()) {
                    size = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return size;
    }


    // helper methods
    private static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ID));
        user.setLogin(rs.getString(Fields.LOGIN));
        user.setPassword(rs.getString(Fields.PASSWORD));
        user.setRole(Role.values()[rs.getInt(Fields.ROLE_ID)]);
        return user;
    }

    private static UserDetails extractUserDetails(ResultSet rs) throws SQLException {
        UserDetails userDetails = new UserDetails();
        long userId = rs.getLong("ud.user_id");
        if (userId == 0) {
            return null;
        }
        userDetails.setUserId(userId);
        userDetails.setFirstName(rs.getString("ud.first_name"));
        userDetails.setLastName(rs.getString("ud.last_name"));
        userDetails.setEmail(rs.getString("ud.email"));
        userDetails.setPhoneNumber(rs.getString("ud.phone_number"));
        userDetails.setAddress(rs.getString("ud.address"));
        return userDetails;
    }

    private static Account extractUserAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        long id = rs.getLong("a.id");
        if (id == 0) {
            return null;
        }
        account.setId(id);
        account.setBalance(rs.getDouble("a.balance"));
        account.setUserId(rs.getLong("a.user_id"));
        return account;
    }
}
