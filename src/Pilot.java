public class Pilot extends AircrewMember {
    public String experience;
    public String lastFlightDate;
    public String masteredAircrafts;
    public int flightHours;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLastFlightDate() {
        return lastFlightDate;
    }

    public void setLastFlightDate(String lastFlightDate) {
        this.lastFlightDate = lastFlightDate;
    }

    public String getMasteredAircrafts() {
        return masteredAircrafts;
    }

    public void setMasteredAircrafts(String masteredAircrafts) {
        this.masteredAircrafts = masteredAircrafts;
    }

    public int getFlightHours() {
        return flightHours;
    }

    public void setFlightHours(int flightHours) {
        this.flightHours = flightHours;
    }
}
