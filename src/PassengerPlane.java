import java.util.ArrayList;

public class PassengerPlane extends Plane {
    public int passengerCapacity;
    public ArrayList<Object> passengers = new ArrayList<>();

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public ArrayList<Object> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Object> setPassengers)
    {
        this.passengers.clear();
        for (int i = 0; i < setPassengers.size(); i++)
        {
            this.passengers.add(setPassengers.get(i));
        }
    }
}
