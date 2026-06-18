
public interface Actionable {

    String[] getQuestions();

    void playAsAction(Player player, Game gameState, String[] answers);
}
