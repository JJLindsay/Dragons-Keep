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
public class EmptyARoom
{
    /**This method sets a room as empty once it has been visited and there is nothing more to do in the room
     */
    public static void setEmptyRoom()
    {
        //checks if every interaction with this particular room is set to 0
        if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsMonster() == 0 && Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsPuzzle() == 0 &&
                Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsArmor() == 0 && Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsWeapon() == 0 &&
                Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsElixir() == 0)
        {
            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setIsEmpty(1);

            //Adds direction to the empty rooms
            String roomDirection = "<";
            //checks the 4 possible exits
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
            roomDirection += ">";
            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setRoomDescription(roomDirection + " This room is empty... and it looks a bit familiar.");
        }
    }
}
