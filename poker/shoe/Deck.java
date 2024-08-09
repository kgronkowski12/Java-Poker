package poker.shoe;
import java.util.ArrayList;
import java.util.Random;

public class Deck{
    private ArrayList<Card> deck;
    private Random rand;

    public Deck(){
        rand = new Random();
        reshuffle();
    }
    public Card revealCard(){
        Card c = deck.remove(rand.nextInt(deck.size()));
        return c;
    }
    public void reshuffle(){
        deck = new ArrayList<Card>();
        for(int rank=2;rank<=14;rank++){
            for(Suit s:Suit.values()){
                Card c = new Card(s,rank);
                deck.add(c);
            }
        }
    }
}