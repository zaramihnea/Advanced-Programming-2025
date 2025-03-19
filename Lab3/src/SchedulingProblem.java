import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SchedulingProblem {
    private final Airport airport;
    private final Set<Flight> flights;

    public SchedulingProblem(Airport airport) {
        this.airport = airport;
        this.flights = new HashSet<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void addFlights(Set<Flight> flightsToAdd) {
        flights.addAll(flightsToAdd);
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public Airport getAirport() {
        return airport;
    }

    public Map<Flight, Runway> solve() {
        Map<Flight, Runway> solution = new HashMap<>();

        // Get the list of runways from the airport
        for (Flight flight : flights) {
            boolean assigned = false;

            // Try to assign the flight to each runway
            for (Runway runway : airport.getRunways()) {
                boolean conflict = false;

                // Check for conflicts with flights already assigned to this runway
                for (Map.Entry<Flight, Runway> entry : solution.entrySet()) {
                    if (entry.getValue().equals(runway) && flight.conflictsWith(entry.getKey())) {
                        conflict = true;
                        break;
                    }
                }

                // If no conflict, assign the flight to this runway
                if (!conflict) {
                    solution.put(flight, runway);
                    flight.setAssignedRunway(runway); // Also update the flight object directly
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                // If the flight could not be assigned to any runway, mark it as unassigned
                solution.put(flight, null);
                System.out.println("Warning: Could not assign runway for flight " + flight.getFlightId());
            }
        }

        return solution;
    }

    public void printSolution(Map<Flight, Runway> solution) {
        System.out.println("Flight Assignments:");

        // Group flights by runway
        Map<Runway, Set<Flight>> runwayFlights = new HashMap<>();
        for (Map.Entry<Flight, Runway> entry : solution.entrySet()) {
            Runway runway = entry.getValue();
            if (runway != null) {
                runwayFlights.computeIfAbsent(runway, k -> new HashSet<>()).add(entry.getKey());
            }
        }

        // Print flights for each runway
        for (Runway runway : airport.getRunways()) {
            System.out.println(runway + ":");
            Set<Flight> flightsOnRunway = runwayFlights.get(runway);
            if (flightsOnRunway != null) {
                for (Flight flight : flightsOnRunway) {
                    System.out.println("  " + flight);
                }
            } else {
                System.out.println("  No flights assigned");
            }
        }

        // Print unassigned flights
        System.out.println("Unassigned flights:");
        boolean hasUnassigned = false;
        for (Map.Entry<Flight, Runway> entry : solution.entrySet()) {
            if (entry.getValue() == null) {
                System.out.println("  " + entry.getKey());
                hasUnassigned = true;
            }
        }
        if (!hasUnassigned) {
            System.out.println("  None");
        }
    }
}