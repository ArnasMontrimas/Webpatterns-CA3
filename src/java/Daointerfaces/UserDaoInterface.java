package Daointerfaces;


import Dtos.*;


/**
 * @author Sam Ponik and Malo and Arnas
 */
public interface UserDaoInterface {
    /**
     * This method can be used to generate a string representing an account password
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
     * A workload of 15 is a very reasonable safe default as of 2020.
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     * @param password_plaintext The account plaintext password as provided during account creation,
     * or when changing an account password.
     * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
     */
    String hashPassword(String password_plaintext);
    
    /**
     * This method can be used to verify a computed hash from a plaintext (e.g. during a login
     * request) with that of a stored hash from a database. The password hash from the database
     * must be passed as the second variable.
     * @param password_plaintext The account plaintext password, as provided during a login request
     * @param stored_hash The account stored password hash, retrieved from the authorization database
     * @return boolean - true if the password matches the password of the stored hash, false otherwise
     */
    boolean checkPassword(String password_plaintext, String stored_hash);
    
    /**
     * Returns The id (primary key) of the user once registered if issue with the server return 0
     * 
     * @param username The username for the account
     * @param email The email used as login for the account
     * @param password The password for the account
     * @return int 0 if no user by that name else return the unique id
     */
    int registerUser(String username, String email, String password);

    /**
     *  If username and password don't match return 0
     *  If the account is inactive return -1
     * 
     * @param email The username for login.
     * @param plaintext_password The password for login.
     * @return int Returns the unique ID of the user if Username and password match or -1 for inactive or 0 for bad login
     */
    int validateLogin(String email,String plaintext_password);
    
     /**
     * Gets the user object by the primary key which is the ID
     * 
     * @param userID The id of the user
     * @return <code>User</code> the User object
     */
    User getUserByID(int userID);
        
     /**
     * Returns true if the password was reset or false if the old password doesn't match the one in the database
     * 
     * @param old_plaintext_password The users current password
     * @param new_plaintext_password The new password to be reset
     * @param username The users username
     * @return boolean True or false
     */
    boolean passwordReset(String old_plaintext_password,String new_plaintext_password,String username);
    
     /**
     * This is when the user is forgets his password and knows his username
     * This method will be called when the security answer is correct which is in the security answers dao.
     *  
     * @param new_plaintext_password The users new chosen password
     * @param username The users username
     * @return boolean True or false
     */
    boolean forgotPasswordReset(String new_plaintext_password,String username);
    
    /**
     * This method checks if a specified email address exists in the database
     * @param userEmail email address to be found in database
     * @return boolean true if found, false otherwise
     */
    boolean validateEmail(String userEmail);

    /**
     * Updates the user's username if that username is available
     *
     * @param newUsername The new username to update
     * @param currentUsername The current username.
     * @return boolean true/false
     */
    boolean changeUsername(String newUsername, String currentUsername);
}
