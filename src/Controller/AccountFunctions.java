package controller;

import controller.actors.Hero;
import controller.inventory.InventoryDB;
import controller.room.BuildRooms;
import controller.room.Rooms;
import controller.actors.ActorDB;
import controller.room.RoomsDB;

import java.util.Map;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 1/2/2015
 *
 * This class represents account features.
 *
 * Purpose: To provide the ability to log-in, create, and save an account. In addition, getHero() enables one Hero
 * to be created and shared amongst all classes.
 */
public class AccountFunctions
{
    private static Hero player;
    private static int loginOrCreateChoice;
    private static boolean accountExist;
//    private static Rooms rooms;
    private static Map<Integer, Rooms> rooms;



    /**This manages the login & create-account
     * @return a room description if successful or the game Title screen if the login/create-account is unsuccessful
     */
    public static String loginCreate(String loginName)
    {
        System.out.println("4TH S0P: " + "entered loginCreate" );  //DEBUG

        if (1 == loginOrCreateChoice)
        {
            //verify user account exist
            if (AccountDB.loginAccount(loginName))
            {
                //account existed prior
                accountExist = true;
                player = new Hero(loginName);

                rooms = BuildRooms.buildRooms(player.getPlayerID(), true);  //CHANGED
                System.out.println("3rd SOP " + rooms.get(11).getRoomDescription());  //DEBUG

                return "Account located. Loading game..." + "\n\n" + MenusAndMessages.enteredRoomMessage();
            } else
            {
                return "ERROR! Could not locate your user name" + "\n\n" + MenusAndMessages.titleScreen();
            }
        }
        else if (2 == loginOrCreateChoice)
        {
            if (!AccountDB.loginAccount(loginName))
            {
                //account did not exist prior
                accountExist = false;
                //defaults to playerID 0 until save is called
                player = new Hero(loginName, 0);

                rooms = BuildRooms.buildRooms(0, false);  //CHANGED
                System.out.println("5th SOP " + rooms.get(11).getRoomDescription());  //DEBUG

                return "Your account was created!" + "\n\n" + MenusAndMessages.enteredRoomMessage();
            }
            return "ERROR! An account with that name already exist." + "\n\n" + MenusAndMessages.titleScreen();
        }
        else
            return "ERROR! Could not understand your response. Try again." + "\n\n" + MenusAndMessages.titleScreen();
    }

    /**The method manages the player's request to save the game
     */
    public static void saveGame()
    {
        //Creates an Account in the database and sets the ID if the user did not login at the game's start
        if (!accountExist)
        {
            player.setPlayerID(AccountDB.createAccount(player.getName()));
            accountExist = true;
        }

        //Prepares the players stats: ID, name, hasInventory, score, and health to be save in the db
        String heroData = player.getPlayerID() + "|" + player.getName() + "|";
        if (player.getInventory() != null)
        {
            heroData += "1" + "|";
        }
        else
        {
            heroData += "0" + "|";
        }
        heroData += player.getScore() + "|" + player.getHealth();

        //saves the player ID, name, hasInventory, score, and health to the database
        ActorDB.saveHeroData(heroData);

        //saves the player's inventory
        InventoryDB.saveHeroInventory(player.getPlayerID(), player.getInventory().getRuckSack());

        //saves the state of all the rooms for this player
        String savedRooms = player.getPlayerID() + "|" + Rooms.getCurrentRoom();
        for (int i = 1; i <= 50;  i++)
        {
            if (rooms.get(i).getIsEmpty() == 0)
            {
                savedRooms += "|" + 0;
            }
            else
            {
                savedRooms += "|" + 1;
            }
        }
        RoomsDB.saveRoomState(savedRooms);
    }

    /**
     * This sets the choice the player makes to login or create a game.
     * @param loginOrCreateChoice A 1 or 2 which corresponds to login or create account.
     */
    public static void setLoginOrCreateChoice(int loginOrCreateChoice)
    {
        AccountFunctions.loginOrCreateChoice = loginOrCreateChoice;
    }

    /**
     * @return player A Hero object that provides central access to all player properties while in other classes
     */
    public static Hero getHero()
    {
        return player;
    }

    public static Map<Integer, Rooms> getRoomsObj()
    {
        return rooms;
    }
}