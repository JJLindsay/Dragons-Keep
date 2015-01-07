package controller.inventory;

import controller.AccountDB;
import model.Database;
import view.View;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/3/2015
 *
 * This class represents an Inventory to database interactions
 *
 * Purpose: To parse data and formulate queries for the player's Inventory from the database
 */
public class InventoryDB
{
    private  Database tdb = new AccountDB().getDatabase();
    private int shiKey = 1;

    /**Retrieves a player's inventory
     * @param playerID The player's database ID
     * @return heroInventory The inventory and all its attributes
     */
    public String loadHeroInventory(int playerID)
    {
        //creates a stringBuilder for the inventory
        StringBuilder heroInventory = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("Select * from savedInventory where playerID = " + playerID);
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                heroInventory.append(rs.getInt("weaponID"));
                heroInventory.append("|");
                heroInventory.append(rs.getInt("armorID"));
                heroInventory.append("|");
                heroInventory.append(rs.getInt("elixirID"));
                heroInventory.append("|");
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroInventory.toString();
    }

    /**Saves the hero's inventory to the inventory table
     * @param playerID The player's ID/primary key
     * @param playerInventory The player's inventory
     * @return true Indicates the success of saving the Hero's inventory
     */
    public Boolean saveHeroInventory(int playerID, String[][] playerInventory)
    {
        boolean looping;

        //if the playerID already exist, delete it
        ResultSet rs;
        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from savedInventory");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getString("playerID").equalsIgnoreCase("" + playerID))
                {
                    tdb.modData("DELETE FROM savedInventory WHERE playerID = " + playerID);
                    break;
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }

        //loops through the inventory and adds each item to the database one at a time
        for (int x = 0; x < playerInventory.length; x++)
        {
            String heroItems = ", " + playerID;

            //checks that the player inventory index is not empty
            if (playerInventory[x][1] != null)
                if (playerInventory[x][1].equalsIgnoreCase("w"))  //checks the itemType in the inventory
                {
                    try
                    {
                        //Query the database. Returns the results in a ResultSet
                        rs = tdb.query("Select * from weapon");
                        //Loop over the result set. next moves the cursor to the next record and returns the current record
                        while(rs.next())
                        {
                            if (rs.getString("name").equalsIgnoreCase(playerInventory[x][0]))
                            {
                                heroItems += ", " + rs.getInt("weaponID");
                                heroItems += ", " + 0;
                                heroItems += ", " + 0;
                                break;
                            }
                        }
                        rs.close();
                    }
                    catch(SQLException sqe)
                    {
                        System.out.println(sqe.getMessage());
                    }
                }
                else if (playerInventory[x][1].equalsIgnoreCase("a"))  //checks the itemType in the inventory
                {
                    try
                    {
                        //Query the database. Returns the results in a ResultSet
                        rs = tdb.query("Select * from armor");
                        //Loop over the result set. next moves the cursor to the next record and returns the current record
                        while(rs.next())
                        {
                            if (rs.getString("name").equalsIgnoreCase(playerInventory[x][0]))
                            {
                                heroItems += ", " + 0;
                                heroItems += ", " + rs.getInt("armorID");
                                heroItems += ", " + 0;
                                break;
                            }
                        }
                        rs.close();
                    }
                    catch(SQLException sqe)
                    {
                        System.out.println(sqe.getMessage());
                    }
                }
                else if (playerInventory[x][1].equalsIgnoreCase("e"))  //checks the itemType in the inventory
                {
                    try
                    {
                        //Query the database. Returns the results in a ResultSet
                        rs = tdb.query("Select * from elixir");
                        //Loop over the result set. next moves the cursor to the next record and returns the current record
                        while(rs.next())
                        {
                            if (rs.getString("name").equalsIgnoreCase(playerInventory[x][0]))
                            {
                                heroItems += ", " + 0;
                                heroItems += ", " + 0;
                                heroItems += ", " + rs.getInt("elixirID");
                                break;
                            }
                        }
                        rs.close();
                    }
                    catch(SQLException sqe)
                    {
                        System.out.println(sqe.getMessage());
                    }
                }
                else
                    continue;

            //takes the sql statement created and inserts it into the database
            if (playerInventory[x][1] != null)
                do
                {
                    int err = tdb.modData("Insert into savedInventory(inventoryID, playerID, weaponID, armorID, elixirID) " +
                            "values (" + shiKey + heroItems + ")");

                    //if no rows were changed
                    if (err == 0)
                    {
                        shiKey++;
                        looping = true;
                    }
                    else
                    {
                        new View().setControllerDisplay("Successfully saved Hero inventory to the database.");
                        looping = false;
                    }
                }while(looping);
        }
        return true;
    }
}