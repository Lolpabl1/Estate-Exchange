
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> newDeck = new ArrayList<>();

    Player player1 = new Player("Mohammed");
    Player player2 = new Player("Husayn");
    Player player3 = new Player("Brennan");
    Player player4 = new Player("Jay");

    private Player[] players = {player1, player2, player3, player4};

    public Player[] getPlayers() {
        return players;
    }

    public void startGame() {
        initializeDeck();
        distributeCards();
        gameLoop();
    }

    public void initializeDeck() {
        // Intializing Money
        deck.add(new MoneyCard(10));

        for (int i = 0; i < 2; i++) {
            deck.add(new MoneyCard(5));
        }

        for (int i = 0; i < 5; i++) {
            deck.add(new MoneyCard(4));
            deck.add(new MoneyCard(2));
        }
        for (int i = 0; i < 6; i++) {
            deck.add(new MoneyCard(3));
            deck.add(new MoneyCard(1));
        }

        // Initializing Standard Properties
        for (int i = 0; i < 4; i++) {
            deck.add(new StandardPropertyCard(PropertyColor.BLACK, 2));
        }

        for (int i = 0; i < 3; i++) {
            deck.add(new StandardPropertyCard(PropertyColor.PINK, 2));
            deck.add(new StandardPropertyCard(PropertyColor.ORANGE, 2));
            deck.add(new StandardPropertyCard(PropertyColor.RED, 3));
            deck.add(new StandardPropertyCard(PropertyColor.YELLOW, 3));
            deck.add(new StandardPropertyCard(PropertyColor.GREEN, 4));
            deck.add(new StandardPropertyCard(PropertyColor.LIGHT_BLUE, 1));
        }

        for (int i = 0; i < 2; i++) {
            deck.add(new StandardPropertyCard(PropertyColor.BROWN, 1));
            deck.add(new StandardPropertyCard(PropertyColor.JADE, 2));
            deck.add(new StandardPropertyCard(PropertyColor.BLUE, 4));
        }

        // Initializing Double Properties
        for (int i = 0; i < 2; i++) {
            deck.add(new DoublePropertyCard(PropertyColor.RED, PropertyColor.YELLOW, 3));
            deck.add(new DoublePropertyCard(PropertyColor.PINK, PropertyColor.ORANGE, 2));
        }

        deck.add(new DoublePropertyCard(PropertyColor.BLUE, PropertyColor.GREEN, 4));
        deck.add(new DoublePropertyCard(PropertyColor.BROWN, PropertyColor.LIGHT_BLUE, 1));
        deck.add(new DoublePropertyCard(PropertyColor.BLACK, PropertyColor.GREEN, 4));
        deck.add(new DoublePropertyCard(PropertyColor.BLACK, PropertyColor.JADE, 2));
        deck.add(new DoublePropertyCard(PropertyColor.BLACK, PropertyColor.LIGHT_BLUE, 4));

        // Initializing Wild Properties
        for (int i = 0; i < 2; i++) {
            deck.add(new WildPropertyCard());
        }

        // Initializing Rent Cards
        for (int i = 0; i < 2; i++) {
            deck.add(new RentCard("Blue", "Green"));
            deck.add(new RentCard("Red", "Yellow"));
            deck.add(new RentCard("Pink", "Orange"));
            deck.add(new RentCard("Black", "Jade"));
            deck.add(new RentCard("Brown", "Light Blue"));
        }

        for (int i = 0; i < 3; i++) {
            deck.add(new WildRentCard());
        }

        // Initializing Action Cards
        for (int i = 0; i < 2; i++) {
            deck.add(new DealBreaker());
            deck.add(new DoubleRent());
        }

        for (int i = 0; i < 3; i++) {
            deck.add(new ForcedDeal());
            deck.add(new SlyDeal());
            deck.add(new DebtCollector());
            deck.add(new Birthday());
        }

        for (int i = 0; i < 10; i++) {
            deck.add(new PassGo());
        }
    }

    public void distributeCards() {
        for (Player player : players) {
            this.drawCards(player, 5);
        }
    }

    public void gameLoop() {
        while (player1.getSets() + player3.getSets() < 5 && player2.getSets() + player4.getSets() < 5) {
            for (Player p : players) {
                if (p.getHand().isEmpty()) {
                    this.drawCards(p, 5);
                } else {
                    this.drawCards(p, 2);
                }
                p.playTurn(this);

                if (player1.getSets() + player3.getSets() >= 5 || player2.getSets() + player4.getSets() >= 5) {
                    break;
                }
            }
        }

        System.out.println("GAME OVER!");
        if (player1.getSets() + player3.getSets() >= 5) {
            System.out.println("The winners are " + player1.getName() + " and " + player3.getName());
        } else {
            System.out.println("The winners are " + player2.getName() + " and " + player4.getName());
        }
    }

    public void drawCards(Player player, int num) {
        if (deck.size() - num < 0) {
            for (Card c : deck) {
                newDeck.add(c);
            }
            deck.addAll(newDeck);
            newDeck.clear();
        }

        for (int i = 0; i < num; i++) {
            int index = (int) (Math.random() * deck.size());
            player.addCard(deck.get(index));
            deck.remove(index);
        }
    }

    public String[] getAnswers(String... questions) {
        Scanner scanner = new Scanner(System.in);
        String[] answers = new String[questions.length];
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            answers[i] = scanner.nextLine();
        }

        return answers;
    }

    public void addCard(Card c) {
        newDeck.add(c);
    }
}
