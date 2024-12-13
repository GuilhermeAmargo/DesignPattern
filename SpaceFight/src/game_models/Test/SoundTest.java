package game_models;

public class SoundTest {
    public static void main(String[] args) {
        // Obtém instâncias de som
        Sound sound1 = Sound.getInstance("res\\Laser_Shoot.wav");
        Sound sound2 = Sound.getInstance("res\\Explosion.wav");


        if (sound1 == sound2) {
            System.out.println("Teste Singleton: PASSOU (somente uma instância foi criada)");
        } else {
            System.out.println("Teste Singleton: FALHOU (mais de uma instância foi criada)");
        }

        System.out.println("Tocando som em loop...");
        sound1.loop();
        sound2.loop();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Para o som
        sound1.stop();
        sound2.stop();
        System.out.println("Som parado.");
    }
}