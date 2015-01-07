package controller;

import controller.room.EmptyARoom;
import controller.room.Rooms;
import controller.actors.Hero;
import controller.itemsAndPuzzle.Armor;
import controller.itemsAndPuzzle.Elixir;
import controller.itemsAndPuzzle.Weapon;

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
    //NEW
    private static Rooms currentRoom;
    private static MenusAndMessages menusAndMessages;

    /**This method manages the collection of any given item found in a room
     */
    public String collectItem()
    {
        Hero player = new AccountFunctions().getHero();
        currentRoom = new AccountFunctions().getCurrentRoom();
        menusAndMessages = new MenusAndMessages();

        //collect the armor in the room
        if (currentRoom.getIsArmor() > 0)
        {
            Armor armor = new Armor();
            player.getInventory().add(armor);
            currentRoom.setIsArmor(0);
            new EmptyARoom().setARoomEmpty();

            return "You have found " + armor.getItemName() + " and added it to your inventory." + "\n\n" + menusAndMessages.changeRoomsMessage();
        }
        //collect the elixir in the room
        else if (currentRoom.getIsElixir() > 0)
        {
            Elixir elixir = new Elixir();
            player.getInventory().add(elixir);
            currentRoom.setIsElixir(0);
            new EmptyARoom().setARoomEmpty();

            return "You have found " + elixir.getItemName() + " and added it to your inventory." + "\n\n" + menusAndMessages.changeRoomsMessage();
        }
        //collect the weapon in the room
        else
        {
            Weapon weapon = new Weapon();
            player.getInventory().add(weapon);
            currentRoom.setIsWeapon(0);
            new EmptyARoom().setARoomEmpty();

            return "You have found " + weapon.getItemName() + " and added it to your inventory." + "\n\n" + menusAndMessages.changeRoomsMessage();
        }
    }
}