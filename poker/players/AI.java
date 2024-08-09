package poker.players;
import poker.shoe.*;
import java.util.ArrayList;
import java.util.Random;
public class AI extends Player {
    //More agressive bets (in %)
    private int agressiveness;
    //Chance to bluff (0-100)
    private int bluffChance;
    //Chance to check with a good hand (0-100)
    private int slowRoll;
    //Lowers likelihood of challenging reraises (0-100)
    private int caution;
    //BetSize
    private int betsize;
    private int heroCall;
    //If pot big, raise small then call. Bigger = less likely to fold when already invested
    private int potModifier;
    private int badHand;
    private Random random;

    public AI(String name, int chips){
        super(name,chips);
        agressiveness = 30;
        bluffChance = 15;
        slowRoll = 30;
        betsize = 50;
        heroCall = 10;
        potModifier = 50;
        badHand = 150;
        random = new Random();
    }
    public int raiseDecision(int raise,boolean isReraise, int score, int pot){
        int fairBet = score/10;
        fairBet = fairBet*(100+agressiveness)/100;
        int bluff = random.nextInt(100);
        if(bluff<=bluffChance)
            fairBet*=random.nextInt(6)+2;
        if(fairBet > pot/2+raise){
            int bet = ((fairBet- pot/2+raise)*(100+betsize)/100);
            if(bet>chips)
                bet=chips;
            return bet;
        }
        else{
            if(raise>(fairBet*100)/(100+caution)){
                if((pot-raise)*100/raise<potModifier || score<badHand){
                    int rand = random.nextInt(100);
                    int addon=0;
                    if(score<badHand)
                        addon=-heroCall+1;
                    if(score>400){
                        addon=3;
                    }
                    if(score>1300){
                        addon=5;
                    }
                    if(rand>heroCall+addon){
                        heroCall+=5;
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
    public void wonPot(){
        heroCall-=2;
    }
    public void folded(){}
    public void showdown(Player opponent, int scoreOne, int scoreTwo){ }
}