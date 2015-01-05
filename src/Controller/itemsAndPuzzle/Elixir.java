package controller.itemsAndPuzzle;

import controller.room.Rooms;

/**
 * @author Everton Gardiner Jr. and JJ Lindsay
 * @version 2.0
 * Course : ITEC 3860 Fall 2014
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create and Elixir object
 *
 * Purpose: To create an Elixir object from the database
 */
public class Elixir extends Item
{
	private int healthBoost;

	/**
	 * Method: Constructor
	 * Constructor for Elixir class
	 */
	public Elixir()
	{
        String[] dbElixir = ItemDB.retrieveElixir(Rooms.getCurrentRoom().getIsElixir()).split("[|]");
        this.setItemName(dbElixir[0]);
        this.healthBoost = Integer.parseInt(dbElixir[1]);
	}

	/**
	 * Method: getHealthBoost
	 * This method gets the current health boost of the Elixir object
	 * @return the current health boost of the Elixir object
	 */
	public int getHealthBoost()
	{
		return healthBoost;
	}
}