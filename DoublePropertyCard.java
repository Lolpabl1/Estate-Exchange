
public class DoublePropertyCard extends Card implements PropertyCard, Settable {

    final private PropertyColor[] colors;
    private int currentIndex;

    public DoublePropertyCard(PropertyColor c1, PropertyColor c2, int value) {
        super(value, c1.name() + "_" + c2.name());
        colors = new PropertyColor[]{c1, c2};
        currentIndex = 0;
    }

    @Override
    public String getColor() {
        return colors[currentIndex].name();
    }

    @Override
    public int getNeedForSet() {
        return colors[currentIndex].getNeedForSet();
    }

    @Override
    public int getRent(int index) {
        return colors[currentIndex].getRent(index);
    }

    public void changeProperty() {
        currentIndex = currentIndex == 0 ? 1 : 0;
    }

    @Override
    public void set(Game gameState) {
        String answer = (gameState.getAnswers(new String[]{"Current color is " + this.getColor() + ". Would you like to switch? "}))[0];
        if (answer.equalsIgnoreCase("yes")) {
            this.changeProperty();
        }
    }

    @Override
    public void play(Player player) {
        player.addProperty(this);
    }
}
