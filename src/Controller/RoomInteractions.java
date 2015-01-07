package controller;

import controller.actors.Hero;
import controller.actors.Monster;
import controller.room.EmptyARoom;
import controller.room.Rooms;
import controller.itemsAndPuzzle.Puzzle;
import view.View;

import java.util.Map;

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
    //instance variables
    private static Hero player;

    //NEW
    private static MenusAndMessages menusAndMessages = new View().getMenusAndMessages();
    private static ItemCollection itemCollection;  //original
    private static EmptyARoom emptyARoom;  //original
    private static Rooms rooms = new AccountFunctions().getRooms();

    //Experiment
//    private static Monster monster;

    /**This manages the changing from one room to another
     * @param userInput The user's response
     * @return The enteredRoom, changeRoom, and mainMenu message
     */
    public String changingRooms(String userInput)
    {
//        menusAndMessages = n;
//        rooms = new AccountFunctions().getRooms();

        //Prompts the user to change to a new room or enter the menu screen
        if (userInput.equalsIgnoreCase("menu"))
        {
            return menusAndMessages.mainMenuMessage();
        }
        else if (userInput.equalsIgnoreCase("head East") && Integer.parseInt(rooms.getCurrentRoom().getExits()[0]) != 0)
        {
            new EmptyARoom().setARoomEmpty();
            rooms.setCurrentRoomID(Integer.parseInt(rooms.getCurrentRoom().getExits()[0]));
            return menusAndMessages.enteredRoomMessage();
        } else if (userInput.equalsIgnoreCase("head NORTH") && Integer.parseInt(rooms.getCurrentRoom().getExits()[1]) != 0)
        {
            new EmptyARoom().setARoomEmpty();
            rooms.setCurrentRoomID(Integer.parseInt(rooms.getCurrentRoom().getExits()[1]));
            return menusAndMessages.enteredRoomMessage();
        } else if (userInput.equalsIgnoreCase("head South") && Integer.parseInt(rooms.getCurrentRoom().getExits()[2]) != 0)
        {
            new EmptyARoom().setARoomEmpty();
            rooms.setCurrentRoomID(Integer.parseInt(rooms.getCurrentRoom().getExits()[2]));
            return menusAndMessages.enteredRoomMessage();
        } else if (userInput.equalsIgnoreCase("head West") && Integer.parseInt(rooms.getCurrentRoom().getExits()[3]) != 0)
        {
            new EmptyARoom().setARoomEmpty();
            rooms.setCurrentRoomID(Integer.parseInt(rooms.getCurrentRoom().getExits()[3]));
            return menusAndMessages.enteredRoomMessage();
        }
        // if the user correctly enters a direction but there is no room in that direction.
        else if (userInput.equalsIgnoreCase("head East") && Integer.parseInt(rooms.getCurrentRoom().getExits()[0]) == 0 ||
                userInput.equalsIgnoreCase("head NORTH") && Integer.parseInt(rooms.getCurrentRoom().getExits()[1]) == 0 ||
                userInput.equalsIgnoreCase("head South") && Integer.parseInt(rooms.getCurrentRoom().getExits()[2]) == 0 ||
                userInput.equalsIgnoreCase("head West") && Integer.parseInt(rooms.getCurrentRoom().getExits()[3]) == 0)
        {
            return "This is embarrassing. You entered: " + userInput + " and walked into a wall. There is no door"
                    + " that way. Try again." + "\n\n" + menusAndMessages.changeRoomsMessage();
        }
        // the user entered something else
        else
        {
            return "There was an error in trying to make sense of you request. Check your spelling and try again." +
                    "\n\n" + menusAndMessages.changeRoomsMessage();
        }
    }

    /**Manages room interactions
     * @param userInput The user's response
     * @return results and the user's actions and changeRoomMessage()
     */
    public String roomInteractions(String userInput)
    {
//        menusAndMessages = new View().getMenusAndMessages();
//        rooms = new AccountFunctions().getRooms();

        System.out.println("Your response: "+  userInput);  //DEBUG CODE
        player = new AccountFunctions().getHero();

        //rucksack is not an item, monster or puzzle and is therefore not represented in the
        //db for the first room except to say the room is not empty. This makes the if below necessary!
        if (2 == rooms.getCurrentRoomID() && player.getInventory() == null)
        {
            if (userInput.equalsIgnoreCase("yes"))  
            {
                player.createInventory();
                new EmptyARoom().setARoomEmpty();
                return "You've acquired a rucksack which gives you access to an inventory for holding 10 items" +
                        "\n\n" + menusAndMessages.changeRoomsMessage();
            }
            //The user may get a different response in the final game so this is Tentative
            else if (userInput.equalsIgnoreCase("no"))  
            {
                player.createInventory();
                new EmptyARoom().setARoomEmpty();
                return "You shake your head no but then hear a terrifying scream of someone further in." +
                        "\nIt sounds like they most certainly met their demise... or wish they had. You look back" +
                        "down on the dirty, stained rucksack\nand suddenly it doesn't look so bad. Maybe it could come in handy." +
                        "You've acquired a rucksack which gives you access to an inventory for holding 10 items" +
                        "\n\n" + menusAndMessages.changeRoomsMessage();

            } else  
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        menusAndMessages.roomSummaryMessage();
            }
        }
        //checks if an puzzle is in the room
        else if (rooms.getCurrentRoom().getIsPuzzle() > 0)
        {
            if (userInput.equalsIgnoreCase("yes"))  
            {
                Puzzle puzzle = new Puzzle();
                return puzzle.getPuzzle();
            } else if (userInput.equalsIgnoreCase("no"))  
            {
                return menusAndMessages.changeRoomsMessage();
            } else
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        menusAndMessages.roomSummaryMessage();
            }
        }
        //checks if an monster is in the room
        else if (rooms.getCurrentRoom().getIsMonster() > 0)
        {
            if (userInput.equalsIgnoreCase("yes"))
            {
//                monster = new Monster();
                new GameInteractions().setFreshEncounter(true);
                return menusAndMessages.battleMessage();
            } else if (userInput.equalsIgnoreCase("no"))  
            {
                return menusAndMessages.changeRoomsMessage();
            } else  
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        menusAndMessages.roomSummaryMessage();
            }
        }
        //checks if an item is in the room
        else if (rooms.getCurrentRoom().getIsArmor() > 0 || rooms.getCurrentRoom().getIsWeapon() > 0 ||
                rooms.getCurrentRoom().getIsElixir() > 0)
        {
            if (userInput.equalsIgnoreCase("yes"))  
            {
                itemCollection = new ItemCollection();
                return itemCollection.collectItem();
            } else if (userInput.equalsIgnoreCase("no"))  
            {
                return menusAndMessages.changeRoomsMessage();
            } else  
            {
                return "There was an error in trying to make sense of your request. Check your spelling." +
                        menusAndMessages.roomSummaryMessage();
            }
        }
        //if there is no monster, puzzle, or item.
        else
        {
            emptyARoom = new EmptyARoom();
            emptyARoom.setARoomEmpty();
            return changingRooms(userInput);
        }
    }
//
//    public Monster getMonster()
//    {
//        return monster;
//    }
}