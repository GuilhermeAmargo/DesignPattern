package game_models;

import javax.swing.*;
import java.awt.*;

public abstract class Character {
    private Image image;
    private int x, y;
    private int height, width;

    //Posição da criação do personagem
    public Character(int x, int y){
        this.x = x;
        this.y = y;
    }

    //Imagem e tamanho do personagem
    public final void loadCharacter(String imagePath){
        loadImage(imagePath);
        onCharacterLoaded();
    }

    private void loadImage (String imagePath){
        ImageIcon reference = new ImageIcon(imagePath);
        image = reference.getImage();
        height = image.getHeight(null);
        width = image.getWidth(null);
    }

    protected  abstract void onCharacterLoaded();

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    // Obtém os limites para detecção de colisão
    public abstract Rectangle getBounds();
}
