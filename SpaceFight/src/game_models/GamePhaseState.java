package game_models;

public class GamePhaseState implements ScreenState {

    @Override
    public void showScreen(Screen context) {
        context.getMain().getContentPane().removeAll();
        Phase phase = new Phase();
        context.getMain().getContentPane().add(phase);
        context.getMain().revalidate();
        context.getMain().repaint();
        phase.requestFocusInWindow();
    }
}