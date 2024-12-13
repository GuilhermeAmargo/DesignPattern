package game_models;

import game.Main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Screen extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton aboutButton;
    private JButton exitButton;
    private JButton backButton;
    private Main main;
    private Font gameFont;

    private ScreenState currentState;

    public Screen(Main main) {
        this.main = main;
        setLayout(null); // Usamos layout absoluto para posicionar os componentes manualmente

        loadFont(); // Carrega a fonte personalizada

        // Criação e posicionamento dos botões
        startButton = createButton("JOGAR", 400, 400, 200, 50);
        aboutButton = createButton("SOBRE", 400, 460, 200, 50);
        exitButton = createButton("SAIR", 400, 520, 200, 50);

        // Adiciona os botões ao painel
        add(startButton);
        add(aboutButton);
        add(exitButton);

        // Inicializa o botão "BACK"
        backButton = createButton("BACK", 0, 0, 200, 50); // Posição inicial arbitrária
        backButton.addActionListener(this);

        currentState = new HomeScreenState();
    }

    public Main getMain() {
        return main;  // Retorna a referência ao objeto 'main'
    }

    public JButton getBackButton() {
        return backButton;  // Retorna o objeto 'backButton'
    }

    // Método para carregar a fonte personalizada
    private void loadFont() {
        try {
            // Tenta carregar a fonte personalizada do arquivo
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/PressStart2P-Regular.ttf")).deriveFont(Font.BOLD, 20f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(gameFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Se houver falha, define uma fonte padrão
            gameFont = new Font("Helvetica", Font.BOLD, 20);
        }
    }

    // Método para criar um botão com texto, posição e tamanho especificados
    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(gameFont); // Define a fonte personalizada no botão
        button.setBounds(x, y, width, height); // Define posição e tamanho
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Define o cursor para o botão
        button.setUI(new CustomButtonUI()); // Define a UI personalizada
        button.addActionListener(this); // Adiciona ActionListener ao botão
        return button;
    }

    // Método para desenhar o componente gráfico
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha a imagem de fundo
        ImageIcon screenHome = new ImageIcon("res/telainicio.png");
        g.drawImage(screenHome.getImage(), 0, 0, null);
    }

    // Método para tratar eventos de ação dos botões
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            currentState = new GamePhaseState(); // Mostra a fase do jogo
        } else if (e.getSource() == aboutButton) {
            currentState = new AboutScreenState(); // Mostra a tela "Sobre"
        } else if (e.getSource() == exitButton) {
            System.exit(0); // Sai do jogo
        } else if (e.getSource() == backButton) {
            currentState = new HomeScreenState(); // Volta para a tela inicial
        }

        currentState.showScreen(this);
    }

    // Classe interna para definir a UI personalizada dos botões
    private class CustomButtonUI extends BasicButtonUI {
        @Override
        public void installDefaults(AbstractButton b) {
            super.installDefaults(b);
            b.setBackground(Color.YELLOW); // Define a cor de fundo do botão
            b.setContentAreaFilled(false); // Desativa a pintura da área de conteúdo
            b.setFocusPainted(false); // Desativa a pintura do foco
            b.setBorderPainted(false); // Desativa a pintura da borda
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();

            if (model.isPressed()) {
                g.setColor(b.getBackground()); // Pinta o botão quando pressionado
            } else {
                g.setColor(b.getBackground()); // Pinta o botão normalmente
            }

            g.fillRect(0, 0, c.getWidth(), c.getHeight()); // Preenche o retângulo do botão
            super.paint(g, c);
        }
    }
}
