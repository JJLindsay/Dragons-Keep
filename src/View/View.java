package View;

import Controller.Game;

import java.util.Scanner;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/23/2014
 *
 * This class represents the View in Model View Controller
 *
 * Purpose: To pose the user a question or to receive an answer. Also, it restricts the user's access to the bare min
 */
public class View
{
    private String display = "Error";  //set to 'error' so opening sequence runs the first time.
    private Scanner keyboard = new Scanner(System.in);
    private String response;
    private boolean startingUp = true;

    public void gameMenus()
    {
        if (display.contains("Error"))
            openingSequence();

        if (display.contains("quit")) //view knows the user is seeing the game menu if 'quit' is there
            mainMenu();

        if (display.contains("<"))
            moveLocationMenu();

        if (display.contains("Are you"))
            enteringRoomMenu();

        if(display.contains("escape"))
            battleMenu();

        if (display.contains("puzzle"))
            puzzleMenu();

        if (display.contains("closing?"))  //view knows the user is seeing the quit-game menu if 'closing' is there
            quitGameMenu();
    }

    private void openingSequence()
    {
        if(startingUp)
        {
            display = Game.gameIntro();  //enter 1 or 2 for login/create account
            System.out.println(display);
            response = keyboard.nextLine();
        }

        display = Game.playGame(response);   //entering 1 or 2 to login or create, receive login/create
        System.out.println(display);  //login, create, or error --> gameIntro()
        startingUp = false;
        //enter the user name to create or login
        response = keyboard.nextLine();  //enters name

        if (display.contains("Error"))
            gameMenus();


        display = Game.loginCreate(response);
        System.out.println(display);  //loaded-->enteredRoomMessage, created-->enteredRoomMessage, or error--> gameIntro()
        response = keyboard.nextLine();  //enters name

        if (display.contains("ERROR!"))
            gameMenus();

        gameMenus();
    }

    private void mainMenu()
    {
        display = Game.gameMenu(response);
        System.out.println(display);  //gameMenuMessage
        response = keyboard.nextLine();

        gameMenus();
    }
    private void moveLocationMenu()
    {
        display = Game.changeRooms(response);  //calls enteredRoom for room description
        System.out.println(display);
        response = keyboard.nextLine();  //user responds

        gameMenus();
    }

    private void enteringRoomMenu()
    {
        display = Game.enteredRoom(response);  //allows room interaction:: enter room response, initially rucksack, received changedRoomsMessage()
        System.out.println(display);
        response = keyboard.nextLine();  //user responds

        gameMenus();
    }

    private void battleMenu()
    {
        display = Game.battle(response);
        System.out.println(display);  //SOP inventory --> battleMenu
        response = keyboard.nextLine();

        if (display.contains("keep!"))
        {
            openingSequence();
        }

        gameMenus();
    }

    private void puzzleMenu()
    {
        display = Game.solvePuzzle(response);
        System.out.println(display);  //SOP congrats on getting the puzzle right, pursue the monster?, collect the item?, or move on?
        response = keyboard.nextLine();

        gameMenus();
    }

    private void quitGameMenu()
    {
        display = Game.quitGame(response);
        System.out.println(display);
        response = keyboard.nextLine();  //user responds

        gameMenus();
    }
}
