package controller.room;

import controller.AccountFunctions;

import java.util.Map;
import java.util.TreeMap;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/4/2015
 *
 * This class represents a ...
 *
 * Purpose: Allows the manipulation of a ...
 */
public class BuildRooms
{
    //static instance variables
//    static int currentRoom;
    static Map<Integer, Rooms> roomsMap;

    static Rooms aRoom = new Rooms();

    //Loads all the rooms into an array
    static String[] allRooms;
    static String[] roomExits;
    //creates a rooms Map using the roomID as a key and the room as a value

    public static Map<Integer, Rooms> buildRooms(int playerID, boolean accountExist)
    {
        //Loads all the rooms into an array
        String[] allRooms = RoomsDB.retrieveAllRooms().split("[|]");
        String[] temp = new String[4];
        //creates a rooms Map using the roomID as the key and the room as the object
        roomsMap = new TreeMap<Integer, Rooms>();

        //loops through the rooms array to create a room object before adding the room object to the roomsMap
        for (int i = 0; i < allRooms.length - 11; i = i + 12)
        {
            Rooms rooms = new Rooms();
            temp[0] = allRooms[i + 1];
            temp[1] = allRooms[i + 2];
            temp[2] = allRooms[i + 3];
            temp[3] = allRooms[i + 4];
            rooms.setExits(temp);

            rooms.setRoomDescription(allRooms[i + 5]);  //THIS will CHANGE FOR LOGIN
            rooms.setIsEmpty(Integer.parseInt(allRooms[i + 6]));  //THIS will CHANGE FOR LOGIN
            rooms.setIsArmor(Integer.parseInt(allRooms[i + 7])); //points to the specific armor
            rooms.setIsElixir(Integer.parseInt(allRooms[i + 8])); //points to the specific elixir
            rooms.setIsWeapon(Integer.parseInt(allRooms[i + 9])); //points to the specific weapon
            rooms.setIsMonster(Integer.parseInt(allRooms[i + 10])); //points to the specific monster
            rooms.setIsPuzzle(Integer.parseInt(allRooms[i + 11])); //points to the specific puzzle

            //converts roomID from string to int and stores roomID as key and the room created in a map
            roomsMap.put(Integer.parseInt(allRooms[i]), rooms);
        }
        Rooms.setCurrentRoom(1);

        //user successfully logged into saved account
        if (accountExist)
        {
            String[] savedRooms = RoomsDB.loadSavedRooms(playerID).split("[|]");

            if (Integer.parseInt(savedRooms[0]) == playerID)
            {
                //retrieves the last recorded current room for this playerID
                Rooms.setCurrentRoom(Integer.parseInt(savedRooms[1]));

                //is the room empty (1 for true, 0 for false)
                for (int y = 2; y <= Rooms.getCurrentRoom() + 1; y++)
                {
                    //if the room is empty
                    if (Integer.parseInt(savedRooms[y]) == 1)
                    {
                        roomsMap.get(y - 1).setRoomDescription("This room is empty... and it looks a bit familiar");
                        roomsMap.get(y - 1).setIsEmpty(1);
                        roomsMap.get(y - 1).setIsArmor(0);
                        roomsMap.get(y - 1).setIsElixir(0);
                        roomsMap.get(y - 1).setIsWeapon(0);
                        roomsMap.get(y - 1).setIsPuzzle(0);
                    }
                }
            }
        }
        return roomsMap;
    }

    public static Rooms getARoom(int roomNumber)
    {
        return roomsMap.get(roomNumber);
    }


}
