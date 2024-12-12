package game_models;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Spacecraft extends Character {

    private int dx, dy;
    private boolean isVisible;
    private List<Shoot> shoots;
    private final int SCREEN_WIDTH = 1024;  // Largura da tela
    private final int SCREEN_HEIGHT = 728;  // Altura da tela
    private long lastShotTime;
    private final int SHOT_DELAY = 300;  // Intervalo mínimo entre os tiros em milissegundos

    // Construtor da espaçonave, que coloca a posição que será gerado, coloca a espaçonave visivel, cria um array list de tiros e coloca a variavel lastShotTime com 0
    public Spacecraft(int x, int y) {
        super(x, y);
        isVisible = true;
        shoots = new ArrayList<Shoot>();
        lastShotTime = 0; // Inicializa com zero
    }

    // Carrega a imagem e o tamanho da espaçonave
    public void load() {
        super.load("res\\spacecraft.gif");
    }

    // Cria um retangulo para ser checado a colisão
    public Rectangle getBounds() {
        return new Rectangle(getX() - 5, getY() + 20, getWidth(), getHeight());
    }

    // Movimentação da espaçonave e colisão com as bordas da tela
    public void move() {
        int newX = getX() + dx;
        int newY = getY() + dy;

        if (newX >= 0 - (getWidth() * 0) && newX <= SCREEN_WIDTH - (getWidth() * 1.2)) {
            super.setX(newX);
        }
        if (newY >= 0 - (getHeight() * 0.2) && newY <= SCREEN_HEIGHT - (getHeight() * 0.9)) {
            super.setY(newY);
        }
    }

    // Método do tiro da espaçonave com controle de delay
    public void singleShot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= SHOT_DELAY) {
            this.shoots.add(new Shoot(getX() + getWidth() - 42, getY() + getHeight() - 54));
            lastShotTime = currentTime;  // Atualiza o tempo do último tiro
            Sound.soundShoot.play();
        }
    }

    // Teclas da espaçonave e sua velocidade de movimento
    public void keyPressed(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_Z) {
            singleShot();
        }
        if (code == KeyEvent.VK_UP) {
            dy = -4;
        }
        if (code == KeyEvent.VK_DOWN) {
            dy = 4;
        }
        if (code == KeyEvent.VK_RIGHT) {
            dx = 4;
        }
        if (code == KeyEvent.VK_LEFT) {
            dx = -4;
        }
    }

    // Teclas não pressionadas
    public void keyRelease(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (code == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        if (code == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (code == KeyEvent.VK_LEFT) {
            dx = 0;
        }
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
