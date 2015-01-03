package Controller;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/2/2015
 *
 * This class represents Item collection
 *
 * Purpose: Allows an item in a room to be collected and added to the player's inventory
 */
public class ItemCollection
{
    //static instance variables
    private static Hero player = AccountFunctions.getHero();

    /**This method manages the collection of any given item found in a room
     */
    public static String collectItem()
    {
        //collect the armor in the room
        if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsArmor() > 0)
        {
            Armor armor = new Armor();
            player.getInventory().add(armor);
            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setIsArmor(0);

            return "You have found " + armor.getItemName() + " and added it to your inventory." + "\n\n" + MenusAndMessages.changeRoomsMessage();
        }
        //collect the elixir in the room
        else if (Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsElixir() > 0)
        {
            Elixir elixir = new Elixir();
            player.getInventory().add(elixir);
            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setIsElixir(0);

            return "You have found " + elixir.getItemName() + " and added it to your inventory." + "\n\n" + MenusAndMessages.changeRoomsMessage();
        }
        //collect the weapon in the room
        else
        {
            Weapon weapon = new Weapon();
            player.getInventory().add(weapon);
            Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).setIsWeapon(0);

            return "You have found " + weapon.getItemName() + " and added it to your inventory." + "\n\n" + MenusAndMessages.changeRoomsMessage();
        }
    }
}
