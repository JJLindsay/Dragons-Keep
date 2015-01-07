package controller.itemsAndPuzzle;

import controller.AccountFunctions;
import controller.room.Rooms;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860 Spring 2014
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Armor object
 *
 * Purpose: To create and Armor object
 */
public class Armor extends Item
{
    //instance variables
	private int armorStrength;

    //NEW
    private static ItemDB itemDB = new ItemDB();
    private static Rooms rooms = new AccountFunctions().getRooms();

    /**
	 * Method: Constructor for Armor class
	 * Constructor for Armor class
	 */
	public Armor()
	{
        String[] dbArmor = itemDB.retrieveArmor(rooms.getCurrentRoom().getIsArmor()).split("[|]");
        this.setItemName(dbArmor[0]);
		this.armorStrength = Integer.parseInt(dbArmor[1]);
	}

    public Armor(String name, int armorStrength)
    {
        super(name);
        this.armorStrength = armorStrength;
    }

	/**
	 * Method: getHealthBoost
	 * This method gets the current health boost of the Armor object
	 * @return the current health boost of the current health boost of the Armor object
	 */
	public int getArmorDefense()
	{
		return armorStrength;
	}
}