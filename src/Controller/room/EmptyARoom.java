package controller.room;

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
    /**This method sets a room as empty once it has been visited and there is nothing more to do in the room
     */
    public static void setARoomEmpty()
    {
        //checks if every interaction with this particular room is set to 0
        if (Rooms.getCurrentRoom().getIsMonster() == 0 && Rooms.getCurrentRoom().getIsPuzzle() == 0 &&
                Rooms.getCurrentRoom().getIsArmor() == 0 && Rooms.getCurrentRoom().getIsWeapon() == 0 &&
                Rooms.getCurrentRoom().getIsElixir() == 0)
        {
            Rooms.getCurrentRoom().setIsEmpty(1);

            //Adds direction to the empty rooms
            String roomDirection = "<";
            //checks the 4 possible exits
            for (int x = 0; x < 4; x++)
            {
                //if an exit exist
                if (Integer.parseInt(Rooms.getCurrentRoom().getExits()[x]) != 0)
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
            Rooms.getCurrentRoom().setRoomDescription(roomDirection + " This room is empty... and it looks a bit familiar.");
        }
    }
}