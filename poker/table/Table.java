package poker.table;
import poker.shoe.*;
import poker.players.*;
import java.util.*;
import java.lang.Math;

public class Table {
    private Player playerOne;
    private Player playerTwo;
    private HandEvaluator handEvaluator;
    private Deck deck;
    private ArrayList<Card> board;
    private int blind = 25;
    private int pot;

    public Table(Player playerOne,Player playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        deck = new Deck();
        board = new ArrayList<Card>();
        handEvaluator = new HandEvaluator();
    }

    public void folded(Player folder, Player winner){
        winner.setChips(winner.getChips()+pot);
        printState();
        folder.folded();
        winner.wonPot();
    }

    public void setPot(int blind){
        pot=blind*2;
        playerOne.setChips(playerOne.getChips()-blind);
        playerTwo.setChips(playerTwo.getChips()-blind);
    }

    public int turnDecision(int base, Player player, Player opponent,boolean isReraise,int score, int curPot){
        printState();
        int raise = player.raiseDecision(base,isReraise,score,curPot);
        if(raise==-1){
            folded(player,opponent);
            return -1;
        }
        if(raise>opponent.getChips()){
            raise=opponent.getChips();
        }
        if(base==raise){
            base-=raise;
        }
        pot+=base + raise;
        player.setChips(player.getChips()-raise-base);
        System.out.println("SUbtracted "+base+" "+raise);
        if(raise==0 && base!=0){
            return -2;
        }
        return raise;
    }
    public boolean makeTurns(){
        int base = 0;
        boolean isReraise=false;
        int scoreOne = handEvaluator.evaluateHand(playerOne.getHand(), board);
        int scoreTwo = handEvaluator.evaluateHand(playerTwo.getHand(), board);
        while(true){
            //Player one chooses to raise,call or fold
            int raise = turnDecision(base,playerOne,playerTwo,isReraise,scoreOne,pot);
            if(raise==-1)//Folded
                return false;
            if(raise==-2)//Called a reraise, don't give next player another turn
                return true;

            //Player two responds by calling, reraising or folding
            int call = turnDecision(raise,playerTwo,playerOne,isReraise,scoreTwo,pot);
            if(call==-1)
                return false;
            if(call==-2)
                return true;

            printState();
            //If PlayerTwo reraised, action passes to PlayerOne again
            if(call>raise){
                base=call-raise;
                isReraise=true;
            }
            else{
                return true;
            }
        }
    }

    public void playRound(){
        giveHands();
        if(!makeTurns()){
            return;
        }
        flop();
        for(int i=0;i<3;i++){
            board.add(deck.revealCard());
            if(!makeTurns()){
                return;
            }
        }
        printState();
        int scoreOne = handEvaluator.evaluateHand(playerOne.getHand(), board);
        int scoreTwo = handEvaluator.evaluateHand(playerTwo.getHand(), board);
        if(scoreOne==scoreTwo){
            int winner = 0;
            if(scoreOne<400){
                winner = handEvaluator.checkKicker(5,playerOne.getHand(),playerTwo.getHand(),board);
            }
            if(scoreOne<2500){
                winner = handEvaluator.checkKicker(4,playerOne.getHand(),playerTwo.getHand(),board);
            }
            else if(scoreOne<4000){
                winner = handEvaluator.checkKicker(3,playerOne.getHand(),playerTwo.getHand(),board);
            }
            if(winner==1){
                scoreOne+=1;
            }
            else if(winner==2){
                scoreTwo+=1;
            }
        }
        playerOne.showdown(playerTwo,scoreOne,scoreTwo);
        playerTwo.showdown(playerOne,scoreTwo,scoreOne);
        if(scoreOne>scoreTwo){
            playerOne.setChips(playerOne.getChips()+pot);
        }
        else if(scoreOne==scoreTwo){
            playerOne.setChips(playerOne.getChips()+pot/2);
            playerTwo.setChips(playerTwo.getChips()+pot/2);
        }
        else{
            playerTwo.setChips(playerTwo.getChips()+pot);
        }
    }

    public void roundSetup(){
        printState();
        blind += 5;
        pot = 0;
        board = new ArrayList<Card>();
        int maxBlind = Math.min(playerOne.getChips(),playerTwo.getChips());
        if (maxBlind>blind){
            setPot(blind);
        }
        else{
            setPot(maxBlind);
        }
    }
    // Returns the winning player
    public Player startGame(){
        while(true){
            roundSetup();
            playRound();

            if(playerOne.getChips()<=0){
                return playerTwo;
            }
            if(playerTwo.getChips()<=0){
                return playerOne;
            }
        }
    }
    public void giveHands(){
        deck.reshuffle();
        playerOne.clearHand();
        playerTwo.clearHand();
        for(int x=0;x<2;x++){
            playerOne.giveCard(deck.revealCard());
            playerTwo.giveCard(deck.revealCard());
        }
    }
    public void flop(){
        for(int x=0;x<2;x++){
            board.add(deck.revealCard());
        }
    }
    public void printState(){
        for(int x=0;x<20;x++){
            System.out.println("\n");
        }
        System.out.println(playerOne.getName() + ", "+playerOne.getChips()+" chips\n---\nBoard:");
        for (Card c:board){
            System.out.print(c.toString());
        }
        System.out.println("\nPot: "+pot+" chips, Blind: "+blind+" chips\n");
        System.out.println(playerTwo.getName() + ", "+playerTwo.getChips()+" chips \n---");
    }
    private void setPlayers( Player one, Player two){ playerOne=one;playerTwo = two;}
}
