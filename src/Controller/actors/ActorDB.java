package controller.actors;

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
 * This class represents Actor to database interactions
 *
 * Purpose: To parse data and formulate queries between Actors and the database
 */
public class ActorDB
{
    //instance variables
    private static ResultSet rs = null;
    private static Database tdb = new Database();

    /**Retrieves a player's attributes
     * @param playerName The player's name
     * @return heroBuilder The hero and its attributes
     */
    public static String loadHero(String playerName)
    {
        //creates a stringBuilder for the hero
        StringBuilder heroBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from playerFile");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (playerName.equalsIgnoreCase(rs.getString("name")))
                {
                    heroBuilder.append(rs.getInt("playerID"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getString("name"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getInt("hasInventory"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getInt("score"));
                    heroBuilder.append("|");
                    heroBuilder.append(rs.getInt("health"));
                    heroBuilder.append("|");
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return heroBuilder.toString();
    }

    /**retrieves a particular monster
     * @param monsterIndex the monster database ID
     * @return monsterBuilder The monster and all its attributes
     */
    public static String retrieveMonster(int monsterIndex)
    {
        //creates a stringBuilder for the monster attributes
        StringBuilder monsterBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from monster");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("monsterID") == monsterIndex)
                {
                    monsterBuilder.append(rs.getString("name"));
                    monsterBuilder.append("|");
                    monsterBuilder.append(rs.getInt("AttackPower"));
                    monsterBuilder.append("|");
                    monsterBuilder.append(rs.getInt("health"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return monsterBuilder.toString();
    }

    /**save the Hero ID, name, hasInventory, score, and health to the database
     * @param heroAttributes The attributes of the player
     * @return true Indicates Hero data was successfully saved
     */
    public static Boolean saveHeroData(String heroAttributes)
    {
        String[] savedHero = heroAttributes.split("[|]");

        //create the sql statement using hero attributes
        int err = tdb.modData("UPDATE playerFile SET hasInventory = " + savedHero[2] + ", score = " +
                savedHero[3] + ", health = " + savedHero[4] + " WHERE playerID = " + savedHero[0] + "");

        //if no rows were changed
        if (err == 0)
        {
            View.setControllerDisplay("There was an error in updating saved Hero Data to playerFile");
            return false;
        }
        else
        {
            View.setControllerDisplay("Successfully saved Hero profile to the database.");
        }
        return true;
    }
}