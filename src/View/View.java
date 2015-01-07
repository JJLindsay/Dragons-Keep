package view;

import controller.GameInteractions;
import controller.*;
import controller.RoomInteractions;
import controller.itemsAndPuzzle.Puzzle;

import java.util.Scanner;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/23/2014
 *
 * This class represents the view in model-view-controller
 *
 * Purpose: To pose the user a question or to receive an answer. Also, it restricts the user's access to the bare minimum
 */
public class View
{
    //instance variables
    private String display = "Error";  //set to 'error' so opening sequence runs the first time.
    private Scanner keyboard = new Scanner(System.in);
    private String response;
    private boolean startingUp = true;

    //static instance variable
    private static MenusAndMessages menusAndMessages;  //original
    private static GameInteractions gameInteractions;  //original
    private static RoomInteractions roomInteractions;
    private static AccountFunctions accountFunctions;  //original
    private static String controllerDisplay;

    /**All the in-game menus displayed to the user
     */
    public void gameMenus()
    {
        if (display.contains("Error"))
            openingSequence();

        if (display.contains("quit")) //view knows the user is seeing the game menu if 'quit' is there
            mainMenu();

        if (display.contains("Are you"))
            enteringRoomMenu();

        if(display.contains("escape"))
            battleMenu();

        if (display.contains("puzzle"))
            puzzleMenu();

        if (display.contains("closing?"))  //view knows the user is seeing the quit-game menu if 'closing' is there
            quitGameMenu();

        if (display.contains("<"))
            moveLocationMenu();
    }

    /**Passes the user's response to titleScreenInput(), displays the results, and accepts the user's reply
     * If there are errors
     */
    private void openingSequence()
    {
        if(startingUp)
        {
            menusAndMessages = new MenusAndMessages(); //fine but make these static. May share these with other classes
            gameInteractions = new GameInteractions();
            accountFunctions = new AccountFunctions();
            display = menusAndMessages.titleScreen();  //enter 1 or 2 for login/create account
            showDisplayAndRespond();
        }

        display = gameInteractions.titleScreenInput(response);   //entering 1 or 2 to login or create, receive login/create
        showDisplayAndRespond();
        startingUp = false;

        if (display.contains("Error"))
            gameMenus();

        display = accountFunctions.loginCreate(response);
        showDisplayAndRespond();

        gameMenus();
    }

    /**Passes the user's response to mainMenu(), displays the results, accepts the user's reply and calls gameMenus()
     */
    private void mainMenu()
    {
        display = gameInteractions.mainMenu(response);
        showDisplayAndRespond();

        gameMenus();
    }

    /**Passes the user's response to changingRooms(), displays the results, accepts the user's reply and calls gameMenus()
     */
    private void moveLocationMenu()
    {
        roomInteractions = new RoomInteractions();  //Do only one once

        display = roomInteractions.changingRooms(response);  //calls roomInteractions for room description
        showDisplayAndRespond();

        gameMenus();
    }

    /**Passes the user's response to roomInteractions(), displays the results, accepts the user's reply and calls gameMenus()
     */
    private void enteringRoomMenu()
    {
        roomInteractions = new RoomInteractions();  //Do only one once

        display = roomInteractions.roomInteractions(response);  //allows room interaction:: enter room response, initially rucksack, received changedRoomsMessage()
        showDisplayAndRespond();

        gameMenus();
    }

    /**Passes the user's response to battle(), displays the results, and accepts the user's reply.
     * If the user died, openingSequence() is called otherwise gameMenus() is called
     */
    private void battleMenu()
    {
        display = gameInteractions.battle(response);
        showDisplayAndRespond();

        if (display.contains("keep!"))
        {
            openingSequence();
        }

        gameMenus();
    }

    /**Passes the user's response to solvePuzzle(), displays the results, accepts the user's reply and calls gameMenus()
     */
    private void puzzleMenu()
    {
        display = new Puzzle().solvePuzzle(response);
        showDisplayAndRespond();

        gameMenus();
    }

    /**Passes the user's response to quitGame(), displays the results, accepts the user's reply and calls gameMenus()
     */
    private void quitGameMenu()
    {
        display = gameInteractions.quitGame(response);
        showDisplayAndRespond();

        gameMenus();
    }

    /**
     * Displays the a message and accepts the user's response
     */
    private void showDisplayAndRespond()
    {
        if (controllerDisplay == null)
        {
            System.out.println(display);
        }
        else
        {
            System.out.println(controllerDisplay);
            System.out.println(display);
            controllerDisplay = null;
        }
        response = keyboard.nextLine();  //user responds
    }

    /**Allows Inventory methods in the controller package with return types other than string to send a message to view
     * @param controllerDisplay The message to be displayed
     */
    public void setControllerDisplay(String controllerDisplay)
    {
        View.controllerDisplay = controllerDisplay;
    }

    public MenusAndMessages getMenusAndMessages()
    {
        return menusAndMessages;
    }

    public void setMenusAndMessages(MenusAndMessages menusAndMessages)
    {
        View.menusAndMessages = menusAndMessages;
    }
}