import java.util.ArrayList;

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
        ArrayList <PropertyCard> steal = new ArrayList<>();

        for (Player p : gameState.getPlayers()) {
            if (p.getName().equalsIgnoreCase(answers[0])) {
                if (justSayNo(player, p, gameState)) break;

                for (PropertyCard pc : p.getProperties()) {
                    if (pc.getColor().equalsIgnoreCase(answers[1])) {
                        steal.add(pc);
                    }
                }

                for (int i = 0; i < steal.get(0).getNeedForSet(); i++){
                    player.addProperty(steal.get(i));
                    p.removeProperty(steal.get(i));
                }
                break;
            }
        }
    }
}
