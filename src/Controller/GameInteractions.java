package controller;

import controller.actors.Hero;
import controller.actors.Monster;
import controller.room.Rooms;

/**
 * author: JJ Lindsay
 * version: 4.2.1
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a game interactions
 *
 * Purpose: Allows a player to play/quit game and interact with battle and main menu.
 */
public class GameInteractions
{
    //static instance variables
    private static Hero player = AccountFunctions.getHero();

    /**Interprets the user's response and points them to login() or CreateAccount()
     */
    public static String titleScreenInput(String userInput)
    {
        if (userInput.equalsIgnoreCase("1"))
        {
            AccountFunctions.setLoginOrCreateChoice(1);
            return "Enter your user name: ";
        }
        else if (userInput.equalsIgnoreCase("2"))
        {
            AccountFunctions.setLoginOrCreateChoice(2);
            return "Enter a name you would like to create for your account: ";
        }
        else
        {
            return "Error interpreting your last request" + "\n\n" + MenusAndMessages.titleScreen();
        }
    }

    /**This method handles the Hero's request to quit the game
     */
    public static String quitGame(String userInput)
    {
        if(null != userInput && userInput.equalsIgnoreCase("yes"))
        {
            AccountFunctions.saveGame();
            System.exit(0);
        }
        else if(null != userInput && userInput.equalsIgnoreCase("no"))
        {
            System.exit(0);
        }
        else
        {
            return "Error interpreting your last request." + "\n\n" + MenusAndMessages.quitGameMessage();
        }
        return null;
    }

    /**This method handles the fight interactions between the Hero and an enemy
     */
    public static String battle(String userInput)
    {
        Monster monster = new Monster();

        //checks the player's response against a minimum length
        if (userInput.length() > 5)
        {
            if (userInput.equalsIgnoreCase("inventory"))  //cleared
            {
                return player.getInventory().view() + "\n" + MenusAndMessages.battleMessage();
            }
            else if (userInput.substring(0, 5).equalsIgnoreCase("equip"))
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(6)))
                {
                    //identify the item as a weapon, armor, or elixir by its itemType
                    if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("w"))
                    {
                        player.setAttackPower(player.getInventory().getWeapon(userInput.substring(6)).getStrength());
                        return "You have drawn your " + userInput.substring(6) + "\n\n" + MenusAndMessages.battleMessage();
                    } else if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("a"))
                    {
                        player.setDefenseStrength(player.getInventory().getArmor(userInput.substring(6)).getArmorDefense());
                        return "You have put on the " + userInput.substring(6) + "\n\n" + MenusAndMessages.battleMessage();
                    } else //if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("e"))
                    {
                        player.setHealth(player.getHealth() + player.getInventory().getElixir(userInput.substring(6)).getHealthBoost());
                        player.getInventory().remove(userInput.substring(6));
                        return "You drank all of the " + userInput.substring(6) + "\n\n" + MenusAndMessages.battleMessage();
                    }
                } else
                {
                    return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + MenusAndMessages.battleMessage();
                }
            }
            else if (userInput.length() > 6 && userInput.substring(0, 6).equalsIgnoreCase("remove"))
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(7)))
                {
                    //remove item from inventory
                    player.getInventory().remove(userInput.substring(7));
                    return "You removed " + userInput.substring(7) + " from your inventory" + "\n\n" + MenusAndMessages.battleMessage();

                } else
                {
                    return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + MenusAndMessages.battleMessage();
                }
            }
            else if (userInput.equalsIgnoreCase("attack"))
            {
                String attackResults;

                //Hero attacks first
                if (monster.getHealth() - player.getAttackPower() > 0)  //monster lives
                {
                    monster.setHealth(monster.getHealth() - player.getAttackPower());
                    attackResults = "You lunged at " + monster.getName() + " but your attack wasn't good enough to bring'em down.";
                } else  //monster dies
                {
                    attackResults = "You dealt a deadly blow with that last move! You killed " + monster.getName() + ".";
                    //set monster to zero for this room
                    AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).setIsMonster(0);

                    //Game won message
                    if (50 == Rooms.getCurrentRoom())
                    {
                        attackResults += "\nYou have rid Dragons Keep of its corrupted emperor. Thanks to you " + player.getName() + " what evil" +
                        " was brewing just beneath the city wont be unleashed anytime soon...";
                        attackResults += "\n\n-----------------------------------------------------------------------\n";
                        attackResults += "\nCongratulations on your completion of Dragons Keep." +
                        "\nIt was a pleasure making this and we hope you enjoyed it! \n Like a challenge? Try completing all 50 rooms and solving all puzzles.";

                        return attackResults + "\n\n" + MenusAndMessages.quitGameMessage();
                    }

                    if (AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsArmor() > 0 || AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsWeapon() > 0 ||
                            AccountFunctions.getRoomsObj().get(Rooms.getCurrentRoom()).getIsElixir() > 0)  //did an item appear?
                    {
                        return attackResults + "\n\nThere is an item to collect. Do you want to collect it? (yes/no)";
                    }
                    return attackResults + "\n\n" + MenusAndMessages.changeRoomsMessage();
                }

                //Monster retaliates
                attackResults += "\nYour last attack didn't defeat " + monster.getName() + " and you've been hit with a counter-attack.";
                if ((player.getHealth() + player.getDefenseStrength())   - monster.getAttackPower() > 0)  //Hero didn't die
                {
                    if ((player.getHealth() + player.getDefenseStrength())  - monster.getAttackPower() <= player.getHealth())  //armor was lower than enemy attack power
                    {
                        player.setHealth((player.getHealth() + player.getDefenseStrength()) - monster.getAttackPower());
                    }
                    return attackResults + "\n\n" + MenusAndMessages.battleMessage();
                } else  //Hero dies
                {
                    attackResults +=  "\n\nYour losing a lot of blood, you don't know how." + monster.getName() + "attacked you so fast! \nYour stumbling towards the door..." +
                            "\nyou've got to get out of here, you think to yourself.\nIt's no use. You collapse on the ground before even reaching door. Just before everything" +
                            " goes black, you think of the\npeople who were depending on you, and with your last breath you whisper I'm sorry.";
                    attackResults += "\n*****************************************\n";
                    attackResults += "\n---------------GAME OVER-----------------\n";
                    attackResults += "\n*****************************************";

                    return attackResults + "\n\n" + MenusAndMessages.titleScreen();
                }
            } else if (userInput.equalsIgnoreCase("run away"))
            {
                monster.setHealth(monster.getHealth() + 5);
                return "You ran away screaming; your heart is racing." +
                        "\nRefusing to look back, you think to yourself, that monster can't POSSIBLY get any stronger..." + "\n\n" + MenusAndMessages.changeRoomsMessage();
            } else
            {
                return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + MenusAndMessages.battleMessage();
            }
        }
        else
        {
            return "There was an error in trying to make sense of you request. Check your spelling." + "\n\n" + MenusAndMessages.battleMessage();
        }
    }

    /**This is the in game menu the Hero can call at any time
     */
    public static String mainMenu(String userInput)
    {
        if (userInput.equalsIgnoreCase("inventory"))  //cleared
        {
            if (player.getInventory() == null)
            {
                return "Opps! You don't have an inventory." + "\n\n" + MenusAndMessages.mainMenuMessage();
            }
            else
            {
                return player.getInventory().view() + "\n" + MenusAndMessages.mainMenuMessage();
            }
        }
        else if (userInput.equalsIgnoreCase("exit"))  //cleared
        {
            return MenusAndMessages.changeRoomsMessage();
        }
        else if (userInput.equalsIgnoreCase("save"))  //cleared
        {
            AccountFunctions.saveGame();
            return "Saved game successfully" + "\n\n" + MenusAndMessages.mainMenuMessage();
        }
        else if (userInput.equalsIgnoreCase("quit"))  //cleared
        {
            return MenusAndMessages.quitGameMessage();
        }
        else if (userInput.length() > 5 && userInput.substring(0, 5).equalsIgnoreCase("equip"))
        {
            if (player.getInventory() == null)  //cleared
            {
                return "Opps! You don't have an inventory." + "\n\n" + MenusAndMessages.mainMenuMessage();
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
                        return "You are drawing your " + userInput.substring(6) + "\n\n" + MenusAndMessages.mainMenuMessage();
                    } else if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("a"))
                    {
                        player.setDefenseStrength(player.getInventory().getArmor(userInput.substring(6)).getArmorDefense());
                        return "You are putting on the " + userInput.substring(6) + "\n\n" + MenusAndMessages.mainMenuMessage();
                    } else //if (player.getInventory().getItemType(userInput.substring(6)) != null && player.getInventory().getItemType(userInput.substring(6)).equalsIgnoreCase("e"))
                    {
                        player.setHealth(player.getHealth() + player.getInventory().getElixir(userInput.substring(6)).getHealthBoost());
                        player.getInventory().remove(userInput.substring(6));
                        return "You're drinking the " + userInput.substring(6) + "\n\n" + MenusAndMessages.mainMenuMessage();
                    }
                } else  //cleared
                {
                    return "There was an error in understanding your request. Check your spelling."
                            + "\n\n" + MenusAndMessages.mainMenuMessage();
                }
            }
        }
        //removes an item from inventory
        else if(userInput.length() > 6 && userInput.substring(0, 6).equalsIgnoreCase("remove"))
        {
            if (player.getInventory() == null)  //cleared
            {
                return "Opps! You don't have an inventory." + "\n\n" + MenusAndMessages.mainMenuMessage();
            }
            else
            {
                //check if the item exist in inventory
                if (player.getInventory().confirmItem(userInput.substring(7)))
                {
                    //remove item from inventory
                    player.getInventory().remove(userInput.substring(7));
                    return "You have successfully removed " + userInput.substring(7) + " from your inventory"
                            + "\n\n" + MenusAndMessages.mainMenuMessage();

                } else  //cleared
                {
                    return "There was an error in understanding your request. Check your spelling."
                            + "\n\n" + MenusAndMessages.mainMenuMessage();
                }
            }
        }
        else  //cleared
        {
            return "There was an error in understanding your request. Check your spelling."
                    + "\n\n" + MenusAndMessages.mainMenuMessage();
        }
    }
}