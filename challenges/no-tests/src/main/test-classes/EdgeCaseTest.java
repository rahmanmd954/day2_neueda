package testclasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeCaseTest {

    @Test
    void testZeroInput() {
        // Arrange
        int input = 0; // testing
        int expectedOutput = 0;

        // Act
        int actualOutput = sampleMethod(input);

        // Assert
        assertEquals(expectedOutput, actualOutput, "The sampleMethod should return 0 for an input of 0.");
    }

    @Test
    void testNegativeInput() {
        // Arrange
        int input = -5;
        int expectedOutput = 25;

        // Act
        int actualOutput = sampleMethod(input);

        // Assert
        assertEquals(expectedOutput, actualOutput, "The sampleMethod should return the square of the input even for negative numbers.");
    }

    private int sampleMethod(int input) {
        // Mock implementation for demonstration
        return input * input;
    }
}