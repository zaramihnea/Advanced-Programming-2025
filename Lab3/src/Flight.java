import java.time.LocalTime;


public class Flight {
    private final String flightId;
    private final Aircraft aircraft;
    private final LocalTime landingStart;
    private final LocalTime landingEnd;
    private Runway assignedRunway = null;

    public Flight(String flightId, Aircraft aircraft, LocalTime landingStart, LocalTime landingEnd) {
        this.flightId = flightId;
        this.aircraft = aircraft;
        this.landingStart = landingStart;
        this.landingEnd = landingEnd;
    }

    public String getFlightId() {
        return flightId;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public LocalTime getLandingStart() {
        return landingStart;
    }

    public LocalTime getLandingEnd() {
        return landingEnd;
    }

    public Runway getAssignedRunway() {
        return assignedRunway;
    }

    public void setAssignedRunway(Runway assignedRunway) {
        this.assignedRunway = assignedRunway;
    }

    public boolean conflictsWith(Flight other) {
        return !(this.landingEnd.isBefore(other.landingStart) ||
                this.landingStart.isAfter(other.landingEnd));
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId='" + flightId + '\'' +
                ", aircraft=" + aircraft.getCallSign() +
                ", landingStart=" + landingStart +
                ", landingEnd=" + landingEnd +
                ", assignedRunway=" + (assignedRunway != null ? assignedRunway.getId() : "unassigned") +
                '}';
    }
}