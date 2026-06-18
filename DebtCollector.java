
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
                if (justSayNo(player, p, gameState)) break;

                for (Card c : p.pay(5, gameState)){
                    if (c instanceof PropertyCard pc){
                        player.addProperty(pc);
                    } else player.addValue(c);
                }
            }
        }
    }
}
