package game_models;

import game.Main;
import game_models.Asteroid;
import game_models.Spacecraft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Phase extends JPanel implements ActionListener {
    private Image background;
    private Spacecraft spacecraft;
    private Timer timer;
    private List<Asteroid> asteroids;
    private boolean inGame;
    private int asteroidsKill;
    private int score;
    private Font gameFont;
    private boolean isPaused;
    private boolean showRoundText = false;
    private long roundTextStartTime;
    private final int roundTextDuration = 1300; //Duração em milissegundos
    private int currentRound = 1;

    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        //Fonte das letras
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/PressStart2P-Regular.ttf"));
            gameFont = gameFont.deriveFont(Font.BOLD, 20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameFont = new Font("Helvetica", Font.BOLD, 20);
        }

        initGame(); //Iniciar o jogo
        addKeyListener(new TecladoAdapter());//Permite o teclado funcionar

        timer = new Timer(5, this);
        timer.start();
    }

    //Inicio do jogo
    private void initGame() {
        Sound.soundgame.loop();

        ImageIcon reference = new ImageIcon("res\\background.gif");
        background = reference.getImage();

        spacecraft = new Spacecraft(470, 450); //Spawn da espaçonave
        spacecraft.load(); //Carrega a imagem e o tamanho dela

        asteroids = new ArrayList<>(); //Arraylist de asteroids
        addAsteroidsForRound(currentRound); //Adicionando quantidade de asteroids por round

        inGame = true;
        isPaused = false;
        score = 0;
        asteroidsKill = 0;

        // Inicia o timer para mostrar o texto do round
        showRoundText = true;
        roundTextStartTime = System.currentTimeMillis();
    }

    //Fases
    private void addAsteroidsForRound(int round) {
        int numAsteroids = 0;
        switch (round) {
            case 1:
                numAsteroids = 10;
                break;
            case 2:
                numAsteroids = 20;
                break;
            case 3:
                numAsteroids = 30;
                break;
            case 4:
                numAsteroids = 40;
                break;
            case 5:
                numAsteroids = 50;
                break;
        }

        //Criação dos asteroids
        for (int i = 0; i < numAsteroids; i++) {
            int x = (int) (Math.random() * (920));
            int y = -728 - (int) (Math.random() * 100);

            Asteroid asteroid = new Asteroid(x, y);
            asteroid.load(); //Carrega a imagem e o tamanho dela
            asteroids.add(asteroid); //Adiciona o asteroid
        }
    }

    //Pinta tudo o que possue na tela
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graficos = (Graphics2D) g;

        if (inGame) {
            graficos.drawImage(background, 0, 0, null); //Imagem do fundo
            graficos.drawImage(spacecraft.getImage(), spacecraft.getX(), spacecraft.getY(), this); //Imagem da espaçonave

            //Score
            graficos.setFont(gameFont);
            graficos.setColor(Color.WHITE);
            graficos.drawString("Score:" + score, 10, 30);

            //Colocar imagem em todos os asteroids
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroid = asteroids.get(j);
                asteroid.load();
                graficos.drawImage(asteroid.getImage(), asteroid.getX(), asteroid.getY(), this);
            }

            //Colocar imagem em todos os tiros
            List<Shoot> shoots = spacecraft.getShoots();
            for (int i = 0; i < shoots.size(); i++) {
                Shoot m = shoots.get(i);
                m.load();
                graficos.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            //tela de pause
            if (isPaused) {
                graficos.setFont(gameFont.deriveFont(Font.BOLD, 40f));
                graficos.setColor(Color.YELLOW);
                graficos.drawString("PAUSED", 400, 330);

                graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
                graficos.setColor(Color.WHITE);
                graficos.drawString("Press R to restart", 340, 370);
            }

            //Informar mudança de fase
            if (showRoundText) {
                if (!isPaused){
                    graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
                    graficos.setColor(Color.WHITE);
                    graficos.drawString("Round " + currentRound, 440, 364);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }

        //Tela de derrota
        else {
            ImageIcon gameOver = new ImageIcon("res\\GameOver.png");
            graficos.drawImage(gameOver.getImage(), 0, 0, null);
            Toolkit.getDefaultToolkit().sync();

            graficos.setFont(gameFont.deriveFont(Font.BOLD, 40f));
            graficos.setColor(Color.WHITE);
            graficos.drawString("Score:" + score, 340, 435);

            graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
            graficos.setColor(Color.WHITE);
            graficos.drawString("Press R to restart", 340, 470);
        }

        //Tela de vitória
        if (asteroidsKill == 150) {
            inGame = false;
            ImageIcon youWin = new ImageIcon("res\\YouWin.jpeg");
            graficos.drawImage(youWin.getImage(), 0, 0, null);
            Toolkit.getDefaultToolkit().sync();

            graficos.setFont(gameFont.deriveFont(Font.BOLD, 20f));
            graficos.setColor(Color.WHITE);
            graficos.drawString("Press R to restart", 325, 485);
        }
        g.dispose();
    }

    //Método para checar colisões
    public void checkCollisions() {
        Rectangle shapeSpacecraft = spacecraft.getBounds();
        Rectangle shapeShoot;
        Rectangle shapeAsteroid;

        //Criando uma forma para a colisão do asteroid
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid tempAsteroid = asteroids.get(i);
            shapeAsteroid = tempAsteroid.getBounds();
            if (inGame) {
                //Colisão entre a espaçonave e o asteroid
                if (shapeSpacecraft.intersects(shapeAsteroid)) {
                    tempAsteroid.setVisible(false);
                    Sound.kill.play();
                    inGame = false;
                }
            }
        }

        //Criando uma forma para a colisão do tiro
        List<Shoot> shoots = spacecraft.getShoots();
        for (int j = 0; j < shoots.size(); j++) {
            Shoot tempShoot = shoots.get(j);
            shapeShoot = tempShoot.getBounds();
            for (int k = 0; k < asteroids.size(); k++) {
                Asteroid tempAsteroid = asteroids.get(k);
                shapeAsteroid = tempAsteroid.getBounds();
                if (inGame) {
                    //Colisão entre o tiro com o asteroid
                    if (shapeShoot.intersects(shapeAsteroid)) {
                        tempAsteroid.setVisible(false);
                        tempShoot.setVisible(false);
                        score += 50;
                        asteroidsKill += 1;

                        //Aparecer a mudança de fase
                        if (asteroidsKill == 10 || asteroidsKill == 30 || asteroidsKill == 60 || asteroidsKill == 100) {
                            currentRound++;
                            addAsteroidsForRound(currentRound);
                            showRoundText = true;
                            roundTextStartTime = System.currentTimeMillis();
                        }
                        Sound.explosion.play();
                    }
                }
            }
        }
    }

    //Ações do jogo
    @Override
    public void actionPerformed(ActionEvent e) {
        //Se o jogo estiver pausado ou se não estiver em jogo não realiza as ações
        if (isPaused || !inGame) {
            return;
        }

        spacecraft.move(); //Movimentação da espaçonave
        //Movimentação ou remoção do tiro
        List<Shoot> shoots = spacecraft.getShoots();
        for (int i = 0; i < shoots.size(); i++) {
            Shoot m = shoots.get(i);
            if (m.isVisible()) {
                m.move();
            } else {
                shoots.remove(i);
            }
        }

        //Movimentação ou remoção do asteroid
        for (int j = 0; j < asteroids.size(); j++) {
            Asteroid asteroid = asteroids.get(j);
            if (asteroid.isVisible()) {
                asteroid.move();
            } else {
                asteroids.remove(j);
            }
        }

        if (showRoundText && System.currentTimeMillis() - roundTextStartTime >= roundTextDuration) {
            showRoundText = false;
        }

        checkCollisions(); //Checa se possue colisão
        repaint(); //Repinta a tela com as localizações dos objetos atualizadas
    }

    //Método para resetar o jogo
    private void resetGame() {
        currentRound = 1;
        initGame();
        repaint();
    }

    //Método para o uso do teclado
    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            //Tecla para pausar o jogo
            if (code == KeyEvent.VK_ESCAPE) {
                if (inGame) {
                    isPaused = !isPaused;
                }
            }

            //Tecla para resetar o jogo se estive pausado ou fora de jogo
            if (code == KeyEvent.VK_R) {
                if (isPaused || !inGame) {
                    resetGame();
                }
            }

            //Permite as açoes da espaçonave se estiver em jogo e não estiver pausado
            if (inGame && !isPaused) {
                spacecraft.keyPressed(e);
            }
        }

        //Reconhecer quando a tecla não está pressionada
        @Override
        public void keyReleased(KeyEvent e) {
            spacecraft.keyRelease(e);
        }
    }
}
