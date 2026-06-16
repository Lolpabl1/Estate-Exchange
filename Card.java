// I made card my abstract for the three different types of cards that exist. 
// Since all cards have a value and can be played as money, it seems most logical

public abstract class Card {

    protected final int value;
    protected final String name;

    protected Card(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void play(Player player) {
        player.addValue(value);
    }
}
