package controller;

import controller.actors.Hero;
import controller.actors.Monster;
import controller.room.Rooms;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/2/2015
 *
 * This class represents all the messages and menus offered to the user.
 *
 * Purpose: Allows the user to navigate the various options in the game.
 */
public class MenusAndMessages
{
    //static instance variables
    //These are static so Hero and startup are remembered on every visit to this class
    private static Hero player;
    private static boolean startingUp = true;

    /**The title screen message and login or create request
     * @return The title screen message and login or create request
     */
    public static String titleScreen()
    {
        return "Welcome to Dragon's Keep!" +
                "\nEnter 1 to login or 2 to create a new account";  //Game welcome message
    }

    /**A quit game question
     * @return The quit game question
     */
    public static String quitGameMessage()
    {
        return "Do you want to save your game before closing? (yes/no)";
    }

    /**Summarizes the enteredRoomMessage by leaving out the room description
     * @return A request for user action
     */
    public static String roomSummaryMessage()
    {
        if (2 == Rooms.getCurrentRoom() && player.getInventory() == null)
        {
            return "\nAre you going to collect the rucksack? (yes/no)";
        }
        else if (AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsPuzzle() > 0){
            return "\nAre you going to attempt this puzzle? (yes/no)";
        }
        //checks if an monster is in the room
        else if (AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsMonster() > 0)
        {
            return "\nAre you going to fight the monster? (yes/no)";
        }
        //checks if an item is in the room
        else
        {
            return "\nThere is an item to collect. Are you going to collect it? (yes/no)";
        }
    }

    /**Provides a description of the room and either requests the user's next action or provides the change
     * rooms menu
     * @return description of the room and a question or a the change rooms menu
     */
    public static String enteredRoomMessage()
    {
        //retrieves the user's HERO from AccountFunctions
        //this occurs only once after a successful login/create
        if (startingUp)
        {
            player = AccountFunctions.getHero();
            startingUp = false;
        }

        if (2 == Rooms.getCurrentRoom() && player.getInventory() == null)
        {
            return AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nAre you going to collect the rucksack? (yes/no)";
        }
        else if (AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsPuzzle() > 0){
            return AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nAre you going to attempt this puzzle? (yes/no)";
        }
        //checks if an monster is in the room
        else if (AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsMonster() > 0)
        {
            return AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nAre you going to fight the monster? (yes/no)";
        }
        //checks if an item is in the room
        else if (AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsArmor() > 0 || AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsWeapon() > 0 ||
                AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsElixir() > 0)
        {
            return AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nThere is an item to collect. Are you going to collect it? (yes/no)";
        }
        else  //if the room is empty
        {
            //Displays the current room description
            return AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getRoomDescription() + "\n\n" + changeRoomsMessage();
        }
    }

    /**A message that displays what exits are available to exit the current room
     * @return roomDirection The exit room message
     */
    public static String changeRoomsMessage()
    {
        //display possible exits
        String roomDirection = "<";
        //checks which of 4 possible exits this particular room has
        for (int x = 0; x < 4; x++)
        {
            //if an exit exist
            if (Integer.parseInt(AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getExits()[x]) != 0)
            {
                if (x == 0)
                {
                    roomDirection += "E";
                } else if (x == 1)
                {
                    roomDirection += "N";
                } else if (x == 2)
                {
                    roomDirection += "S";
                } else
                {
                    roomDirection += "W";
                }
            }
        }
        roomDirection += "> ";

        return roomDirection + "Where would you like to go next or enter \"menu\" to pull up the game menu.";
    }

    /**This is th main menu the player can call at almost anytime to access a variety of options
     * @return message The main menu
     */
    public static String mainMenuMessage()
    {
        String message = "-----------------------------------------";
        message += "\nEnter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                "\nEnter \"remove item name\" to throw away an item. \nEnter \"save\" to save your game. \nEnter \"quit\" to quit the game. \nEnter \"exit\" to return to game";
        message += "\n-----------------------------------------";
        message += "\nYour health is currently: " + player.getHealth();
        message += "\n-----------------------------------------";

        return message;
    }

    /**Provides the menu that appears before every enemy encounter
     * @return battleMessage The battle menu
     */
    public static String battleMessage()
    {
        Monster monster = new Monster();

        String battleMessage = "*****************************************";
        //prints a pre-fight menu
        battleMessage += "\n-----------------------------------------";
        battleMessage += "\nEnter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                "\nEnter \"remove item name\" to throw away an item. \nEnter \"attack\" to start the fight. \nEnter \"run away\" to escape.";
        battleMessage += "\n-----------------------------------------";
        battleMessage += "\nYour health is currently: " + player.getHealth();
        battleMessage += "\n" + monster.getName() + "'s health is: " + monster.getHealth();
        battleMessage += "\n-----------------------------------------";

        return battleMessage;
    }
}