// AsteroidFactoryTest.java
package game_models.Test;

import game_models.Asteroid;
import game_models.AsteroidFactory;
import game_models.SimpleAsteroidFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AsteroidFactoryTest {

    @Test
    public void testCreateAsteroid() {
        AsteroidFactory factory = new SimpleAsteroidFactory();
        Asteroid asteroid = factory.createAsteroid(100, 200);

        assertNotNull(asteroid, "Asteroid should not be null");
        assertEquals(100, asteroid.getX(), "Asteroid X position should be 100");
        assertEquals(200, asteroid.getY(), "Asteroid Y position should be 200");
    }
}