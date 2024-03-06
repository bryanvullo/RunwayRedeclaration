package uk.ac.soton.comp2211.model;

public class UnitConverter {
    public static double metersToFeet(double meters) {
        return meters * 3.28084;
    }

    public static double feetToMeters(double feet) {
        return feet / 3.28084;
    }

    public static double milesToKilometers(double miles) {
        return miles * 1.60934;
    }

    public static double kilometersToMiles(double kilometers) {
        return kilometers / 1.60934;
    }
    public static double metersToKilometers(double meters) {
        return meters / 1000;
    }

    public static double kilometersToMeters(double kilometers) {
        return kilometers * 1000;
    }

    public static double feetToMiles(double feet) {
        return feet / 5280;
    }

    public static double milesToFeet(double miles) {
        return miles * 5280;
    }
}