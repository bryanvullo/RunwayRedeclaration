package uk.ac.soton.comp2211.Utility;

// FlightUpdatesService.java (Modified)
public class FlightUpdatesService {
    public FlightParameters calculateFlightParameters(String flightNumber) {
        // Simulate calculation or fetching of flight parameters
        // Placeholder values for the example
        return new FlightParameters(flightNumber, "Gate 12", "Delayed", "14:30", "17:45");
    }

    public void updateFlightTracker(String flightNumber, FlightParameters parameters) {
        // Update the flight tracker system with the new parameters
        // This can be a complex operation depending on the system's design
    }

    public FlightNotification generateFlightUpdateNotification(String flightNumber) {
        // Simulate generating a notification for flight updates
        FlightParameters parameters = calculateFlightParameters(flightNumber);
        return new FlightNotification(flightNumber, "Your flight " + parameters.getFlightNumber() +
            " to gate " + parameters.getGate() + " is " + parameters.getStatus() + ". " +
            "Estimated departure: " + parameters.getEstimatedDepartureTime() +
            " and arrival: " + parameters.getEstimatedArrivalTime() + ".");
    }
}