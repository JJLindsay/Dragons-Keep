package Controller;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/2/2015
 *
 * This class represents a ...
 *
 * Purpose: Allows the manipulation of a ...
 */
public class MenusAndMessages
{
    //static instance variables
    //NEW
    private static Hero player;
    private static boolean startingUp = true;

    public static String gameIntro()
    {
        return "Welcome to Dragon's Keep!" +
                "\nEnter 1 to login or 2 to create a new account";  //Game welcome message
    }

    public static String quitGameMessage()
    {
        return "Do you want to save your game before closing? (yes/no)";
    }

    public static String roomSummaryMessage()
    {
        if (2 == Rooms.getCurrentRoom() && player.getInventory() == null)
        {
            return "\nAre you going to collect the rucksack? (yes/no)";
        }
        else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsPuzzle() > 0){
            return "\nAre you going to attempt this puzzle? (yes/no)";
        }
        //checks if an monster is in the room
        else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsMonster() > 0)
        {
            return "\nAre you going to fight the monster? (yes/no)";
        }
        //checks if an item is in the room
        else
        {
            return "\nThere is an item to collect. Are you going to collect it? (yes/no)";
        }
    }

    public static String enteredRoomMessage()
    {
        //retrieves the user HERO from AccountFunctions
        if (startingUp)
        {
            player = AccountFunctions.getHero();
            startingUp = false;
        }

        if (2 == Rooms.getCurrentRoom() && player.getInventory() == null)
        {
            return Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nAre you going to collect the rucksack? (yes/no)";
        }
        else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsPuzzle() > 0){
            return Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nAre you going to attempt this puzzle? (yes/no)";
        }
        //checks if an monster is in the room
        else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsMonster() > 0)
        {
            return Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nAre you going to fight the monster? (yes/no)";
        }
        //checks if an item is in the room
        else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsArmor() > 0 || Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsWeapon() > 0 ||
                Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsElixir() > 0)
        {
            return Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getRoomDescription() +
                    "\nThere is an item to collect. Are you going to collect it? (yes/no)";
        }
        else  //if the room is empty
        {
            //Displays the current room description
            return Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getRoomDescription() + "\n\n" + changeRoomsMessage();
        }
    }

    public static String changeRoomsMessage()
    {
        //display possible exits
        String roomDirection = "<";
        //checks which of 4 possible exits this particular room has
        for (int x = 0; x < 4; x++)
        {
            //if an exit exist
            if (Integer.parseInt(Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getChoices()[x]) != 0)
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

    public static String puzzleMessage()
    {
        Puzzle puzzle = new Puzzle();
        return puzzle.getPuzzle();  //somehow based on this call solvePuzzle()
    }

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