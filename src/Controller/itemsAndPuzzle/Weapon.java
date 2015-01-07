package controller.itemsAndPuzzle;

import controller.AccountFunctions;
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
    private static ItemDB itemDB;
    private static Rooms rooms;

    /**no argument constructor
     */
	public Weapon()
	{
        itemDB = new ItemDB();
        rooms = new AccountFunctions().getRooms();
        String[] dbWeapon = itemDB.retrieveWeapon(rooms.getCurrentRoom().getIsWeapon()).split("[|]");
        setItemName(dbWeapon[0]);
		strength = Integer.parseInt(dbWeapon[1]);
	}

    /**Two argument constructor
     * @param name The name of the weapon
     * @param strength The strength of the weapon
     */
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