
public class DebtCollector extends Card implements Actionable {

    public DebtCollector() {
        super(3, "Debt Collector");
    }

    @Override
    public String[] getQuestions() {
        return new String[]{"Which player do you want to steal from? "};
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        for (Player p : gameState.getPlayers()) {
            if (p.getName().equalsIgnoreCase(answers[0])) {
                player.addValue(p.pay(5));
            }
        }
    }
}
