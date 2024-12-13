package game_models;

import javax.swing.*;

public class HomeScreenState implements ScreenState {

    @Override
    public void showScreen(Screen context) {
        context.getMain().getContentPane().removeAll();
        Screen homeScreen = new Screen(context.getMain());
        context.getMain().getContentPane().add(homeScreen);
        context.getMain().revalidate();
        context.getMain().repaint();
    }
}
