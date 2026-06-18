
public class RentCard extends Card implements Actionable {

    private final PropertyColor color1;
    private final PropertyColor color2;

    public RentCard(PropertyColor color1, PropertyColor color2) {
        super(1, color1.name() + "_" + color2.name() + " rent");
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public String[] getQuestions() {
        return new String[0];
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        int one = 0, two = 0, max = 0;

        for (PropertyCard pc : player.getProperties()) {
            if (pc.getColor().equalsIgnoreCase(color1.name())) {
                one++;
                max = Math.max(max, pc.getRent(one));
            } else if (pc.getColor().equalsIgnoreCase(color2.name())) {
                two++;
                max = Math.max(max, pc.getRent(two));
            }
        }

        for (Player p : gameState.getPlayers()) {
            if (p != player) {
                if (justSayNo(player, p, gameState)) continue;
                for (Card c : p.pay(max, gameState)) {
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
