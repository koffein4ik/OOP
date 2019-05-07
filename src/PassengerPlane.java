import java.io.Serializable;
import java.util.ArrayList;

public class PassengerPlane extends Plane implements Serializable {
    public int passengerCapacity;
    public ArrayList<Passenger> passengers = new ArrayList<>();

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Object> setPassengers)
    {
        this.passengers.clear();
        for (int i = 0; i < setPassengers.size(); i++)
        {
            this.passengers.add((Passenger)setPassengers.get(i));
        }
    }
}
