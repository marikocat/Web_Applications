package com.my.dao;

import com.my.domain.*;

import java.sql.Connection;
import java.util.List;

public interface ServiceDAO {
    /**
     * Method of getting service from DB by id.
     * @param con
     * @param id of service to find
     * @return Service object, which contains all fields from table 'service'.
     */
    Service readServiceById(Connection con, int id) throws DAOException;

    /**
     * Method of getting all services from DB.
     * @param con
     * @return List of all services.
     */
    List<Service> readAllServices(Connection con) throws DAOException;


    /**
     * Method of adding tariff plan to DB.
     * After execution tariff plan id set with value from DB.
     *
     * @param con
     * @param tariffPlan with tariff plan data to add
     * @return tariff plan object.
     */
    long createTariffPlan(Connection con, TariffPlan tariffPlan) throws DAOException;

    /**
     * Method of getting tariff plan from DB by user id.
     *
     * @param con
     * @param userId to find tariff plan
     * @return Tariff plan object, which contains all fields from table 'tariff_plan'.
     */
    TariffPlan readTariffPlanByUserId(Connection con, long userId) throws DAOException;

    /**
     * Method of updating tariff plan in DB.
     *
     * @param con
     * @param tariffPlan with tariff plan data to update
     * @return true if tariff plan was updated.
     */
    boolean updateTariffPlan(Connection con, TariffPlan tariffPlan) throws DAOException;

    /**
     * Method of updating tariff plan status in DB.
     *
     * @param con
     * @param tariffPlan with tariff plan data to update
     * @return true if tariff plan status was updated.
     */
    boolean updateTariffPlanStatus(Connection con, TariffPlan tariffPlan) throws DAOException;

    /**
     * Method to delete tariff plan from DB.
     * @param con
     * @param planId
     * @return true if tariff plan was deleted from DB, false - otherwise.
     */
    boolean deleteTariffPlan(Connection con, long planId) throws DAOException;


    /**
     * Method of inserting tariffs for tariff plan into DB.
     *
     * @param con
     * @param tariffs list of tariffs to insert
     * @param planId  id of plan
     * @return true if tariffs were inserted, false - otherwise
     */
    boolean createTariffsByPlanId(Connection con, List<Tariff> tariffs, long planId) throws DAOException;

    /**
     * Method of inserting tariffs for request into DB.
     *
     * @param con
     * @param tariffs   list of tariffs to insert
     * @param requestId if of user request
     * @return true if tariffs were inserted, false - otherwise
     */
    boolean createTariffsByRequestId(Connection con, List<Tariff> tariffs, long requestId) throws DAOException;

    /**
     * Method of getting tariff from DB by id.
     *
     * @param con
     * @param id  of tariff to find
     * @return Tariff object, which contains all fields from table 'tariff'.
     */
    Tariff readTariff(Connection con, int id) throws DAOException;

    /**
     * Method of getting sorted tariffs by service id from DB.
     * @param con
     * @param service
     * @param sort type of sort
     * @return List of sorted tariffs.
     */
    List<Tariff> readTariffsByServiceId(Connection con, int service, int sort) throws DAOException;

    /**
     * Method of getting tariffs by tariff plan id from DB.
     *
     * @param con
     * @param planId
     * @return list of tariffs, which belongs to tariff plan.
     */
    List<Tariff> readTariffsByPlanId(Connection con, long planId) throws DAOException;

    /**
     * Method of getting tariffs by request id from DB.
     *
     * @param con
     * @param requestId
     * @return list of tariffs, which belong to user request.
     */
    List<Tariff> readTariffsByRequestId(Connection con, long requestId) throws DAOException;

    /**
     * Method of deleting tariffs for tariff plan from DB.
     *
     * @param con
     * @param planId of tariff plan
     * @return true if tariffs for tariff plan were deleted.
     */
    boolean deleteTariffsByPlanId(Connection con, long planId) throws DAOException;


    /**
     * Method of adding request to DB.
     * After execution request id set with value from DB.
     *
     * @param con
     * @param userRequest data about user and cost of tariff plan needed for registration
     * @return inserted request id.
     */
    long createRequest(Connection con, Request userRequest) throws DAOException;

    /**
     * Method of getting user request from DB by request id.
     *
     * @param con
     * @param requestId to find user request
     * @return Request object, which contains all fields from table 'request'.
     */
    Request readRequestById(Connection con, long requestId) throws DAOException;

    /**
     * Method of getting all requests from DB.
     * @param con
     * @return List of all requests.
     */
    List<Request> readAllRequests(Connection con) throws DAOException;

    /**
     * Method to delete user request from DB.
     * @param con
     * @param requestId
     * @return true if request was deleted from DB, false - otherwise.
     */
    boolean deleteRequestById(Connection con, long requestId) throws DAOException;

    /**
     * Method to count all tariff plans in DB.
     * @param con
     * @return sum of plan ids.
     */
    long countTariffPlans(Connection con) throws DAOException;

    /**
     * Method to get services table size from DB.
     *
     * @param con
     * @return size of services table
     */
    int getServicesSize(Connection con) throws DAOException;

    /**
     * Method of updating a tariff in DB.
     *
     * @param con
     * @param tariff with tariff data to update
     * @return true if tariff was updated.
     */
    boolean updateTariff(Connection con, Tariff tariff) throws DAOException;
}
