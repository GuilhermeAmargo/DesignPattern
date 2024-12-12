package game;

import game_models.Screen;

import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Space Fight");
        setSize(1024, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("res\\spacecraftIcon.png");
        setIconImage(icon.getImage());

        // Cria o painel inicial (Screen) e adiciona à janela principal
        Screen homePanel = new Screen(this);
        getContentPane().add(homePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}