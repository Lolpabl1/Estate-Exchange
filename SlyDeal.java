// You can now pick specific names when stealing since we are adding names to cards thus for deal breaker, sly deal etc etc, we can basically
// just check with name thus avoiding trouble with wild cards

public class SlyDeal extends Card implements Actionable {

    public SlyDeal() {
        super(3, "Sly Deal");
    }

    @Override
    public String[] getQuestions() {
        return new String[]{"Which player do you want to steal from? ", "Which property do you want to steal? "};
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        outer:
        for (Player p : gameState.getPlayers()) {
            if (p.getName().equalsIgnoreCase(answers[0])) {
                for (PropertyCard pc : p.getProperties()) {
                    if (pc.getColor().equalsIgnoreCase(answers[1])) {
                        player.addProperty(pc);
                        p.removeProperty(pc);
                        break outer;
                    }
                }
            }
        }
    }
}
