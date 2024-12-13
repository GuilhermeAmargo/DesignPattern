package game_models;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spacecraft extends Character {

    private int dx, dy;
    private boolean isVisible;
    private List<Shoot> shoots;
    private final int SCREEN_WIDTH = 1024;
    private final int SCREEN_HEIGHT = 728;
    private long lastShotTime;
    private final int SHOT_DELAY = 300;
    private Map<Integer, Command> commandMap;

    public Spacecraft(int x, int y) {
        super(x, y);
        isVisible = true;
        shoots = new ArrayList<>();
        lastShotTime = 0;
        commandMap = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        // Movimentos
        commandMap.put(KeyEvent.VK_UP, () -> setDy(-4)); // Movimento para cima
        commandMap.put(KeyEvent.VK_DOWN, () -> setDy(4)); // Movimento para baixo
        commandMap.put(KeyEvent.VK_LEFT, () -> setDx(-4)); // Movimento para a esquerda
        commandMap.put(KeyEvent.VK_RIGHT, () -> setDx(4)); // Movimento para a direita

        // Parar movimentos
        commandMap.put(KeyEvent.VK_UP + 1000, () -> setDy(0));
        commandMap.put(KeyEvent.VK_DOWN + 1000, () -> setDy(0));
        commandMap.put(KeyEvent.VK_LEFT + 1000, () -> setDx(0));
        commandMap.put(KeyEvent.VK_RIGHT + 1000, () -> setDx(0));

        // Comando para atirar
        commandMap.put(KeyEvent.VK_Z, this::singleShot); // Tiro ao pressionar espaço
    }

    @Override
    protected void onCharacterLoaded() {
        // Configurações adicionais após o carregamento do personagem
    }

    public void load() {
        super.loadCharacter("res\\spacecraft.gif");
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX() - 5, getY() + 20, getWidth(), getHeight());
    }

    public void move() {
        int newX = getX() + dx;
        int newY = getY() + dy;

        if (newX >= 0 && newX <= SCREEN_WIDTH - getWidth()) {
            setX(newX);
        }
        if (newY >= 0 && newY <= SCREEN_HEIGHT - getHeight()) {
            setY(newY);
        }
    }

    public void singleShot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= SHOT_DELAY) {
            shoots.add(new Shoot(getX() + getWidth() - 42, getY() + getHeight() - 54));
            lastShotTime = currentTime;
            Sound.soundShoot.play();
        }
    }

    public void keyPressed(KeyEvent tecla) {
        int code = tecla.getKeyCode();
        Command command = commandMap.get(code);
        if (command != null) {
            command.execute();
        }
    }

    public void keyRelease(KeyEvent tecla) {
        int code = tecla.getKeyCode();
        Command command = commandMap.get(code + 1000);
        if (command != null) {
            command.execute();
        }
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public List<Shoot> getShoots() {
        return shoots;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}