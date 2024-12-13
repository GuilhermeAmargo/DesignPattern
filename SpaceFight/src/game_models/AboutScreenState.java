package game_models;

import javax.swing.*;

public class AboutScreenState implements ScreenState {

    @Override
    public void showScreen(Screen context) {
        context.getMain().getContentPane().removeAll();

        ImageIcon aboutImage = new ImageIcon("res/tela_aboutscreen.png");
        JLabel imageLabel = new JLabel(aboutImage);
        JButton backButton = context.getBackButton();
        backButton.setBounds(790, 500, 200, 50);

        imageLabel.setBounds(0, 0, aboutImage.getIconWidth(), aboutImage.getIconHeight());

        JPanel aboutPanel = new JPanel(null);
        aboutPanel.add(backButton);
        aboutPanel.add(imageLabel);

        context.getMain().getContentPane().add(aboutPanel);
        context.getMain().revalidate();
        context.getMain().repaint();
    }
}