package Controller;

import Model.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * author: JJ Lindsay
 * version: 4.2.1
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a game user interface
 *
 * Purpose: Allows a player to play the game and interact with other classes and objects.
 */
public class Game
{
    //static instance variables
    private static Hero player;
    private static Armor armor;
    private static Weapon weapon;
    private static Elixir elixir;
    private static int currentRoom;
    private static Map<Integer,Rooms> roomsMap; //Map<room, roomObj>
    private static Boolean loginResults = false;
    private static Boolean accountSaved = false;
    private static int loginCreate;

    //NEW
    private static Puzzle puzzle;
    private static Monster monster;
    private static boolean failed = false;

    /**This manages the login & create-account interface
     * @return a room description or the game intro for successful or unsuccessful
     * login/create-account attempts
     */
    public static String loginCreate(String loginName)
    {
        if (1 == loginCreate)
        {
            //verify user account exist
            if (Database.loginAccount(loginName))
            {
                //get saved player data
                String[] loginDetails = LoadEntity.loadHero(loginName).split("[|]");
                //create player with ID, name, score, and health from saved information
                player = new Hero(Integer.parseInt(loginDetails[0]), loginDetails[1], Integer.parseInt(loginDetails[3]), Integer.parseInt(loginDetails[4]));

                //check for an inventory
                if (!loginDetails[2].equalsIgnoreCase("0"))
                {
                    int p = 0;
                    player.createInventory();
                    String[] heroInventory = LoadEntity.loadHeroInventory(player.getPlayerID()).split("[|]");

                    while (p < heroInventory.length - 2)
                    {
                        //checks if a weaponID exists
                        if (!heroInventory[p].equalsIgnoreCase("0"))
                        {
                            //get the weapon from the database
                            String[] dbWeapon = LoadEntity.retrieveWeapon(Integer.parseInt(heroInventory[p])).split("[|]");
                            //builds an weapon(name, strength)
                            weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
                            //adds it to inventory
                            player.getInventory().add(weapon);
                        }
                        //checks if a armorID exists
                        else if (!heroInventory[p + 1].equalsIgnoreCase("0"))
                        {
                            //get the armor from the database
                            String[] dbArmor = LoadEntity.retrieveArmor(Integer.parseInt(heroInventory[p + 1])).split("[|]");
                            //builds an armor(name, defenseBoost)
                            armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
                            //adds it to inventory
                            player.getInventory().add(armor);
                        }
                        //checks if a elixirID exists
                        else if (!heroInventory[p + 2].equalsIgnoreCase("0"))
                        {
                            //get the elixir from the database
                            String[] dbElixir = LoadEntity.retrieveElixir(Integer.parseInt(heroInventory[p + 2])).split("[|]");
                            //builds an elixir(name, healthBoost)
                            elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
                            //adds it to inventory
                            player.getInventory().add(elixir);
                        }
                        p += 3;
                    }
                }
                loadGame();
                return "Account located. Loading game..." + "\n\n" + enteredRoomMessage();
            } else
            {
                return "ERROR! Could not locate your user name" + "\n\n" + gameIntro();
            }
        }
        else if (2 == loginCreate)
        {
            if (!Database.loginAccount(loginName))
            {
                //defaults to playerID 0 until save is called
                player = new Hero(loginName, 0);
                loadGame();
                return "Your account was created!" + "\n\n" + enteredRoomMessage();
            }
            return "ERROR! An account with that name already exist." + "\n\n" + gameIntro();
        }
        else
            return "ERROR! Could not understand your response. Try again." + "\n\n" + gameIntro();
    }

    public static String gameIntro()
    {
        return "Welcome to Dragon's Keep!" +
                "\nEnter 1 to login or 2 to create a new account";  //Game welcome message
    }

    /**This method welcomes the player and points to login() or CreateAccount() depending on the player's actions
     */
    public static String playGame(String userInput)
    {
        if (userInput.equalsIgnoreCase("1"))
        {
            loginCreate = 1;
            return "Enter your user name: ";
        }
        else if (userInput.equalsIgnoreCase("2"))
        {
            loginCreate = 2;
            return "Enter a name you would like to create for your account: ";
        }
        else
        {
            return "Error interpreting your last request" + "\n\n" + gameIntro();
        }
    }

    private static String quitGameMessage()
    {
        return "Do you want to save your game before closing? (yes/no)";
    }

    /**This method handles the player's request to quit the game
     */
    public static String quitGame(String userInput)
    {
        if(null != userInput && userInput.equalsIgnoreCase("yes"))
        {
            saveGame();
            System.exit(0);
        }
        else if(null != userInput && userInput.equalsIgnoreCase("no"))
        {
            System.exit(0);
        }
        else
        {
            return "Error interpreting your last request." + "\n\n" + quitGameMessage();
        }
        return null;
    }

    /**The method manages the player's request to save the game
     */
    public static void saveGame()
    {
        //an Account is created in the database and the ID is set if the user did not login
        if (!loginResults && !accountSaved)
        {
            player.setPlayerID(Database.createAccount(player.getName()));
            accountSaved = true;
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
        UpdateEntity.saveHeroData(heroData);  //testing with fixed values

        //saves the player's inventory
        UpdateEntity.saveHeroInventory(player.getPlayerID(), player.getInventory().getRuckSack());

        //saves the state of all the rooms for this player
        String savedRooms = player.getPlayerID() + "|" + currentRoom;
        for (int i = 1; i <= 50;  i++)
        {
            if (roomsMap.get(i).getIsEmpty() == 0)
            {
                savedRooms += "|" + 0;
            }
            else
            {
                savedRooms += "|" + 1;
            }
        }
        UpdateEntity.saveRoomState(savedRooms);
    }

    /**This method loads the game one a save has been retrieved successfully or an account has been created
     */
    private static void loadGame()
    {
        //Loads all the rooms into an array
        String[] allRooms = LoadEntity.retrieveAllRooms().split("[|]");
        String[] temp = new String[4];
        //creates a rooms Map using the roomID as the key and the room as the object
        roomsMap = new TreeMap<Integer, Rooms>();

        //loops through the rooms array to create a room object before adding the room object to the roomsMap
        for (int i = 0; i < allRooms.length-11; i= i+12)
        {
            Rooms rooms = new Rooms();
            temp[0] = allRooms[i+1];
            temp[1] = allRooms[i+2];
            temp[2] = allRooms[i+3];
            temp[3] = allRooms[i+4];
            rooms.setChoices(temp);

            rooms.setRoomDescription(allRooms[i + 5]);  //THIS will CHANGE FOR LOGIN
            rooms.setIsEmpty(Integer.parseInt(allRooms[i + 6]));  //THIS will CHANGE FOR LOGIN
            rooms.setIsArmor(Integer.parseInt(allRooms[i + 7])); //points to the specific armor
            rooms.setIsElixir(Integer.parseInt(allRooms[i + 8])); //points to the specific elixir
            rooms.setIsWeapon(Integer.parseInt(allRooms[i + 9])); //points to the specific weapon
            rooms.setIsMonster(Integer.parseInt(allRooms[i + 10])); //points to the specific monster
            rooms.setIsPuzzle(Integer.parseInt(allRooms[i + 11])); //points to the specific puzzle

            //converts roomID from string to int and stores roomID as key and the room created in a map
            roomsMap.put(Integer.parseInt(allRooms[i]), rooms);
        }
        currentRoom = 1;

        //user successfully logged into saved account
        if (loginResults)
        {
            String[] savedRooms = LoadEntity.loadSavedRooms(player.getPlayerID()).split("[|]");

            if (Integer.parseInt(savedRooms[0]) == player.getPlayerID())
            {
                //retrieves the last recorded current room for this playerID
                currentRoom = Integer.parseInt(savedRooms[1]);

                //is the room empty (1 for true, 0 for false)
                for (int y = 2; y <= currentRoom+1; y++)
                {
                    //if the room is empty
                    if (Integer.parseInt(savedRooms[y]) == 1)
                    {
                        roomsMap.get(y-1).setRoomDescription("This room is empty... and it looks a bit familiar");
                        roomsMap.get(y-1).setIsEmpty(1);
                        roomsMap.get(y-1).setIsArmor(0);
                        roomsMap.get(y-1).setIsElixir(0);
                        roomsMap.get(y-1).setIsWeapon(0);
                        roomsMap.get(y-1).setIsPuzzle(0);
                    }
                }
            }
        }
    }

    private static String battleMessage()
    {
        //create monster
        String[] dbMonster = LoadEntity.retrieveMonster(roomsMap.get(currentRoom).getIsMonster()).split("[|]");
        monster = new Monster(dbMonster[0], Integer.parseInt(dbMonster[1]), Integer.parseInt(dbMonster[2]));

        String battleMessage = "*****************************************";
        //prints a pre-fight menu
        battleMessage += "\n-----------------------------------------";
        battleMessage += "\nEnter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                "\nEnter \"remove item name\" to throw away an item. \nEnter \"attack\" to start the fight. \nEnter \"run away\" to escape.";
        battleMessage += "\n-----------------------------------------";
        battleMessage += "\nYour health is currently: " + player.getHealth();
        battleMessage += "\n" + monster.getName() + "'s health is: " + monster.getHealth();
        battleMessage += "\n-----------------------------------------";

        return battleMessage;
    }


    /**This method handles the fight interactions between the player and an enemy
     */
    public static String battle(String userInput)
    {
        //compares the user response against possible choices
        if (userInput.length() > 5)
        {
            if (userInput.equalsIgnoreCase("inventory"))  //cleared
            {
                return player.getInventory().view() + "\n" + battleMessage();
            }
            else if (userInput.length() > 5 && userInput.substring(0, 5).equalsIgnoreCase("equip"))
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(6)))
                {
                    //identify the item as a weapon, armor, or elixir by its itemType
                    if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("w"))
                    {
                        player.setAttackPower(player.getInventory().getWeapon(userInput.substring(6)).getStrength());
                        return "You have drawn your " + userInput.substring(6) + "\n\n" + battleMessage();
                    } else if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("a"))
                    {
                        player.setDefenseStrength(player.getInventory().getArmor(userInput.substring(6)).getArmorDefense());
                        return "You have put on the " + userInput.substring(6) + "\n\n" + battleMessage();
                    } else //if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("e"))
                    {
                        player.setHealth(player.getHealth() + player.getInventory().getElixir(userInput.substring(6)).getHealthBoost());
                        player.getInventory().remove(userInput.substring(6));
                        return "You drank all of the " + userInput.substring(6) + "\n\n" + battleMessage();
                    }
                } else
                {
                    return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + battleMessage();
                }
            }
            else if (userInput.length() > 6 && userInput.substring(0, 6).equalsIgnoreCase("remove"))
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(7)))
                {
                    //remove item from inventory
                    player.getInventory().remove(userInput.substring(7));
                    return "You removed " + userInput.substring(7) + " from your inventory" + "\n\n" + battleMessage();

                } else
                {
                    return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + battleMessage();
                }
            }
            else if (userInput.equalsIgnoreCase("attack"))
            {
                String attackResults;

                //player attacks first
                if (monster.getHealth() - player.getAttackPower() > 0)  //monster lives
                {
                    monster.setHealth(monster.getHealth() - player.getAttackPower());
                    attackResults = "You lunged at " + monster.getName() + " but your attack wasn't good enough to bring'em down.";
                } else  //monster dies
                {
                    attackResults = "You dealt a deadly blow with that last move! You killed " + monster.getName() + ".";
                    //set monster to zero for this room
                    roomsMap.get(currentRoom).setIsMonster(0);

                    //Game won message
                    if (50 == currentRoom)
                    {
                        attackResults += "\nYou have rid Dragons Keep of its corrupted emperor. Thanks to you " + player.getName() + " what evil" +
                        " was brewing just beneath the city wont be unleashed anytime soon...";
                        attackResults += "\n\n-----------------------------------------------------------------------\n";
                        attackResults += "\nCongratulations on your completion of Dragons Keep." +
                        "\nIt was a pleasure making this and we hope you enjoyed it! \n Like a challenge? Try completing all 50 rooms and solving all puzzles.";

                        return attackResults + "\n\n" + quitGameMessage();
                    }

                    if (roomsMap.get(currentRoom).getIsArmor() > 0 || roomsMap.get(currentRoom).getIsWeapon() > 0 ||
                            roomsMap.get(currentRoom).getIsElixir() > 0)  //did an item appear?
                    {
                        return attackResults + "\n\nThere is an item to collect. Do you want to collect it? (yes/no)";
                    }
                    return attackResults + "\n\n" + changeRoomsMessage();
                }

                //Monster retaliates
                attackResults += "\nYour last attack didn't defeat " + monster.getName() + " and you've been hit with a counter-attack.";
                if ((player.getHealth() + player.getDefenseStrength())   - monster.getAttackPower() > 0)  //player didn't die
                {
                    if ((player.getHealth() + player.getDefenseStrength())  - monster.getAttackPower() <= player.getHealth())  //armor was lower than enemy attack power
                    {
                        player.setHealth((player.getHealth() + player.getDefenseStrength()) - monster.getAttackPower());
                    }
                    return attackResults + "\n\n" + battleMessage();
                } else  //player dies  ------cleared
                {
                    attackResults +=  "\n\nYour losing a lot of blood, you don't know how." + monster.getName() + "attacked you so fast! \nYour stumbling towards the door..." +
                            "\nyou've got to get out of here, you think to yourself.\nIt's no use. You collapse on the ground before even reaching door. Just before everything" +
                            " goes black, you think of the\npeople who were depending on you, and with your last breath you whisper I'm sorry.";
                    attackResults += "\n*****************************************\n";
                    attackResults += "\n---------------GAME OVER-----------------\n";
                    attackResults += "\n*****************************************";

                    return attackResults + "\n\n" + gameIntro();
                }
            } else if (userInput.equalsIgnoreCase("run away"))
            {
                monster.setHealth(monster.getHealth() + 5);
                return "You ran away screaming; your heart is racing." +
                        "\nRefusing to look back, you think to yourself, that monster can't POSSIBLY get any stronger..." + "\n\n" + changeRoomsMessage();
            } else
            {
                return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + battleMessage();
            }
        }
        else
        {
            return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + battleMessage();
        }
    }

    /**This method sets a room as empty once it has been visited and there is nothing more to do in the room
     */
    private static void setEmptyRoom()
    {
        //checks if every interaction with this particular room is set to 0
        if (roomsMap.get(currentRoom).getIsMonster() == 0 && roomsMap.get(currentRoom).getIsPuzzle() == 0 &&
            roomsMap.get(currentRoom).getIsArmor() == 0 && roomsMap.get(currentRoom).getIsWeapon() == 0 &&
            roomsMap.get(currentRoom).getIsElixir() == 0)
        {
            roomsMap.get(currentRoom).setIsEmpty(1);

            //Adds direction to the empty rooms
            String roomDirection = "<";
            //checks the 4 possible exits
            for (int x = 0; x < 4; x++)
            {
                //if an exit exist
                if (Integer.parseInt(roomsMap.get(currentRoom).getChoices()[x]) != 0)
                {
                    if (x == 0)
                    {
                        roomDirection += "E";
                    } else if (x == 1)
                    {
                        roomDirection += "N";
                    } else if (x == 2)
                    {
                        roomDirection += "S";
                    } else
                    {
                        roomDirection += "W";
                    }
                }
            }
            roomDirection += ">";
            roomsMap.get(currentRoom).setRoomDescription(roomDirection + " This room is empty... and it looks a bit familiar.");
        }
    }

    /**This method manages the collection of any given item found in a room
     */
    private static String collectItem()
    {
        //collect the armor in the room
        if (roomsMap.get(currentRoom).getIsArmor() > 0)
        {
            //get an armor from the database
            String[] dbArmor = LoadEntity.retrieveArmor(roomsMap.get(currentRoom).getIsArmor()).split("[|]");
            //builds an armor(name, defenseBoost)
            armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
            player.getInventory().add(armor);
            roomsMap.get(currentRoom).setIsArmor(0);

            return "You have found " + dbArmor[0] + " and added it to your inventory." + "\n\n" + changeRoomsMessage();
        }
        //collect the elixir in the room
        else if (roomsMap.get(currentRoom).getIsElixir() > 0)
        {
            //get an elixir from the database
            String[] dbElixir = LoadEntity.retrieveElixir(roomsMap.get(currentRoom).getIsElixir()).split("[|]");
            //builds an elixir(name, healthBoost)
            elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
            player.getInventory().add(elixir);
            roomsMap.get(currentRoom).setIsElixir(0);

            return "You have found " + dbElixir[0] + " and added it to your inventory." + "\n\n" + changeRoomsMessage();
        }
        //collect the weapon in the room
        else
        {
            //get an weapon from the database
            String[] dbWeapon = LoadEntity.retrieveWeapon(roomsMap.get(currentRoom).getIsWeapon()).split("[|]");
            //builds an weapon(name, strength)
            weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));
            player.getInventory().add(weapon);
            roomsMap.get(currentRoom).setIsWeapon(0);

            return "You have found " + dbWeapon[0] + " and added it to your inventory." + "\n\n" + changeRoomsMessage();
        }
    }

    private static String puzzleMessage()
    {
        //create and puzzle from the db
        String[] dbPuzzle = LoadEntity.retrievePuzzle(roomsMap.get(currentRoom).getIsPuzzle()).split("[|]");
        puzzle = new Puzzle(dbPuzzle[0], dbPuzzle[1], dbPuzzle[2], dbPuzzle[3], Integer.parseInt(dbPuzzle[4]));

        //returns the puzzle description
        return "Puzzle: " + puzzle.getPuzzle();  //somehow based on this call solvePuzzle()
    }

    /**This method manages the interaction with any given puzzle
     */
    public static String solvePuzzle(String userInput)
    {
       if (!failed)
       {
           //compares the solution to the answer
           if (puzzle.getSolution().equalsIgnoreCase(userInput))  //cleared
           {
               roomsMap.get(currentRoom).setIsPuzzle(0);

               if (roomsMap.get(currentRoom).getIsMonster() > 0)  //did a monster appear?
               {
                   return puzzle.getSuccessMessage() + roomSummaryMessage(); //"\n\nAre you going to fight the monster? (yes/no)";
               }
               else if (roomsMap.get(currentRoom).getIsArmor() > 0 || roomsMap.get(currentRoom).getIsWeapon() > 0 ||
                       roomsMap.get(currentRoom).getIsElixir() > 0)  //did an item appear?
               {
                   return puzzle.getSuccessMessage() + roomSummaryMessage(); //"\n\nThere is an item to collect. Are you going to collect it? (yes/no)";
               }
               else  //is the room completely empty?
               {
                   return puzzle.getSuccessMessage() + "\n\n" + changeRoomsMessage();
               }
           } else
           {
               failed = true;
               return puzzle.getFailureMessage() + "\nTry puzzle again? (yes/no)";  //somehow based on this call puzzleMessage()
           }
       }
       else
       {
           if (userInput.equalsIgnoreCase("yes"))  //try again  \\CLEARED
           {
               failed = false;
               return puzzleMessage();
           } else if (userInput.equalsIgnoreCase("no"))  //don't try again  \\CLEARED
           {
               failed = false;
               return changeRoomsMessage();
           } else  //cleared
           {
               return "Your request was unclear. Check your spelling. \nTry the puzzle again? (yes/no)";
           }
       }
    }

    private static String gameMenuMessage()
    {
        String message = "-----------------------------------------";
        message += "\nEnter \"inventory\" to check inventory. \nEnter \"equip item name\" to equip a specific item in inventory." +
                "\nEnter \"remove item name\" to throw away an item. \nEnter \"save\" to save your game. \nEnter \"quit\" to quit the game. \nEnter \"exit\" to return to game";
        message += "\n-----------------------------------------";
        message += "\nYour health is currently: " + player.getHealth();
        message += "\n-----------------------------------------";

        return message;
    }

    /**This is the in game menu the player can call at any time
     */
    public static String gameMenu(String userInput)
    {
        if (userInput.equalsIgnoreCase("inventory"))  //cleared
        {
            if (player.getInventory() == null)
            {
                return "Opps! You don't have an inventory." + "\n\n" + gameMenuMessage();
            }
            else
            {
                return player.getInventory().view() + "\n" + gameMenuMessage();
            }
        }
        else if (userInput.equalsIgnoreCase("exit"))  //cleared
        {
            return changeRoomsMessage();
        }
        else if (userInput.equalsIgnoreCase("save"))  //cleared
        {
            saveGame();
            return "Saved game successfully" + "\n\n" + gameMenuMessage();
        }
        else if (userInput.equalsIgnoreCase("quit"))  //cleared
        {
            return quitGameMessage();
        }
        else if (userInput.length() > 5 && userInput.substring(0, 5).equalsIgnoreCase("equip"))
        {
            if (player.getInventory() == null)  //cleared
            {
                return "Opps! You don't have an inventory." + "\n\n" + gameMenuMessage();
            }
            else  //cleared
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(6)))
                {
                    //identify the item as a weapon, armor, or elixir by its itemType
                    if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("w"))
                    {
                        player.setAttackPower(player.getInventory().getWeapon(userInput.substring(6)).getStrength());
                        return "You are drawing your " + userInput.substring(6) + "\n\n" + gameMenuMessage();
                    } else if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("a"))
                    {
                        player.setDefenseStrength(player.getInventory().getArmor(userInput.substring(6)).getArmorDefense());
                        return "You are putting on the " + userInput.substring(6) + "\n\n" + gameMenuMessage();
                    } else //if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("e"))
                    {
                        player.setHealth(player.getHealth() + player.getInventory().getElixir(userInput.substring(6)).getHealthBoost());
                        player.getInventory().remove(userInput.substring(6));
                        return "You're drinking the " + userInput.substring(6) + "\n\n" + gameMenuMessage();
                    }
                } else  //cleared
                {
                    return "There was an error in understanding your request. Check your spelling."
                            + "\n\n" + gameMenuMessage();
                }
            }
        }
        //removes an item from inventory
        else if(userInput.length() > 6 && userInput.substring(0, 6).equalsIgnoreCase("remove"))
        {
            if (player.getInventory() == null)  //cleared
            {
                return "Opps! You don't have an inventory." + "\n\n" + gameMenuMessage();
            }
            else
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(7)))
                {
                    //remove item from inventory
                    player.getInventory().remove(userInput.substring(7));
                    return "You have successfully removed " + userInput.substring(7) + " from your inventory"
                            + "\n\n" + gameMenuMessage();

                } else  //cleared
                {
                    return "There was an error in understanding your request. Check your spelling."
                            + "\n\n" + gameMenuMessage();
                }
            }
        }
        else  //cleared
        {
            return "There was an error in understanding your request. Check your spelling."
                    + "\n\n" + gameMenuMessage();
        }
    }


    private static String changeRoomsMessage()
    {
        //display possible exits
        String roomDirection = "<";
        //checks which of 4 possible exits this particular room has
        for (int x = 0; x < 4; x++)
        {
            //if an exit exist
            if (Integer.parseInt(roomsMap.get(currentRoom).getChoices()[x]) != 0)
            {
                if (x == 0)
                {
                    roomDirection += "E";
                } else if (x == 1)
                {
                    roomDirection += "N";
                } else if (x == 2)
                {
                    roomDirection += "S";
                } else
                {
                    roomDirection += "W";
                }
            }
        }
        roomDirection += "> ";

        return roomDirection + "Where would you like to go next or enter \"menu\" to pull up the game menu.";
    }

    /**This manages the changing from one room to another
     */
    public static String changeRooms(String userInput)
    {
        //Prompts the user to change to a new room or enter the menu screen
            if (userInput.equalsIgnoreCase("menu"))
            {
                return gameMenuMessage();
            }
            else if (userInput.equalsIgnoreCase("head East") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]);
                return enteredRoomMessage();
            } else if (userInput.equalsIgnoreCase("head NORTH") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]);
                return enteredRoomMessage();
            } else if (userInput.equalsIgnoreCase("head South") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]);
                return enteredRoomMessage();
            } else if (userInput.equalsIgnoreCase("head West") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]) != 0)
            {
                currentRoom = Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]);
                return enteredRoomMessage();
            }
            // if the user correctly enters a direction but there is no room in that direction.
            else if (userInput.equalsIgnoreCase("head East") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[0]) == 0 ||
                    userInput.equalsIgnoreCase("head NORTH") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[1]) == 0 ||
                    userInput.equalsIgnoreCase("head South") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[2]) == 0 ||
                    userInput.equalsIgnoreCase("head West") && Integer.parseInt(roomsMap.get(currentRoom).getChoices()[3]) == 0)
            {
                return "This is embarrassing. You entered: " + userInput + " and walked into a wall. There is no door"
                + " that way. Try again." + "\n\n" + changeRoomsMessage();
            }
            // the user entered something else
            else
            {
                return "There was an error in trying to make sense of you request. Check your spelling and try again." +
                       "\n\n" + changeRoomsMessage();
            }
    }

    private static String enteredRoomMessage()
    {
        if (2 == currentRoom && player.getInventory() == null)
        {
            return roomsMap.get(currentRoom).getRoomDescription() +
                    "\nAre you going to collect the rucksack? (yes/no)";
        }
        else if (roomsMap.get(currentRoom).getIsPuzzle() > 0){
            return roomsMap.get(currentRoom).getRoomDescription() +
                    "\nAre you going to attempt this puzzle? (yes/no)";
        }
        //checks if an monster is in the room
        else if (roomsMap.get(currentRoom).getIsMonster() > 0)
        {
            return roomsMap.get(currentRoom).getRoomDescription() +
                    "\nAre you going to fight the monster? (yes/no)";
        }
        //checks if an item is in the room
        else if (roomsMap.get(currentRoom).getIsArmor() > 0 || roomsMap.get(currentRoom).getIsWeapon() > 0 ||
                roomsMap.get(currentRoom).getIsElixir() > 0)
        {
            return roomsMap.get(currentRoom).getRoomDescription() +
                    "\nThere is an item to collect. Are you going to collect it? (yes/no)";
        }
        else  //if the room is empty
        {
            //Displays the current room description
            return roomsMap.get(currentRoom).getRoomDescription() + "\n\n" + changeRoomsMessage();
        }
    }

    private static String roomSummaryMessage()
    {
        if (2 == currentRoom && player.getInventory() == null)
        {
            return "\nAre you going to collect the rucksack? (yes/no)";
        }
        else if (roomsMap.get(currentRoom).getIsPuzzle() > 0){
            return "\nAre you going to attempt this puzzle? (yes/no)";
        }
        //checks if an monster is in the room
        else if (roomsMap.get(currentRoom).getIsMonster() > 0)
        {
            return "\nAre you going to fight the monster? (yes/no)";
        }
        //checks if an item is in the room
        else
        {
            return "\nThere is an item to collect. Are you going to collect it? (yes/no)";
        }
    }

    /**Manages room interactions
     */
    public static String enteredRoom(String userInput) //CHANGE THIS TO ACCEPT USER INPUT enteredRoom(String userResponse)
    {
            //rucksack is not an item, monster or puzzle and is therefore not represented in the
            //db for the first room except to say the room is not empty. This makes the if below necessary!
            if (2 == currentRoom && player.getInventory() == null)
            {
                if (userInput.equalsIgnoreCase("yes"))  //cleared
                {
                    player.createInventory();
                    return "You've acquired a rucksack which gives you access to an inventory for holding 10 items" +
                            "\n\n" + changeRoomsMessage();
                }
                //The user may get a different response in the final game so this is Tentative
                else if (userInput.equalsIgnoreCase("no"))  //cleared
                {
                    player.createInventory();
                    return "You shake your head no but then hear a terrifying scream of someone further in." +
                            "\nIt sounds like they most certainly met their demise... or wish they had. You look back" +
                            "down on the dirty, stained rucksack\nand suddenly it doesn't look so bad. Maybe it could come in handy." +
                            "You've acquired a rucksack which gives you access to an inventory for holding 10 items" +
                            "\n\n" + changeRoomsMessage();

                } else  //cleared
                {
                    return "There was an error in trying to make sense of your request. Check your spelling." +
                            roomSummaryMessage();
                }
            }
            //checks if an puzzle is in the room
            else if (roomsMap.get(currentRoom).getIsPuzzle() > 0)
            {
                if (userInput.equalsIgnoreCase("yes"))  //cleared
                {
                    return puzzleMessage();
                } else if (userInput.equalsIgnoreCase("no"))  //cleared
                {
                    return changeRoomsMessage();
                } else  //cleared  -- SOMEHOW this must call enteredRoom()
                {
                    return "There was an error in trying to make sense of your request. Check your spelling." +
                            roomSummaryMessage();
                }
            }
            //checks if an monster is in the room
            else if (roomsMap.get(currentRoom).getIsMonster() > 0)
            {
                if (userInput.equalsIgnoreCase("yes"))
                {
                    return battleMessage();
                } else if (userInput.equalsIgnoreCase("no"))  //cleared
                {
                    return changeRoomsMessage();
                } else  //cleared
                {
                    return "There was an error in trying to make sense of your request. Check your spelling." +
                            roomSummaryMessage();
                }
            }
            //checks if an item is in the room
            else if (roomsMap.get(currentRoom).getIsArmor() > 0 || roomsMap.get(currentRoom).getIsWeapon() > 0 ||
                    roomsMap.get(currentRoom).getIsElixir() > 0)
            {
                if (userInput.equalsIgnoreCase("yes"))  //cleared
                {
                    return collectItem();
                } else if (userInput.equalsIgnoreCase("no"))  //cleared
                {
                    return changeRoomsMessage();
                } else  //cleared
                {
                    return "There was an error in trying to make sense of your request. Check your spelling." +
                            roomSummaryMessage();
                }
            }
            //if there is no monster, puzzle, or item.
            else  //cleared
            {
                setEmptyRoom();
                return changeRooms(userInput);
            }
    }
}
