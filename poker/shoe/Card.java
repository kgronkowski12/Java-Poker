package poker.shoe;
import java.util.*;

enum Suit{
    //Note: Emoji's might not work on all terminals.
    HEARTS('@',Color.RED),DIAMONDS('#',Color.RED),CLUBS('*',Color.BLACK),SPADE('^',Color.BLACK);

    private char symbol;
    private Color color;
    Suit(char symbol,Color color){
        this.symbol = symbol;
        this.color = color;
    }
    public char getSymbol(){ return symbol; }
    public Color getColor(){ return color; }
}

enum Color{
    RED,BLACK
}
public class Card implements Comparable<Card>{
	private Suit suit;
	private Color color;
	private int rank;
    private HashMap<Integer,Character> names = new HashMap<Integer,Character>();

	public Card(Suit s, int r){
	    this.suit = s;
	    this.rank = r;
	    names.put(10,'T');
	    names.put(11,'J');
	    names.put(12,'Q');
	    names.put(13,'K');
	    names.put(14,'A');
	}

	@Override
    public int compareTo(Card c) {
        return c.rank-rank;
    }
	@Override
	public String toString(){
	    String name = "";
	    if (rank<10){
	        name+=rank;
	    }
	    else{
	        name+=names.get(rank);
	    }
	    String result = "[ " + suit.getSymbol() +" " + name + " " + suit.getSymbol() + " ]";
	    return result;
	}
	public Color getColor(){ return suit.getColor(); }
	public Suit getSuit(){ return suit; }
	public int getRank(){ return rank; }
}