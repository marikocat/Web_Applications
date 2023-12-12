package com.my;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.my.dao.DAOException;
import com.my.dao.DAOFactory;
import com.my.dao.UserDAO;
import com.my.dao.mysql.Constants;
import com.my.dao.mysql.Fields;
import com.my.domain.Account;
import com.my.domain.Role;
import com.my.domain.User;
import com.my.domain.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOTest {
    private final UserDAO userDAO;

    public UserDAOTest() throws Exception {
        DAOFactory.setDAOFactoryFCN("com.my.dao.mysql.MysqlDAOFactory");
        userDAO = DAOFactory.getInstance().getUserDAO();
    }

    @Test
    void testCreateUser() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(1)).thenReturn(15L);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);
        when(pstmt.getGeneratedKeys()).thenReturn(rs);

        User user = new User();
        user.setLogin("login");
        user.setPassword("pass");
        user.setRole(Role.SUBSCRIBER);

        long expectedResult = 15;
        long actualResult = userDAO.createUser(con, user);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testReadUser() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID)).thenReturn(2L);
        when(rs.getString(Fields.LOGIN)).thenReturn("biden");
        when(rs.getString(Fields.PASSWORD)).thenReturn("bidenpass");
        when(rs.getInt(Fields.ROLE_ID)).thenReturn(1);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_USER_BY_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        User expectedUser = new User();
        expectedUser.setId(2);
        expectedUser.setLogin("biden");
        expectedUser.setPassword("bidenpass");
        expectedUser.setRole(Role.SUBSCRIBER);

        User actualUser = userDAO.readUser(con, 2);

        Assertions.assertTrue(
                new ReflectionEquals(expectedUser)
                        .matches(actualUser));
    }

    @Test
    void testReadUserByLogin() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID)).thenReturn(44L);
        when(rs.getString(Fields.LOGIN)).thenReturn("obama");
        when(rs.getString(Fields.PASSWORD)).thenReturn("obamapass");
        when(rs.getInt(Fields.ROLE_ID)).thenReturn(1);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_USER_BY_LOGIN)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        User expectedUser = new User();
        expectedUser.setLogin("obama");
        expectedUser.setPassword("obamapass");
        expectedUser.setRole(Role.SUBSCRIBER);

        User actualUser = userDAO.readUserByLogin(con, "obama");

        Assertions.assertTrue(
                new ReflectionEquals(expectedUser, Fields.ID)
                    .matches(actualUser));
    }

    @Test
    void testReadAllSubscribers() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);
        when(rs.getString(Fields.LOGIN))
                .thenReturn("login1")
                .thenReturn("login2")
                .thenReturn("login3");
        when(rs.getString(Fields.PASSWORD))
                .thenReturn("pass1")
                .thenReturn("pass2")
                .thenReturn("pass3");
        when(rs.getInt(Fields.ROLE_ID))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);

        Statement stmt = mock(Statement.class);
        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.SQL_GET_ALL_SUBSCRIBERS)).thenReturn(rs);

        List<User> expectedUsers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setId(i);
            user.setLogin("login" + i);
            user.setPassword("pass" + i);
            user.setRole(Role.SUBSCRIBER);
            expectedUsers.add(user);
        }
        List<User> actualUsers = userDAO.readAllSubscribers(con);

        for (int i = 0; i < expectedUsers.size(); i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i))
                            .matches(actualUsers.get(i)));
        }
    }

    @Test
    void testReadAllUsersWithDetails() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);
        when(rs.getString(Fields.LOGIN))
                .thenReturn("login1")
                .thenReturn("login2")
                .thenReturn("login3");
        when(rs.getString(Fields.PASSWORD))
                .thenReturn("pass1")
                .thenReturn("pass2")
                .thenReturn("pass3");
        when(rs.getInt(Fields.ROLE_ID))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        when(rs.getLong("ud.user_id"))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);
        when(rs.getString("ud.first_name"))
                .thenReturn("name1")
                .thenReturn("name2")
                .thenReturn("name3");
        when(rs.getString("ud.last_name"))
                .thenReturn("surname1")
                .thenReturn("surname2")
                .thenReturn("surname3");
        when(rs.getString("ud.email"))
                .thenReturn("user1@mail.com")
                .thenReturn("user2@mail.com")
                .thenReturn("user3@mail.com");
        when(rs.getString("ud.phone_number"))
                .thenReturn("+12(345)1234451")
                .thenReturn("+12(345)1234452")
                .thenReturn("+12(345)1234453");
        when(rs.getString("ud.address"))
                .thenReturn("Address St. 1")
                .thenReturn("Address St. 2")
                .thenReturn("Address St. 3");
        when(rs.getLong("a.id"))
                .thenReturn(11L)
                .thenReturn(12L)
                .thenReturn(13L);
        when(rs.getDouble("a.balance"))
                .thenReturn(100.0)
                .thenReturn(200.0)
                .thenReturn(300.0);
        when(rs.getLong("a.user_id"))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);

        Statement stmt = mock(Statement.class);
        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.SQL_GET_ALL_USERS_WITH_DETAILS)).thenReturn(rs);

        List<User> expectedUsers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setId(i);
            user.setLogin("login" + i);
            user.setPassword("pass" + i);
            user.setRole(Role.SUBSCRIBER);
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(i);
            userDetails.setFirstName("name" + i);
            userDetails.setLastName("surname" + i);
            userDetails.setEmail("user" + i + "@mail.com");
            userDetails.setPhoneNumber("+12(345)123445" + i);
            userDetails.setAddress("Address St. " + i);
            user.setUserDetails(userDetails);
            Account account = new Account();
            account.setId(10 + i);
            account.setBalance(100 * i);
            account.setUserId(i);
            user.setAccount(account);
            expectedUsers.add(user);
        }
        List<User> actualUsers = userDAO.readAllUsersWithDetails(con);

        for (int i = 0; i < expectedUsers.size(); i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i), "userDetails", "account")
                            .matches(actualUsers.get(i)));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i).getUserDetails())
                            .matches(actualUsers.get(i).getUserDetails()));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i).getAccount())
                            .matches(actualUsers.get(i).getAccount()));
        }
    }

    @Test
    void testReadUsersByPage() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);
        when(rs.getString(Fields.LOGIN))
                .thenReturn("login1")
                .thenReturn("login2")
                .thenReturn("login3");
        when(rs.getString(Fields.PASSWORD))
                .thenReturn("pass1")
                .thenReturn("pass2")
                .thenReturn("pass3");
        when(rs.getInt(Fields.ROLE_ID))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        when(rs.getLong("ud.user_id"))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);
        when(rs.getString("ud.first_name"))
                .thenReturn("name1")
                .thenReturn("name2")
                .thenReturn("name3");
        when(rs.getString("ud.last_name"))
                .thenReturn("surname1")
                .thenReturn("surname2")
                .thenReturn("surname3");
        when(rs.getString("ud.email"))
                .thenReturn("user1@mail.com")
                .thenReturn("user2@mail.com")
                .thenReturn("user3@mail.com");
        when(rs.getString("ud.phone_number"))
                .thenReturn("+12(345)1234451")
                .thenReturn("+12(345)1234452")
                .thenReturn("+12(345)1234453");
        when(rs.getString("ud.address"))
                .thenReturn("Address St. 1")
                .thenReturn("Address St. 2")
                .thenReturn("Address St. 3");
        when(rs.getLong("a.id"))
                .thenReturn(11L)
                .thenReturn(12L)
                .thenReturn(13L);
        when(rs.getDouble("a.balance"))
                .thenReturn(100.0)
                .thenReturn(200.0)
                .thenReturn(300.0);
        when(rs.getLong("a.user_id"))
                .thenReturn(1L)
                .thenReturn(2L)
                .thenReturn(3L);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_USERS_BY_PAGE)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        List<User> expectedUsers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setId(i);
            user.setLogin("login" + i);
            user.setPassword("pass" + i);
            user.setRole(Role.SUBSCRIBER);
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(i);
            userDetails.setFirstName("name" + i);
            userDetails.setLastName("surname" + i);
            userDetails.setEmail("user" + i + "@mail.com");
            userDetails.setPhoneNumber("+12(345)123445" + i);
            userDetails.setAddress("Address St. " + i);
            user.setUserDetails(userDetails);
            Account account = new Account();
            account.setId(10 + i);
            account.setBalance(100 * i);
            account.setUserId(i);
            user.setAccount(account);
            expectedUsers.add(user);
        }
        List<User> actualUsers = userDAO.readUsersByPage(con, 1, 3);

        for (int i = 0; i < expectedUsers.size(); i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i), "userDetails", "account")
                            .matches(actualUsers.get(i)));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i).getUserDetails())
                            .matches(actualUsers.get(i).getUserDetails()));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedUsers.get(i).getAccount())
                            .matches(actualUsers.get(i).getAccount()));
        }
    }

    @Test
    void testUpdateUser() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_UPDATE_USER)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        User user = new User();
        user.setId(1);
        user.setLogin("login");
        user.setPassword("pass");
        user.setRole(Role.SUBSCRIBER);

        boolean actualResult = userDAO.updateUser(con, user);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testDeleteUser() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_DELETE_USER)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        boolean actualResult = userDAO.deleteUser(con, 15);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testAddUserDetails() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_USER_DETAILS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        UserDetails userDetails = fillUserDetailsInfo();

        boolean actualResult = userDAO.addUserDetails(con, userDetails);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testReadUserDetails() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong("ud.user_id")).thenReturn(1L);
        when(rs.getString("ud.first_name")).thenReturn("Barak");
        when(rs.getString("ud.last_name")).thenReturn("Obama");
        when(rs.getString("ud.email")).thenReturn("barak@mail.com");
        when(rs.getString("ud.phone_number")).thenReturn("+12(333)1234567");
        when(rs.getString("ud.address")).thenReturn("Oak St.");

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_USER_DETAILS_BY_USER_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        UserDetails expectedUserDetails = new UserDetails();
        expectedUserDetails.setUserId(1);
        expectedUserDetails.setFirstName("Barak");
        expectedUserDetails.setLastName("Obama");
        expectedUserDetails.setEmail("barak@mail.com");
        expectedUserDetails.setPhoneNumber("+12(333)1234567");
        expectedUserDetails.setAddress("Oak St.");

        UserDetails actualUserDetails = userDAO.readUserDetails(con, 1);

        Assertions.assertTrue(
                new ReflectionEquals(expectedUserDetails)
                        .matches(actualUserDetails));
    }

    @Test
    void testUpdateUserDetails() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_UPDATE_USER_DETAILS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        UserDetails userDetails = fillUserDetailsInfo();

        boolean actualResult = userDAO.updateUserDetails(con, userDetails);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testGetUsersSize() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt(1)).thenReturn(5);

        Statement stmt = mock(Statement.class);
        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.SQL_GET_USERS_SIZE)).thenReturn(rs);

        long expectedSize = 5;

        long actualSize = userDAO.getUsersSize(con);

        Assertions.assertEquals(expectedSize, actualSize);
    }

    // helper methods
    private static UserDetails fillUserDetailsInfo() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(100);
        userDetails.setFirstName("name");
        userDetails.setLastName("surname");
        userDetails.setEmail("user@mail.com");
        userDetails.setPhoneNumber("+11(111)2233444");
        userDetails.setAddress("Address St. 17");
        return userDetails;
    }
}
