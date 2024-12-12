package game_models;

import java.awt.*;

public class Shoot extends Character {
    private boolean isVisible;

    private static int VELOCIDADE = -4;

    //Construtor do tiro
    public Shoot(int x, int y) {
        super(x, y);
        isVisible = true;
    }

    //Carrega a imagem e o tamanho dela
    public void load() {
        super.load("res\\shoot.png");
    }

    //Movimentação do tiro
    public void move(){
        super.setY(getY() + VELOCIDADE);
        if(getY() >  938){
            isVisible = false;
        }
    }

    // Cria um retangulo para ser checado a colisão
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
