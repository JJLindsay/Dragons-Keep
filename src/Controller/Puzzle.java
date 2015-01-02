package Controller;

import Model.LoadEntity;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
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
    private int completedPuzzle;
    private boolean failed = false;

    /**Five arg constructor
     */
	public Puzzle()
	{
        //create and puzzle from the db
        String[] dbPuzzle = LoadEntity.retrievePuzzle(Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsPuzzle()).split("[|]");

        this.puzzle = dbPuzzle[0];
        this.solution = dbPuzzle[1];
        this.successMessage = dbPuzzle[2];
		this.failureMessage = dbPuzzle[3];
        this.completedPuzzle = Integer.parseInt(dbPuzzle[4]);
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

    /**This method manages the interaction with any given puzzle
     */
    public String solvePuzzle(String userInput)
    {
        if (!failed)
        {
            //compares the solution to the answer
            if (getSolution().equalsIgnoreCase(userInput))  //cleared
            {
                Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setIsPuzzle(0);

                if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsMonster() > 0)  //did a monster appear?
                {
                    return getSuccessMessage() + MenusAndMessages.roomSummaryMessage(); //"\n\nAre you going to fight the monster? (yes/no)";
                }
                else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsArmor() > 0 || Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsWeapon() > 0 ||
                        Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsElixir() > 0)  //did an item appear?
                {
                    return getSuccessMessage() + MenusAndMessages.roomSummaryMessage(); //"\n\nThere is an item to collect. Are you going to collect it? (yes/no)";
                }
                else  //is the room completely empty?
                {
                    return getSuccessMessage() + "\n\n" + MenusAndMessages.changeRoomsMessage();
                }
            } else
            {
                failed = true;
                return getFailureMessage() + "\nTry puzzle again? (yes/no)";  //somehow based on this call puzzleMessage()
            }
        }
        else
        {
            if (userInput.equalsIgnoreCase("yes"))  //try again  \\CLEARED
            {
                failed = false;
                return getPuzzle();
            } else if (userInput.equalsIgnoreCase("no"))  //don't try again  \\CLEARED
            {
                failed = false;
                return MenusAndMessages.changeRoomsMessage();
            } else  //cleared
            {
                return "Your request was unclear. Check your spelling. \nTry the puzzle again? (yes/no)";
            }
        }
    }
}
