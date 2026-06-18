
public class ForcedDeal extends Card implements Actionable {

    public ForcedDeal() {
        super(3, "Forced Deal");
    }

    @Override
    public String[] getQuestions() {
        return new String[]{
            "Which player do you want to swap with? ",
            "Which property do you want to give? ", "Which property do you want to take? "
        };
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {

        outer:
        for (Player p : gameState.getPlayers()) {
            if (p.getName().equalsIgnoreCase(answers[0])) {
                  
                if (justSayNo(player, p, gameState)) break;

                for (PropertyCard pc : player.getProperties()) {
                    if (pc.getColor().equalsIgnoreCase(answers[1])) {
                        player.removeProperty(pc);
                        p.addProperty(pc);
                        break;
                    }
                }

                for (PropertyCard pc : p.getProperties()) {
                    if (pc.getColor().equalsIgnoreCase(answers[2])) {
                        player.addProperty(pc);
                        p.removeProperty(pc);
                        break outer;
                    }
                }
            }
        }
    }
}
