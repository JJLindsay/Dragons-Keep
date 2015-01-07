package controller;

import controller.actors.Hero;
import model.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/3/2015
 *
 * This class represents an Account to database interactions
 *
 * Purpose: To parse data and formulate queries for the playerFile in the database
 */
public class AccountDB
{
    //static instance variables
    private static Database tdb = new Database(); //original
    private boolean duplicateKey;
    private int caKey;

    /**verifies the user account
     * @param playerName The player name
     * @return true/false If logging in succeeds or fails
     */
    public boolean loginAccount(String playerName)  //Somehow this is creating problems for mod later
    {
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("SELECT name FROM playerFile");

            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName != null && playerName.equalsIgnoreCase(rs.getString("name")))
                {
                    return true;
                }
            }

            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return false;
    }

    /**creates a user profile
     * @param name The player's name
     * @return caKey The database primary key associated with this player name
     */
    public int createAccount(String name)
    {
        caKey = 1;
        duplicateKey = true;

        do{
            //the value returned is the number of effected rows (for us its either 0 or 1)
            int err = tdb.modData("INSERT INTO playerFile (playerID, name, hasInventory, score, health) " +
                    "VALUES (" + caKey + ", \'" + name + "\'," + 0 + "," + 0 + "," + 100 + ")");

            //If 0 rows were effected
            if (err == 0)
            {
                caKey++;
            }
            else
            {
                duplicateKey = false;
            }
        }while (duplicateKey);

        return caKey;
    }

    public Database getDatabase()
    {
        return tdb;
    }
}