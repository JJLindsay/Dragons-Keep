package controller.actors;

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
 * This class represents Actor to database interactions
 *
 * Purpose: To parse data and formulate queries between Actors and the database
 */
public class ActorDB
{
//    //instance variables
    private ResultSet rs = null;
    private static Database tdb = new AccountDB().getDatabase();

    /**Retrieves a player's attributes
     * @param playerName The player's name
     * @return heroBuilder The hero and its attributes
     */
    public String loadHero(String playerName)
    {
        //creates a stringBuilder for the hero
        StringBuilder heroBuilder = new StringBuilder();
//        tdb = new Database();

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
    public String retrieveMonster(int monsterIndex)
    {
        //creates a stringBuilder for the monster attributes
        StringBuilder monsterBuilder = new StringBuilder();
//        tdb = new Database();

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
//
//    /**save the Hero ID, name, hasInventory, score, and health to the database
//     * @param heroAttributes The attributes of the player
//     * @return true Indicates Hero data was successfully saved
//     */
//    public Boolean saveHeroData(String heroAttributes)
//    {
//        tdb = new Database();
//        String[] savedHero = heroAttributes.split("[|]");
//        System.out.println("save#10 Inside saveHeroData: " + "UPDATE playerFile SET hasInventory = " + savedHero[2] + ", score = " +
//                savedHero[3] + ", health = " + savedHero[4] + " WHERE playerID = " + savedHero[0] + ""); //DEBUG CODE
//
//        //create the sql statement using hero attributes
//        int err = tdb.modData("UPDATE playerFile SET hasInventory = " + savedHero[2] + ", score = " +
//                savedHero[3] + ", health = " + savedHero[4] + " WHERE playerID = " + savedHero[0] + "");
//        System.out.println("save#9 Inside saveHeroData: " + err); //DEBUG CODE
//
//        //if no rows were changed
//        if (err == 0)
//        {
//            View.setControllerDisplay("There was an error in updating saved Hero Data to playerFile");
//            return false;
//        }
//        else
//        {
//            View.setControllerDisplay("Successfully saved Hero profile to the database.");
//        }
//        return true;
//    }

    private int shiKey = 1;


    /**save the Hero ID, name, hasInventory, score, and health to the database
     * @param heroAttributes The attributes of the player
     * @return true Indicates Hero data was successfully saved
     */
    public Boolean saveHeroData(String heroAttributes)
    {
        String[] savedHero = heroAttributes.split("[|]");
        System.out.println("save#11 in saveHeroData: " + savedHero[0]); //DEBUG CODE

        //create the sql statement using hero attributes
        int err = tdb.modData("UPDATE playerFile SET hasInventory = " + savedHero[2] + ", score = " +
                savedHero[3] + ", health = " + savedHero[4] + " WHERE playerID = " + savedHero[0] + "");

        //if no rows were changed
        if (err == 0)
        {
            System.err.println("There was an error in updating saved Hero Data to playerFile");
            return false;
        }
        else
        {
            System.out.println("Successfully saved Hero profile to the database.");
        }
        return true;
    }

    /**save the state of the rooms to the savedRooms table
     * @param roomsState The empty or not empty state of all the rooms
     * @return true Indicates the rooms were successfully saved in the database
     */
    public Boolean saveRoomState(String roomsState)
    {
        String[] rooms = roomsState.split("[|]");

        //inserts room 1-50
        String playerRoomsInsert = "Insert into playerRooms(playerID, currentRoom";
        for (int i = 1; i <= 50; i++)
        {
            playerRoomsInsert += ", room" + i;
        }
        playerRoomsInsert += ") values (" + rooms[0] + ", " + rooms[1];

        //inserts data for room 1-50
        for (int i = 1; i <= 50; i++)
        {
            playerRoomsInsert += ", " + rooms[i+1];
        }
        playerRoomsInsert += ")";
        int err = tdb.modData(playerRoomsInsert);


        //if no rows are updated
        if (err == 0)
        {
            playerRoomsInsert = "UPDATE playerRooms SET currentRoom = " + rooms[1] + ", room1  = " + rooms[2];
            for (int i = 2; i <= 50; i++)
            {
                playerRoomsInsert += ", room" + i + " = " + rooms[i+1];
            }
            playerRoomsInsert += " WHERE playerID = " + rooms[0];
            tdb.modData(playerRoomsInsert);
            System.out.println("Successfully saved the rooms to the database.");
        }
        else
        {
            System.out.println("Successfully saved the rooms to the database.");
        }
        return true;
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
        ResultSet rs = null;
        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from savedInventory");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getString("playerID").equalsIgnoreCase("" + playerID))
                {
                    int err = tdb.modData("DELETE FROM savedInventory WHERE playerID = " + playerID);
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
                        System.out.println("Successfully saved Hero inventory to the database.");
                        looping = false;
                    }
                }while(looping);
        }
        return true;
    }
}