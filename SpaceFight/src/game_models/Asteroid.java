package game_models;

import java.awt.*;
import java.util.Arrays;

public class Asteroid extends Character {
    private int speed;
    private boolean isVisible;
    private String imageFile; // Variável de instância para armazenar a imagem do asteroide
    private static boolean toggleImage = true; // Variável estática para alternar entre as imagens

    // Construtor do asteroide
    public Asteroid(int x, int y) {
        super(x, y);
        this.speed = (int) (Math.random() * 5) + 1; // Velocidade aleatória entre 1 e 5
        isVisible = true;
        selectImage(); // Seleciona a imagem ao criar o asteroide
        load(); // Carrega a imagem selecionada
    }

    // Seleciona a imagem do asteroide e seu tamanho
    private void selectImage() {
        if (isVisible) {
            String[] asteroidImages = {"res\\asteroid.gif", "res\\asteroidSmall.gif"};
            imageFile = asteroidImages[toggleImage ? 0 : 1]; // Alterna entre as duas imagens
            toggleImage = !toggleImage; // Alterna o valor de toggleImage
        } else {
            imageFile = "res\\explosion.png";
        }
    }

    // Carrega a imagem selecionada
    @Override
    public void load() {
        super.load(imageFile);
    }

    // Obtém os limites para detecção de colisão
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth() - 10, getHeight());
    }

    // Movimentação do asteroide
    public void move() {
        setY(getY() + speed);
        if (getY() > 728) { // Se o asteroide sair da tela (assumindo 728 como a altura da tela)
            resetPosition();
        }
    }

    // Quando chegar no fim da tela, o asteroide volta para o começo
    private void resetPosition() {
        setX((int) (Math.random() * 920)); // Nova posição X aleatória
        setY(-getHeight()); // Reposicionar no topo, fora da tela
    }

    // Verifica se o asteroide está visível
    public boolean isVisible() {
        return isVisible;
    }

    // Define a visibilidade do asteroide e seleciona a imagem correspondente
    public void setVisible(boolean visible) {
        isVisible = visible;
        selectImage(); // Atualiza a imagem com base na nova visibilidade
        load(); // Recarrega a imagem após a atualização
    }
}
