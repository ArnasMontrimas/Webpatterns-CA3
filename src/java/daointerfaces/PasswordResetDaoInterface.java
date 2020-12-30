/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daointerfaces;

import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;

/**
 * PasswordResetDaoInterface this interface provides functionality to PasswordRestDao class
 * @author Arnas
 */
public interface PasswordResetDaoInterface {
    
    /**
     * This method will add a new user attempt
     * @param id clients id
     * @return true on success false otherwise
     */
    public boolean addNewUserAttempt(int id);
    
    /**
     * Gets the clients id from the database
     * @param id clients id
     * @return true on success false otherwise
     */
    public boolean getUserid(int id);
    
    /**
     * Updates password_reset table adding 1 attempt to attempts column
     * @param id clients id
     * @return true on success false otherwise
     */
    public boolean addAttempt(int id);
    
    /**
     * Gets clients attempts from the database
     * @param id client id
     * @return the number of attempts, 0 is returned if nothing found
     */
    public int getAttempts(int id);
    
    /**
     * This will updated the password_reset table setting the timeout column
     * @param id clients id
     * @return true on success false otherwise
     */
    public boolean addTimeout(int id);
    
    /**
     * Gets the timeout of the client
     * @param id clients id
     * @return 
     */
    public LocalDateTime getTimeout(int id);
    
    /**
     * Removes clients information from the password_reset table
     * @param id clients id
     * @return true on success false otherwise
     */
    public boolean removeUserAttempt(int id);
    
    /**
     * Gets the date of clients first attempt at reseting password
     * @param id clients id
     * @return returns the date of when client started making his attempts
     */
    public LocalDateTime getCreatedAt(int id);
    
    /**
     * This method incorporates all of the methods above, this method was created to shorten and decouple functionality from "ForgotPasswordResetCommand.java"
     * @param id clients id
     * @param session HttpSession object
     * @return true if attempt limit is reached false otherwise
     */
    public boolean handlePasswordResetAttempts(int id, HttpSession session);
}
