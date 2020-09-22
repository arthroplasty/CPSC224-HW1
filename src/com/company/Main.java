import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStream;

class Main {
    private static final int DICE_IN_PLAY = 5;

    public static void main(String[] args) {
        int[] hand;
        hand = new int [DICE_IN_PLAY];
        do {
            getHand(hand, DICE_IN_PLAY);
            scoreHand(hand, DICE_IN_PLAY);
        } while(isPlayAgain());

    }

    public static void getHand(int [] hand, int numberOfDies){
        int [] keep = new int []{0,0,0,0,0};
        int turn = 1;
        while(turn < 4 && !(isKeep(keep))){
            for(int dieNum = 0; dieNum < numberOfDies; dieNum++){
                if(keep[dieNum] != 1)
                    hand[dieNum] = rollDie();
            }
            print("Your roll was:");
            for(int dieNum = 0; dieNum < numberOfDies; dieNum++){
                System.out.print(hand[dieNum]);
            }
            printNewLines(1);
            if(turn < 3){
                Scanner sc = new Scanner(System.in);
                String rerollString = "";
                for(int i = 0; i < DICE_IN_PLAY; i++){
                    keep[i] = 1;
                }
                do{
                    print("Enter the die/dice to roll.. (e.g. Enter 123 to reroll dice 1, 2, and 3. Enter 0 to NOT reroll)");
                    rerollString = sc.nextLine();
                } while (rerollString.length() > 5 || !(isValidReroll(rerollString)));
                rerollHand(rerollString, keep);
                //sc.close();
            }
            turn++;

        }

    }

    public static void scoreHand(int [] hand, int numberOfDies){
        sortArray(hand);
        print("Here is your sorted hand: ");
        for(int i = 0; i < numberOfDies; i++){
            System.out.print(hand[i]);
        }
        printNewLines(1);
        for(int i = 1; i <= 6; i++){
            int count = 0;
            for(int j = 0; j < 5; j++){
                if(hand[j] == i)
                    count++;
            }
            print("Score ");
            System.out.print(i * count);
            print(" on the ");
            System.out.print(i);
            print(" line");
            printNewLines(1);
        }
        if(maxOfAKindFound(hand) >= 3){
            print("Score ");
            System.out.print(totalAllDice(hand));
            print(" on the 3 of a Kind line");
        } else {
            print("Score 0 on the 3 of a Kind line");
            printNewLines(1);
        }
        if(maxOfAKindFound(hand) >= 4){
            print("Score ");
            System.out.print(totalAllDice(hand));
            print(" on the 4 of a Kind line");
            printNewLines(1);
        } else {
            print("Score 0 on the 4 of a Kind line");
            printNewLines(1);
        }
        if(fullHouseFound(hand)){
            print("Score 25 on the Full House line");
            printNewLines(1);
        } else {
            print("Score 0 on the Full House line");
            printNewLines(1);
        }
        if(maxStraightRound(hand) >= 4){
            print("Score 30 on the Small Straight line");
            printNewLines(1);
        } else {
            print("Score 0 on the Small Straight line");
            printNewLines(1);
        }
        if(maxStraightRound(hand) >= 5){
            print("Score 40 on the Large Straight line");
            printNewLines(1);
        } else {
            print("Score 0 on the Large Straight line");
            printNewLines(1);
        }
        if(maxOfAKindFound(hand) >= 5){
            print("Score 50 on the Yahtzee line");
            printNewLines(1);
        }
        print("Score ");
        System.out.print(totalAllDice(hand));
        print(" on the Chance line");
        printNewLines(2);
    }

    public static boolean isValidReroll(String reroll){
        for(int i = 0; i < reroll.length(); i++){
            if(reroll.charAt(i) != '0' && reroll.charAt(i) != '1' && reroll.charAt(i) != '2' && reroll.charAt(i) != '3' &&
                    reroll.charAt(i) != '4' && reroll.charAt(i) != '5'){
                return false;
            }
        }
        return true;
    }

    public static void rerollHand(String theDice, int [] keep){
        printNewLines(1);
        print(theDice);
        if(theDice.charAt(0) == '0'){
            for(int i = 0; i < DICE_IN_PLAY; ++i){
                keep[i] = 1;
            }
        } else {
            for(int i = 0; i < theDice.length(); ++i){
                if(theDice.charAt(i) == '1'){
                    keep[0] = 0;
                } else if(theDice.charAt(i) == '2'){
                    keep[1] = 0;
                } else if(theDice.charAt(i) == '3'){
                    keep[2] = 0;
                } else if(theDice.charAt(i) == '4'){
                    keep[3] = 0;
                } else if(theDice.charAt(i) == '5'){
                    keep[4] = 0;
                }
            }
        }
    }

    public static boolean isKeep(int [] keep){
        for(int i = 0; i < keep.length; i++){
            if(keep[i] == 0)
                return false;
        }
        return true;
    }

    public static boolean isPlayAgain(){
        print("Play Again? (y / n )");
        printNewLines(1);
        Scanner sc = new Scanner(System.in);
        char userInput = sc.next().charAt(0);
        while(userInput != 'y' && userInput != 'n'){
            print("Invalid Input, enter y for yes, n for no");
            userInput = sc.next().charAt(0);
        }
        sc.close();
        if(userInput == 'y')
            return true;
        return false;

    }

    public static int rollDie(){
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        return roll;
    }

    public static int totalAllDice(int hand[]){
        int total = 0;
        for(int i = 0; i < DICE_IN_PLAY; i++){
            total += hand[i];
        }
        return total;
    }

    public static int maxOfAKindFound(int [] hand){
        int maxCount = 0;
        int currentCount;
        for(int dieValue = 1; dieValue <=6; dieValue++){
            currentCount = 0;
            for(int diePosition = 0; diePosition < 5; diePosition++){
                if(hand[diePosition] == dieValue)
                    currentCount++;
            }
            if(currentCount > maxCount)
                maxCount = currentCount;
        }
        return maxCount;
    }

    public static void sortArray(int [] array){
        Arrays.sort(array);
    }

    public static int maxStraightRound(int [] hand){
        int maxLength = 1;
        int curLength = 1;
        for(int i = 0; i < 4; i++){
            if(hand[i] + 1 == hand[i+1])
                curLength++;
            else if(hand[i] + 1 < hand[i+1])
                curLength = 1;
            if (curLength > maxLength)
                maxLength = curLength;
        }
        return maxLength;
    }

    public static boolean fullHouseFound(int [] hand){
        boolean foundFH = false;
        boolean found3k = false;
        boolean found2k = false;
        int currentCount;
        for(int dieValue = 1; dieValue <= 6; dieValue++){
            currentCount = 0;
            for(int diePosition = 0; diePosition < 5; diePosition++){
                if(hand[diePosition] == dieValue)
                    currentCount++;
            }
            if(currentCount == 2)
                found2k = true;
            if(currentCount == 3)
                found3k = true;
        }
        if(found2k && found3k)
            foundFH = true;
        return foundFH;

    }

    public static void print(String theLine){
        System.out.print(theLine);
    }

    public static void printNewLines(int numLines){
        for(int i = 0; i < numLines; i++){
            System.out.println();
        }
    }
}