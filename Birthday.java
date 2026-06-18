
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
                if(justSayNo(player, p, gameState)) continue;

                for (Card c : p.pay(2, gameState)) {
                    if (c instanceof PropertyCard pc) {
                        player.addProperty(pc);
                    } else {
                        player.addValue(c);
                    }
                }
            }
        }
    }
}