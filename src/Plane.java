public class Plane {
    public String name;
    public String model;
    public String regNumber;
    public int maxSpeed;
    public int flightRange;
    public int maxHeight;
    public int fuelCapacity;
    public int crewMembers;
    public Airport airport;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public Airport getAirport()
    {
        return this.airport;
    }

    public void setAirport(Airport newAirp)
    {
        this.airport = newAirp;
    }
}



