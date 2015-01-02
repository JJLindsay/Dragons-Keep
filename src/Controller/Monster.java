package Controller;

import Model.LoadEntity;

/**
 * author: Thaonguyen Nguyen
 * version: 1.0
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a Monster.
 *
 * Purpose: Allows for the creation and manipulation of a monster.
 */
public class Monster extends Actor
{
    /**no argument constructor
     */
	public Monster() {
		super();
        //create monster
        String[] dbMonster = LoadEntity.retrieveMonster(Rooms.getRoomsMap().get(Rooms.getCurrentRoom()).getIsMonster()).split("[|]");
        this.setName(dbMonster[0]);
        this.setHealth(Integer.parseInt(dbMonster[1]));
        this.setAttackPower(Integer.parseInt(dbMonster[2]));
	}
}
