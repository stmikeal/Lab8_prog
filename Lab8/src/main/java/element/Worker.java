package element;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс из задачи.
 * Класс работника, составлен из варианта лабораторной работы.
 * @author mike
 */
public class Worker implements Serializable, Comparable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Double salary; 
    private LocalDate startDate;
    private Position position;
    private Status status;
    private Person person;
    private String owner = null;
    private double coordX;
    private double coordY;
    private Color hair;
    private Color eye;
    private Integer height;
    private Country country;
    
    public Worker(String name, Coordinates coordinates, Double salary, 
            java.time.LocalDate startDate, Position position, Status status,
            Person person) {
        this.creationDate = LocalDate.now();
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.person = person;
        char[] nameArr = name.toCharArray();
        double sum = 0;
        for(char c:nameArr)sum+=((int)c)*Math.random()/1024;
        this.id = (int)Math.round((sum+coordinates.getX()/(coordinates.getX()+3000000)+
                coordinates.getY()/(coordinates.getY()+3000000)+
                salary/(salary+3000000)+
                ((position!=null)?1/256:0)+
                ((status!=null)?1/512:0)+
                ((person!=null)?1/2048:0) )*100000000);
    }
    
    public Worker(int id) {
        this.id = id;
    }

    public void setOwner(String login) {
        this.owner = login;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Double getSalary() {
        return salary;
    }

    public LocalDateTime getCreationDate() {
        return creationDate.atStartOfDay();
    }

    public LocalDateTime getStartDate() {
        return startDate.atStartOfDay();
    }

    public Person getPerson() {
        return person;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public double getCoordX() {
        return coordinates.getX();
    }

    public double getCoordY() {
        return coordinates.getY();
    }

    public String getOwner() {
        return owner;
    }

    public Color getEye() {
        try {
            return person.getEyeColor();
        } catch(NullPointerException e) {
            return null;
        }
    }

    public Color getHair() {
        try {
            return person.getHairColor();
        } catch(NullPointerException e) {
            return null;
        }
    }

    public Country getCountry() {
        try {
            return person.getNationality();
        } catch(NullPointerException e) {
            return null;
        }
    }

    public Integer getHeight() {
        try {
            return person.getHeight();
        } catch(NullPointerException e) {
            return 0;
        }
    }

    public static int statusToInt(String status) {
        switch(status) {
            case "RECOMMENDED_FOR_PROMOTION" : return 2;
            case "FIRED" : return 1;
            case "REGULAR" : return 3;
            case "null": return 0;
            default: return -1;
        }
    }

    public static int statusToInt(Status status) {
        if (status==null) return 0;
        switch(status) {
            case RECOMMENDED_FOR_PROMOTION : return 2;
            case FIRED : return 1;
            case REGULAR : return 3;
            default: return -1;
        }
    }

    public int statusToInt() {
        return statusToInt(status);
    }
    
    @Override
    public String toString() {
        String result = "";           
        result += "id: "+id+"\n";
        result += "name: "+name+"\n";
        result += "coordinates: "+coordinates.getX()+", "+coordinates.getY()+"\n";
        result += "salary: "+Double.toString(salary)+"\n";
        result += "startDate: "+startDate.toString()+"\n";
        if (position==null) result += "position: null\n";
        else result += "position: "+position.toString()+"\n";
        if (status==null) result += "status: null\n";
        else result += "status: "+status.toString()+"\n";
        if (person==null) result += "person: null\n";
        else result += "person: "+person.toString()+"\n";
        result += "owner: "+this.owner;
        return result;
    }
    
    public String toStringSave() {
        String result = "";
        result += name+"\n";
        result += coordinates.getX()+"\n"+coordinates.getY()+"\n";
        result += Double.toString(salary)+"\n";
        result += startDate.getYear()+"\n"+startDate.getMonthValue()+"\n"+startDate.getDayOfMonth()+"\n";
        if (position==null) result += "null\n";
        else result += position.toString()+"\n";
        if (status==null) result += "null\n";
        else result += status.toString()+"\n";
        if (person==null) result += "n\n";
        else result += "y\n"+person.toStringSave();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (this.id>((Worker)o).getId()) return 1;
        else if (this.id>((Worker)o).getId()) return -1;
        else return 0;
    }
}
