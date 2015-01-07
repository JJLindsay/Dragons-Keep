package controller.itemsAndPuzzle;

import controller.AccountDB;
import model.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/4/2015
 *
 * This class represents Puzzle to database interactions
 *
 * Purpose: To parse data from and formulate queries for the puzzle table in the database
 */
public class PuzzleDB
{
    private static Database tdb = new AccountDB().getDatabase();

    /**retrieves a particular puzzle
     * @param puzzleIndex the puzzle database ID
     * @return puzzleBuilder The puzzle and all its attributes
     */
    public String retrievePuzzle(int puzzleIndex)
    {
        //creates a stringBuilder for the puzzle attributes
        StringBuilder puzzleBuilder = new StringBuilder();

        try
        {
            //Query the database. Returns the results in a ResultSet
            ResultSet rs = tdb.query("Select * from puzzle");
            //Loop over the result set. next moves the cursor to the next record and returns the current record
            while(rs.next())
            {
                if (rs.getInt("puzzleID") == puzzleIndex)
                {
                    //formats the problem description with new lines
                    String[] problemHolder = rs.getString("problem").split("[+]");
                    String problem = problemHolder[0];
                    for (int u = 1; u < problemHolder.length; u++)
                    {
                        problem += "\n" + problemHolder[u];
                    }
                    puzzleBuilder.append(problem);

                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getString("solution"));
                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getString("successMessage"));
                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getString("failureMessage"));
                    puzzleBuilder.append("|");
                    puzzleBuilder.append(rs.getInt("isSolved"));
                }
            }
            rs.close();
        }
        catch(SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
        return puzzleBuilder.toString();
    }
}