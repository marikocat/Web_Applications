package com.my.dao.mysql;

import com.my.dao.DAOException;
import com.my.dao.ServiceDAO;
import com.my.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlServiceDAO implements ServiceDAO {
    // Services
    @Override
    public Service readServiceById(Connection con, int id) throws DAOException {
        Service service = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_SERVICE_BY_ID)) {
            int k = 0;
            pstmt.setInt(++k, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    service = extractService(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return service;
    }

    @Override
    public List<Service> readAllServices(Connection con) throws DAOException {
        List<Service> services = new ArrayList<>();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(Constants.SQL_GET_ALL_SERVICES)) {
            while (rs.next()) {
                services.add(extractService(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return services;
    }

    @Override
    public int getServicesSize(Connection con) throws DAOException {
        int size = 0;
        try (Statement stmt = con.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(Constants.SQL_GET_SERVICES_SIZE)) {
                if (rs.next()) {
                    size = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return size;
    }


    // Tariff Plan
    @Override
    public long createTariffPlan(Connection con, TariffPlan tariffPlan) throws DAOException {
        long planId = 0;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_TARIFF_PLAN, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            pstmt.setDouble(++k, tariffPlan.getCost());
            pstmt.setDate(++k, tariffPlan.getStartDate());
            pstmt.setDate(++k, tariffPlan.getEndDate());
            pstmt.setLong(++k, tariffPlan.getUserId());
            pstmt.setInt(++k, tariffPlan.getStatus().getId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        planId = rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return planId;
    }

    @Override
    public TariffPlan readTariffPlanByUserId(Connection con, long userId) throws DAOException {
        TariffPlan tariffPlan = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_TARIFF_PLAN_BY_USER_ID)) {
            int k = 0;
            pstmt.setLong(++k, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tariffPlan = extractTariffPlan(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tariffPlan;
    }

    @Override
    public boolean updateTariffPlan(Connection con, TariffPlan tariffPlan) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_UPDATE_TARIFF_PLAN)) {
            int k = 0;
            pstmt.setDouble(++k, tariffPlan.getCost());
            pstmt.setDate(++k, tariffPlan.getStartDate());
            pstmt.setDate(++k, tariffPlan.getEndDate());
            pstmt.setInt(++k, tariffPlan.getStatus().getId());
            pstmt.setLong(++k, tariffPlan.getId());

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
    public boolean updateTariffPlanStatus(Connection con, TariffPlan tariffPlan) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_UPDATE_TARIFF_PLAN_STATUS)) {
            int k = 0;
            pstmt.setDate(++k, tariffPlan.getStartDate());
            pstmt.setDate(++k, tariffPlan.getEndDate());
            pstmt.setInt(++k, tariffPlan.getStatus().getId());
            pstmt.setLong(++k, tariffPlan.getId());
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
    public boolean deleteTariffPlan(Connection con, long planId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_DELETE_TARIFF_PLAN_BY_ID)) {
            int k = 0;
            pstmt.setLong(++k, planId);
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
    public long countTariffPlans(Connection con) throws DAOException {
        long count = 0;
        try (Statement stmt = con.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(Constants.SQL_COUNT_TARIFF_PLANS)) {
                if (rs.next()) {
                    count = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return count;
    }


    // Tariffs
    @Override
    public boolean createTariffsByPlanId(Connection con, List<Tariff> tariffs, long planId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_TARIFFS_FOR_PLAN_ID)) {
            int rows = 0;
            for (Tariff t : tariffs) {
                int k = 0;
                pstmt.setLong(++k, planId);
                pstmt.setInt(++k, t.getId());
                rows += pstmt.executeUpdate();
            }
            if (rows == tariffs.size()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public boolean createTariffsByRequestId(Connection con, List<Tariff> tariffs, long requestId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_TARIFFS_FOR_REQUEST_ID)) {
            int rows = 0;
            for (Tariff t : tariffs) {
                int k = 0;
                pstmt.setLong(++k, requestId);
                pstmt.setInt(++k, t.getId());
                rows += pstmt.executeUpdate();
            }
            if (rows == tariffs.size()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public Tariff readTariff(Connection con, int id) throws DAOException {
        Tariff tariff = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_TARIFF_BY_ID)) {
            int k = 0;
            pstmt.setInt(++k, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tariff = extractTariffWithService(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tariff;
    }

    @Override
    public List<Tariff> readTariffsByServiceId(Connection con, int serviceId, int sort) throws DAOException {
        String query;
        if (sort == 1) {
            query = Constants.SQL_GET_TARIFFS_SORT_BY_NAME_ASC;
        } else if (sort == 2) {
            query = Constants.SQL_GET_TARIFFS_SORT_BY_NAME_DESC;
        } else {
            query = Constants.SQL_GET_TARIFFS_SORT_BY_COST;
        }
        List<Tariff> tariffs = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            int k = 0;
            pstmt.setInt(++k, serviceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tariffs.add(extractTariffWithService(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tariffs;
    }

    @Override
    public List<Tariff> readTariffsByPlanId(Connection con, long planId) throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_TARIFFS_BY_TARIFF_PLAN_ID)) {
            int k = 0;
            pstmt.setLong(++k, planId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tariffs.add(extractTariffWithService(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tariffs;
    }

    @Override
    public List<Tariff> readTariffsByRequestId(Connection con, long requestId) throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_TARIFFS_BY_REQUEST_ID)) {
            int k = 0;
            pstmt.setLong(++k, requestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tariffs.add(extractTariffWithService(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tariffs;
    }

    @Override
    public boolean updateTariff(Connection con, Tariff tariff) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_UPDATE_TARIFF)) {
            int k = 0;
            pstmt.setDouble(++k, tariff.getCost());
            pstmt.setString(++k, tariff.getDescription());
            pstmt.setInt(++k, tariff.getId());
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
    public boolean deleteTariffsByPlanId(Connection con, long planId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_DELETE_TARIFFS_FOR_PLAN_ID)) {
            int k = 0;
            pstmt.setLong(++k, planId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }


    // Request
    @Override
    public long createRequest(Connection con, Request userRequest) throws DAOException {
        long requestId = 0;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            pstmt.setString(++k, userRequest.getUserDetails().getFirstName());
            pstmt.setString(++k, userRequest.getUserDetails().getLastName());
            pstmt.setString(++k, userRequest.getUserDetails().getEmail());
            pstmt.setString(++k, userRequest.getUserDetails().getPhoneNumber());
            pstmt.setString(++k, userRequest.getUserDetails().getAddress());
            pstmt.setDouble(++k, userRequest.getPlanCost());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        requestId = rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return requestId;
    }

    @Override
    public Request readRequestById(Connection con, long requestId) throws DAOException {
        Request userRequest = null;
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_GET_REQUEST_BY_ID)) {
            int k = 0;
            pstmt.setLong(++k, requestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userRequest = extractRequest(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return userRequest;
    }

    @Override
    public List<Request> readAllRequests(Connection con) throws DAOException {
        List<Request> requests = new ArrayList<>();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(Constants.SQL_GET_ALL_REQUESTS)) {
            while (rs.next()) {
                requests.add(extractRequest(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return requests;
    }

    @Override
    public boolean deleteRequestById(Connection con, long requestId) throws DAOException {
        try (PreparedStatement pstmt = con.prepareStatement(Constants.SQL_DELETE_REQUEST_BY_ID)) {
            int k = 0;
            pstmt.setLong(++k, requestId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }


    // helper methods
    private static TariffPlan extractTariffPlan(ResultSet rs) throws SQLException {
        TariffPlan tariffPlan = new TariffPlan();
        tariffPlan.setId(rs.getLong(Fields.ID));
        tariffPlan.setCost(rs.getDouble(Fields.COST));
        tariffPlan.setStartDate(rs.getDate(Fields.START_DATE));
        tariffPlan.setEndDate(rs.getDate(Fields.END_DATE));
        tariffPlan.setUserId(rs.getLong(Fields.USER_ID));
        tariffPlan.setStatus(Status.values()[rs.getInt(Fields.STATUS_ID)]);
        return tariffPlan;
    }

    private static Service extractService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt(Fields.ID));
        service.setName(rs.getString(Fields.NAME));
        service.setDescription(rs.getString(Fields.DESCRIPTION));
        return service;
    }

    private static Tariff extractTariffWithService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt("s.id"));
        service.setName(rs.getString("s.name"));
        service.setDescription(rs.getString("s.description"));

        Tariff tariff = new Tariff();
        tariff.setId(rs.getInt("t.id"));
        tariff.setName(rs.getString("t.name"));
        tariff.setCost(rs.getDouble("t.cost"));
        tariff.setDescription(rs.getString("t.description"));
        tariff.setService(service);
        return tariff;
    }

    private Request extractRequest(ResultSet rs) throws SQLException {
        Request request = new Request();
        UserDetails userDetails = new UserDetails();
        request.setId(rs.getLong("id"));
        userDetails.setFirstName(rs.getString("first_name"));
        userDetails.setLastName(rs.getString("last_name"));
        userDetails.setEmail(rs.getString("email"));
        userDetails.setPhoneNumber(rs.getString("phone_number"));
        userDetails.setAddress(rs.getString("address"));
        request.setUserDetails(userDetails);
        request.setPlanCost(rs.getDouble("plan_cost"));
        return request;
    }
}
