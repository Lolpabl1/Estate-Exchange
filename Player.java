import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Player {

    Scanner scanner = new Scanner(System.in);

    private String name;
    private int cash;
    private ArrayList<Card> hand;
    private ArrayList<PropertyCard> properties;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    public void addValue(int value) {
        cash += value;
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

    public int getCash() {
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
        for (PropertyCard c : this.getProperties()) {
            map.put(c.getColor(), map.getOrDefault(c.getColor(), 0) + 1);
            if (map.get(c.getColor()) >= c.getNeedForSet()) {
                sets++;
                map.put(c.getColor(), 0);
            }
        }

        return sets;
    }

    // The way we can take care of the payment system instead of just tracking the cash value. 
    // We can create an ArrayList with the different types of cash and give players the option of how exactly they can pay.
    // We also have to make edge cases where paying 3m and 2m is fine if you need pay 4m. 
    // But paying 4m and 1m is considered overpaying, basically break as soon as you hit the amount.
    // Payment is only necessary for the player.pay() and rent cards. We can use generics to fix that.
    public int pay(int amount) {
        int temp = amount <= cash ? amount : cash;
        cash -= temp;
        return temp;
    }

    public void play(Card c, Game gameState) {
        if (c instanceof Settable){
            ((Settable)c).set(gameState);
        }

        if (c instanceof Actionable) {
            System.out.println("Do you want to play it as an action? ");
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("YES")) {
                ((Actionable) c).playAsAction(this, gameState, gameState.getAnswers(((Actionable) c).getQuestions()));
                gameState.addCard(c);
            } else {
                c.play(this);
            }
        } else {
            c.play(this);
        }

        System.out.println(this.getName() + " played " + c.getName());
        this.discardCard(c, gameState);
    }

    public void playTurn(Game gameState) {
        System.out.println("It is " + this.getName() + "'s turn");

        for (int i = 0; i < 3; i++) {
            System.out.println(this.getName() + "'s hand:");

            for (Card c : hand) {
                System.out.println(c.getName());
            }

            System.out.println();

            System.out.println("Properties: ");
            for (PropertyCard pc : this.getProperties()) {
                System.out.println(pc.getColor());
            }

            System.out.println("Cash: " + this.getCash());
            System.out.println("Sets: " + this.getSets());

            System.out.println("What kind of move do you want to make? ");
            System.out.println("Play a card/Pick up a card/End Your turn/Switch Properties: ");
            String a = scanner.nextLine();

            if (a.equalsIgnoreCase("end")) break;

            else if (a.equalsIgnoreCase("Pick up")){
                System.out.println("Which card do you want to pick up? ");
                String b = scanner.nextLine();
                for (PropertyCard pc : this.getProperties()){
                    if (((Card) pc).getName().equalsIgnoreCase(b)){
                        this.addCard((Card)pc);
                        this.removeProperty(pc);
                        break;
                    }
                }
            }

            else if (a.equalsIgnoreCase("Switch")){
                System.out.println("Which property do you want to swtich? ");
                String b = scanner.nextLine();
                if (b.equalsIgnoreCase("Wild")){
                    System.out.println("Which color do you want to switch to? ");
                    String d = scanner.nextLine();
                    for (PropertyCard pc : this.getProperties()){
                        if (pc instanceof WildPropertyCard){
                            ((WildPropertyCard) pc).changeProperty(d);
                            break;
                        }
                    }
                } 

                else {
                    for (PropertyCard pc : this.getProperties()){
                        if (((Card)pc).getName().equals(b)) {
                            ((DoublePropertyCard)pc).changeProperty();
                        }
                    }
                }
            }

            else {
                System.out.println("Which card do you want to play? ");
                String s = scanner.nextLine();
                System.out.println();

                for (Card c : hand) {
                    if (c.getName().equalsIgnoreCase(s)) {
                        this.play(c, gameState);

                        if (i < 2 && (c instanceof RentCard || c instanceof WildRentCard)) {
                            for (Card card : this.getHand()) {
                                if (card instanceof DoubleRent){
                                    System.out.println("Do you want to play your double the rent card? ");
                                    String t = scanner.nextLine();
                                    if (t.equalsIgnoreCase("yes")) {
                                        ((Actionable) card).playAsAction(this, gameState, ((Actionable) card).getQuestions());
                                        i++;
                                        this.discardCard(card, gameState);
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }

        while (this.getHand().size() > 7) {
            System.out.println("Your hand size is greater than 7. Select a card to burn: ");
            String v = scanner.nextLine();
            for (Card card : this.getHand()) {
                if (card.getName().equalsIgnoreCase(v)) {
                    this.discardCard(card, gameState);
                    gameState.addCard(card);
                    System.out.println(this.getName() + " burned " + card.getName());
                    break;
                }
            }
        }
    }
}
