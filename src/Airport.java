import java.util.ArrayList;

public class Airport {
    public String name;
    public String location;
    public int passengersPerYear;
    public String iataCode;
    public String timeZone;
    public ArrayList<Object> airlines = new ArrayList<>();
    public ArrayList<Object> planes = new ArrayList<>();
    public ArrayList<Object> employees = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getPassengersPerYear() {
        return this.passengersPerYear;
    }

    Airport()
    {
        Plane bp1 = new Plane();
        bp1.setName("Airbus");
        Plane bp2 = new Plane();
        bp2.setName("Boeing");
        Plane bp3 = new Plane();
        bp3.setName("Embraer");
        planes.add(bp1);
        planes.add(bp2);
        planes.add(bp3);
    }

    public ArrayList<Object> getAirlines() {
        return this.airlines;
    }

    public void setAirlines(ArrayList<Object> setAirlines)
    {
        this.airlines.clear();
        for (int i = 0; i < setAirlines.size(); i++)
        {
            this.airlines.add(setAirlines.get(i));
        }
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
