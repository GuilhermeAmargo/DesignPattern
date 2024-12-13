package game_models;

public class SimpleAsteroidFactory implements AsteroidFactory {
    @Override
    public Asteroid createAsteroid(int x, int y) {
        Asteroid asteroid = new Asteroid(x, y);
        asteroid.load(); // Carrega a imagem do asteroide
        return asteroid;
    }
}