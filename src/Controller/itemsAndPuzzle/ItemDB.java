package controller.itemsAndPuzzle;

import model.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/3/2015
 *
 * This class represents Item to database interactions
 *
 * Purpose: To parse data and formulate queries for armor, elixir, and weapon table in the database
 */
public class ItemDB
{
    //instance variables
    private static ResultSet rs = null;
    private static Database tdb = new Database();

    /**retrieves a particular armor
     * @param armorIndex the armor database ID
     * @return armorBuilder The armor and all its attributes
     */
    public static String retrieveArmor(int armorIndex)
    {
        //creates a stringBuilder for the armor attributes
        StringBuilder armorBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from armor");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("armorID") == armorIndex)
                {
                    armorBuilder.append(rs.getString("name"));
                    armorBuilder.append("|");
                    armorBuilder.append(rs.getInt("armorDefense"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return armorBuilder.toString();
    }

    /**retrieves a particular elixir
     * @param elixirIndex the elixir database ID
     * @return elixirBuilder The elixir and all its attributes
     */
    public static String retrieveElixir(int elixirIndex)
    {
        //creates a stringBuilder for the elixir attributes
        StringBuilder elixirBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from elixir");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("elixirID") == elixirIndex)
                {
                    elixirBuilder.append(rs.getString("name"));
                    elixirBuilder.append("|");
                    elixirBuilder.append(rs.getInt("healthBoost"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return elixirBuilder.toString();
    }

    /**retrieves a particular weapon
     * @param weaponIndex the weapon database ID
     * @return weaponBuilder The weapon and all its attributes
     */
    public static String retrieveWeapon(int weaponIndex)
    {
        //creates a stringBuilder for the weapon attributes
        StringBuilder weaponBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from weapon");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("weaponID") == weaponIndex)
                {
                    weaponBuilder.append(rs.getString("name"));
                    weaponBuilder.append("|");
                    weaponBuilder.append(rs.getInt("strength"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return weaponBuilder.toString();
    }
}