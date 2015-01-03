package Controller;

import Model.LoadEntity;

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
    private  Inventory inventory;
	private  int score;
	private  int defenseStrength;

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
        String[] loginDetails = LoadEntity.loadHero(heroName).split("[|]");

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
        String[] heroInventory = LoadEntity.loadHeroInventory(playerID).split("[|]");

        while (p < heroInventory.length - 2)
        {
            //checks if a weaponID exists
            if (!heroInventory[p].equalsIgnoreCase("0"))
            {
                //builds an weapon(name, strength)
                Weapon weapon = new Weapon();
                //adds it to inventory
                this.getInventory().add(weapon);
            }
            //checks if a armorID exists
            else if (!heroInventory[p + 1].equalsIgnoreCase("0"))
            {
                //builds an armor(name, defenseBoost)
                Armor armor = new Armor();
                //adds it to inventory
                this.getInventory().add(armor);
            }
            //checks if a elixirID exists
            else if (!heroInventory[p + 2].equalsIgnoreCase("0"))
            {
                //builds an elixir(name, healthBoost)
                Elixir elixir = new Elixir();
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
}
