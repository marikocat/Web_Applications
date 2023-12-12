package com.my.logic;

import com.my.dao.DAOException;
import com.my.dao.DAOFactory;
import com.my.dao.ServiceDAO;
import com.my.domain.*;
import com.my.util.DBConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class ServiceManager {
    private static ServiceManager instance;

    public static synchronized ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    private final ServiceDAO serviceDAO;
    private final DBConnector dbConnector;
    private static final Logger log = LogManager.getLogger(ServiceManager.class);

    private ServiceManager() {
        DAOFactory.setDAOFactoryFCN("com.my.dao.mysql.MysqlDAOFactory");
        try {
            serviceDAO = DAOFactory.getInstance().getServiceDAO();
        } catch (Exception e) {
            log.error("Cannot get access to Service DAO");
            throw new IllegalStateException(e);
        }
        dbConnector = DBConnector.getInstance();
    }

    public Service findServiceById(int id) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readServiceById(con, id);
        } catch (DAOException e) {
            log.error("Cannot find a service by id", e);

            throw new LogicException("Cannot find a service!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<Service> findAllServices() throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readAllServices(con);
        } catch (DAOException e) {
            log.error("Cannot find services", e);

            throw new LogicException("Cannot find services!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public Tariff findTariffById(int id) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readTariff(con, id);
        } catch (DAOException e) {
            log.error("Cannot find a tariff by id", e);

            throw new LogicException("Cannot find a tariff!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<Tariff> findAllTariffsByServiceId(int serviceId, int sort) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readTariffsByServiceId(con, serviceId, sort);
        } catch (DAOException e) {
            log.error("Cannot find tariffs for a service", e);

            throw new LogicException("Cannot find tariffs for a service!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<Tariff> findAllTariffsByPlanId(long planId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readTariffsByPlanId(con, planId);
        } catch (DAOException e) {
            log.error("Cannot find tariffs for a tariff plan", e);

            throw new LogicException("Cannot find tariffs for a tariff plan!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<Tariff> findAllTariffsByRequestId(long requestId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readTariffsByRequestId(con, requestId);
        } catch (DAOException e) {
            log.error("Cannot find tariffs for a request", e);

            throw new LogicException("Cannot find tariffs for a request!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public long createTariffPlanForUser(TariffPlan tariffPlan, List<Tariff> tariffs) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            long planId = serviceDAO.createTariffPlan(con, tariffPlan);
            serviceDAO.createTariffsByPlanId(con, tariffs, planId);

            dbConnector.commitTransaction(con);
            return planId;
        } catch (DAOException e) {
            log.error("Cannot create a tariff plan for a user", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot connect a tariff plan!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public TariffPlan findTariffPlanByUserId(long userId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readTariffPlanByUserId(con, userId);
        } catch (DAOException e) {
            log.error("Cannot find a tariff plan by user id", e);

            throw new LogicException("Cannot find a tariff plan!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public void updateTariffPlanForUser(TariffPlan tariffPlan, List<Tariff> tariffs) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            serviceDAO.updateTariffPlan(con, tariffPlan);
            serviceDAO.deleteTariffsByPlanId(con, tariffPlan.getId());
            serviceDAO.createTariffsByPlanId(con, tariffs, tariffPlan.getId());

            dbConnector.commitTransaction(con);
        } catch (DAOException e) {
            log.error("Cannot update a tariff plan for a user", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot change a tariff plan!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public void updateTariffPlanStatus(TariffPlan tariffPlan) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            serviceDAO.updateTariffPlanStatus(con, tariffPlan);
        } catch (DAOException e) {
            log.error("Cannot update a tariff plan status", e);

            throw new LogicException("Cannot update a tariff plan status!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public void deleteTariffPlanById(long planId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            serviceDAO.deleteTariffPlan(con, planId);
        } catch (DAOException e) {
            log.error("Cannot delete a tariff plan by id", e);

            throw new LogicException("Cannot delete a tariff plan!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public long createRequest(Request userRequest, List<Tariff> tariffs) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            dbConnector.disallowAutoCommit(con);

            long requestId = serviceDAO.createRequest(con, userRequest);
            serviceDAO.createTariffsByRequestId(con, tariffs, requestId);

            dbConnector.commitTransaction(con);
            return requestId;
        } catch (DAOException e) {
            log.error("Cannot create a connection request with tariffs", e);

            dbConnector.rollbackTransaction(con);

            throw new LogicException("Cannot create a connection request!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public Request findRequestById(long requestId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readRequestById(con, requestId);
        } catch (DAOException e) {
            log.error("Cannot find a connection request by id", e);

            throw new LogicException("Cannot find a connection request!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public List<Request> findAllRequests() throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.readAllRequests(con);
        } catch (DAOException e) {
            log.error("Cannot find connection requests", e);

            throw new LogicException("Cannot find connection requests!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public void deleteRequest(long requestId) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            serviceDAO.deleteRequestById(con, requestId);
        } catch (DAOException e) {
            log.error("Cannot delete a connection request by id", e);

            throw new LogicException("Cannot delete a connection request!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public int getServicesSize() throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            return serviceDAO.getServicesSize(con);
        } catch (DAOException e) {
            log.error("Cannot get services size", e);

            throw new LogicException("Cannot get an amount of services!", e);
        } finally {
            dbConnector.closeConnection(con);
        }
    }

    public void updateTariff(Tariff tariff) throws LogicException {
        Connection con = null;
        try {
            con = dbConnector.getConnection();
            serviceDAO.updateTariff(con, tariff);
        } catch (DAOException e) {
            log.error("Cannot update a tariff", e);

            throw new LogicException("Cannot update a tariff!", e);
        }
    }
}
