package uk.ac.soton.comp2211;

import uk.ac.soton.comp2211.model.UnitConverter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class TestUnitConverter {

    @Test
    public void testMetersToFeet() {
        assertEquals(3.28084, UnitConverter.metersToFeet(1), 0.00001);
        assertEquals(6.56168, UnitConverter.metersToFeet(2), 0.00001);
        assertEquals(32.8084, UnitConverter.metersToFeet(10), 0.00001);
    }

    @Test
    public void testFeetToMeters() {
        assertEquals(0.3048, UnitConverter.feetToMeters(1), 0.00001);
        assertEquals(0.6096, UnitConverter.feetToMeters(2), 0.00001);
        assertEquals(3.048, UnitConverter.feetToMeters(10), 0.00001);
    }

    @Test
    public void testMetersToKilometers() {
        assertEquals(0.001, UnitConverter.metersToKilometers(1), 0.00001);
        assertEquals(0.002, UnitConverter.metersToKilometers(2), 0.00001);
        assertEquals(1, UnitConverter.metersToKilometers(1000), 0.00001);
    }

    @Test
    public void testKilometersToMeters() {
        assertEquals(1, UnitConverter.kilometersToMeters(0.001), 0.00001);
        assertEquals(2, UnitConverter.kilometersToMeters(0.002), 0.00001);
        assertEquals(1000, UnitConverter.kilometersToMeters(1), 0.00001);
    }

    @Test
    public void testFeetToMiles() {
        assertEquals(0.00018939394, UnitConverter.feetToMiles(1), 0.00000001);
        assertEquals(0.00037878788, UnitConverter.feetToMiles(2), 0.00000001);
        assertEquals(0.00056818181, UnitConverter.feetToMiles(3), 0.00000001);
    }

    @Test
    public void testMilesToFeet() {
        assertEquals(5280, UnitConverter.milesToFeet(1), 0.00001);
        assertEquals(10560, UnitConverter.milesToFeet(2), 0.00001);
        assertEquals(15840, UnitConverter.milesToFeet(3), 0.00001);
    }

    @Test
    public void testMilesToKilometers() {
        assertEquals(1.60934, UnitConverter.milesToKilometers(1), 0.00001);
        assertEquals(3.21868, UnitConverter.milesToKilometers(2), 0.00001);
        assertEquals(4.82802, UnitConverter.milesToKilometers(3), 0.00001);
    }
    @Test
    public void testKilometersToMiles() {
        assertEquals(0.621371, UnitConverter.kilometersToMiles(1), 0.00001);
        assertEquals(1.24274, UnitConverter.kilometersToMiles(2), 0.00001);
        assertEquals(1.86411, UnitConverter.kilometersToMiles(3), 0.00001);
    }

}
