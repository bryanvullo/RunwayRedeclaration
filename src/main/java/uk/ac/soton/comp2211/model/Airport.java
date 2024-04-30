package uk.ac.soton.comp2211.model;

import java.util.ArrayList;
import java.util.List;

public class Airport {
  private String airportName;
  private List<Runway> runways;

  public Airport(String airportName) {
    this.airportName = airportName;
    this.runways = new ArrayList<>();
  }

  public void setAirportName(String airportName) {
    this.airportName = airportName;
  }

  public String getAirportName() {
    return airportName;
  }

  public void addRunway(Runway runway) {
    runways.add(runway);
  }

  public List<Runway> getRunways() {
    return runways;
  }

  @Override
  public String toString() {
    return "Airport: " + airportName;
  }
}
