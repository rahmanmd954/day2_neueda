package testclasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NullInputTest {

    @Test
    void testNullInput() {
        // Arrange
        Integer input = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> sampleMethod(input), "The sampleMethod should throw NullPointerException for null input.");
    }

    private int sampleMethod(Integer input) {
        // Mock implementation for demonstration
        return input * input;
    }
}