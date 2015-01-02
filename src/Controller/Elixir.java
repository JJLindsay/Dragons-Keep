package Controller;

import Model.LoadEntity;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860 Fall 2014
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create and Elixir object
 *
 * Purpose: To create an Elixir object
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
        super("");
        //get an elixir from the database
        String[] dbElixir = LoadEntity.retrieveElixir(Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsElixir()).split("[|]");
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
