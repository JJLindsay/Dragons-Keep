package controller.room;

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
 * This class represents Rooms to database interactions
 *
 * Purpose: To parse data from and formulate queries for the playerRooms and room table in the database
 */
public class RoomsDB
{
    //instance variables
    private  ResultSet rs = null;
    private  Database tdb = new AccountDB().getDatabase();

    /**Retrieves the state of the saved rooms
     * @param playerID The player's database ID
     * @return savedRoomBuilder The saved rooms and its attributes
     */
    public String loadSavedRooms(int playerID)
    {
        //creates a stringBuilder for the savedRooms
        StringBuilder savedRoomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from playerRooms WHERE playerID = " + playerID);
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                savedRoomBuilder.append(rs.getInt("playerID"));
                savedRoomBuilder.append("|");
                savedRoomBuilder.append(rs.getInt("currentRoom"));

                //add saved rooms 1 - 50
                for (int x = 1; x <= 50; x++)
                {
                    savedRoomBuilder.append("|");
                    savedRoomBuilder.append(rs.getInt("room" + x));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return savedRoomBuilder.toString();
    }

    /**
     * @return roomBuilder All the rooms and their attributes
     */
    public String retrieveAllRooms()
    {
        //creates a stringBuilder for the rooms
        StringBuilder roomBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            rs = tdb.query("Select * from room");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                roomBuilder.append(rs.getInt("RoomID"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomE"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomN"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomS"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("roomW"));
                roomBuilder.append("|");

                //formats the description with new lines
                String[] temp = rs.getString("description").split("[+]");
                String str = temp[0];
                for (int u = 1; u < temp.length; u++)
                {
                    str += "\n" + temp[u];
                }
                roomBuilder.append(str);

                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("isEmpty"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Armor"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Elixir"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Weapon"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Monster"));
                roomBuilder.append("|");
                roomBuilder.append(rs.getInt("Puzzle"));
                roomBuilder.append("|");
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        roomBuilder.deleteCharAt(roomBuilder.lastIndexOf("|"));
        return roomBuilder.toString();
    }

    /**saves the state of the rooms to the savedRooms table
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
            View.setControllerDisplay("Successfully saved the rooms to the database.");

        }
        else
        {
            View.setControllerDisplay("Successfully saved the rooms to the database.");
        }
        return true;
    }
}