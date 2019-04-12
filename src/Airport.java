import java.util.ArrayList;

public class Airport {
    public String name;
    public String location;
    public int passengersPerYear;
    public String iataCode;
    public String timeZone;
    public ArrayList<Object> planes = new ArrayList<>();
    public ArrayList<Object> employees = new ArrayList<>();

    public void setPassengersPerYear(int passengersPerYear) {
        this.passengersPerYear = passengersPerYear;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getPassengersPerYear() {
        return this.passengersPerYear;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String loc)
    {
        this.location = loc;
    }

    public ArrayList<Object> getPlanes() {
        return this.planes;
    }

    public void setPlanes(ArrayList<Object> setPlanes)
    {
        this.planes.clear();
        for (int i = 0; i < setPlanes.size(); i++)
        {
            this.planes.add(setPlanes.get(i));
        }
    }

    public ArrayList<Object> getEmployees() {
        return this.employees;
    }

    public void setEmployees(ArrayList<Object> setEmployees)
    {
        this.employees.clear();
        for (int i = 0; i < setEmployees.size(); i++)
        {
            this.employees.add(setEmployees.get(i));
        }
    }
}
