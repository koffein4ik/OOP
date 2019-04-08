public class Plane {
    public String name;
    public String model;
    public String regNumber;
    public String airport;
    public int maxSpeed;
    public int flightRange;
    public int maxHeight;
    public int fuelCapacity;
    public int crewMembers;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getAirport()
    {
        if (this.airport != null)
        {
            return this.airport;
        }
        else
        {
            return "";
        }
    }
}



