package Controller;

import Model.LoadEntity;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**Class: Rooms
 * author Everton Gardiner Jr.
 * version 1.0
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
	private String[] choices; //room options for exiting North south east west

    //NEW
    private static int currentRoom;
    private static Map<Integer, Rooms> roomsMap;

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
        choices =  new String[4];
    }

    public Rooms(int playerID, boolean accountExist)
    {
        //Loads all the rooms into an array
        String[] allRooms = LoadEntity.retrieveAllRooms().split("[|]");
        String[] oneRoom = new String[4];
        //creates a rooms Map using the roomID as the key and the room as the object
        roomsMap = new TreeMap<Integer, Rooms>();

        //loops through the rooms array to create a room object before adding the room object to the roomsMap
        for (int i = 0; i < allRooms.length-11; i= i+12)
        {
            oneRoom[0] = allRooms[i+1];
            oneRoom[1] = allRooms[i+2];
            oneRoom[2] = allRooms[i+3];
            oneRoom[3] = allRooms[i+4];
            this.setChoices(oneRoom);

            this.setRoomDescription(allRooms[i + 5]);  //THIS will CHANGE FOR LOGIN
            this.setIsEmpty(Integer.parseInt(allRooms[i + 6]));  //THIS will CHANGE FOR LOGIN
            this.setIsArmor(Integer.parseInt(allRooms[i + 7])); //points to the specific armor
            this.setIsElixir(Integer.parseInt(allRooms[i + 8])); //points to the specific elixir
            this.setIsWeapon(Integer.parseInt(allRooms[i + 9])); //points to the specific weapon
            this.setIsMonster(Integer.parseInt(allRooms[i + 10])); //points to the specific monster
            this.setIsPuzzle(Integer.parseInt(allRooms[i + 11])); //points to the specific puzzle

            //converts roomID from string to int and stores roomID as key and the room created in a map
            roomsMap.put(Integer.parseInt(allRooms[i]), this);
        }
        currentRoom = 1;

        //user successfully logged into saved account
        if (accountExist)
        {
            String[] savedRooms = LoadEntity.loadSavedRooms(playerID).split("[|]");

            if (Integer.parseInt(savedRooms[0]) == playerID)
            {
                //retrieves the last recorded current room for this playerID
                currentRoom = Integer.parseInt(savedRooms[1]);

                //is the room empty (1 for true, 0 for false)
                for (int y = 2; y <= currentRoom+1; y++)
                {
                    //if the room is empty
                    if (Integer.parseInt(savedRooms[y]) == 1)
                    {
                        roomsMap.get(y-1).setRoomDescription("This room is empty... and it looks a bit familiar");
                        roomsMap.get(y-1).setIsEmpty(1);
                        roomsMap.get(y-1).setIsArmor(0);
                        roomsMap.get(y-1).setIsElixir(0);
                        roomsMap.get(y-1).setIsWeapon(0);
                        roomsMap.get(y-1).setIsPuzzle(0);
                    }
                }
            }
        }
    }

    /**Sets the room exit choices
     * @param choices the exit rooms choices
     */
    public void setChoices(String[] choices)
    {
        this.choices = Arrays.copyOf(choices, choices.length);
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
    public String[] getChoices()
    {
        return choices;
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

    public static int getCurrentRoom()
    {
        return currentRoom;
    }

    public static void setCurrentRoom(int currentRoom)
    {
        Rooms.currentRoom = currentRoom;
    }

    public static Map<Integer, Rooms> getRoomsMap()
    {
        return roomsMap;
    }
}