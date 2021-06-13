package command;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import server.DataManager;
import tools.Speaker;

/**
 * Абстрактный класс команды.
 *
 * @author mike
 */
public abstract class Command  implements Serializable{

    boolean ready = false;
    Speaker speaker;
    protected String username = null;
    protected String password = null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Command(String... args) {
        ready = true;
    }

    public Speaker event(DataManager collection) throws ExecutionException {
        return null;
    }

    public boolean isReady() {
        return ready;
    }
    
    public void setDone() {
        this.ready = false;
    }
}
