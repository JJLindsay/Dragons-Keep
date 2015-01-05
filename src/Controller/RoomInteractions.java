package controller;

import controller.actors.Hero;
import controller.room.EmptyARoom;
import controller.room.Rooms;
import controller.itemsAndPuzzle.Puzzle;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/2/2015
 *
 * This class represents room interactions
 *
 * Purpose: To allow a player to change from one room to another or interact with objects within a room.
 */
public class RoomInteractions
{
    //static instance variables
    private static Hero player = AccountFunctions.getHero();

    /**This manages the changing from one room to another
     * @param userInput The user's response
     * @return The enteredRoom, changeRoom, and mainMenu message
     */
    public static String changingRooms(String userInput)
    {
        //Prompts the user to change to a new room or enter the menu screen
        if (userInput.equalsIgnoreCase("menu"))
        {
            return MenusAndMessages.mainMenuMessage();
        }
        else if (userInput.equalsIgnoreCase("head East") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[0]) != 0)
        {
            Rooms.setCurrentRoomID(Integer.parseInt(Rooms.getCurrentRoom().getExits()[0]));
            return MenusAndMessages.enteredRoomMessage();
        } else if (userInput.equalsIgnoreCase("head NORTH") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[1]) != 0)
        {
            Rooms.setCurrentRoomID(Integer.parseInt(Rooms.getCurrentRoom().getExits()[1]));
            return MenusAndMessages.enteredRoomMessage();
        } else if (userInput.equalsIgnoreCase("head South") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[2]) != 0)
        {
            Rooms.setCurrentRoomID(Integer.parseInt(Rooms.getCurrentRoom().getExits()[2]));
            return MenusAndMessages.enteredRoomMessage();
        } else if (userInput.equalsIgnoreCase("head West") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[3]) != 0)
        {
            Rooms.setCurrentRoomID(Integer.parseInt(Rooms.getCurrentRoom().getExits()[3]));
            return MenusAndMessages.enteredRoomMessage();
        }
        // if the user correctly enters a direction but there is no room in that direction.
        else if (userInput.equalsIgnoreCase("head East") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[0]) == 0 ||
                userInput.equalsIgnoreCase("head NORTH") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[1]) == 0 ||
                userInput.equalsIgnoreCase("head South") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[2]) == 0 ||
                userInput.equalsIgnoreCase("head West") && Integer.parseInt(Rooms.getCurrentRoom().getExits()[3]) == 0)
        {
            return "This is embarrassing. You entered: " + userInput + " and walked into a wall. There is no door"
                    + " that way. Try again." + "\n\n" + MenusAndMessages.changeRoomsMessage();
        }
        // the user entered something else
        else
        {
            return "There was an error in trying to make sense of you request. Check your spelling and try again." +
                    "\n\n" + MenusAndMessages.changeRoomsMessage();
        }
    }

    /**Manages room interactions
     * @param userInput The user's response
     * @return results and the user's actions and changeRoomMessage()
     */
    public static String roomInteractions(String userInput)
    {
        //rucksack is not an item, monster or puzzle and is therefore not represented in the
        //db for the first room except to say the room is not empty. This makes the if below necessary!
        if (2 == Rooms.getCurrentRoomID() && player.getInventory() == null)
        {
            if (userInput.equalsIgnoreCase("yes"))  
            {
                player.createInventory();
                return "You've acquired a rucksack which gives you access to an inventory for holding 10 items" +
                        "\n\n" + MenusAndMessages.changeRoomsMessage();
            }
            //The user may get a different response in the final game so this is Tentative
            else if (userInput.equalsIgnoreCase("no"))  
            {
                player.createInventory();
                return "You shake your head no but then hear a terrifying scream of someone further in." +
                        "\nIt sounds like they most certainly met their demise... or wish they had. You look back" +
                        "down on the dirty, stained rucksack\nand suddenly it doesn't look so bad. Maybe it could come in handy." +
                        "You've acquired a rucksack which gives you access to an inventory for holding 10 items" +
                        "\n\n" + MenusAndMessages.changeRoomsMessage();

            } else  
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        MenusAndMessages.roomSummaryMessage();
            }
        }
        //checks if an puzzle is in the room
        else if (Rooms.getCurrentRoom().getIsPuzzle() > 0)
        {
            if (userInput.equalsIgnoreCase("yes"))  
            {
                Puzzle puzzle = new Puzzle();
                return puzzle.getPuzzle();
            } else if (userInput.equalsIgnoreCase("no"))  
            {
                return MenusAndMessages.changeRoomsMessage();
            } else
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        MenusAndMessages.roomSummaryMessage();
            }
        }
        //checks if an monster is in the room
        else if (Rooms.getCurrentRoom().getIsMonster() > 0)
        {
            if (userInput.equalsIgnoreCase("yes"))
            {
                return MenusAndMessages.battleMessage();
            } else if (userInput.equalsIgnoreCase("no"))  
            {
                return MenusAndMessages.changeRoomsMessage();
            } else  
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        MenusAndMessages.roomSummaryMessage();
            }
        }
        //checks if an item is in the room
        else if (Rooms.getCurrentRoom().getIsArmor() > 0 || Rooms.getCurrentRoom().getIsWeapon() > 0 ||
                Rooms.getCurrentRoom().getIsElixir() > 0)
        {
            if (userInput.equalsIgnoreCase("yes"))  
            {
                return ItemCollection.collectItem();
            } else if (userInput.equalsIgnoreCase("no"))  
            {
                return MenusAndMessages.changeRoomsMessage();
            } else  
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        MenusAndMessages.roomSummaryMessage();
            }
        }
        //if there is no monster, puzzle, or item.
        else
        {
            EmptyARoom.setARoomEmpty();
            return changingRooms(userInput);
        }
    }
}