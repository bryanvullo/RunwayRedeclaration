package uk.ac.soton.comp2211.Utility;

// FlightParameters.java
public class FlightParameters {
    private String flightNumber;
    private String gate;
    private String status; // e.g., On-time, Delayed
    private String estimatedDepartureTime;
    private String estimatedArrivalTime;
    
    // Constructors, getters, and setters would follow

    public FlightParameters(String flightNumber, String gate, String status,
                            String estimatedDepartureTime, String estimatedArrivalTime) {
        this.flightNumber = flightNumber;
        this.gate = gate;
        this.status = status;
        this.estimatedDepartureTime = estimatedDepartureTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    // Getters and setters for each field
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public void setEstimatedDepartureTime(String estimatedDepartureTime) {
        this.estimatedDepartureTime = estimatedDepartureTime;
    }

    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(String estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }
    
    // You might want to add more methods here to handle other flight parameter related operations
}
