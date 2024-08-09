package poker;
import poker.shoe.*;
import poker.players.*;
import poker.table.*;

public class Main {

    public static void main(String[] args) {
		AI playerOne = new AI("AI",1000);
		HumanControlled playerTwo = new HumanControlled("You",1000);
		Table table = new Table(playerOne,playerTwo);
		table.startGame();
    }
}