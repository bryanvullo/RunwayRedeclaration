package uk.ac.soton.comp2211;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.soton.comp2211.model.Calculation;
import uk.ac.soton.comp2211.model.Obstacle;
import uk.ac.soton.comp2211.model.Runway;

/**
 * This is the TestCalculation class.
 * Where each calculation method will be tested.
 * We will be using Scenarios from the Specification to test the methods.
 */
public class TestCalculation {
    
    Calculation calculation;
    Runway runway;
    Obstacle obstacle;
    
    @BeforeEach
    void setUp() {
    }
    
    // ######################## Test for Scenario 1 ########################
    @Test
    void testCalcLandingOver1() {
        runway = new Runway("Test-09L", 3902,3902,3902, 3595);
        runway.setDisplacedThreshold(306);
        obstacle = new Obstacle(12,  -50);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingOver();
        
        assertEquals(2985, calculation.getLda());
        assertTrue(calculation.getLda() == 2985);
    }
    @Test
    void testCalcTOAway1() {
        runway = new Runway("Test-09L", 3902,3902,3902, 3595);
        runway.setDisplacedThreshold(306);
        obstacle = new Obstacle(12,  -50);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOAway();
        
        assertEquals(3346, calculation.getTora());
        assertEquals(3346, calculation.getToda());
        assertEquals(3346, calculation.getAsda());
    }
    @Test
    void testCalculateTakeOffAwayLandingOver1() {
        runway = new Runway("Test-09L", 3902,3902,3902, 3595);
        runway.setDisplacedThreshold(306);
        obstacle = new Obstacle(12,  -50);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffAwayLandingOver();
        
        assertEquals(3346, calculation.getTora());
        assertEquals(3346, calculation.getToda());
        assertEquals(3346, calculation.getAsda());
        assertEquals(2985, calculation.getLda());
        
    }
    @Test
    void testCalcLandingTowards1() {
        runway = new Runway("Test-27R", 3884,3962,3884, 3884);
        obstacle = new Obstacle(12,  3646);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingTowards();
        
        assertEquals(3346, calculation.getLda());
    }
    @Test
    void testCalcTOTowards1() {
        runway = new Runway("Test-27R", 3884,3962,3884, 3884);
        obstacle = new Obstacle(12,  3646);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOTowards();
        
        assertEquals(2986, calculation.getTora());
        assertEquals(2986, calculation.getToda());
        assertEquals(2986, calculation.getAsda());
    }
    @Test
    void testCalculateTakeOffTowardsLandingTowards1() {
        runway = new Runway("Test-27R", 3884,3962,3884, 3884);
        obstacle = new Obstacle(12,  3646);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffTowardsLandingTowards();
        
        assertEquals(2986, calculation.getTora());
        assertEquals(2986, calculation.getToda());
        assertEquals(2986, calculation.getAsda());
        assertEquals(3346, calculation.getLda());
    }
    
    
    // ######################## Test for Scenario 2 ########################
    @Test
    void testCalcLandingTowards2() {
        runway = new Runway("Test-09R", 3660,3660,3660, 3353);
        runway.setDisplacedThreshold(307);
        obstacle = new Obstacle(25,  2853);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingTowards();
        
        assertEquals(2553, calculation.getLda());
    }
    @Test
    void testCalcTOTowards2() {
        runway = new Runway("Test-09R", 3660,3660,3660, 3353);
        runway.setDisplacedThreshold(307);
        obstacle = new Obstacle(25,  2853);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOTowards();
        
        assertEquals(1850, calculation.getTora());
        assertEquals(1850, calculation.getToda());
        assertEquals(1850, calculation.getAsda());
    }
    @Test
    void testCalculateTakeOffTowardsLandingTowards2() {
        runway = new Runway("Test-09R", 3660,3660,3660, 3353);
        runway.setDisplacedThreshold(307);
        obstacle = new Obstacle(25,  2853);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffTowardsLandingTowards();
        
        assertEquals(1850, calculation.getTora());
        assertEquals(1850, calculation.getToda());
        assertEquals(1850, calculation.getAsda());
    }
    @Test
    void testCalcTOAway2() {
        runway = new Runway("Test-27L", 3660,3660,3660, 3660);
        obstacle = new Obstacle(25,  500);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOAway();
        
        assertEquals(2860, calculation.getTora());
        assertEquals(2860, calculation.getToda());
        assertEquals(2860, calculation.getAsda());
    }
    @Test
    void testCalcLandingOver2() {
        runway = new Runway("Test-27L", 3660,3660,3660, 3660);
        obstacle = new Obstacle(25, 500);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingOver();
        
        assertEquals(1850, calculation.getLda());
    }
    @Test
    void testCalculateTakeOffAwayLandingOver2() {
        runway = new Runway("Test-27L", 3660,3660,3660, 3660);
        obstacle = new Obstacle(25,  500);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffAwayLandingOver();
        
        assertEquals(2860, calculation.getTora());
        assertEquals(2860, calculation.getToda());
        assertEquals(2860, calculation.getAsda());
        assertEquals(1850, calculation.getLda());
    }
    
    
    // ######################## Test for Scenario 3 ########################
    @Test
    void testCalcLandingTowards3() {
        runway = new Runway("Test-27L", 3660,3660,3660, 3660);
        obstacle = new Obstacle(15,  3203);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingTowards();
        
        assertEquals(2903, calculation.getLda());
    }
    @Test
    void testCalcTOTowards3() {
        runway = new Runway("Test-27L", 3660,3660,3660, 3660);
        obstacle = new Obstacle(15,  3203);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOTowards();
        
        assertEquals(2393, calculation.getTora());
        assertEquals(2393, calculation.getToda());
        assertEquals(2393, calculation.getAsda());
    }
    @Test
    void testCalculateTakeOffTowardsLandingTowards3() {
        runway = new Runway("Test-27L", 3660,3660,3660, 3660);
        obstacle = new Obstacle(15,  3203);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffTowardsLandingTowards();
        
        assertEquals(2393, calculation.getTora());
        assertEquals(2393, calculation.getToda());
        assertEquals(2393, calculation.getAsda());
        assertEquals(2903, calculation.getLda());
    }
    @Test
    void testCalcTOAway3() {
        runway = new Runway("Test-09R", 3660,3660,3660, 3353);
        runway.setDisplacedThreshold(307);
        obstacle = new Obstacle(15,  150);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOAway();
        
        assertEquals(2903, calculation.getTora());
        assertEquals(2903, calculation.getToda());
        assertEquals(2903, calculation.getAsda());
    }
    @Test
    void testCalcLandingOver3() {
        runway = new Runway("Test-09R", 3660,3660,3660, 3353);
        runway.setDisplacedThreshold(307);
        obstacle = new Obstacle(15,  150);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingOver();
        
        assertEquals(2393, calculation.getLda());
    }
    @Test
    void testCalculateTakeOffAwayLandingOver3() {
        runway = new Runway("Test-09R", 3660,3660,3660, 3353);
        runway.setDisplacedThreshold(307);
        obstacle = new Obstacle(15,  150);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffAwayLandingOver();
        
        assertEquals(2903, calculation.getTora());
        assertEquals(2903, calculation.getToda());
        assertEquals(2903, calculation.getAsda());
        assertEquals(2393, calculation.getLda());
    }
    
    
    // ######################## Test for Scenario 4 ########################
    @Test
    void testCalcLandingTowards4() {
        runway = new Runway("Test-09L", 3902,3902,3902, 3595);
        runway.setDisplacedThreshold(306);
        obstacle = new Obstacle(20,  3546);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingTowards();
        
        assertEquals(3246, calculation.getLda());
    }
    @Test
    void testCalcTOTowards4() {
        runway = new Runway("Test-09L", 3902,3902,3902, 3595);
        runway.setDisplacedThreshold(306);
        obstacle = new Obstacle(20,  3546);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOTowards();
        
        assertEquals(2792, calculation.getTora());
        assertEquals(2792, calculation.getToda());
        assertEquals(2792, calculation.getAsda());
    }
    @Test
    void testCalculateTakeOffTowardsLandingTowards4() {
        runway = new Runway("Test-09L", 3902,3902,3902, 3595);
        runway.setDisplacedThreshold(306);
        obstacle = new Obstacle(20,  3546);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffTowardsLandingTowards();
        
        assertEquals(2792, calculation.getTora());
        assertEquals(2792, calculation.getToda());
        assertEquals(2792, calculation.getAsda());
        assertEquals(3246, calculation.getLda());
    }
    @Test
    void testCalcTOAway4() {
        runway = new Runway("Test-27R", 3884,3962,3884, 3884);
        runway.setClearway(78);
        obstacle = new Obstacle(20,  50);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcTOAway();
        
        assertEquals(3534, calculation.getTora());
        assertEquals(3612, calculation.getToda());
        assertEquals(3534, calculation.getAsda());
    }
    @Test
    void testCalcLandingOver4() {
        runway = new Runway("Test-27R", 3884,3962,3884, 3884);
        obstacle = new Obstacle(20,  50);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calcLandingOver();
        
        assertEquals(2774, calculation.getLda());
    }
    @Test
    void testCalculateTakeOffAwayLandingOver4() {
        runway = new Runway("Test-27R", 3884,3962,3884, 3884);
        runway.setClearway(78);
        obstacle = new Obstacle(20, 50);
        calculation = new Calculation(runway, obstacle);
        
        calculation.calculateTakeOffAwayLandingOver();
        
        assertEquals(3534, calculation.getTora());
        assertEquals(3612, calculation.getToda());
        assertEquals(3534, calculation.getAsda());
        assertEquals(2774, calculation.getLda());
    }
    
}
