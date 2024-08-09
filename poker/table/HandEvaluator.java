package poker.table;
import poker.shoe.*;
import poker.players.*;
import java.util.*;

public class HandEvaluator {
    private Player playerOne;
    private Player playerTwo;
    private Deck deck;
    private ArrayList<Card> board;


    public int evaluateHand(ArrayList<Card> hand, ArrayList<Card> board){
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(board);
        allCards.addAll(hand);
        Collections.sort(allCards);
        int score = 0;

        //Checks for straight Flush
        score = hasStraight(allCards, true);
        if(score>0){
            return score;
        }

        score = hasFourofKind(allCards);
        if(score>0){
            return score;
        }

        score = hasFullHouse(allCards);
        if(score>0){
            return score;
        }

        score = hasFlush(allCards);
        if(score>0){
            return score;
        }

        score = hasStraight(allCards, false);
        if(score>0){
            return score;
        }

        score = hasTrips(allCards);
        if(score>0){
            return score;
        }

        score = hasTwoPair(allCards);
        if(score>0){
            return score;
        }

        score = hasPair(allCards);
        if(score>0){
            return score;
        }

        //Highest card
        score = 15*allCards.get(0).getRank();
        return score;
    }
    private int hasFourofKind(ArrayList<Card> allCards){
        for(int i=0;i<allCards.size()-3;i++){
            for(int x=0;x<3;x++){
                if(allCards.get(i+x).getRank()!=allCards.get(i+x+1).getRank()){
                    break;
                }
                else if (x==2){
                    return 8000+15*allCards.get(i).getRank();
                }
            }
        }
        return 0;
    }
    private int hasFlush(ArrayList<Card> allCards){
        for(int i=0;i<allCards.size()-4;i++){
            for(int x=0;x<4;x++){
                if(allCards.get(i+x).getSuit()!=allCards.get(i+x+1).getSuit()){
                    break;
                }
                else if (x==3){
                    return 5000+15*allCards.get(i).getRank();
                }
            }
        }
        return 0;
    }
    private int hasStraight(ArrayList<Card> allCards,boolean Flush){
        for(int i=0;i<allCards.size()-4;i++){
            boolean sameSuit=true;
            for(int x=0;x<4;x++){
                if(allCards.get(i+x).getSuit()!=allCards.get(i+x+1).getSuit()){
                    sameSuit=false;
                }
                if(allCards.get(i+x).getRank()!=allCards.get(i+x+1).getRank()+1){
                    break;
                }
                else if (x==3){
                    if (sameSuit){
                        return 10000+30*allCards.get(i).getRank();
                    }
                    else if(!Flush){
                        return 4200+15*allCards.get(i).getRank();
                    }
                }
            }
        }
        return 0;
    }
    private int hasTwoPair(ArrayList<Card> allCards){
        int times = 0;
        int biggest = 0;
        for(int i=0;i<allCards.size()-1;i++){
            if (allCards.get(i).getRank()==allCards.get(i+1).getRank()){
                times+=1;
                if(allCards.get(i).getRank()>biggest){
                    biggest = allCards.get(i).getRank();
                }
            }
            if(times>=2){
                return 2500+biggest*15;
            }
        }
        return 0;
    }

    private int hasPair(ArrayList<Card> allCards){
        for(int i=0;i<allCards.size()-1;i++){
            if (allCards.get(i).getRank()==allCards.get(i+1).getRank()){
                return 400+30*allCards.get(i+1).getRank();
            }
        }
        return 0;
    }

    private int hasTrips(ArrayList<Card> allCards){
        for(int i=0;i<allCards.size()-2;i++){
            if (allCards.get(i).getRank()==allCards.get(i+1).getRank()&&allCards.get(i).getRank()==allCards.get(i+2).getRank()){
                return 3500+15*allCards.get(i+1).getRank();
            }
        }
        return 0;
    }
    private int hasFullHouse(ArrayList<Card> allCards){
        int score = hasTrips(allCards);
        if(score==0){
            return 0;
        }
        ArrayList<Card> board = tripsRemover(allCards);
        score+=2000;
        int addon = hasPair(allCards);
        if(addon>0){
            return score+addon;
        }
        return 0;
    }

    public int checkKicker(int howMany, ArrayList<Card> handOne, ArrayList<Card> handTwo, ArrayList<Card> board){
        ArrayList<Card> setOne = new ArrayList<Card>();
        setOne.addAll(board);
        setOne.addAll(handOne);
        ArrayList<Card> setTwo = new ArrayList<Card>();
        setTwo.addAll(board);
        setTwo.addAll(handTwo);
        Collections.sort(setOne);
        Collections.sort(setTwo);
        for(int x=0;x<setOne.size()-1;x++){
            if(setOne.get(x).getRank()>setTwo.get(x).getRank()){
                return 1;
            }
            if(setOne.get(x).getRank()<setTwo.get(x).getRank()){
                return 2;
            }
            //if card is part of a pair or trips doesn't count it as a kicker.
            if(setOne.get(x).getRank()!=setOne.get(x+1).getRank()){
                howMany-=1;
                if(howMany<=0){
                    return 0;
                }
            }
        }
        return 0;
    }
    
    private ArrayList<Card> tripsRemover(ArrayList<Card> allCards){
        ArrayList<Card> changed = new ArrayList<Card>();
        changed.addAll(allCards);
        for(int i=0;i<allCards.size()-2;i++){
            if (allCards.get(i).getRank()==allCards.get(i+1).getRank()&&allCards.get(i).getRank()==allCards.get(i+2).getRank()){
                changed.remove(i+2);
                changed.remove(i+1);
                changed.remove(i);
                return changed;
            }
        }
        return null;
    }
}