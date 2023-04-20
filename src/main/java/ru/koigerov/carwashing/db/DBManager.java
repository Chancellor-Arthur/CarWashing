package ru.koigerov.carwashing.db;

import ru.koigerov.carwashing.entities.Record;
import ru.koigerov.carwashing.entities.Service;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public final class DBManager {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/car-washing";
    static final String DB_USERNAME = "postgres";
//    static final String DB_PASSWORD = "postgres";
    static final String DB_PASSWORD = "password";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private static void executeUpdate(String query) throws SQLException {
        getConnection().createStatement().executeUpdate(query);
    }

    public static ResultSet getUser(String login, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ? AND password = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, login);
        statement.setString(2, password);

        return statement.executeQuery();
    }

    public static ResultSet getUser(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, login);

        return statement.executeQuery();
    }

    public static ResultSet getService(String serviceName) throws SQLException {
        String query = "SELECT * FROM service WHERE service_name = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, serviceName);

        return statement.executeQuery();
    }

    public static ResultSet getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";

        PreparedStatement statement = getConnection().prepareStatement(query);

        return statement.executeQuery();
    }

    public static ResultSet getAllRecords(boolean isDeleted) throws SQLException {
        String query;
        if (isDeleted) query = "SELECT * FROM record";
        else query = "SELECT * FROM record WHERE deleted_at IS NULL";

        PreparedStatement statement = getConnection().prepareStatement(query);

        return statement.executeQuery();
    }

    public static ResultSet getAllService() throws SQLException {
        String query = "SELECT * FROM service";

        PreparedStatement statement = getConnection().prepareStatement(query);

        return statement.executeQuery();
    }

    public static ResultSet getAllRecordByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM record WHERE user_id = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, userId);

        return statement.executeQuery();
    }

    public static ResultSet getAllRecordForDay(LocalDate date) throws SQLException {
        String query = "SELECT * FROM record WHERE date = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setDate(1, Date.valueOf(date.toString()));

        return statement.executeQuery();
    }

    public static ResultSet getAllRecordsForThreeDays() throws SQLException, ParseException {
        String query = "SELECT * FROM record WHERE date > ?";

        PreparedStatement statement = getConnection().prepareStatement(query);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        calendar.setTime(sdf.parse(String.valueOf(LocalDate.now())));
        calendar.add(Calendar.DATE, -3);
        var date = calendar.getTime();

        statement.setDate(1, (Date) date);

        return statement.executeQuery();
    }

    public static ResultSet getRecord(int id) throws SQLException {
        String query = "SELECT * FROM record WHERE id = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, id);

        return statement.executeQuery();
    }

    public static ResultSet getServiceById(int id) throws SQLException {
        String query = "SELECT * FROM service WHERE id = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, id);

        return statement.executeQuery();
    }

    public static ResultSet getServiceByName(String name) throws SQLException {
        String query = "SELECT * FROM service WHERE service_name = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, name);

        return statement.executeQuery();
    }

    public static ResultSet getAllRecords(int userId) throws SQLException {
        String query = "SELECT record.id, user_id, service_name, car_name, date, time FROM record INNER JOIN service on service.id = record.service_id WHERE user_id = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, userId);

        return statement.executeQuery();
    }

    public static void createRecord(Record record) throws SQLException {
        int serviceId = -1;
        var service = getServiceByName(record.getService());
        if (service.next()) serviceId = service.getInt("id");

        if (serviceId == -1) return;

        String query = "INSERT INTO record (user_id, service_id, car_name, date, time) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, record.getUserId());
        statement.setInt(2, serviceId);
        statement.setString(3, record.getCar());
        statement.setDate(4, Date.valueOf(record.getDate()));
        statement.setString(5, record.getTime());

        statement.executeUpdate();
    }

    public static void createService(Service service) throws SQLException {
        String query = "INSERT INTO service (service_name, duration) VALUES (?, ?)";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, service.getName());
        statement.setInt(2, service.getDuration());

        statement.executeUpdate();
    }

    public static void createUser(String login, String password, Boolean isAdmin) throws SQLException {
        String query = "INSERT INTO users (login, password, is_admin) VALUES (?, ?, ?)";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, login);
        statement.setString(2, password);
        statement.setBoolean(3, isAdmin);

        statement.executeUpdate();
    }

    public static void removeUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

    public static void removeRecord(int id) throws SQLException {
        String query = "UPDATE record SET deleted_at = ? WHERE id = ?;";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setDate(1, Date.valueOf(LocalDate.now()));
        statement.setInt(2, id);

        statement.executeUpdate();
    }

    public static void removeService(int id) throws SQLException {
        String query = "DELETE FROM service WHERE id = ?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
    }
}
