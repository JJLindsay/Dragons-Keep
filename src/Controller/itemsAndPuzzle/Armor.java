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

    /**
	 * Method: Constructor for Armor class
	 * Constructor for Armor class
	 */
	public Armor()
	{
		super("");
        String[] dbArmor = ItemDB.retrieveArmor(AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsArmor()).split("[|]");
        this.setItemName(dbArmor[0]);
		this.armorStrength = Integer.parseInt(dbArmor[1]);
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