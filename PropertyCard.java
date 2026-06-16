// I made this an interface because it makes iterating through properties easier later. 
// It also helps cleanly split up the three types of property cards

interface PropertyCard {

    String getColor();

    int getNeedForSet();

    int getRent(int index);

}
