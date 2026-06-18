
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Player {

    private final String name;
    private final ArrayList<Card> cash;
    private final ArrayList<Card> hand;
    private final ArrayList<PropertyCard> properties;

    public Player(String name) {
        this.name = name;
        cash = new ArrayList<>();
        hand = new ArrayList<>();
        properties = new ArrayList<>();
    }

    public void addValue(Card c) {
        cash.add(c);
    }

    public void removeCash(Card c) {
        cash.remove(c);
    }

    public void addCard(Card c) {
        hand.add(c);
    }

    public void discardCard(Card c, Game gameState) {
        hand.remove(c);
    }

    public void addProperty(PropertyCard pc) {
        properties.add(pc);
    }

    public void removeProperty(PropertyCard pc) {
        properties.remove(pc);
    }

    public ArrayList<Card> getCash() {
        return cash;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PropertyCard> getProperties() {
        return properties;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getSets() {
        HashMap<String, Integer> map = new HashMap<>();
        int sets = 0;
        for (PropertyCard pc : getProperties()) {
            map.put(pc.getColor(), map.getOrDefault(pc.getColor(), 0) + 1);
            if (map.get(pc.getColor()) >= pc.getNeedForSet()) {
                sets++;
                map.put(pc.getColor(), 0);
            }
        }

        return sets;
    }

    // I chose to use Card to track cash just because it will make life easier for UI as sly deal will ultimately be different to 3m 
    // even if it functionally similar once we don't play it as an action.
    public ArrayList<Card> pay(int amount, Game gameState) {
        ArrayList<Card> list = new ArrayList<>();
        while (amount > 0 && (!getCash().isEmpty() || !getProperties().isEmpty())) {
            System.out.println(getName() + ", You need to pay: " + amount);
            String answer = gameState.getAnswers("How would you like to pay? ")[0];

            StringTokenizer st = new StringTokenizer(answer);

            while (st.hasMoreTokens()) {
                String t = st.nextToken();
                if (t.matches("\\d+")) {
                    int value = Integer.parseInt(t);
                    for (Card c : cash) {
                        if (c.getValue() == value) {
                            amount-=value;
                            list.add(c);
                            removeCash(c);
                            break;
                        }
                    }
                } else {
                    for (PropertyCard pc : getProperties()) {
                        if (pc.getColor().equalsIgnoreCase(t)) {
                            list.add((Card) pc);
                            removeProperty(pc);
                            amount-=((Card)pc).getValue();
                            break;
                        }
                    }
                }
            }
        }

        return list;
    }
    
    public void play(Card c, Game gameState) {
        if (c instanceof Settable sc) {
            (sc).set(gameState);
        }

        if (c instanceof Actionable ac) {
            String answer = gameState.getAnswers("Do you want to play it as an action? ")[0];
            if (answer.equalsIgnoreCase("YES")) {
                ac.playAsAction(this, gameState, gameState.getAnswers(ac.getQuestions()));
                gameState.addCard(c);
            } else {
                c.play(this);
            }
        } else {
            c.play(this);
        }

        System.out.println(getName() + " played " + c.getName());
        discardCard(c, gameState);
    }

    public void displayPlayerStatus(){
        System.out.println(getName() + "'s hand:");

            for (Card c : hand) {
                System.out.println(c.getName());
            }

            System.out.println();

            System.out.println("Properties: ");
            for (PropertyCard pc : getProperties()) {
                System.out.println(pc.getColor());
            }

            System.out.print("Cash: " + "[");
            for (Card c : cash) {
                System.out.print(c.getValue() + ", ");
            }
            System.out.println("]");
            System.out.println("Sets: " + getSets());
    }
   
    public int placeCard(Game gameState, int turnNumber){
        String answer = gameState.getAnswers("What card do you want to play? ")[0];

        for (Card c : hand) {
            if (c.getName().equalsIgnoreCase(answer)) {
                play(c, gameState);

                if (turnNumber < 2 && (c instanceof RentCard || c instanceof WildRentCard)) {
                    for (Card card : getHand()) {
                        if (card instanceof DoubleRent) {
                            answer = gameState.getAnswers("Do you want to play your double the rent card? ")[0];
                            if (answer.equalsIgnoreCase("yes")) {
                                ((Actionable) card).playAsAction(this, gameState, ((Actionable) card).getQuestions());
                                turnNumber++;
                                discardCard(card, gameState);
                            }
                            break;
                        }
                    }
                }
                break;
            }
        }
        return turnNumber;
    }
    
    public void pickUpCard(Game gameState){
        String answer = gameState.getAnswers("What card do you want to pick up? ")[0];
        for (PropertyCard pc : getProperties()) {
            if (((Card) pc).getName().equalsIgnoreCase(answer)) {
                addCard((Card) pc);
                removeProperty(pc);
                break;
            }
        }
        System.out.println(getName() + " picked up " + answer);
    }
    
    public void switchCard(Game gameState){
        String colorToBeSwitched = gameState.getAnswers("Which property do you want to switch? ")[0];

        if (colorToBeSwitched.equalsIgnoreCase("Wild card")) {
            String newColor = gameState.getAnswers("What color do you want to switch to? ")[0];
            for (PropertyCard pc : getProperties()) {
                if (pc instanceof WildPropertyCard wpc) {
                    wpc.changeProperty(newColor);
                    break;
                }
            }
        } else {
            for (PropertyCard pc : getProperties()) {
                if (((Card) pc).getName().equalsIgnoreCase(colorToBeSwitched)) {
                    ((DoublePropertyCard) pc).changeProperty();
                }
            }
        }

        System.out.println(getName() + " switched his property");
    }
    
    public void burnCards(Game gameState){
        while (getHand().size() > 7) {
            String burnedCard = gameState.getAnswers("Your hand size is greater than 7. Select a card to burn: ")[0];
            for (Card card : getHand()) {
                if (card.getName().equalsIgnoreCase(burnedCard)) {
                    discardCard(card, gameState);
                    gameState.addCard(card);
                    System.out.println(getName() + " burned " + card.getName());
                    break;
                }
            }
        }
    }
    
    public void playTurn(Game gameState) {
        System.out.println("It is " + getName() + "'s turn");

        outer:
        for (int i = 0; i < 3; i++) {
            displayPlayerStatus();

            boolean invalidChoice = true;

            while (invalidChoice){
                int choice = Integer.parseInt(gameState.getAnswers("What kind of move do you want to make? " + "\n" + "Play a card[0]" + "\n"
                        + "Pick up a card[1]" + "\n" + "Switch Properties[2]" + "\n" + "End your turn[3]")[0]);

                switch (choice) {
                    case 0 -> {
                        i = placeCard(gameState, i);
                        invalidChoice = false;
                    }

                    case 1 -> {
                        pickUpCard(gameState);
                        invalidChoice = false;
                    }

                    case 2 -> {
                        switchCard(gameState);
                        invalidChoice = false;
                    }

                    case 3 -> {
                        break outer;
                    }

                    default -> {
                        System.out.println("Invalid input. Please pick a number from 0 to 3.");
                    }
                }
            }
        }

        burnCards(gameState);
    }
}
