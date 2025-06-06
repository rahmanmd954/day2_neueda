package testclasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    @Test
    void testSampleMethod() {
        // Arrange
        int input = 5;
        int expectedOutput = 25;

        // Act
        int actualOutput = sampleMethod(input);

        // Assert
        assertEquals(expectedOutput, actualOutput, "The sampleMethod should return the square of the input.");
    }

    private int sampleMethod(int input) {
        // Mock implementation for demonstration
        return input * input;
    }
}