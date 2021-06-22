package server;

import element.Worker;

import java.sql.SQLException;
import java.util.TreeSet;
import java.util.stream.Stream;

public class DataManager {
    private DatabaseHandler handler;

    public  DataManager(DatabaseHandler handler) {
        this.handler = handler;
    }

    public TreeSet<Worker> getCollection() throws SQLException{
        return handler.getCollection();
    }

    public boolean findId(Integer id) throws SQLException{
        return handler.findId(id);
    }

    public void reconnect() throws SQLException {
        int count = 10000;
        while(!handler.connect()&&count>0) count--;
        if (count<=0) {
            throw new SQLException("База данных недоступна");
        }
    }

    public void add(Worker worker, boolean flag) throws SQLException {
        try{
            handler.add(worker, flag);
        } catch(SQLException e) {
            try {
                reconnect();
                handler.add(worker, flag);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }
    public void add(Worker worker) throws SQLException{
        add(worker, false);
    }
    public Boolean isEmpty() throws SQLException{
        try{
            return handler.isEmpty();
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.isEmpty();
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }
    public Worker first(String username) throws SQLException{
        try{
            return handler.first(username);
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.first(username);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public void clear(String username) throws SQLException{
        try{
            handler.clear(username);
        } catch(SQLException e) {
            try {
                reconnect();
                handler.clear(username);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public Stream<Worker> stream() throws SQLException{
        try{
            return handler.stream();
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.stream();
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public Integer size() throws SQLException{
        try{
            return handler.size();
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.size();
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public void remove(Integer id, String username) throws SQLException{
        try{
            handler.remove(id, username);
        } catch(SQLException e) {
            try {
                reconnect();
                handler.remove(id, username);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public Worker floor(Worker worker) throws SQLException{
        try{
            return handler.floor(worker);
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.floor(worker);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public boolean login(String username, String password) throws SQLException {
        try{
            return handler.login(username, password);
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.login(username, password);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }

    public Boolean register(String username, String password) throws SQLException {
        try{
            return handler.register(username, password);
        } catch(SQLException e) {
            try {
                reconnect();
                return handler.register(username, password);
            } catch(SQLException v) {
                System.out.println("Не удалось переподключиться к БД.");
                throw new SQLException(e.getMessage());
            }
        }
    }
}
