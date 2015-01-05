package controller.itemsAndPuzzle;

import controller.room.Rooms;

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

    /**no argument constructor
     */
	public Weapon()
	{
        String[] dbWeapon = ItemDB.retrieveWeapon(Rooms.getCurrentRoom().getIsWeapon()).split("[|]");
        setItemName(dbWeapon[0]);
		strength = Integer.parseInt(dbWeapon[1]);
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