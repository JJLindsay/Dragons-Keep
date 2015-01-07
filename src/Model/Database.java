package model;

import java.sql.*;

/**
 * author: JJ Lindsay
 * version: 2.1
 * Course: ITEC 3860 Fall 2014
 * Written: 11/16/2014
 *
 * This class represents a Database with account methods.
 *
 * Purpose: Gives the ability to log into or create a new player profile in the database.
 */
public class Database
{
    //instance variables
    private  Statement stmt;
    private Connection conn;
    private  ResultSet rs = null;
    private static int count = 1;

    /**No argument constructor
     */
    public Database()
    {
        //register the driver name
        String sDriver = "org.sqlite.JDBC";
        try
        {
            Class.forName(sDriver);
        }
        catch(ClassNotFoundException cfne)
        {
            System.out.println(cfne.getMessage());
        }

        //Build the URL for SQLite DB
        String database = "./libs/DragonsKeep.db";
        String jdbc = "jdbc:sqlite";
        String dbURL = jdbc + ":" + database;
        //Set db timeout
        int timeOut = 30;

        try
        {
            //Establish a connection to the database
            conn = DriverManager.getConnection(dbURL);
            //Create a container for the SQL statement
            stmt = conn.createStatement();
            //set timeout on the statement
            stmt.setQueryTimeout(timeOut);
        }
        catch (SQLException sqe)
        {
            System.out.println(sqe.getMessage());
        }
    }

    /**Allows for querying the database
     * @param sql database query statement
     * @return rs The results from the query
     */
    public ResultSet query(String sql)
    {
        rs = null;
        try
        {
            rs = stmt.executeQuery(sql);
        }
        catch(SQLException sqe)
        {
            sqe.printStackTrace();
        }
        return rs;
    }

    /**Allows for inserting and modifying the SQLite database
     * @param sql database modification statement
     * @return num the number of changed rows
     */
    public int modData(String sql)
    {
        //default value nothing is changed, 0.
        int num = 0;

        try
        {
            num = stmt.executeUpdate(sql);
            return num;
        }
        catch(SQLException sqe)
        {
            return num;
        }
    }
}