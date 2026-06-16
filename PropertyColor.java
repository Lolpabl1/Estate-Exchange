
public enum PropertyColor {
    BLACK(4, new int[]{0, 1, 2, 3, 4}),
    LIGHT_BLUE(3, new int[]{0, 1, 2, 3}),
    PINK(3, new int[]{0, 1, 2, 4}),
    ORANGE(3, new int[]{0, 1, 3, 5}),
    RED(3, new int[]{0, 2, 3, 6}),
    YELLOW(3, new int[]{0, 2, 4, 6}),
    GREEN(3, new int[]{0, 2, 4, 7}),
    BLUE(2, new int[]{0, 3, 8}),
    JADE(2, new int[]{0, 1, 2}),
    BROWN(2, new int[]{0, 1, 2});

    private final int needForSet;
    private final int[] rent;

    PropertyColor(int needForSet, int[] rent) {
        this.needForSet = needForSet;
        this.rent = rent;
    }

    public int getNeedForSet() {
        return needForSet;
    }

    public int getRent(int index) {
        return rent[index];
    }
}
