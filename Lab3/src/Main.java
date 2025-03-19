import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n==== COMPULSORY PART ====\n");

        Airliner boeing737 = new Airliner("Boeing 737-800", "AA123", 35.8, 189);
        Airliner airbus320 = new Airliner("Airbus A320", "UA456", 35.8, 180);
        Freighter boeing747 = new Freighter("Boeing 747-8F", "FX789", 68.4, 137.7);
        Freighter antonov225 = new Freighter("Antonov An-225", "AN001", 88.4, 250.0);
        Drone dji = new Drone("DJI Matrice 300", "DJI001", 1.2, 0.009, 1.5);
        Drone amazon = new Drone("Amazon Prime Air", "AMZN002", 1.5, 0.02, 2.0);

        System.out.println("Aircraft capable of transporting goods:");
        CargoCapable[] cargoAircraft = {
                boeing747,
                antonov225,
                dji,
                amazon
        };

        for (CargoCapable aircraft : cargoAircraft) {
            if (aircraft instanceof Aircraft) {
                System.out.println(aircraft.toString() + " - Max payload: " + aircraft.getMaxPayload() + " tons");
            }
        }

        System.out.println("\n==== HOMEWORK PART ====\n");

        Airport airport = new Airport("International Airport");

        List<Runway> runways = new ArrayList<>();
        runways.add(new Runway("R1"));
        runways.add(new Runway("R2"));
        runways.add(new Runway("R3"));
        airport.addRunways(runways);

        System.out.println("Airport created with " + airport.getNumberOfRunways() + " runways");

        Set<Flight> flights = new HashSet<>();
        flights.add(new Flight("FL001", boeing737, LocalTime.of(10, 0), LocalTime.of(10, 30)));
        flights.add(new Flight("FL002", airbus320, LocalTime.of(10, 15), LocalTime.of(10, 45)));
        flights.add(new Flight("FL003", boeing747, LocalTime.of(11, 0), LocalTime.of(11, 45)));
        flights.add(new Flight("FL004", antonov225, LocalTime.of(12, 0), LocalTime.of(13, 0)));
        flights.add(new Flight("FL005", dji, LocalTime.of(10, 20), LocalTime.of(10, 35)));
        flights.add(new Flight("FL006", amazon, LocalTime.of(11, 30), LocalTime.of(11, 50)));
        flights.add(new Flight("FL007", boeing737, LocalTime.of(13, 15), LocalTime.of(13, 45)));

        System.out.println("Created " + flights.size() + " flights");

        SchedulingProblem problem = new SchedulingProblem(airport);
        problem.addFlights(flights);

        System.out.println("Scheduling problem created with " + problem.getFlights().size() +
                " flights and " + problem.getAirport().getNumberOfRunways() + " runways");

        System.out.println("\nSolving the scheduling problem...");
        Map<Flight, Runway> solution = problem.solve();

        System.out.println("\nSolution found!");
        problem.printSolution(solution);
    }
}