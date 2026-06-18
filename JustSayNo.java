
public class JustSayNo extends Card implements Actionable {

    public JustSayNo() {
        super(4, "Just Say No");
    }

    @Override
    public String[] getQuestions() {
        return new String[0];
    }

    @Override
    public void playAsAction(Player player, Game gameState, String[] answers) {

    }
}
