package controller.room;

import controller.AccountFunctions;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/2/2015
 *
 * This class represents a feature to set a room empty
 *
 * Purpose: To declare a visited and empty room as visited and empty.
 */
public class EmptyARoom
{
    //NEW
    private static Rooms rooms = new AccountFunctions().getRooms();

    /**This method sets a room as empty once it has been visited and there is nothing more to do in the room
     */
    public void setARoomEmpty()
    {
        //checks if every interaction with this particular room is set to 0
        if (rooms.getCurrentRoom().getIsMonster() == 0 && rooms.getCurrentRoom().getIsPuzzle() == 0 &&
                rooms.getCurrentRoom().getIsArmor() == 0 && rooms.getCurrentRoom().getIsWeapon() == 0 &&
                rooms.getCurrentRoom().getIsElixir() == 0)
        {
            rooms.getCurrentRoom().setIsEmpty(3);

            //Adds direction to the empty rooms
            String roomDirection = "<";
            //checks the 4 possible exits
            for (int x = 0; x < 4; x++)
            {
                //if an exit exist
                if (Integer.parseInt(rooms.getCurrentRoom().getExits()[x]) != 0)
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
            roomDirection += ">";
            rooms.getCurrentRoom().setRoomDescription(roomDirection + " This room is empty... and it looks a bit familiar.");
        }
    }
}