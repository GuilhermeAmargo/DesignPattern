package game_models;
import java.awt.event.*;


public interface GameState {
    void show();
    void handleEvents(ActionEvent e);
}
