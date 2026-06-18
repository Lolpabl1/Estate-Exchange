
public class StandardPropertyCard extends Card implements PropertyCard {

    private final PropertyColor color;

    public StandardPropertyCard(PropertyColor color, int value) {
        super(value, color.name());
        this.color = color;
    }

    @Override
    public String getColor() {
        return color.name();
    }

    @Override
    public int getNeedForSet() {
        return color.getNeedForSet();
    }

    @Override
    public int getRent(int index) {
        return color.getRent(index);
    }

    @Override
    public void play(Player player) {
        player.addProperty(this);
    }
}
