import java.io.Serializable;

public class Passenger extends Human implements Serializable {
    public String departurePoint;
    public String arrivalPoint;
    public String ticketNumber;
    public String departureTime;

    public PassengerPlane passengerPlane;

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public void setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public PassengerPlane getPassengerPlane() {
        return passengerPlane;
    }

    public void setPassengerPlane(PassengerPlane passengerPlane) {
        this.passengerPlane = passengerPlane;
    }
}
