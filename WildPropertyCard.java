
public class WildPropertyCard extends Card implements PropertyCard, Settable {

    private final PropertyColor[] totalProperties = PropertyColor.values();
    private int currentIndex;

    public WildPropertyCard() {
        super(0, "Wild Card");
        currentIndex = 0;
    }

    @Override
    public String getColor() {
        return totalProperties[currentIndex].name();
    }

    @Override
    public int getNeedForSet() {
        return totalProperties[currentIndex].getNeedForSet();
    }

    @Override
    public int getRent(int index) {
        return totalProperties[currentIndex].getRent(index);
    }

    public void changeProperty(String targetColor) {
        for (int i = 0; i < totalProperties.length; i++) {
            if (targetColor.equalsIgnoreCase(totalProperties[i].name())) {
                currentIndex = i;
                return;
            }
        }
    }

    @Override
    public void set(Game gameState){
        String answer = (gameState.getAnswers(new String[] {"What color do you want to switch to? "}))[0];
        this.changeProperty(answer);
    }

    @Override
    public void play(Player player){
        player.addProperty(this);
    }
}
