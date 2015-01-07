package controller.itemsAndPuzzle;

import controller.AccountFunctions;
import controller.MenusAndMessages;
import controller.room.EmptyARoom;
import controller.room.Rooms;
import view.View;

/**
 * @author Everton Gardiner Jr. and JJ Lindsay
 * @version 2.0
 * Course : ITEC 3860 
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Puzzle object
 *
 * Purpose: To create a Puzzle object
 */
public class Puzzle
{
    //instance variables
    private String puzzle;
    private String solution;
    private String successMessage;
	private String failureMessage;
    private boolean failed = false;

    //NEW
    private static PuzzleDB puzzleDB = new PuzzleDB();  //original
    private static Rooms currentRoom = new AccountFunctions().getCurrentRoom();
    private static MenusAndMessages menusAndMessages = new View().getMenusAndMessages();

    /**No argument constructor
     */
	public Puzzle()
	{
        //create and puzzle from the db
        String[] dbPuzzle = puzzleDB.retrievePuzzle(currentRoom.getIsPuzzle()).split("[|]");

        this.puzzle = dbPuzzle[0];
        this.solution = dbPuzzle[1];
        this.successMessage = dbPuzzle[2];
		this.failureMessage = dbPuzzle[3];
    }

	/**
	 * Method: getSolution
	 * This method gets the solution of the Puzzle object
	 * @return The current solution of the Puzzle object
	 */
	public String getSolution()
	{
		return solution;
	}

    /**
	 * Method: getPuzzle
	 * This method returns the current puzzle of the Puzzle object
	 * @return The current puzzle of the Puzzle object
	 */
	public String getPuzzle()
	{
		return "puzzle: " + puzzle;
	}

    /**
	 * Method: getFailureMessage
	 * This method returns the current failure message of Puzzle object
	 * @return The current failure message of the Puzzle object
	 */
	public String getFailureMessage()
	{
		return failureMessage;
	}

    /**
	 * Method: getSuccessMessage
	 * This method returns the current success message of the Puzzle object
	 * @return The current success message of the Puzzle object
	 */
	public String getSuccessMessage()
	{
		return successMessage;
	}

    /**This method manages the interaction with the puzzle
     * @param userInput The user's response to the puzzle challenge
     * @return Success, failure, changeRooms, or roomSummary messages
     */
    public String solvePuzzle(String userInput)
    {
        if (!failed)
        {
            //compares the user solution to the correct answer
            if (getSolution().equalsIgnoreCase(userInput))  //cleared
            {
                currentRoom.setIsPuzzle(0);

                if (currentRoom.getIsMonster() > 0 || currentRoom.getIsArmor() > 0 || currentRoom.getIsWeapon() > 0 ||
                        currentRoom.getIsElixir() > 0)//did an item or monster appear?
                {
                    return getSuccessMessage() + menusAndMessages.roomSummaryMessage(); //"\n\nThere is an item to collect. Are you going to collect it? (yes/no)";
                }
                else //is the room completely empty?
                {
                    new EmptyARoom().setARoomEmpty();
                    return getSuccessMessage() + "\n\n" + menusAndMessages.changeRoomsMessage();
                }
            } else
            {
                failed = true;
                return getFailureMessage() + "\nTry puzzle again? (yes/no)";  //somehow based on this call puzzleMessage()
            }
        }
        else //for failing the puzzle
        {
            if (userInput.equalsIgnoreCase("yes"))  //try again  \\CLEARED
            {
                failed = false;
                return getPuzzle();
            } else if (userInput.equalsIgnoreCase("no"))  //don't try again  \\CLEARED
            {
                failed = false;
                return menusAndMessages.changeRoomsMessage();
            } else  //cleared
            {
                return "Your request was unclear. Check your spelling. \nTry the puzzle again? (yes/no)";
            }
        }
    }
}