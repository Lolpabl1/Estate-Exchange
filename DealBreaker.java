
public class DealBreaker extends Card implements Actionable {

    public DealBreaker() {
        super(5, "Deal Breaker");
    }

    @Override
    public String[] getQuestions() {
        return new String[]{
            "Which player do you want to steal from? ",
            "Which color do you want? "
        };
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        outer:
        for (Player p : gameState.getPlayers()) {
            if (p.getName().equalsIgnoreCase(answers[0])) {
                for (PropertyCard pc : p.getProperties()) {
                    if (pc.getColor().equalsIgnoreCase(answers[1])) {
                        for (int i = 0; i < pc.getNeedForSet(); i++) {
                            player.addProperty(pc);
                            p.removeProperty(pc);
                        }
                        break outer;
                    }
                }
            }
        }
    }
}
