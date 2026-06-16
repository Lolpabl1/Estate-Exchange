
public class RentCard extends Card implements Actionable {

    private String color1;
    private String color2;

    public RentCard(String color1, String color2) {
        super(1, color1 + "_" + color2 + "_rent");
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
            if (pc.getColor().equalsIgnoreCase(color1)) {
                one++;
                max = Math.max(max, pc.getRent(one));
            } else if (pc.getColor().equalsIgnoreCase(color2)) {
                two++;
                max = Math.max(max, pc.getRent(two));
            }
        }

        for (Player p : gameState.getPlayers()) {
            if (p != player) {
                player.addValue(p.pay(max));
            }
        }
    }
}
