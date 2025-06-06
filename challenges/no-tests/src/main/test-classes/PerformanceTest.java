package testclasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceTest {

    @Test
    void testLargeInputPerformance() {
        // Arrange
        int input = 100000;
        long expectedOutput = (long) input * input;

        // Act
        long actualOutput = sampleMethod(input);

        // Assert
        assertEquals(expectedOutput, actualOutput, "The sampleMethod should handle large inputs efficiently.");
    }

    private long sampleMethod(int input) {
        // Mock implementation for demonstration
        return (long) input * input;
    }
}