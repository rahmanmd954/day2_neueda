package testclasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {
// comment
    @Test
    void testMaxIntegerInput() {
        // Arrange
        int input = Integer.MAX_VALUE;
        long expectedOutput = (long) input * input;

        // Act
        long actualOutput = sampleMethod(input);

        // Assert
        assertEquals(expectedOutput, actualOutput, "The sampleMethod should handle the maximum integer input correctly.");
    }

    @Test
    void testMinIntegerInput() {
        // Arrange
        int input = Integer.MIN_VALUE;
        long expectedOutput = (long) input * input;

        // Act
        long actualOutput = sampleMethod(input);

        // Assert
        assertEquals(expectedOutput, actualOutput, "The sampleMethod should handle the minimum integer input correctly.");
    }

    private long sampleMethod(int input) {
        // Mock implementation for demonstration
        return (long) input * input;
    }
}