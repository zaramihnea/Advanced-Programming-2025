import java.util.ArrayList;
import java.util.List;

public class Airport {
    private final String name;
    private final List<Runway> runways;
    private final List<Flight> scheduledFlights;

    public Airport(String name) {
        this.name = name;
        this.runways = new ArrayList<>();
        this.scheduledFlights = new ArrayList<>();
    }

    public void addRunway(Runway runway) {
        runways.add(runway);
    }

    public void addRunways(List<Runway> runwaysToAdd) {
        runways.addAll(runwaysToAdd);
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public int getNumberOfRunways() {
        return runways.size();
    }

    public void scheduleFlights(List<Flight> flights) {
        scheduledFlights.addAll(flights);
    }

    public List<Flight> getScheduledFlights() {
        return scheduledFlights;
    }

    public void printSchedule() {
        System.out.println("Flight Schedule for " + name + ":");
        for (Runway runway : runways) {
            System.out.println(runway + ":");
            for (Flight flight : scheduledFlights) {
                if (flight.getAssignedRunway() != null && flight.getAssignedRunway().equals(runway)) {
                    System.out.println("  " + flight);
                }
            }
        }
    }
}