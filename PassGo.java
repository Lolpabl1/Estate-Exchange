
public class PassGo extends Card implements Actionable {

    public PassGo() {
        super(1, "Pass Go");
    }

    @Override
    public String[] getQuestions() {
        return new String[0];
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        gameState.drawCards(player, 2);
    }
}
