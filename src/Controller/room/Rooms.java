package controller.room;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**Class: Rooms
 * author Everton Gardiner Jr. and JJ Lindsay
 * version 2.0
 * Course : ITEC 3150 Spring 2014
 * Written: Nov 18, 2014
 *
 * This class illustrates the structure of a room.
 *
 * Purpose: To allow the creation and querying of a room.
 */
public class Rooms
{
    //instance variables
    private String roomDescription;
	private int isMonster;
	private int isElixir;
	private int isArmor;
	private int isWeapon;
	private int isPuzzle;
	private int isEmpty; // 0 or 1 acts as sqlite boolean
	private String[] exits; //room options for exiting North south east west

    //static instance variables
    private static int currentRoom;
    private Map<Integer, Rooms> roomsMap;

    /**No argument constructor
     */
    public Rooms()
    {
        roomDescription = null;
        isMonster = 0;
        isElixir = 0;
        isArmor =  0;
        isWeapon =  0;
        isPuzzle =  0;
        isEmpty =  0;
        exits =  new String[4];
    }

    /**Two argument constructor to load rooms from the database
     * @param playerID The player's database ID
     * @param accountExist Whether or not an account existed prior
     */
//    public Rooms(int playerID, boolean accountExist)
//    {
//        //Loads all the rooms into an array
//        String[] allRooms = RoomsDB.retrieveAllRooms().split("[|]");
//
//        String[] roomExits = new String[4];
//        //creates a rooms Map using the roomID as a key and the room as a value
//        roomsMap = new TreeMap<Integer, Rooms>();
//        Rooms aRoom = new Rooms();
//
//        //loops through the rooms array to create a room object before adding the room object to the roomsMap
//        for (int i = 0; i < allRooms.length-11; i= i+12)
//        {
//            roomExits[0] = allRooms[i+1];
//            roomExits[1] = allRooms[i+2];
//            roomExits[2] = allRooms[i+3];
//            roomExits[3] = allRooms[i+4];
//            aRoom.setExits(roomExits);
//
//            aRoom.setRoomDescription(allRooms[i + 5]);  //THIS will CHANGE FOR LOGIN
//            aRoom.setIsEmpty(Integer.parseInt(allRooms[i + 6]));  //THIS will CHANGE FOR LOGIN
//            aRoom.setIsArmor(Integer.parseInt(allRooms[i + 7])); //points to the specific armor
//            aRoom.setIsElixir(Integer.parseInt(allRooms[i + 8])); //points to the specific elixir
//            aRoom.setIsWeapon(Integer.parseInt(allRooms[i + 9])); //points to the specific weapon
//            aRoom.setIsMonster(Integer.parseInt(allRooms[i + 10])); //points to the specific monster
//            aRoom.setIsPuzzle(Integer.parseInt(allRooms[i + 11])); //points to the specific puzzle
//
//            //converts roomID from string to int and stores roomID as a key and the room created as a value
//            roomsMap.put(Integer.parseInt(allRooms[i]), aRoom);
//        }
//        currentRoom = 1;
//
//        //user successfully logged into saved account
//        if (accountExist)
//        {
//            String[] savedRooms = RoomsDB.loadSavedRooms(playerID).split("[|]");
//
//            if (Integer.parseInt(savedRooms[0]) == playerID)
//            {
//                //retrieves the last recorded current room for this playerID
//                currentRoom = Integer.parseInt(savedRooms[1]);
//
//                //is the room empty (1 for true, 0 for false)
//                for (int y = 2; y <= currentRoom+1; y++)
//                {
//                    //if the room is empty
//                    if (Integer.parseInt(savedRooms[y]) == 1)
//                    {
//                        roomsMap.get(y-1).setRoomDescription("This room is empty... and it looks a bit familiar");
//                        roomsMap.get(y-1).setIsEmpty(1);
//                        roomsMap.get(y-1).setIsArmor(0);
//                        roomsMap.get(y-1).setIsElixir(0);
//                        roomsMap.get(y-1).setIsWeapon(0);
//                        roomsMap.get(y-1).setIsPuzzle(0);
//                    }
//                }
//            }
//        }
//    }

    /**Sets the room exit
     * @param exits the rooms exits
     */
    public void setExits(String[] exits)
    {
        this.exits = Arrays.copyOf(exits, exits.length);
    }

    /**Sets the room description
     * @param roomDescription The description for a room
     */
    public void setRoomDescription(String roomDescription)
    {
        this.roomDescription = roomDescription;
    }

    /**
     * @return roomDescription Gets the room description
     */
    public String getRoomDescription()
    {
        return roomDescription;
    }

    /**
     * @return isMonster Gets is there a monster in the room
     */
    public int getIsMonster()
    {
        return isMonster;
    }

    /**
     * @return isElixir Gets is there an elixir in the room
     */
    public int getIsElixir()
    {
        return isElixir;
    }

    /**
     * @return isArmor Gets is there an armor in the room
     */
    public int getIsArmor()
    {
        return isArmor;
    }

    /**
     * @return isWeapon Gets is there a weapon in the room
     */
    public int getIsWeapon()
    {
        return isWeapon;
    }

    /**
     * @return isPuzzle Gets is there a puzzle in the room
     */
    public int getIsPuzzle()
    {
        return isPuzzle;
    }

    /**
     * @return isEmpty Gets is the room empty
     */
    public int getIsEmpty()
    {
        return isEmpty;
    }

    /**
     * @return choices Gets the room exits
     */
    public String[] getExits()
    {
        return exits;
    }

    /**Sets if there is a monster in the room
     * @param isMonster a 0 or 1 to indicate if there isn't or is a monster in the room
     */
    public void setIsMonster(int isMonster)
    {
        this.isMonster = isMonster;
    }

    /**Sets if there is an elixir in the room
     * @param isElixir a 0 or 1 to indicate if there isn't or is an elixir in the room
     */
    public void setIsElixir(int isElixir)
    {
        this.isElixir = isElixir;
    }

    /**Sets if there is an armor in the room
     * @param isArmor a 0 or 1 to indicate if there isn't or is an armor in the room
     */
    public void setIsArmor(int isArmor)
    {
        this.isArmor = isArmor;
    }

    /**Sets if there is a weapon in the room
     * @param isWeapon a 0 or 1 to indicate if there isn't or is a weapon in the room
     */
    public void setIsWeapon(int isWeapon)
    {
        this.isWeapon = isWeapon;
    }

    /**Sets if there is a puzzle in the room
     * @param isPuzzle a 0 or 1 to indicate if there isn't or is a puzzle in the room
     */
    public void setIsPuzzle(int isPuzzle)
    {
        this.isPuzzle = isPuzzle;
    }

    /**Sets the if the room is empty
     * @param isEmpty a 0 or 1 for empty or not empty
     */
    public void setIsEmpty(int isEmpty)
    {
        this.isEmpty = isEmpty;
    }

    /**
     * @return currentRoom Gets the currentRoom
     */
    public static int getCurrentRoom()
    {
        return currentRoom;
    }

    /**Sets the current room
     * @param currentRoom the new current room
     */
    public static void setCurrentRoom(int currentRoom)
    {
        Rooms.currentRoom = currentRoom;
    }

    /**
     * @return roomsMap Gets the roomsMap
     */
    public Map<Integer, Rooms> getRoomsMap()
    {
        return roomsMap;
    }
//
//    /**This method sets a room as empty once it has been visited and there is nothing more to do in the room
//     */
//    public static void setEmptyRoom()
//    {
//        //checks if every interaction with this particular room is set to 0
//        if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsMonster() == 0 && Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsPuzzle() == 0 &&
//                Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsArmor() == 0 && Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsWeapon() == 0 &&
//                Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsElixir() == 0)
//        {
//            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setIsEmpty(1);
//
//            //Adds direction to the empty rooms
//            String roomDirection = "<";
//            //checks the 4 possible exits
//            for (int x = 0; x < 4; x++)
//            {
//                //if an exit exist
//                if (Integer.parseInt(Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getExits()[x]) != 0)
//                {
//                    if (x == 0)
//                    {
//                        roomDirection += "E";
//                    } else if (x == 1)
//                    {
//                        roomDirection += "N";
//                    } else if (x == 2)
//                    {
//                        roomDirection += "S";
//                    } else
//                    {
//                        roomDirection += "W";
//                    }
//                }
//            }
//            roomDirection += ">";
//            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setRoomDescription(roomDirection + " This room is empty... and it looks a bit familiar.");
//        }
//    }
}