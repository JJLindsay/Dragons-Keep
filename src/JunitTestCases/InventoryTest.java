package junitTestCases;

import controller.AccountFunctions;
import controller.inventory.Inventory;
import controller.itemsAndPuzzle.Weapon;
import controller.itemsAndPuzzle.Armor;
import controller.itemsAndPuzzle.Elixir;

import controller.room.Rooms;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents an Inventory JUnit Test.
 *
 * Purpose: Tests all the methods in the Inventory class.
 */
public class InventoryTest
{
    Inventory inventory;
    Weapon weapon;
    Armor armor;
    Elixir elixir;

    @Before
    public void setUp() throws Exception
    {
        AccountFunctions.setLoginOrCreateChoice(2);
        AccountFunctions.loginCreate("InventoryTestName");

        inventory = new Inventory();

        Rooms.setCurrentRoomID(3);
        weapon = new Weapon();
        inventory.add(weapon);

        Rooms.setCurrentRoomID(25);
        armor = new Armor();
        inventory.add(armor);

        Rooms.setCurrentRoomID(6);
        elixir = new Elixir();
        inventory.add(elixir);
    }

    @Test
    public void testAddWeapon() throws Exception
    {
        assertTrue(inventory.add(weapon));
    }

    @Test
    public void testAddArmor() throws Exception
    {
        assertTrue(inventory.add(armor));
    }

    @Test
    public void testAddElixir() throws Exception
    {
        assertTrue(inventory.add(elixir));
    }

    @Test
    public void testGetWeapon() throws Exception
    {
        assertEquals(weapon, inventory.getWeapon("dagger"));

        assertNotEquals(weapon, inventory.getWeapon(null));
        assertNotEquals(weapon, inventory.getWeapon(""));
        assertNotEquals(weapon, inventory.getWeapon(" "));
    }

    @Test
    public void testGetElixir() throws Exception
    {
        assertEquals(elixir, inventory.getElixir("Blue Elixir"));

        assertNotEquals(elixir, inventory.getWeapon(null));
        assertNotEquals(elixir, inventory.getWeapon(""));
        assertNotEquals(elixir, inventory.getWeapon(" "));
    }

    @Test
    public void testGetArmor() throws Exception
    {
        assertEquals(armor, inventory.getArmor("Dragon Scales"));

        assertNotEquals(armor, inventory.getArmor(null));
        assertNotEquals(armor, inventory.getArmor(""));
        assertNotEquals(armor, inventory.getArmor(" "));
    }

    @Test
    public void testConfirmItem() throws Exception
    {
        assertTrue(inventory.confirmItem("dagger"));
        assertTrue(inventory.confirmItem("Blue Elixir"));
        assertTrue(inventory.confirmItem("Dragon Scales"));

        assertFalse(inventory.confirmItem("weapon"));
        assertFalse(inventory.confirmItem("elixir"));
        assertFalse(inventory.confirmItem("armor"));

        assertFalse(inventory.confirmItem(null));
        assertFalse(inventory.confirmItem(""));
        assertFalse(inventory.confirmItem(" "));
    }

    @Test
    public void testGetItemType() throws Exception
    {
        inventory.view();

        assertEquals("w", inventory.getItemType("dagger"));
        assertEquals("e", inventory.getItemType("Blue Elixir"));
        assertEquals("a", inventory.getItemType("Dragon Scales"));

        assertNotEquals("a", inventory.getItemType("dagger"));
        assertNotEquals("w", inventory.getItemType("Blue Elixir"));
        assertNotEquals("e", inventory.getItemType("Dragon Scales"));

        assertNotEquals("a", inventory.getItemType(null));
        assertNotEquals("w", inventory.getItemType(""));
        assertNotEquals("e", inventory.getItemType(" "));
    }

    @Test
    public void testRemove() throws Exception
    {
        assertTrue(inventory.remove("dagger"));
        assertTrue(inventory.remove("Blue Elixir"));
        assertTrue(inventory.remove("Dragon Scales"));

        assertFalse(inventory.remove("armorIV"));
        assertFalse(inventory.remove(null));
        assertFalse(inventory.remove(""));
        assertFalse(inventory.remove(" "));
    }

    @Test
    public void testRemoveQuery() throws Exception
    {
        assertTrue(inventory.remove("Blue Elixir"));

        assertNotEquals("e", inventory.getItemType("Blue Elixir"));
        assertFalse(inventory.confirmItem("Blue Elixir"));

        assertTrue(inventory.confirmItem("dagger"));
        assertTrue(inventory.confirmItem("Dragon Scales"));

        assertEquals("w", inventory.getItemType("dagger"));
        assertEquals("a", inventory.getItemType("Dragon Scales"));
    }
}