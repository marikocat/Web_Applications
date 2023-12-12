package com.my.dao.mysql;

public class Constants {
    public static final String SQL_INSERT_USER = "INSERT INTO user (login, password, role_id) VALUES (?, ?, ?)";
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    public static final String SQL_UPDATE_USER = "UPDATE user SET login=?, password=?, role_id=? WHERE id=?";
    public static final String SQL_UPDATE_USER_DETAILS = "UPDATE user_details SET first_name=?, last_name=?, email=?, phone_number=?, address=? WHERE user_id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";

    public static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    public static final String SQL_GET_ALL_SERVICES = "SELECT * FROM service";

    public static final String SQL_GET_TARIFF_BY_ID = "SELECT * " +
                                                        "FROM service s " +
                                                        "LEFT JOIN tariff t ON s.id = t.service_id " +
                                                        "WHERE t.id=?";
    public static final String SQL_GET_TARIFFS_SORT_BY_COST = "SELECT * " +
                                                                "FROM service s " +
                                                                "LEFT JOIN tariff t ON s.id = t.service_id " +
                                                                "WHERE s.id=? " +
                                                                "ORDER BY t.cost ASC";

    public static final String SQL_GET_TARIFFS_SORT_BY_NAME_ASC = "SELECT * " +
                                                                "FROM service s " +
                                                                "LEFT JOIN tariff t ON s.id = t.service_id " +
                                                                "WHERE s.id=? " +
                                                                "ORDER BY t.name ASC";

    public static final String SQL_GET_TARIFFS_SORT_BY_NAME_DESC = "SELECT * " +
                                                                "FROM service s " +
                                                                "LEFT JOIN tariff t ON s.id = t.service_id " +
                                                                "WHERE s.id=? " +
                                                                "ORDER BY t.name DESC";

    public static final String SQL_GET_TARIFFS_BY_TARIFF_PLAN_ID = "SELECT * " +
                                                                    "FROM service s " +
                                                                    "LEFT JOIN tariff t ON s.id = t.service_id " +
                                                                    "WHERE t.id IN (SELECT tariff_id FROM tariff_plan_has_tariff WHERE plan_id=?)";
    public static final String SQL_GET_TARIFFS_BY_REQUEST_ID = "SELECT * " +
                                                                "FROM service s " +
                                                                "LEFT JOIN tariff t ON s.id = t.service_id " +
                                                                "WHERE t.id IN (SELECT tariff_id FROM request_has_tariff WHERE request_id=?)";

    public static final String SQL_GET_SERVICE_BY_ID = "SELECT * FROM service WHERE id=?";
    public static final String SQL_GET_TARIFF_PLAN_BY_USER_ID = "SELECT * FROM tariff_plan WHERE user_id=?";
    public static final String SQL_INSERT_TARIFF_PLAN = "INSERT INTO tariff_plan (cost, start_date, end_date, user_id, status_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_TARIFFS_FOR_PLAN_ID = "INSERT INTO tariff_plan_has_tariff (plan_id, tariff_id) VALUES (?, ?)";
    public static final String SQL_UPDATE_TARIFF_PLAN = "UPDATE tariff_plan SET cost=?, start_date=?, end_date=?, status_id=? WHERE id=?";
    public static final String SQL_UPDATE_TARIFF_PLAN_STATUS = "UPDATE tariff_plan SET start_date=?, end_date=?, status_id=? WHERE id=?";
    public static final String SQL_DELETE_TARIFFS_FOR_PLAN_ID = "DELETE FROM tariff_plan_has_tariff WHERE plan_id=?";
    public static final String SQL_INSERT_REQUEST = "INSERT INTO request (first_name, last_name, email, phone_number, address, plan_cost) values (?, ?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_TARIFFS_FOR_REQUEST_ID = "INSERT INTO request_has_tariff (request_id, tariff_id) VALUES (?, ?)";
    public static final String SQL_GET_ALL_REQUESTS = "SELECT * FROM request";
    public static final String SQL_GET_REQUEST_BY_ID = "SELECT * FROM request WHERE id=?";
    public static final String SQL_INSERT_USER_DETAILS = "INSERT INTO user_details values (?, ?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_ACCOUNT = "INSERT INTO account values (default, 0.00, ?)";
    public static final String SQL_GET_ALL_USERS_WITH_DETAILS = "SELECT * " +
                                                                "FROM user u " +
                                                                "LEFT JOIN user_details ud ON u.id = ud.user_id " +
                                                                "LEFT JOIN account a ON u.id = a.user_id";
    public static final String SQL_DELETE_ACCOUNT_BY_USER_ID = "DELETE FROM account WHERE user_id=?";
    public static final String SQL_DELETE_TARIFF_PLAN_BY_ID = "DELETE FROM tariff_plan WHERE id=?";
    public static final String SQL_DELETE_REQUEST_BY_ID = "DELETE FROM request WHERE id=?";
    public static final String SQL_GET_ACCOUNT_BY_USER_ID = "SELECT * FROM account WHERE user_id=?";
    public static final String SQL_UPDATE_ACCOUNT = "UPDATE account SET balance=? WHERE user_id=?";
    public static final String SQL_COUNT_TARIFF_PLANS = "SELECT COUNT(id) FROM tariff_plan";
    public static final String SQL_GET_ALL_SUBSCRIBERS = "SELECT * FROM user WHERE role_id=1";
    public static final String SQL_GET_USERS_BY_PAGE = "SELECT * " +
                                                        "FROM user u " +
                                                        "LEFT JOIN user_details ud ON u.id = ud.user_id " +
                                                        "LEFT JOIN account a ON u.id = a.user_id " +
                                                        "LIMIT ? OFFSET ?";
    public static final String SQL_GET_USERS_SIZE = "SELECT COUNT(*) FROM user";
    public static final String SQL_GET_SERVICES_SIZE = "SELECT COUNT(*) FROM service";
    public static final String SQL_GET_USER_DETAILS_BY_USER_ID = "SELECT * FROM user_details ud WHERE ud.user_id=?";
    public static final String SQL_UPDATE_TARIFF = "UPDATE tariff SET cost=?, description=? WHERE id=?";
}