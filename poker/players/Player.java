package poker.players;
import poker.shoe.*;
import java.util.ArrayList;

public abstract class Player{
    private ArrayList<Card> hand;
    protected int chips;
    private String name;

    public Player(String name, int chips){
        this.name = name;
        this.chips = chips;
        hand = new ArrayList<Card>();
    }

    public abstract void wonPot();
    public abstract void folded();
    public abstract int raiseDecision(int raise, boolean isReraise, int handStrength, int pot);
    public abstract void showdown(Player opponent, int scoreOne, int scoreTwo);

    public void printHand(){
        for(Card c:hand){
            System.out.print(c.toString());
        }
        System.out.println("");
    }
    public void giveCard(Card c){
        hand.add(c);
    }
    public void clearHand(){ hand = new ArrayList<Card>(); }
    public ArrayList<Card>getHand(){ return hand;}
    public void setChips(int c){ chips=c; }
    public int getChips(){ return chips; }
    public String getName(){ return name; }
}