
public class WildRentCard extends Card implements Actionable {

    public WildRentCard() {
        super(3, "Wild Rent Card");
    }

    @Override
    public String[] getQuestions() {
        return new String[]{"Select player to charge rent: ", "What colour rent do you want to charge? "};
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {
        int counter = 0, max = 0;

        for (PropertyCard pc : player.getProperties()) {
            if (pc.getColor().equalsIgnoreCase(answers[1])) {
                counter++;
                max = pc.getRent(counter);
            }
        }

        for (Player p : gameState.getPlayers()) {
            if (p.getName().equalsIgnoreCase(answers[0])) {
                if (justSayNo(player, p, gameState)) break;

                for (Card c : p.pay(max, gameState)){
                    if (c instanceof PropertyCard pc){
                        player.addProperty(pc);
                    } else player.addValue(c);
                }
                break;
            }
        }
    }
}
