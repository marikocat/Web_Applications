package com.my.dao;

import com.my.domain.User;
import com.my.domain.UserDetails;

import java.sql.Connection;
import java.util.List;

/**
 * Interface for data manipulation in table 'users'.
 * CRUD methods.
 *
 */

public interface UserDAO {
    /**
     * Method to add user to DB.
     * After execution user id set with value from DB.
     * @param con
     * @param user with user data to add
     * @return user id.
     */
    long createUser(Connection con, User user) throws DAOException;

    /**
     * Method to get user from DB by id.
     * @param con
     * @param id of user to find
     * @return User object, which contains all fields from table 'user'.
     */
    User readUser(Connection con, long id) throws DAOException;

    /**
     * Method to get user from DB by login.
     * @param con
     * @param login of user to find
     * @return User object, which contains all fields from table 'users'.
     */

    User readUserByLogin(Connection con, String login) throws DAOException;

    /**
     * Method to get all subscribers from DB.
     * @param con
     * @return List of all subscribers.
     */
    List<User> readAllSubscribers(Connection con) throws DAOException;

    /**
     * Method to get all users with details from DB.
     * @param con
     * @return List of all users with details.
     */
    List<User> readAllUsersWithDetails(Connection con) throws DAOException;

    /**
     * Method to get all users with details for one page from DB.
     * @param con
     * @param offset
     * @param limit
     * @return List of all users with details for one page.
     */
    List<User> readUsersByPage(Connection con, int offset, int limit) throws DAOException;

    /**
     * Method to update user in DB.
     * @param con
     * @param user object with user data to update
     * @return true if user was updated in DB, false - otherwise.
     */
    boolean updateUser(Connection con, User user) throws DAOException;


    /**
     * Method to delete user from DB.
     * @param con
     * @param userId
     * @return true if user was deleted from DB, false - otherwise.
     */
    boolean deleteUser(Connection con, long userId) throws DAOException;

    /**
     * Method to add user details to DB.
     *
     * @param con
     * @param userDetails user data to add
     * @return true if details were added
     */
    boolean addUserDetails(Connection con, UserDetails userDetails) throws DAOException;

    /**
     * Method to read user details from DB.
     * @param con
     * @param userId
     * @return object User Details with user data
     */
    UserDetails readUserDetails(Connection con, long userId) throws DAOException;

    /**
     * Method to update user details in DB.
     *
     * @param con
     * @param userDetails object with user data to update
     * @return
     */
    boolean updateUserDetails(Connection con, UserDetails userDetails) throws DAOException;

    /**
     * Method to get users table size from DB.
     *
     * @param con
     * @return size of users table
     */
    long getUsersSize(Connection con) throws DAOException;
}
