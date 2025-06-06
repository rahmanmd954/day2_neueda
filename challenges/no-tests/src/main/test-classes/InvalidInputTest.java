package testclasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidInputTest {

    @Test
    void testInvalidInput() {
        // Arrange
        String input = "invalid";

        // Act & Assert
        assertThrows(ClassCastException.class, () -> sampleMethod(input), "The sampleMethod should throw ClassCastException for invalid input types.");
    }

    private int sampleMethod(Object input) {
        // Mock implementation for demonstration
        return (int) input * (int) input;
    }
}