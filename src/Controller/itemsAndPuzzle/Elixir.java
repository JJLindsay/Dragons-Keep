package controller.itemsAndPuzzle;

import controller.AccountFunctions;
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

    //NEW
    private static ItemDB itemDB = new ItemDB();
    private static Rooms rooms = new AccountFunctions().getRooms();

	/**
	 * Method: Constructor
	 * Constructor for Elixir class
	 */
	public Elixir()
	{
        String[] dbElixir = itemDB.retrieveElixir(rooms.getCurrentRoom().getIsElixir()).split("[|]");
        this.setItemName(dbElixir[0]);
        this.healthBoost = Integer.parseInt(dbElixir[1]);
	}

    public Elixir(String name, int healthBoost)
    {
        super(name);
        this.healthBoost = healthBoost;
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