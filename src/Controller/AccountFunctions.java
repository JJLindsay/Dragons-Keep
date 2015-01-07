package controller;

import controller.actors.Hero;
import controller.inventory.InventoryDB;
import controller.itemsAndPuzzle.ItemDB;
import controller.room.Rooms;
import controller.actors.ActorDB;
import controller.room.RoomsDB;
import view.View;

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
    private static Hero player; //original
    private static int loginOrCreateChoice;  // = getFromGameInteractions login decision
    private static boolean accountExist;

    //NEW
    private  static AccountDB accountDB;  //original
    private static Rooms rooms;  //original
    private  ActorDB actorDB;
    private  InventoryDB inventoryDB;
    private  RoomsDB roomsDB;
    private MenusAndMessages menusAndMessages = new View().getMenusAndMessages();


    /**This manages the login & create-account
     * @return a room description if successful or the game Title screen if the login/create-account is unsuccessful
     */
    public String loginCreate(String loginName)
    {
//        menusAndMessages = new MenusAndMessages();

        if (loginOrCreateChoice == 1)
        {
            accountDB = new AccountDB();  //do this once or don't do at all if 2 was done

            //verify user account exist
            if (accountDB.loginAccount(loginName))
            {
                //account existed prior
                accountExist = true;
                player = new Hero(loginName);

                rooms = new Rooms(player.getPlayerID(), true);  //CHANGED
//                roomsMap = rooms.getRoomsMap();  //NOT SURE WHY I NEED THIS

                return "Account located. Loading game..." + "\n" + menusAndMessages.enteredRoomMessage();
            } else
            {
                return "Error Could not locate your user name" + "\n" + menusAndMessages.titleScreen();
            }
        }
        else if (loginOrCreateChoice == 2)
        {
            accountDB = new AccountDB(); //do this once or don't do at all if 1 was done

            if (!accountDB.loginAccount(loginName))
            {
                //account did not exist prior
                accountExist = false;
                //defaults to playerID 0 until save is called
                player = new Hero(loginName, 0);

                rooms = new Rooms(0, false);  //CHANGED
//                roomsMap = rooms.getRoomsMap();  //NOT SURE WHY I NEED THIS

                return "Your account was created!" + "\n" + menusAndMessages.enteredRoomMessage();
            }
            return "Error creating your account. An account with that name already exist." + "\n" + menusAndMessages.titleScreen();
        }
        else
            return "Error understanding your response. Try again." + "\n" + menusAndMessages.titleScreen();
    }

    /**The method manages the player's request to save the game
     */
    public void saveGame()
    {
        System.out.println("save#1 " + player.getName()); //DEBUG CODE
        System.out.println("save#6 true: " + accountExist); //DEBUG CODE
        System.out.println("save#7 !null: " + player.getInventory()); //DEBUG CODE



        //Creates an Account in the database and sets the ID if the user did not login at the game's start
        if (!accountExist)
        {
            accountDB = new AccountDB();

            System.out.println("save#2 " + player.getName()); //DEBUG CODE = new Hero(loginName, 0);
            player.setPlayerID(accountDB.createAccount(player.getName()));
            accountExist = true;
        }

        System.out.println("save#9 "); //DEBUG CODE = new Hero(loginName, 0);

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
        System.out.println("save#2.5 Prior to 3"); //DEBUG CODE

        actorDB = new ActorDB();
        //saves the player ID, name, hasInventory, score, and health to the database
        actorDB.saveHeroData(heroData);
        System.out.println("save#3 hero data "); //DEBUG CODE

        inventoryDB = new InventoryDB();
        //saves the player's inventory
        inventoryDB.saveHeroInventory(player.getPlayerID(), player.getInventory().getRuckSack());
        System.out.println("save#4 hero inventory"); //DEBUG CODE

        //saves the state of all the rooms for this player
        String savedRooms = player.getPlayerID() + "|" + rooms.getCurrentRoomID();
        for (int i = 1; i <= 50;  i++)
        {
            if (rooms.getRoomsMap().get(i).getIsEmpty() == 0)
            {
                savedRooms += "|" + 0;  //false
            }
            else if (rooms.getRoomsMap().get(i).getIsEmpty() == 3)
            {
                savedRooms += "|" + 3;  //true and its been visited
            }
            else
            {
                savedRooms += "|" + 1;  //true and never visited
            }
        }
        roomsDB = new RoomsDB();
        roomsDB.saveRoomState(savedRooms);
        System.out.println("save#5 saved rooms "); //DEBUG CODE

    }

    /**
     * This sets the choice the player makes to login or create a game.
     * @param loginOrCreateChoice A 1 or 2 which corresponds to login or create account.
     */
    public void setLoginOrCreateChoice(int loginOrCreateChoice)
    {
        AccountFunctions.loginOrCreateChoice = loginOrCreateChoice;
    }

    /**
     * @return player A Hero object that provides central access to all player properties while in other classes
     */
    public Hero getHero()
    {
        return player;
    }

    /**
     * Gets the actual current room itself
     * @return
     */
    public Rooms getCurrentRoom()
    {
        return rooms.getCurrentRoom();
    }

    public Map<Integer, Rooms> getRoomsMap()
    {
        return rooms.getRoomsMap();
    }

    /**
     * Gets the room with the map in it
     * @return
     */
    public Rooms getRooms()
    {
        return rooms;
    }
}