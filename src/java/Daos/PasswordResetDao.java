/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Daointerfaces.PasswordResetDaoInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpSession;

/**
 * This class is responsible for accessing password_reset table in the database
 * @author Arnas
 */
public class PasswordResetDao extends Dao implements PasswordResetDaoInterface {
    
    /**
     * Default constructor
     */
    public PasswordResetDao() {
        super();
    }
    
    /**
     * Constructor which connects to a specified database
     * @param databaseName database name to which a connection needs to be made
     */
    public PasswordResetDao(String databaseName) {
        super(databaseName);
    }
   
    /**
     * This method will add a new user attempt
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    @Override
    public boolean addNewUserAttempt(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;
        
        try {
            con = getConnection();
            
            ps = con.prepareStatement("INSERT INTO password_reset (id,ip_address,attempts) VALUES (null,?,?)");
            ps.setString(1, ipAddress);
            ps.setInt(2, 1);
            count = ps.executeUpdate();
            
            //Check if query worked
            if(count > 0) return true;
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Gets the clients ip address from the database
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    @Override
    public boolean getIpAddress(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ip = "";
        
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ip_address FROM password_reset WHERE ip_address = ?;");
            ps.setString(1, ipAddress);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                ip = rs.getString("ip_address");
                return (ip.equals(ipAddress));
            }
            else return false;
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
         
        return false;
    }
    
    /**
     * Updates password_reset table adding 1 attempt to attempts column
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    @Override
    public boolean addAttempt(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;
        
        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE password_reset SET attempts = (attempts + 1) WHERE ip_address = ?;");
            ps.setString(1, ipAddress);
            count = ps.executeUpdate();
            
            //Check if query worked
            if(count > 0) return true;
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Gets clients attempts from the database
     * @param ipAddress
     * @return the number of attempts, 0 is returned if nothing found
     */
    @Override
    public int getAttempts(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int attempts = 0;
        
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT attempts FROM password_reset WHERE ip_address = ?;");
            ps.setString(1, ipAddress);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                attempts = rs.getInt("attempts");
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }            
        }
        
        return attempts;
    }
    
    /**
     * This will updated the password_reset table setting the timeout column
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    @Override
    public boolean addTimeout(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;
        
        //Get the current time then add 15minutes to it (This will be our timeout)
        LocalDateTime time = LocalDateTime.now().plusMinutes(15);
        
        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE password_reset SET timeout = ? WHERE ip_address = ?;");
            ps.setString(1, time.toString());
            ps.setString(2, ipAddress);
            count = ps.executeUpdate();
            
            if(count > 0) return true;
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }            
        }
        
        return false;
    }

    /**
     * Gets the timeout of the client
     * @param ipAddress clients ip address
     * @return 
     */
    @Override
    public LocalDateTime getTimeout(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDateTime time = null;
        
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT timeout FROM password_reset WHERE ip_address = ?;");
            ps.setString(1, ipAddress);
            rs = ps.executeQuery();

            if(rs.next()) {
                //Check if results is not null (as its null in database by default this causes nullpointerexceptions)
                if(rs.getString("timeout") != null) {
                    time = LocalDateTime.parse(rs.getString("timeout"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                }
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return time;
    }
    
    /**
     * Removes clients information from the password_reset table
     * @param ipAddress clients ip address
     * @return true on success false otherwise
     */
    @Override
    public boolean removeUserAttempt(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            con = getConnection();
            ps = con.prepareStatement("DELETE FROM password_reset WHERE ip_address = ?;");
            ps.setString(1, ipAddress);
            count = ps.executeUpdate();
            
            if(count > 0) return true;
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }

    /**
     * Gets the date of clients first attempt at reseting password
     * @param ipAddress clients ip address
     * @return returns the date of when client started making his attempts
     */
    @Override
    public LocalDateTime getCreatedAt(String ipAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDateTime time = null;
        
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT created_at FROM password_reset WHERE ip_address = ?;");
            ps.setString(1, ipAddress);
            rs = ps.executeQuery();

            if(rs.next()) {
                //Check if results is not null (as its null in database by default this causes nullpointerexceptions)
                if(rs.getString("created_at") != null) {
                    time = LocalDateTime.parse(rs.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
                if(con != null) {
                    freeConnection(con);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return time;
    }
    
    /**
     * This method incorporates all of the methods above, this method was created to shorten and decouple functionality from "ForgotPasswordResetCommand.java"
     * @param ipAddress clients ip address
     * @param session HttpSession object
     * @return true if attempt limit is reached false otherwise
     */
    @Override
    public boolean handlePasswordResetAttempts(String ipAddress, HttpSession session) {
            //Store timeout 
            LocalDateTime timeout = getTimeout(ipAddress);
            
            //Check if attempt is being made by a new user
            if(!getIpAddress(ipAddress)) {
                //Insert details into database for new user (this will be counted as attempt 1)
                addNewUserAttempt(ipAddress);
            }
            //Another attempt being made by the user (So we will add to his attempts counter)
            else {
                //Check if 2 hours have passed since user started making attempts
                //This will prevent a situation where a user makes 2 attempts leaves and comes back tomorrow his attempts will still be at 2
                //So we will reset attemps if 2 hours have passed since he started making his attempts
                if(LocalDateTime.now().plusHours(2).isAfter(getCreatedAt(ipAddress))) {
                    //Check if the current user has made 3 attempts if he didnt add attempt
                    if(getAttempts(ipAddress) >= 3) {
                        //Add timeout (How long user has to wait) but first check if timeout is not there already
                        if(timeout == null) {
                            //Add timeout
                            addTimeout(ipAddress);
                            //Send user back with message telling him how much time is left to wait
                            Duration duration = Duration.between(LocalDateTime.now(), getTimeout(ipAddress)); //Have to get timeout after adding if 'timeout' variable used will get null pointer exception
                            session.setAttribute("Message", "You have made to many attempts<br>Please wait " +duration.toMinutes()+ " minutes before trying again");
                            return true;
                        }
                        else {
                            //Check if timeout has passed
                            if(LocalDateTime.now().isAfter(timeout)) {
                                removeUserAttempt(ipAddress);
                            }
                            else {
                                //Send user back with message telling him how much time is left to wait
                                Duration duration = Duration.between(LocalDateTime.now(), timeout);
                                session.setAttribute("Message", "You have made to many attempts<br>Please wait " +duration.toMinutes()+ " minutes before trying again");
                                return true;
                            }
                        }
                    }
                    else {
                        //Add attempts
                        addAttempt(ipAddress);
                    }
                }
                else {
                    //Reset the users attempts
                    removeUserAttempt(ipAddress);
                }
            }
            
            return false;
    }
}
