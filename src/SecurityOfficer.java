import java.io.Serializable;

public class SecurityOfficer extends Employee implements Serializable {
    String workingHours;
    String location;
    String armed;

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArmed() {
        return armed;
    }

    public void setArmed(String armed) {
        this.armed = armed;
    }
}
