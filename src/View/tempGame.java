package View;

public String playGame(String userResponse)
{
    if (in.equalsIgnoreCase("1"))
    {
        //necessary to load saved rooms in loadGame()
        loginResults = login();
        if (loginResults)
        {
            return "Account located. Loading game...";
        }
        else
        {
            return "Error, could not locate your user name";
        }
    }
    else if (in.equalsIgnoreCase("2"))
    {
        //Attempts to create a user account if one does not already exist
        if (!createAccount())
        {
            return "Your account was created!";
        }
        else
        {
            return "Error, an account with this name already exist";
        }
    }
    else
        {
            return"Error, interpreting your last request.";
        }
}