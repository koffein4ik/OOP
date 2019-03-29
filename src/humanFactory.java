public class humanFactory
{
    public Human createPerson() {
        return new Human();
    }
}


class employeeFactory extends humanFactory
{
    @Override
    public Employee createPerson()
    {
        return new Employee();
    }

}

class securityFactory extends employeeFactory
{
    @Override
    public SecurityOfficer createPerson()
    {
        return new SecurityOfficer();
    }
}

class aircrewMFactory extends employeeFactory
{
    @Override
    public AircrewMember createPerson()
    {
        return new AircrewMember();
    }
}

class pilotFactory extends aircrewMFactory
{
    @Override
    public Pilot createPerson()
    {
        return new Pilot();
    }
}

class passengerFactory extends humanFactory
{
    @Override
    public Passenger createPerson()
    {
        return new Passenger();
    }
}








   /* public enum HumanType {
        Human,
        Employee,
        AircrewMember,
        Pilot,
        Passenger,
    } */

    /*public static Human createHuman(HumanType Type)
    {
        switch (Type) {
            case Human:
                return new Human();
            case Employee:
                return new Employee();
            case AircrewMember:
                return new AircrewMember();
            case Pilot:
                return new Pilot();
            case Passenger:
                return new Passenger();
            default:
                return new Human();
        }
    }

    public static Employee createEmployee()
    {
        return new Employee();
    }

    public static Pilot createPilot()
    {
        return new Pilot();
    }
    */
