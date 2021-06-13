package server;

import element.*;
import tools.LocalDateAdapter;

import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Stream;

public class DatabaseHandler {
    private final String URL;
    private final String username;
    private final String password;
    private Connection connection;
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (login, password) VALUES (?, ?)";
    private static final String REMOVE_WORKER_REQUEST = "DELETE FROM WORKER WHERE name = ? AND x = ? AND y = ? AND salary = ? AND position = ? AND status = ? AND personal_data = ? AND country = ? AND eye = ? AND hair = ? AND height = ? AND owner = ? AND date_creation = ? AND date_start = ?";
    private static final String ADD_NEW_WORKER_REQUEST = "INSERT INTO WORKER (name, x, y, salary, position, status, personal_data, country, eye, hair, height, owner, date_creation, date_start) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK_USER_REQUEST = "SELECT * FROM USERS WHERE login = ?";
    private static final String WORKER_REQUEST = "SELECT * FROM WORKER";
    private static final String LOGIN_USER_REQUEST = "SELECT * FROM USERS WHERE login = ? AND password = ?";
    private static final String FIRST_WORKER_REQUEST = "SELECT MIN(id) FROM WORKER WHERE owner = ? AND password = ?";
    private static final String CLEAR_REQUEST = "DELETE FROM WORKER WHERE owner = ?";
    private static final String FLOOR_REQUEST = "SELECT * FROM WORKER WHERE id = ? AND owner = ?";

    public DatabaseHandler(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }
    
    public boolean connect() throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
        } catch(Exception e) {}
        Connection check = DriverManager.getConnection(URL, username, password);
        if (check != null) {
            this.connection = check;
            System.out.println("Установлено соединение с БД.");
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) throws SQLException {
        if (userExists(username)) {
            return false;
        }
        PreparedStatement register = connection.prepareStatement(ADD_USER_REQUEST);
        register.setString(1, username);
        register.setString(2, password);
        register.executeUpdate();
        register.close();
        return true;
    }

    public void add(Worker worker) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(ADD_NEW_WORKER_REQUEST);
        setWorker(saveStatement, worker);
        saveStatement.executeUpdate();
        saveStatement.close();
    }

    private void setWorker(PreparedStatement statement, Worker worker) throws SQLException{
        statement.setString(1, worker.getName());
        statement.setDouble(2, worker.getCoordinates().getX());
        statement.setDouble(3, worker.getCoordinates().getY());
        statement.setDouble(4, worker.getSalary());
        statement.setString(5, forcedString(worker.getPosition()));
        statement.setString(6, forcedString(worker.getStatus()));
        boolean personal = worker.getPerson() != null;
        statement.setBoolean(7, personal);
        if (personal) {
            statement.setString(8, forcedString(worker.getPerson().getNationality()));
            statement.setString(9, forcedString(worker.getPerson().getEyeColor()));
            statement.setString(10, forcedString(worker.getPerson().getHairColor()));
            statement.setInt(11, worker.getPerson().getHeight());
        } else {
            statement.setString(8, null);
            statement.setString(9, null);
            statement.setString(10, null);
            statement.setInt(11, 0);
        }
        statement.setString(12, worker.getOwner());
        statement.setTimestamp(13, Timestamp.valueOf(worker.getCreationDate()));
        statement.setTimestamp(14, Timestamp.valueOf(worker.getStartDate()));
    }

    private String forcedString(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return null;
        }
    }

    private Worker getWorker(ResultSet result) throws SQLException{
        String name = result.getString(2);
        Coordinates coordinates = new Coordinates(result.getDouble(3),
                result.getDouble(4));
        double salary = result.getDouble(5);
        Position position = null;
        try {
            position = Position.valueOf(result.getString(6));
        } catch(IllegalArgumentException | NullPointerException e) {}
        Status status = null;
        try {
            status = Status.valueOf(result.getString(7));
        } catch(IllegalArgumentException | NullPointerException e) {}
        boolean personalData = result.getBoolean(8);
        Person person = null;
        Country country = null;
        Color eyeColor = null;
        Color hairColor = null;
        int height = 0;
        try {
            country = Country.valueOf(result.getString(9));
        } catch(IllegalArgumentException | NullPointerException e) {}
        try {
            eyeColor = Color.valueOf(result.getString(10));
        } catch(IllegalArgumentException | NullPointerException e) {}
        try {
            hairColor = Color.valueOf(result.getString(11));
        } catch(IllegalArgumentException | NullPointerException e) {}
        height = result.getInt(12);
        String owner = result.getString(13);
        LocalDate dateCreation = LocalDateAdapter.encode(result.getString(14));
        LocalDate dateStart = LocalDateAdapter.encode(result.getString(15));
        if (personalData) {
            person = new Person(height, eyeColor, hairColor, country);
        }
        Worker worker = new Worker(name, coordinates, salary, dateStart, position, status, person);
        worker.setOwner(owner);
        return worker;
    }

    public boolean isEmpty() throws SQLException{
        PreparedStatement joinStatement = connection.prepareStatement(WORKER_REQUEST);
        ResultSet result = joinStatement.executeQuery();
        return result.next();
    }

    public void clear(String username) throws SQLException{
        PreparedStatement clearStatement = connection.prepareStatement(CLEAR_REQUEST);
        clearStatement.setString(1, username);
        clearStatement.executeUpdate();
    }

    public int size() throws SQLException{
        PreparedStatement sizeStatement = connection.prepareStatement(WORKER_REQUEST);
        ResultSet result = sizeStatement.executeQuery();
        int count = 0;
        while(result.next()) {
            count++;
        }
        return count;
    }

    public void remove(Worker worker) throws SQLException{
        PreparedStatement removeStatement = connection.prepareStatement(REMOVE_WORKER_REQUEST);
        setWorker(removeStatement, worker);
        removeStatement.executeUpdate();
    }

    public Worker first() throws SQLException{
            PreparedStatement joinStatement = connection.prepareStatement(FIRST_WORKER_REQUEST);
            ResultSet result = joinStatement.executeQuery();
            if (result.next()) {
                return getWorker(result);
            } else {
                return new Worker(-2147483648);
            }
    }

    public Stream<Worker> stream() throws SQLException{
        TreeSet<Worker> collection = new TreeSet<>(new Comparator<Worker>() {
            @Override
            public int compare(Worker o1, Worker o2) {
                return (o1.getId()>o2.getId()) ? 1 : -1;
            }
        });
        int count = 0;
        PreparedStatement table = connection.prepareStatement(WORKER_REQUEST);
        ResultSet result = table.executeQuery();
        while (result.next()&&count < 100) {
            collection.add(getWorker(result));
        }
        return collection.stream();
    }
    public Worker floor(Worker worker) throws SQLException{
        PreparedStatement floorStatement = connection.prepareStatement(FLOOR_REQUEST);
        floorStatement.setInt(1, worker.getId());
        floorStatement.setString(2, worker.getOwner());
        ResultSet result = floorStatement.executeQuery();
        if (result.next()) {
            return getWorker(result);
        }
        return new Worker(worker.getId()+1);
    }

    public boolean login(String username, String password) throws SQLException {
        PreparedStatement login = connection.prepareStatement(LOGIN_USER_REQUEST);
        login.setString(1, username);
        login.setString(2, password);
        ResultSet result = login.executeQuery();
        if (result.next()) {
            login.close();
            return true;
        }
        login.close();
        return false;
    }

    public boolean userExists(String username) throws SQLException {
        PreparedStatement check = connection.prepareStatement(CHECK_USER_REQUEST);
        check.setString(1,username);
        ResultSet result = check.executeQuery();
        if (result.next()) {
            check.close();
            return true;
        }
        check.close();
        return false;
    }


}
