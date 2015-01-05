package controller;

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
    private static Database tdb = new Database();
    private static boolean duplicateKey = true;
    private static int caKey = 1;

    /**verifies the user account
     * @param playerName The player name
     * @return true/false If logging in succeeds or fails
     */
    public static boolean loginAccount(String playerName)
    {
        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("Select name from playerFile");

            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName != null && playerName.equalsIgnoreCase(rs.getString("name")))
                {
                    return true;
                }
            }

            rs.close();
//            stmt.close();
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
    public static int createAccount(String name)
    {
        do{
            //BEFORE inserting:: compare user requested name against names already in the db

            //the value returned is the number of effected rows (for us its either 0 or 1)
            int err = tdb.modData("Insert into playerFile (playerID, name, hasInventory, score, health) " +
                    "values (" + caKey + ", \'" + name + "\'," + 0 + "," + 0 + "," + 100 + ")");

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

//            stmt.close();
        return caKey;
    }
}