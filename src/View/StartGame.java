package view;

import controller.AccountFunctions;
import controller.itemsAndPuzzle.Weapon;

import java.util.Arrays;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/1/2015
 *
 * This class starts the actual game
 *
 * Purpose: Start the game without revealing its mechanics
 */
public class StartGame
{
    public static void main(String[] args)
    {
        int num = 1;
        AccountFunctions.setLoginOrCreateChoice(2);
        AccountFunctions.loginCreate("InventoryTestName");

        System.out.println("Here is start SOP " + AccountFunctions.getRoomsObj().get(5).getIsWeapon());

        Weapon weapon = new Weapon();
//        while(num <= 50)
//        {
//            System.out.println("Here is start SOP " + AccountFunctions.getRoomsObj().get(num).getIsWeapon());
//            num++;
//        }
//        View view = new View();
//        view.gameMenus();
    }
}
