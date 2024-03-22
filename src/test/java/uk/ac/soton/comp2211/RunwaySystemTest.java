package uk.ac.soton.comp2211;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import uk.ac.soton.comp2211.model.UnitConverter;

public class RunwaySystemTest {

    @Test
    public void testMetersToFeetConversion() {
        double meters = 1000;
        double expectedFeet = 3280.84;
        double feet = UnitConverter.metersToFeet(meters);
        assertEquals(expectedFeet, feet, 0.1, "Meters to feet conversion failed");
    }

    @Test
    public void testFeetToMetersConversion() {
        double feet = 3280.84;
        double expectedMeters = 1000;
        double meters = UnitConverter.feetToMeters(feet);
        assertEquals(expectedMeters, meters, 0.1, "Feet to meters conversion failed");
    }
}
