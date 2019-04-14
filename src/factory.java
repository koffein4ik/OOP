abstract class factory {
    public abstract Object create();
}

class humanFactory extends factory
{
    public  Human create() {
        return new Human();
    }
}


class employeeFactory extends humanFactory
{
    public Employee create()
    {
        return new Employee();
    }

}

class securityFactory extends employeeFactory
{
    public SecurityOfficer create()
    {
        return new SecurityOfficer();
    }
}

class aircrewMFactory extends employeeFactory
{
    public AircrewMember create()
    {
        return new AircrewMember();
    }
}

class pilotFactory extends aircrewMFactory
{
    public Pilot create()
    {
        return new Pilot();
    }
}

class passengerFactory extends humanFactory
{
    public Passenger create()
    {
        return new Passenger();
    }
}

class planeFactory extends factory
{
    public Plane create()
    {
        return new Plane();
    }

}

class cargoPlaneFactory extends planeFactory
{
    public CargoPlane create()
    {
        return  new CargoPlane();
    }
}

class passengerPlaneFactory extends planeFactory
{
    public PassengerPlane create()
    {
        return new PassengerPlane();
    }
}

class airportFactory extends factory
{
    public Airport create()
    {
        return new Airport();
    }
}
