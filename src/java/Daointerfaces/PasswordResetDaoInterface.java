/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daointerfaces;

import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;

/**
 * PasswordResetDaoInterface this interface provides functionality to PasswordRestDao class
 * @author Arnas
 */
public interface PasswordResetDaoInterface {
    
    /**
     * This method will add a new user attempt
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    public boolean addNewUserAttempt(String ipAddress);
    
    /**
     * Gets the clients ip address from the database
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    public boolean getIpAddress(String ipAddress);
    
    /**
     * Updates password_reset table adding 1 attempt to attempts column
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    public boolean addAttempt(String ipAddress);
    
    /**
     * Gets clients attempts from the database
     * @param ipAddress
     * @return the number of attempts, 0 is returned if nothing found
     */
    public int getAttempts(String ipAddress);
    
    /**
     * This will updated the password_reset table setting the timeout column
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    public boolean addTimeout(String ipAddress);
    
    /**
     * Gets the timeout of the client
     * @param ipAddress clients ip address
     * @return 
     */
    public LocalDateTime getTimeout(String ipAddress);
    
    /**
     * Removes clients information from the password_reset table
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    public boolean removeUserAttempt(String ipAddress);
    
    /**
     * Gets the date of clients first attempt at reseting password
     * @param ipAddress clients ip address
     * @return returns the date of when client started making his attempts
     */
    public LocalDateTime getCreatedAt(String ipAddress);
    
    /**
     * This method incorporates all of the methods above, this method was created to shorten and decouple functionality from "ForgotPasswordResetCommand.java"
     * @param ipAddress clients ip address
     * @return true if attempt limit is reached false otherwise
     */
    public boolean handlePasswordResetAttempts(String ipAddress, HttpSession session);
}
