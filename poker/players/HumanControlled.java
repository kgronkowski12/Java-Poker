package poker.players;
import poker.shoe.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanControlled extends Player {
    Scanner scanner = new Scanner(System.in);

    public HumanControlled(String name, int chips){
        super(name, chips);
        scanner = new Scanner(System.in);
    }
    public void awaitCommand(){
        System.out.println("Press 1 to continue...");
        scanner.nextInt();
    }
    public int printMenuRaise(){
        System.out.println("""
        
        What do you want to do?
        1. Check
        2. Raise
        3. Fold
        """);
        return scanner.nextInt();

    }
    public int printMenuCall(){
        System.out.println("""

        What do you want to do?
        1. Call
        2. Reraise
        3. Fold
        """);
        return scanner.nextInt();

    }
    public int raiseChoice(int choice){ //change to simple while loop
        while (true){
            switch(choice){
                case 1:
                    return 0;
                case 2:
                    int value = reRaise(0);
                    if(value>0){
                        return value;
                    }
                    else{
                        choice = printMenuRaise();
                        break;
                    }
                case 3:
                    return -1;
                default:
                    System.out.println("Not a correct option!\nMake sure your input is a number.");
                    choice = printMenuRaise();
            }
        }
    }
    public int callChoice(int raise, boolean isReraise, int score, int pot){
        if(!isReraise){
            System.out.println("Your opponent raised by "+raise+" chips!");
        }
        else{
            System.out.println("Your opponent reraised your bet by "+raise+" chips!");
        }
        int choice = printMenuCall();
        while(true){
            switch(choice){
                case 1:
                    return raise;
                case 2:
                    int value = reRaise(raise);
                    if(value>0){
                        return value;
                    }
                    else{
                        choice = printMenuCall();
                        break;
                    }
                case 3:
                    return -1;
                default:
                    System.out.println("Not a correct option!\nMake sure your input is a number.");
                    choice = printMenuCall();
            }
        }
    }
    public int reRaise(int raise){
        System.out.println("How much do you want to raise? (max : "+(chips-raise)+")");
        System.out.println("Type 0 to go back");
        int choice = scanner.nextInt();
        while(true){
            if(choice<0){
                System.out.println("Can't be negative!");
                choice = scanner.nextInt();
            }
            else if(choice>chips-raise){
                System.out.println("Can't be over your chip limit ("+(chips-raise)+")!");
                choice = scanner.nextInt();
            }
            else{
                return choice;
            }
        }
    }
    public int raiseDecision(int raise,boolean isReraise,int score, int pot){
        System.out.println("Your hand:");
        printHand();
        if(raise==0){
            int choice = printMenuRaise();
            return raiseChoice(choice);
        }
        return callChoice(raise,isReraise,0,0);
    }
    public void wonPot(){
        System.out.println("Your opponent folded!");
        System.out.println("You've won the pot.");
        awaitCommand();
    }
    public void folded(){
        System.out.println("You folded and lost the pot!");
        awaitCommand();
    }

    public void showdown(Player opponent, int yourScore,int opponentScore){
        System.out.println("Showdown!\nYour hand:");
        printHand();
        System.out.println("Your opponents hand:");
        opponent.printHand();
        if(yourScore>opponentScore){
            System.out.println("You won the pot!");
        }
        if(yourScore<opponentScore){
            System.out.println("You lost the pot!");
        }
        if(yourScore==opponentScore){
            System.out.println("You split the pot!");
        }
        awaitCommand();
    }
}
