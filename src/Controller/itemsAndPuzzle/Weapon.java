package controller.itemsAndPuzzle;

import controller.AccountFunctions;
import controller.room.BuildRooms;
import controller.room.Rooms;

import java.util.Map;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Weapon object
 *
 * Purpose: To create a Weapon object
 */
public class Weapon extends Item
{
    //instance variable
	private int strength;
    private Map<Integer, Rooms> rooms;// = AccountFunctions.getRoomsObj()

    /**no argument constructor
     */
	public Weapon()
	{
		super("");

        //get an weapon from the database
//        String[] dbWeapon = ItemDB.retrieveWeapon(AccountFunctions.getRoomsObj().getRoomsMap().get(3).getIsWeapon()).split("[|]");
//        rooms = BuildRooms.buildRooms(0, false);
//        System.out.println("6th SOP " + rooms.get(11).getRoomDescription());  //DEBUG
//        System.out.println("7th SOP " + rooms.get(11).getIsEmpty());  //DEBUG

//        for(int num = 1; num <= 50; num++)
//        {
//            System.out.println("Here is 8th: " + num + " start SOP " + BuildRooms.getARoom(num).getRoomDescription()); //DEBUG
//        }



        String[] dbWeapon = ItemDB.retrieveWeapon(AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsWeapon()).split("[|]");

//        System.out.println(dbWeapon[0]);  //DEBUG CODE
//        System.out.println(dbWeapon[1]);  //DEBUG CODE


        setItemName(dbWeapon[0]);
		strength = Integer.parseInt(dbWeapon[1]);
	}

    public Weapon(String name, int strength)
    {
        super(name);
        this.strength = strength;
    }

	/**
	 * Method: getStrength
	 * This method gets the current strength of the 
	 * @return The current strength of the Weapon object
	 */
	public int getStrength()
	{
		return strength;
	}
}