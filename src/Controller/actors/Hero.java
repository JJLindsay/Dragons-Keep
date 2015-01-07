package controller.actors;

import controller.inventory.Inventory;
import controller.inventory.InventoryDB;
import controller.itemsAndPuzzle.ItemDB;
import controller.itemsAndPuzzle.Weapon;
import controller.itemsAndPuzzle.Armor;
import controller.itemsAndPuzzle.Elixir;

/**
 * author: Thaonguyen Nguyen and JJ Lindsay
 * version: 2.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a Hero.
 *
 * Purpose: Allows for the creation and manipulation of a Hero player.
 */
public class Hero extends Actor
{
    //instance variables
    private  int playerID;
    private Inventory inventory;
	private  int score;
	private  int defenseStrength;

    //NEW
    private static ActorDB actorDB = new ActorDB();  //original
    private static InventoryDB inventoryDB = new InventoryDB();  //original
    private static ItemDB itemDB = new ItemDB();  //original

    /**Two argument constructor
     * @param name The Hero's name
     * @param playerID The Hero's database primary key
     */
	public Hero(String name, int playerID) {
		super(name);
		score = 0;
		defenseStrength = 0;
        inventory = null;
        this.playerID = playerID;
	}

    /**
     * Creates a Hero from the database
     * @param heroName The name associated with the player profile in the database
     */
    public Hero(String heroName)
    {
        super(heroName);

        //get saved player data
        String[] loginDetails = actorDB.loadHero(heroName).split("[|]");

        //create player with ID, name, score, and health from saved information
        this.score = Integer.parseInt(loginDetails[3]);
        this.playerID = Integer.parseInt(loginDetails[0]);
        this.setHealth(Integer.parseInt(loginDetails[4]));

        //if the player has an inventory
        if (!loginDetails[2].equals("0"))
        {
            createInventory(playerID);
        }
        else
            this.inventory = null;
    }

    /**Creates an empty inventory
     */
	public  void createInventory()
    {
        inventory = new Inventory();
	}

    /**
     * Creates and populates a player's saved inventory
     * @param playerID The ID to identify the user's inventory in the database
     */
    public  void createInventory(int playerID)
    {
        int p = 0;
        String[] heroInventory = inventoryDB.loadHeroInventory(playerID).split("[|]");
        inventory = new Inventory();

        while (p < heroInventory.length - 2)
        {
            //checks if a weaponID exists
            if (!heroInventory[p].equalsIgnoreCase("0"))
            {
                //get the weapon from the database
                String[] dbWeapon = itemDB.retrieveWeapon(Integer.parseInt(heroInventory[p])).split("[|]");
                //builds an weapon(name, strength)
                Weapon weapon = new Weapon(dbWeapon[0], Integer.parseInt(dbWeapon[1]));                //adds it to inventory
                this.getInventory().add(weapon);
            }
            //checks if a armorID exists
            else if (!heroInventory[p + 1].equalsIgnoreCase("0"))
            {
                //get the armor from the database
                String[] dbArmor = itemDB.retrieveArmor(Integer.parseInt(heroInventory[p + 1])).split("[|]");
                //builds an armor(name, defenseBoost)
                Armor armor = new Armor(dbArmor[0], Integer.parseInt(dbArmor[1]));
                //adds it to inventory
                this.getInventory().add(armor);
            }
            //checks if a elixirID exists
            else if (!heroInventory[p + 2].equalsIgnoreCase("0"))
            {
                //get the elixir from the database
                String[] dbElixir = itemDB.retrieveElixir(Integer.parseInt(heroInventory[p+2])).split("[|]");
                //builds an elixir(name, healthBoost)
                Elixir elixir = new Elixir(dbElixir[0], Integer.parseInt(dbElixir[1]));
                //adds it to inventory
                this.getInventory().add(elixir);
            }
            p += 3;
        }
    }

    /**
     * @return inventory Gets the Hero's inventory
     */
	public  Inventory getInventory() {
		return inventory;
	}

    /**
     * @return score Gets the Hero's score
     */
    public  int getScore() {
		return score;
	}

    /**Sets the Hero's game score
     * @param score The Hero's game score
     */
    public  void setScore(int score) {
        this.score = score;
	}

    /**
     * @return defenseStrength Gets the Hero's defense strength
     */
    public  int getDefenseStrength() {
		return defenseStrength;
	}

    /**Sets the defense strength of the Hero
     * @param defenseStrength The Hero's defense strength
     */
    public  void setDefenseStrength(int defenseStrength) {
        this.defenseStrength = defenseStrength;
	}

    /**
     * @return playerID Gets the player's ID
     */
    public  int getPlayerID()
    {
        return playerID;
    }

    /**Sets the playerID
     * @param playerID The player's database primary key or playerID
     */
    public  void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    public static ActorDB getActorDB()
    {
        return actorDB;
    }
}