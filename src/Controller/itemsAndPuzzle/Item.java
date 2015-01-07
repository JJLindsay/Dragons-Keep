package controller.itemsAndPuzzle;

/**
 * @author Everton Gardiner Jr.
 * @version 1.0
 * Course : ITEC 3860
 * Written: Nov 14, 2014
 *
 * This class illustrates how to create an Item object
 *
 * Purpose: To create an Item object
 */
abstract class Item
{
    //instance variables
	private String itemName;

    /**No argument constructor
     */
    public Item()
    {
        itemName = "";
    }

    /**One argument constructor
     * @param name The name of the actor
     */
    public Item(String name)
    {
        this.itemName = name;
    }

	/**
	 * Method: getItemName
	 * @return The current Item name of the object
	 */
	public String getItemName()
	{
		return itemName;
	}

    /**Sets the name of the actor
     * @param itemName The name of the actor
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
}