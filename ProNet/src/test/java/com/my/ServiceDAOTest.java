package com.my;

import com.my.dao.DAOException;
import com.my.dao.DAOFactory;
import com.my.dao.ServiceDAO;
import com.my.dao.mysql.Constants;
import com.my.dao.mysql.Fields;
import com.my.domain.*;
import com.my.util.UtilFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceDAOTest {
    private final ServiceDAO serviceDAO;

    public ServiceDAOTest() throws Exception {
        DAOFactory.setDAOFactoryFCN("com.my.dao.mysql.MysqlDAOFactory");
        serviceDAO = DAOFactory.getInstance().getServiceDAO();
    }

    // Services
    @Test
    void testReadServiceById() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt(Fields.ID)).thenReturn(1);
        when(rs.getString(Fields.NAME)).thenReturn("Telephone");
        when(rs.getString(Fields.DESCRIPTION)).thenReturn("Service description");

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_SERVICE_BY_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Service expectedService = new Service();
        expectedService.setId(1);
        expectedService.setName("Telephone");
        expectedService.setDescription("Service description");

        Service actualService = serviceDAO.readServiceById(con, 1);

        Assertions.assertTrue(
                new ReflectionEquals(expectedService)
                        .matches(actualService));
    }

    @Test
    void testReadAllServices() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt(Fields.ID))
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(4);
        when(rs.getString(Fields.NAME))
                .thenReturn("Telephone")
                .thenReturn("Internet")
                .thenReturn("Cable TV")
                .thenReturn("IP-TV");
        when(rs.getString(Fields.DESCRIPTION))
                .thenReturn("Service1")
                .thenReturn("Service2")
                .thenReturn("Service3")
                .thenReturn("Service4");
        Statement stmt = mock(Statement.class);
        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.SQL_GET_ALL_SERVICES)).thenReturn(rs);

        List<Service> expectedServices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedServices.add(new Service());
        }
        expectedServices.get(0).setId(1);
        expectedServices.get(0).setName("Telephone");
        expectedServices.get(1).setId(2);
        expectedServices.get(1).setName("Internet");
        expectedServices.get(2).setId(3);
        expectedServices.get(2).setName("Cable TV");
        expectedServices.get(3).setId(4);
        expectedServices.get(3).setName("IP-TV");

        List<Service> actualServices = serviceDAO.readAllServices(con);

        for (int i = 0; i < expectedServices.size(); i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedServices.get(i), "description")
                            .matches(actualServices.get(i)));
        }
    }

    @Test
    void getServicesSize() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt(1)).thenReturn(4);

        Statement stmt = mock(Statement.class);
        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.SQL_GET_SERVICES_SIZE)).thenReturn(rs);

        int expectedResult = 4;
        int actualResult = serviceDAO.getServicesSize(con);

        Assertions.assertEquals(expectedResult, actualResult);
    }


    // Tariff Plan
    @Test
    void testCreateTariffPlan() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(1)).thenReturn(50L);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_TARIFF_PLAN, Statement.RETURN_GENERATED_KEYS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);
        when(pstmt.getGeneratedKeys()).thenReturn(rs);

        TariffPlan tariffPlan = fillTariffPlanInfo();

        long expectedResult = 50;
        long actualResult = serviceDAO.createTariffPlan(con, tariffPlan);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void testReadTariffPlanByUserId(int x) throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(Fields.ID)).thenReturn(15L);
        when(rs.getDouble(Fields.COST)).thenReturn(500.0);
        when(rs.getDate(Fields.START_DATE)).thenReturn(UtilFunctions.getStartDate());
        when(rs.getDate(Fields.END_DATE)).thenReturn(UtilFunctions.getEndDate());
        when(rs.getLong(Fields.USER_ID)).thenReturn(10L);
        when(rs.getInt(Fields.STATUS_ID)).thenReturn(x);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_TARIFF_PLAN_BY_USER_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        TariffPlan expectedTariffPlan = new TariffPlan();
        expectedTariffPlan.setId(15);
        expectedTariffPlan.setCost(500.0);
        expectedTariffPlan.setUserId(10L);
        expectedTariffPlan.setStatus(Status.values()[x]);

        TariffPlan actualTariffPlan = serviceDAO.readTariffPlanByUserId(con, 10);

        Assertions.assertTrue(
                new ReflectionEquals(expectedTariffPlan, "startDate", "endDate")
                        .matches(actualTariffPlan));
    }

    @Test
    void testUpdateTariffPlan() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_UPDATE_TARIFF_PLAN)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        TariffPlan tariffPlan = fillTariffPlanInfo();

        boolean actualResult = serviceDAO.updateTariffPlan(con, tariffPlan);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testUpdateTariffPlanStatus() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_UPDATE_TARIFF_PLAN_STATUS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        TariffPlan tariffPlan = fillTariffPlanInfo();

        boolean actualResult = serviceDAO.updateTariffPlanStatus(con, tariffPlan);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testDeleteTariffPlan() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_DELETE_TARIFF_PLAN_BY_ID)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        boolean actualResult = serviceDAO.deleteTariffPlan(con, 20);
        Assertions.assertTrue(actualResult);
    }


    // Tariffs
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testCreateTariffsByPlanId(int x) throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_TARIFFS_FOR_PLAN_ID)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        List<Tariff> tariffs = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            Tariff tariff = new Tariff();
            tariff.setId(i + 1);
            tariffs.add(tariff);
        }
        boolean result = serviceDAO.createTariffsByPlanId(con, tariffs, 10);

        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testCreateTariffsByRequestId(int x) throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_TARIFFS_FOR_REQUEST_ID)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        List<Tariff> tariffs = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            Tariff tariff = new Tariff();
            tariff.setId(i + 1);
            tariffs.add(tariff);
        }
        boolean result = serviceDAO.createTariffsByRequestId(con, tariffs, 10);

        Assertions.assertTrue(result);
    }

    @Test
    void testReadTariff() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt("s.id")).thenReturn(2);
        when(rs.getString("s.name")).thenReturn("Internet");
        when(rs.getString("s.description")).thenReturn("Internet from ProNet means stability, quality and the most modern technologies for every subscriber.");
        when(rs.getInt("t.id")).thenReturn(5);
        when(rs.getString("t.name")).thenReturn("Newbie 50");
        when(rs.getDouble("t.cost")).thenReturn(125.0);
        when(rs.getString("t.description")).thenReturn("Internet speed: up to 50 Mbit. Unlimited traffic.");

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_TARIFF_BY_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Service s = new Service();
        s.setId(2);
        s.setName("Internet");
        s.setDescription("Internet from ProNet means stability, quality and the most modern technologies for every subscriber.");
        Tariff expectedTariff = new Tariff();
        expectedTariff.setId(5);
        expectedTariff.setName("Newbie 50");
        expectedTariff.setCost(125.0);
        expectedTariff.setDescription("Internet speed: up to 50 Mbit. Unlimited traffic.");
        expectedTariff.setService(s);

        Tariff actualTariff = serviceDAO.readTariff(con, 5);

        Assertions.assertTrue(
                new ReflectionEquals(expectedTariff, "service")
                        .matches(actualTariff));
        Assertions.assertTrue(
                new ReflectionEquals(expectedTariff.getService())
                        .matches(actualTariff.getService()));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testReadTariffsByServiceId(int x) throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt("s.id")).thenReturn(4);
        when(rs.getString("s.name")).thenReturn("IP-TV");
        when(rs.getString("s.description")).thenReturn("Digital television.");

        when(rs.getInt("t.id"))
                .thenReturn(x == 2 ? 11 : 10)
                .thenReturn(x == 2 ? 10 : 11);
        when(rs.getString("t.name"))
                .thenReturn(x == 2 ? "Movies+ 200" : "Light 100")
                .thenReturn(x == 2 ? "Light 100" : "Movies+ 200");
        when(rs.getDouble("t.cost"))
                .thenReturn(x == 2 ? 200.0 : 100.0)
                .thenReturn(x == 2 ? 100.0 : 200.0);
        when(rs.getString("t.description"))
                .thenReturn(x == 2 ? "200 channels in IPTV." : "100 channels in IPTV.")
                .thenReturn(x == 2 ? "100 channels in IPTV." : "200 channels in IPTV.");

        String query;
        if (x == 1) {
            query = Constants.SQL_GET_TARIFFS_SORT_BY_NAME_ASC;
        } else if (x == 2) {
            query = Constants.SQL_GET_TARIFFS_SORT_BY_NAME_DESC;
        } else {
            query = Constants.SQL_GET_TARIFFS_SORT_BY_COST;
        }

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(query)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Service service = new Service();
        service.setId(4);
        service.setName("IP-TV");
        service.setDescription("Digital television.");
        Tariff tariff1 = new Tariff();
        tariff1.setService(service);
        tariff1.setId(10);
        tariff1.setName("Light 100");
        tariff1.setCost(100.0);
        tariff1.setDescription("100 channels in IPTV.");
        Tariff tariff2 = new Tariff();
        tariff2.setService(service);
        tariff2.setId(11);
        tariff2.setName("Movies+ 200");
        tariff2.setCost(200.0);
        tariff2.setDescription("200 channels in IPTV.");
        List<Tariff> expectedTariffs = new ArrayList<>();
        if (x == 2) {
            expectedTariffs.add(tariff2);
            expectedTariffs.add(tariff1);
        } else {
            expectedTariffs.add(tariff1);
            expectedTariffs.add(tariff2);
        }

        List<Tariff> actualTariffs = serviceDAO.readTariffsByServiceId(con, 4, x);

        for (int i = 0; i < 2; i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedTariffs.get(i), "service")
                            .matches(actualTariffs.get(i)));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedTariffs.get(i).getService())
                            .matches(actualTariffs.get(i).getService()));
        }
    }

    @Test
    void testReadTariffsByPlanId() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt("s.id"))
                .thenReturn(2)
                .thenReturn(4);
        when(rs.getString("s.name"))
                .thenReturn("Internet")
                .thenReturn("IP-TV");
        when(rs.getString("s.description"))
                .thenReturn("Internet from ProNet means stability.")
                .thenReturn("Digital television.");

        when(rs.getInt("t.id"))
                .thenReturn(5)
                .thenReturn(10);
        when(rs.getString("t.name"))
                .thenReturn("Newbie 50")
                .thenReturn("Light 100");
        when(rs.getDouble("t.cost"))
                .thenReturn(125.0)
                .thenReturn(100.0);
        when(rs.getString("t.description"))
                .thenReturn("Internet speed: up to 50 Mbit.")
                .thenReturn("100 channels in IPTV.");

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_TARIFFS_BY_TARIFF_PLAN_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Service service1 = new Service();
        service1.setId(2);
        service1.setName("Internet");
        service1.setDescription("Internet from ProNet means stability.");
        Service service2 = new Service();
        service2.setId(4);
        service2.setName("IP-TV");
        service2.setDescription("Digital television.");

        Tariff tariff1 = new Tariff();
        tariff1.setService(service1);
        tariff1.setId(5);
        tariff1.setName("Newbie 50");
        tariff1.setCost(125.0);
        tariff1.setDescription("Internet speed: up to 50 Mbit.");
        Tariff tariff2 = new Tariff();
        tariff2.setService(service2);
        tariff2.setId(10);
        tariff2.setName("Light 100");
        tariff2.setCost(100.0);
        tariff2.setDescription("100 channels in IPTV.");
        List<Tariff> expectedTariffs = new ArrayList<>();
        expectedTariffs.add(tariff1);
        expectedTariffs.add(tariff2);

        List<Tariff> actualTariffs = serviceDAO.readTariffsByPlanId(con, 10);

        for (int i = 0; i < 2; i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedTariffs.get(i), "service")
                            .matches(actualTariffs.get(i)));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedTariffs.get(i).getService())
                            .matches(actualTariffs.get(i).getService()));
        }
    }

    @Test
    void testReadTariffsByRequestId() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getInt("s.id"))
                .thenReturn(2)
                .thenReturn(4);
        when(rs.getString("s.name"))
                .thenReturn("Internet")
                .thenReturn("IP-TV");
        when(rs.getString("s.description"))
                .thenReturn("Internet from ProNet means stability.")
                .thenReturn("Digital television.");

        when(rs.getInt("t.id"))
                .thenReturn(5)
                .thenReturn(11);
        when(rs.getString("t.name"))
                .thenReturn("Newbie 50")
                .thenReturn("Movies+ 200");
        when(rs.getDouble("t.cost"))
                .thenReturn(125.0)
                .thenReturn(200.0);
        when(rs.getString("t.description"))
                .thenReturn("Internet speed: up to 50 Mbit.")
                .thenReturn("200 channels in IPTV.");

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_TARIFFS_BY_REQUEST_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Service service1 = new Service();
        service1.setId(2);
        service1.setName("Internet");
        service1.setDescription("Internet from ProNet means stability.");
        Service service2 = new Service();
        service2.setId(4);
        service2.setName("IP-TV");
        service2.setDescription("Digital television.");

        Tariff tariff1 = new Tariff();
        tariff1.setService(service1);
        tariff1.setId(5);
        tariff1.setName("Newbie 50");
        tariff1.setCost(125.0);
        tariff1.setDescription("Internet speed: up to 50 Mbit.");
        Tariff tariff2 = new Tariff();
        tariff2.setService(service2);
        tariff2.setId(11);
        tariff2.setName("Movies+ 200");
        tariff2.setCost(200.0);
        tariff2.setDescription("200 channels in IPTV.");
        List<Tariff> expectedTariffs = new ArrayList<>();
        expectedTariffs.add(tariff1);
        expectedTariffs.add(tariff2);

        List<Tariff> actualTariffs = serviceDAO.readTariffsByRequestId(con, 50);

        for (int i = 0; i < 2; i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedTariffs.get(i), "service")
                            .matches(actualTariffs.get(i)));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedTariffs.get(i).getService())
                            .matches(actualTariffs.get(i).getService()));
        }
    }

    @Test
    void testUpdateTariff() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_UPDATE_TARIFF)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        Tariff tariff = new Tariff();
        tariff.setId(2);
        tariff.setCost(150.0);
        tariff.setDescription("New tariff");

        boolean actualResult = serviceDAO.updateTariff(con, tariff);

        Assertions.assertTrue(actualResult);
    }

    @Test
    void testDeleteTariffsByPlanId() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_DELETE_TARIFFS_FOR_PLAN_ID)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        boolean actualResult = serviceDAO.deleteTariffsByPlanId(con, 40);
        Assertions.assertTrue(actualResult);
    }


    // Request
    @Test
    void testCreateRequest() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong(1)).thenReturn(20L);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);
        when(pstmt.getGeneratedKeys()).thenReturn(rs);

        Request userRequest = new Request();
        UserDetails userDetails = fillUserDetailsInfo();
        userRequest.setUserDetails(userDetails);
        userRequest.setPlanCost(450.0);

        long expectedResult = 20;
        long actualResult = serviceDAO.createRequest(con, userRequest);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testReadRequestById() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("first_name")).thenReturn("Nick");
        when(rs.getString("last_name")).thenReturn("Right");
        when(rs.getString("email")).thenReturn("nick@mail.com");
        when(rs.getString("phone_number")).thenReturn("+18(123)1568978");
        when(rs.getString("address")).thenReturn("Maple St. 5");
        when(rs.getDouble("plan_cost")).thenReturn(250.0);

        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_GET_REQUEST_BY_ID)).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);

        Request expectedRequest = new Request();
        expectedRequest.setId(1);
        expectedRequest.setPlanCost(250.0);

        Request actualRequest = serviceDAO.readRequestById(con, 1);

        Assertions.assertTrue(
                new ReflectionEquals(expectedRequest, "userDetails")
                        .matches(actualRequest));
    }

    @Test
    void readAllRequests() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(rs.getLong("id"))
                .thenReturn(1L)
                .thenReturn(2L);
        when(rs.getString("first_name"))
                .thenReturn("name1")
                .thenReturn("name2");
        when(rs.getString("last_name"))
                .thenReturn("surname1")
                .thenReturn("surname2");
        when(rs.getString("email"))
                .thenReturn("name1@mail.com")
                .thenReturn("name2@mail.com");
        when(rs.getString("phone_number"))
                .thenReturn("+123")
                .thenReturn("+456");
        when(rs.getString("address"))
                .thenReturn("Street1")
                .thenReturn("Street2");
        when(rs.getDouble("plan_cost"))
                .thenReturn(500.0)
                .thenReturn(250.0);

        Statement stmt = mock(Statement.class);
        Connection con = mock(Connection.class);
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.SQL_GET_ALL_REQUESTS)).thenReturn(rs);

        UserDetails ud1 = new UserDetails();
        ud1.setFirstName("name1");
        ud1.setLastName("surname1");
        ud1.setEmail("name1@mail.com");
        ud1.setPhoneNumber("+123");
        ud1.setAddress("Street1");
        Request request1 = new Request();
        request1.setId(1);
        request1.setPlanCost(500.0);
        request1.setUserDetails(ud1);
        UserDetails ud2 = new UserDetails();
        ud2.setFirstName("name2");
        ud2.setLastName("surname2");
        ud2.setEmail("name2@mail.com");
        ud2.setPhoneNumber("+456");
        ud2.setAddress("Street2");
        Request request2 = new Request();
        request2.setId(2);
        request2.setPlanCost(250.0);
        request2.setUserDetails(ud2);
        List<Request> expectedRequests = new ArrayList<>();
        expectedRequests.add(request1);
        expectedRequests.add(request2);

        List<Request> actualRequests = serviceDAO.readAllRequests(con);

        for (int i = 0; i < 2; i++) {
            Assertions.assertTrue(
                    new ReflectionEquals(expectedRequests.get(i), "userDetails")
                            .matches(actualRequests.get(i)));
            Assertions.assertTrue(
                    new ReflectionEquals(expectedRequests.get(i).getUserDetails())
                            .matches(actualRequests.get(i).getUserDetails()));
        }
    }

    @Test
    void testDeleteRequestById() throws SQLException, DAOException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Connection con = mock(Connection.class);
        when(con.prepareStatement(Constants.SQL_DELETE_REQUEST_BY_ID)).thenReturn(pstmt);
        when(pstmt.executeUpdate()).thenReturn(1);

        boolean actualResult = serviceDAO.deleteRequestById(con, 30);
        Assertions.assertTrue(actualResult);
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

    private static TariffPlan fillTariffPlanInfo() {
        TariffPlan tariffPlan = new TariffPlan();
        tariffPlan.setCost(500.0);
        tariffPlan.setStartDate(UtilFunctions.getStartDate());
        tariffPlan.setEndDate(UtilFunctions.getStartDate());
        tariffPlan.setUserId(10);
        tariffPlan.setStatus(Status.BLOCKED);
        return tariffPlan;
    }
}