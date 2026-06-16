
public class Birthday extends Card implements Actionable {

    public Birthday() {
        super(2, "Birthday");
    }

    @Override
    public String[] getQuestions() {
        return new String[0];
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        for (Player p : gameState.getPlayers()) {
            if (p != player) {
                player.addValue(p.pay(2));
            }
        }
    }
}
