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
    public static void main(String[] args)
    {
        String display;
        Scanner keyboard = new Scanner(System.in);
        String response;

    /*
    loop:
    receive room description
    accepts user request

    head n
    */




        while (true)
        {
            //enter login in/create if it comes true move on else don't

            //loop 1
            //display enter 1 to login or 2 to create
            //send off user response
            //if not Error display request for user, then pass results to a LoginCreate(String userResponse)
            //-->LoginCreate(...) should have a if that checks a static variable set from the 1st user response
            //and depending on that variable, Create or Login will be called.
            //-->At this point there should be rooms with descriptions and etc.
            //if error displayError and repeat loop 1
            //

            //=====================Accounting for errors=========================
            display = Game.gameIntro();  //enter 1 or 2 for login/create account
            System.out.println(display);

            do //DONE
            {
                do
                {
                    response = keyboard.nextLine();
                    display = Game.playGame(response);   //entering 1 or 2 to login or create, receive login/create
                    System.out.println(display);  //enter, create, or error --> gameIntro()
                    //enter the user name to create or login
                } while (display.contains("Error"));


                response = keyboard.nextLine();  //enters name
                display = Game.loginCreate(response);
                System.out.println(display);  //loaded-->enteredRoomMessage, created-->enteredRoomMessage, or error--> gameIntro()
            }while(display.contains("ERROR!"));

            response = keyboard.nextLine();  //say what you (user) want to do
            display = Game.enteredRoom(response);  //allows room interaction:: enter room response, initially rucksack, received changedRoomsMessage()
            System.out.println(display);  //display could show puzzle riddle, battleMenu, item collected-->changeRoomsMessage(), menu/enteredRoomMessage()
            response = keyboard.nextLine();

            //where the user response is sent depends on what menu pulls up
            //-------------------------------------------------------------
            if (display.contains("Puzzle:"))  //view knows the user is seeing the puzzle riddle if 'Puzzle:' is there
            {//DONE
                do
                {
                    display = Game.solvePuzzle(response);
                    System.out.println(display);  //SOP congrats on getting the puzzle right, pursue the monster?, collect the item?, or move on?
                    response = keyboard.nextLine();
                }while(display.contains("puzzle"));
            }
            if (display.contains("escape"))  //view knows the user is seeing the battle menu if 'escape' is there
            {
                display = Game.battle(response);
                System.out.println(display);  //SOP inventory --> battleMenu
            }
            if (display.contains("found")) //view knows the user is seeing the collect and where to next menu if found is there
            {
//            System.out.println(display);  //SOP where would you like to go next?
                display = Game.changeRooms(response);
                System.out.println(display);  //SOP new room description
            }
            while(display.contains("quit")) //view knows the user is seeing the game menu if quit is there
            {
//                if (display.contains("quit")) //view knows the user is seeing the game menu if quit is there
//                {
                display = Game.gameMenu(response);
                System.out.println(display);  //gameMenuMessage
                response = keyboard.nextLine();  //user responds
//                }
            }
            if (display.charAt(0) == '<') //view knows the user is seeing the change rooms menu if '<' is there
            {
                display = Game.changeRooms(response);  //calls enteredRoom for room description
                System.out.println(display);
            }

//            response = keyboard.nextLine();
            display = Game.enteredRoom(response);  //allows room interaction:: enter room response, initially rucksack, received changedRoomsMessage()
            System.out.println(display);  //display could show puzzle riddle, battleMenu, item collected-->changeRoomsMessage(), menu/enteredRoomMessage()
            response = keyboard.nextLine();



            //----
            display = Game.changeRooms(response);  //change rooms is about direction
            System.out.println(display);  //new room description, do the puzzle, fight, collect, or move on or/and menu
            response = keyboard.nextLine();  //user responds


            display = Game.enteredRoom(response); //entered room is about description
            System.out.println(display);  //puzzle riddle, battleMenu, item collected-->changeRoomsMessage() where to next, menu/new room description
            response = keyboard.nextLine();


            //++++++++++++++++++++++++Consider what happens for puzzle, monster, item.+++++++++++++++++++++++++
            //puzzle returns puzzle message
//            display = Game.changeRooms(response); //Gets description and if there is a puzzle, monster, item
//            System.out.println(display); //Attempt the puzzle in this room?
//            response = keyboard.nextLine();   //user enters yes
//            display = Game.enteredRoom(response);
//            System.out.println(display);  //SOP the puzzle riddle
//            response = keyboard.nextLine();  //users provides correct solution

            if (display.contains("Puzzle:"))  //view knows the user is seeing the puzzle riddle if Puzzle: is there
            {
                display = Game.solvePuzzle(response);
                System.out.println(display);  //SOP congrats on getting the puzzle right, pursue the monster?, collect the item?, or move on?
            }

            //room: puzzle --> monster
//            response = keyboard.nextLine();  //user wants to pursue monster
//            display = Game.enteredRoom(response);  //allows room interaction:: get feedback on user's enter room response, initially rucksack, received changedRoomsMessage()
//            System.out.println(display);  //SOP battle message

                //repeats again and again until 1/5 things occur
//                response = keyboard.nextLine();  //user wants to see inventory

            if (display.contains("escape"))  //view knows the user is seeing the battle menu if escape is there
            {
                display = Game.battle(response);
                System.out.println(display);  //SOP inventory --> battleMenu
            }

            //the only exit out is: run away, die, defeat monster, defeat monster--> collect item, win the game,

            //if user beats monster or runs away

            if (display.contains("found")) //view knows the user is seeing the collect and where to next menu if found is there
            {
//            System.out.println(display);  //SOP where would you like to go next?
                display = Game.changeRooms(response);
                System.out.println(display);  //SOP new room description
            }

            while(display.contains("quit")) //view knows the user is seeing the game menu if quit is there
            {
//                if (display.contains("quit")) //view knows the user is seeing the game menu if quit is there
//                {
                    display = Game.gameMenu(response);
                    System.out.println(display);  //gameMenuMessage
                    response = keyboard.nextLine();  //user responds
//                }
            }
            if (display.charAt(0) == '<') //view knows the user is seeing the change rooms menu if '<' is there
            {
                display = Game.changeRooms(response);  //calls enteredRoom for room description
                System.out.println(display);
            }

            //if user dies
            System.out.println(display);  //SOP enter 1 or 2 for login/create account
            display = Game.playGame(response);

            //if user beats monster --> item appears
            System.out.println(display);  //SOP would you like to collect the item?
            response = keyboard.nextLine();  //user wants to collect item
            display = Game.enteredRoom(response);
            System.out.println(display);  //SOP items collected and what room next?

            //if user wins the game
            System.out.println(display);  //SOP you won the game, save before quiting?
            display = Game.quitGame(response);






            //monster returns battleMessage
            display = Game.changeRooms(response);  //entered a new room and gives description and if there is a puzzle, monster, item
            System.out.println(display);
            response = keyboard.nextLine();   //user enters yes to fight monster
            display = Game.enteredRoom(response);  //allows room interaction:: get feedback on user's enter room response, initially rucksack, received changedRoomsMessage()
            System.out.println(display);  //displays battle message

            //items returns what item has been collected
//            display = Game.changeRooms(response);  //sets current room and returns enteredRoomMessage(), gives description and if there is a puzzle, monster, item
//            System.out.println(display);  //SOP collect the item?
//            response = keyboard.nextLine();   //user enters yes to collect item
            display = Game.enteredRoom(response);  //allows room interaction:: get feedback on user's enter room response, initially rucksack, received changedRoomsMessage()
            System.out.println(display);

            //=====================Assuming no errors=========================




            //----------------------------------opening ----------------------
            display = Game.gameIntro();
            System.out.println(display);
            response = keyboard.nextLine();

            if (null != display && display.contains("Welcome"))
            {
                //enter 1 or 2 to login or create
                display = Game.playGame(response);
                System.out.println(display);

                if (null != display && display.matches(".*Error|over.*"))  //entered something other than 1,2
                {
                    display = Game.gameIntro();
                    //loop back up
                }

                //enter the user name to create or login
                display = Game.loginCreate(response);

                if (null != display && display.matches(".*Loading|created.*"))
                {
                    //once game is successfully created/loaded begin play
//                    Game.loadGame();
                    display = Game.enteredRoomMessage();
                    System.out.println(display);
                }

                if (null != display && display.contains("blunder")) //error from loginCreate
                {
                    display = Game.gameIntro();
                    System.out.println(display);
                }
            }

            response = Game.enteredRoom(response);


            display = Game.gameMenuMessage();  //display menu
            System.out.println(display);

            response = keyboard.nextLine(); //user enters request

            //public void quit(){
            display = Game.gameMenu(response); //enters game menu, gets back whatever message
            System.out.println(display);

            //---------------------------------Changing rooms
            if (null != display && display.charAt(0) == '<') //user entered changeRooms
            {
                response = keyboard.nextLine();
                Game.changeRooms(response);
            }
            //---------------------------------

            //------------------------Method:: e.g. GUI button
            if (null != display && display.contains("Puzzle:")) //displayed puzzle, receive user response
            {
                response = keyboard.nextLine();
                display = Game.solvePuzzle(response);
            }

            if (null != display && display.contains("puzzle again")) //displayed try puzzle again, receive user response
            {
                response = keyboard.nextLine();
                display = Game.solvePuzzle(response);
            }

            if (null != display && display.matches(".*rucksack?|puzzle?|monster?|item to.*"))
            {
                Game.enteredRoom(response);
            }

            if (null != display && display.contains("want to save")) //request quit which has two methods:: message & user input
            {
                response = keyboard.nextLine();
                display = Game.quitGame(response);
                if (null != display) // it comes back with an error
                {
                    //call these two methods again
                }
            }

            if (null != display && display.matches(".*drawn|put on|drank.*"))
            {
                display = Game.battleMessage();
                System.out.println(display);

                response = keyboard.nextLine();

                display = Game.battle(response);
                System.out.println(display);
            }

            if (null != display && display.contains("start"))
            {
                response = keyboard.nextLine();

                display = Game.battle(response);
                System.out.println(display);

            }

            //------------------------------entered save from GAME MENU, there is no inventory, view inventory,
            if (null != display && display.matches(".*successfully|have an inventory|Item:|drawing|putting|drinking|understanding.*"))
            {
                display = Game.gameMenuMessage();
                System.out.println(display);

                response = keyboard.nextLine();

                display = Game.gameMenu(response);
                System.out.println(display);
            }

            if (null != response && response.equalsIgnoreCase("exit")) //from main menu----------MAY REMOVE LATER
            {
                response = keyboard.nextLine();
                if (null == response || !(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")))
                    //quit();
                    Game.changeRooms(response);
            }

                //}


            //--------------------------Quit------------
            //these are finished
            display = Game.quitGameMessage();
            System.out.println(display);
            response = keyboard.nextLine();

            if (null != display && display.contains("closing?"))
            {
                display = Game.quitGame(response);
                System.out.println(display);
                //call quit message if user had entered gibberish
            }
            //----------------------------------

//            Game.collectItem();
            Game.saveGame();

            if (true)
            {

            } else
            {

            }

            //room description
            response = keyboard.nextLine();

            //don't handle or ask questions about the response just pass it on

        }
    }
}
