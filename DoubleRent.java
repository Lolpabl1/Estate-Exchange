
public class DoubleRent extends Card implements Actionable {

    public DoubleRent() {
        super(1, "Double the Rent");
    }

    @Override
    public String[] getQuestions() {
        return new String[0];
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {

    }
}
