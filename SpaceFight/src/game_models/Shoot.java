package game_models;

import java.awt.*;

public class Shoot extends Character {
    private boolean isVisible;

    private static int VELOCIDADE = -4;

    // Construtor do tiro
    public Shoot(int x, int y) {
        super(x, y);
        isVisible = true;
        loadCharacter("res\\shoot.png"); // Carrega a imagem diretamente no construtor
    }

    @Override
    protected void onCharacterLoaded() {
        // Lógica adicional para quando o tiro é carregado, se necessário
    }

    // Movimentação do tiro
    public void move() {
        super.setY(getY() + VELOCIDADE);
        if (getY() < 0) { // Verifica se o tiro saiu da tela
            isVisible = false;
        }
    }

    // Cria um retângulo para ser checado a colisão
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Shoot.VELOCIDADE = VELOCIDADE;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}