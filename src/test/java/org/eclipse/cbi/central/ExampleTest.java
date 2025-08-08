package org.eclipse.cbi.central;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {
    @Test
    public void testGreet() {
        Example hw = new Example();
        assertEquals("Hello, Alice!", hw.greet("Alice"));
    }
}
