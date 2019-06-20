
import java.util.Scanner;

/**
 * KIT101 Assignment 2
 *
 * WereWolfenstein 2D -- Organiser Class
 *
 * @author Sage Stainsby 390889
 * @version final
 *
 * Stage Reached: 2
 */
public class WereWolfenstein2D {

    // Final instance variables
    private final int HITSNEEDED = 3; //total hits needed to subdue the werewolf

    // Non-final instance variables
    private Difficulty difficulty; //used for defining the games difficulty
    private String input; //user input 
    private int untilDawn; //actions until dawn arrives
    private int villageCount;//how many villagers remain currently 
    private int move;//used for holding the area the player wants to move into
    private int hitCount;//the amount of times the player has hit the Werewolf
    private int silverBullets;//the number of bullets the player has remaining 
    private Boolean bitten = false; //if the player is bitten or not, false by defualt
    private Boolean villageNear; //boolean for isVillageNear 
    private int[] moveHistory = new int[10]; //Integer array of 10 spaces to hold the 10 most recent moves of the player 
    private int moveCount = 0;//to be used with moveHistory, to countthe number of times the player has moved location
    private int currentPos;//Used to hold the current position of the player 

    private boolean tracing;
    Scanner sc = new Scanner(System.in); //instantiate scanner class
    GameWorld game1 = new GameWorld(true); //Create a new GameWorld called game1
    Result outcome; //instantiates new result called outcome

    public WereWolfenstein2D() {

        explain();
        System.out.println("\n\nWould you like to play WereWolfenstein 2D (y/n)? ");
        input = sc.next();
        input.toLowerCase();

        while (input.equals("n")) {

        }

    }

 /**
     * Displays the goal and title of the game
     */
    public void explain() {

        System.out.println("WereWolfenstein 2D");
        System.out.println("================== \n");
        System.out.println("You have to track down and destroy a supernatural wolf that is terrorizing your village.");

    }
 /**     
     * Used to check if Dawn has arrived and if it has to reset untilDawn to its original value
     */
    public void dawnChecker() {	  	   	

        if (untilDawn == 0) { //if dawn is reached 

            System.out.println("Dawn arrives, another villager has left during the night.\n");
            villageCount--;
            untilDawn = game1.NIGHT_LENGTH;

        }

    }
 /**     
     * Asks the user if they want to play another game
     */
    public void playAgainPrompt() { 

        System.out.println("Would you like to play WereWolfenstein 2D again (y/n)? ");
        input = sc.next();
        input = input.toLowerCase(); //converts input to lower case

        if (input.equals("y")) { //if the user selects "y" for "yes" 

            play();

        } else {

            moveCounterDisp();

            while (bitten = true) {

            }
        }
    }
 /**     
     *Sets up moveHistory array and increments move count 
     */
    public void moveCounter() { 

        currentPos = game1.getCurrentArea(); //gets player current position and puts it in the currentPos variable 
        moveHistory[moveCount] = currentPos;
        moveCount++; //increments moveCount by 1 
        System.out.println(moveHistory[0] + "\n");

    }
 /**     
     *Used to display moveHistory  
     */
    public void moveCounterDisp() {

        System.out.print(moveCount + " moves: ");

        for (int i = 0; i < 9; i++) { //loops printing array 10 times

            System.out.print(moveHistory[i] + ",");

        }

    }
/**     
     *Checks to see if the game is over, asks if the user wants to play again
     */
    public void gameOverCheck() { 

        if (villageCount == 0) {

            playAgainPrompt();

        }

        if (silverBullets == 0) {

            playAgainPrompt();

        }

    }
/**     
     *Main method handles playing of the game, refers to previous methods when tasks are performed multiple times
     */
    public void play() {

        System.out.println("\nSelect game difficulty EASY, NORMAL, HARD");

        input = sc.next();
        input.toLowerCase();

        if (input.equals("easy") || input.equals("normal") || input.equals("hard")) { //if the user has selected a valid option the game can be player, if not they are prompted to select again

            difficulty = Difficulty.valueOf(input.toUpperCase());

            System.out.println("Your selected difficulty settings:");
            String diffMsg = "Settings: " + difficulty; //displays information on the difficulty the player has chosen 
            System.out.println(diffMsg + "\n");
            input = ("");

            game1.newGame(difficulty); //calls the newGame method to start a new game of the chosen difficulty
            untilDawn = game1.NIGHT_LENGTH;

            silverBullets = difficulty.getSilverBulletCount(); //puts the number of silver bullets for the chosen difficulty in a local variable

            villageCount = game1.getVillagePopulation();
            gameOverCheck(); //checks to see if the conditions that mean the game is over have been reached

            while (untilDawn > -1) { 

                System.out.println("There are " + untilDawn + " actions until dawn arrives");
                System.out.println("At last count there were " + villageCount + " villagers remaining");

                villageNear = game1.isVillageNear(); //checks if village is near and puts that value into the local boolean variable

                if (villageNear == true) { //if the village is naer displays a message

                    System.out.println("You can hear sounds of village life nearby.");

                }

                if (bitten == true) { //if the player has been bitten displays a message 

                    System.out.println("\nYour bite has not been healed yet so you are not able to pay attention to your surroundings.");

                }

                System.out.println("\nYou are in area " + game1.getCurrentArea() + "."); //displays player information 
                System.out.println("To your north is " + game1.nextArea('n') + ", to your east is " + game1.nextArea('e') + ", and to your west is " + game1.nextArea('w') + ".");
                System.out.println("You have hit your target " + hitCount + "/" + HITSNEEDED + " times and have " + silverBullets + " bullets remaining.");

                outcome = game1.werewolfNear(); //checks to see if the werewolf is near

                if (bitten == false) { //check if bitten, if not display a message if the werewolf is near.
                    if (outcome == Result.NEAR) {
                        System.out.println("\nYou can smell a wolf nearby.");
                    }
                }

                System.out.println("\nPlease choose from (W)alk, (S)hoot, (R)eset, or (Q)uit: "); //prompts the player to choose an option 
                input = sc.next();
                input = input.toLowerCase();

                while (input.equals("w")) { //if the player chooses to walk

                    System.out.println("Which area would you like to walk into? ");
                    move = sc.nextInt();
                    outcome = game1.tryWalk(move); //outcome becomes the return on the tryWalk method 

                    if (outcome != Result.IMPOSSIBLE) {

                        if (outcome == Result.SUCCESS) {

                            System.out.println("\nWalk successful but your target is still on the loose.");
                            untilDawn = untilDawn - 1;
                            dawnChecker();
                            moveCounter();

                        }

                        if (outcome == Result.VILLAGE) {

                            System.out.println("\nYou walk into the village. They tend to any wounds you have sufferred while away.");
                            bitten = false;
                            untilDawn = untilDawn - 1;
                            dawnChecker();
                            moveCounter();

                        }

                        if (outcome == Result.NEAR) {

                            System.out.println("\nYou can smell a wolf nearby.");
                            untilDawn = untilDawn - 1;
                            dawnChecker();
                            moveCounter();

                        }
                        if (outcome == Result.BITTEN) {

                            if (bitten == true) {

                                playAgainPrompt();

                            } else {

                                System.out.println("\nYou got too close to the target, were bitten and are now delirious.\n");

                                untilDawn = untilDawn - 1;
                                bitten = true;
                                dawnChecker();

                            }

                        }

                        input = ("");

                    }

                }

                while (input.equals("s")) { //the player chooses to "shoot"

                    System.out.println("Which area would you like to shoot into? ");
                    move = sc.nextInt();
                    outcome = game1.shoot(move); //outcome becomes the enum return of the shoot method
                    silverBullets--;

                    if (outcome != Result.IMPOSSIBLE) {

                        if (outcome == Result.SUCCESS) {

                            System.out.println("\nYou hit the target!");
                            hitCount++;
                            untilDawn = untilDawn - 1;
                            dawnChecker();

                        }

                        if (outcome == Result.VILLAGE) {

                            System.out.println("Oops, you just shot wildly into the village! One more villager decides to leave...");
                            villageCount--;
                            untilDawn = untilDawn - 1;
                            dawnChecker();
                        }

                        if (outcome == Result.CAPTURED) {

                            System.out.println("You hit the target! And you're able to capture it. Well done!");
                            playAgainPrompt();

                        }
                        if (outcome == Result.FAILURE) {

                            System.out.println("You fire a silver bullet but hit nothing but thin air.");
                            untilDawn = untilDawn - 1;
                            dawnChecker();

                        }

                    } else {

                        System.out.println("Sorry, that's not possible from your current location.");

                    }

                    input = "";
                }

                if (input.equals("r")) { //if the user chooses to reset the game, all variables reset execpt for the games difficulty

                    game1.reset();
                    untilDawn = game1.NIGHT_LENGTH;
                    silverBullets = difficulty.getSilverBulletCount();
                    villageCount = game1.getVillagePopulation();
                    bitten = false;

                }

                if (input.equals("q")) { //if the user chooses to quit the game, they are presented ith the play again prompt, and some text 

                    System.out.println("Quit selected. ");
                    playAgainPrompt();

                }
            }

        } else {

            System.out.println("please enter a valid option");
            play();
        }
    }

    public void setTracing(boolean onOff) {
       

    }

    public void trace(String message) {
        if (tracing) {
            System.out.println("WereWolfenstein2D: " + message);
        }
    }

}
