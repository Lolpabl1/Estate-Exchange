// I made card my abstract for the three different types of cards that exist. 
// Since all cards have a value and can be played as money, it seems most logical

import java.util.ArrayList;

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
        player.addValue(this);
    }

    public boolean justSayNo(Player player, Player p, Game gameState){
        ArrayList <JustSayNo> playerNos = new ArrayList<>();
        ArrayList <JustSayNo> opponentNos = new ArrayList<>();
        boolean cancel = false;

        for (Card c : p.getHand()){
            if (c instanceof JustSayNo jsn){
                opponentNos.add(jsn);
            }
        }

        for (Card c : player.getHand()){
            if (c instanceof JustSayNo jsn){
                playerNos.add(jsn);
            }
        }

        while (!cancel && !opponentNos.isEmpty()){
            if (!cancel && !opponentNos.isEmpty()){
                String s = gameState.getAnswers("Do you want to play your just say no? ")[0];
                if (s.equals("no")) break;
                cancel = true;
                Card c = opponentNos.remove(0);
                p.discardCard(c, gameState);
                gameState.addCard(c);
            }

            if (cancel && !playerNos.isEmpty()){
                String s = gameState.getAnswers("Do you want to play your just say no? ")[0];
                if (s.equals("no")) break;
                cancel = false;
                Card c = playerNos.remove(0);
                player.discardCard(c, gameState);
                gameState.addCard(c);
            }
        }

        return cancel;
    }
}