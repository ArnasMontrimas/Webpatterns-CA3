/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daotests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import daos.PasswordResetDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arnas
 */
public class PasswordResetDaoTest {
    
    /**
     * Create PasswordResetDao class with connection to test database
     */
    private final PasswordResetDao prdao = new PasswordResetDao("creative_library_test");

    /**
     * Test of addNewUserAttempt method, of class PasswordResetDao.
     * Here there is only 1 test as the ip address is not being checked if its a valid ip address or not
     * but with every request to reset password, ip address has to match with the one in the database
     * if it does not a new row will be created (will be interpreted as new client if no match found)
     */
    @Test
    public void testAddNewUserAttempt() {
        System.out.println("addNewUserAttempt");
        String ipAddress = "";
        boolean actual = prdao.addNewUserAttempt(ipAddress);
        boolean expected = true;
        
        //This will check that the method worked
        if(actual) {
            //Now lets check that the row was actually added in the database
            if(checkRowWasAddedToDatabase(ipAddress) == 1){
                //Now lets remove the row and make sure it works
                //This is here so there are no conflicts when running multiple tests
                if(removeRowFromDatabase(ipAddress) == 1) {
                    actual = true;
                }
                else fail("Row was not removed from database");
            }
            else fail("No Row was added in the databse");
        }
        else fail("addNewUserAttempt(String ipAddress) Method Failed.");
        
        assertEquals(actual,expected);
        
    }
    
    /**
     * Test of testGetIpAddressWithExistingIpAddress method, of class PasswordResetDao.
     */
    @Test
    public void testGetIpAddressWithExistingIpAddress() {
        System.out.println("testGetIpAddressWithExistingIpAddress");
        String ipAddress = "0:0:0:0:0:1";
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(ipAddress);
        
        //If adding worked
        if(added) {
            //Now lets check that added ip address is the correct one
            //This method gets the ip address and also checks if its equal to the one provided as parameter
            if(prdao.getIpAddress(ipAddress)) {
                //Everything works lets remove the added row
                if(removeRowFromDatabase(ipAddress) == 1) {
                    actual = true;
                }
                else fail("Row was not removed from database");
            }
            else fail("getIpAddress(String ipAddress) Method failed");
        }
        else fail("No Row was added in the databse");
        
        assertEquals(actual, expected);
    }

    /**
     * Test of testGetIpAddressWithNonExistingIpAddress method, of class PasswordResetDao.
     */
    @Test
    public void testGetIpAddressWithNonExistingIpAddress() {
        System.out.println("testGetIpAddressWithNonExistingIpAddress");
        String ipAddress = "0:0:0:0:0:1";
        String ipAddress2 = "0:1:1:1:0:0";
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(ipAddress2);
        
        //If adding worked
        if(added) {
            //Now lets check that added ip address is the correct one
            //This should return false as no ip address should be found
            if(!prdao.getIpAddress(ipAddress)) {
                //Everything works lets remove the added row
                if(removeRowFromDatabase(ipAddress2) == 1) {
                    actual = true;
                }
                else fail("Row was not removed from database");
            }
            else fail("getIpAddress(String ipAddress) Method failed");
        }
        else fail("No Row was added in the databse");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddAttemptWithExistingIpAddress method, of class PasswordResetDao.
     */
    @Test
    public void testAddAttemptWithExistingIpAddress() {
        System.out.println("testAddAttemptWithExistingIpAddress");
        String ipAddress = "0:0:0:0:0:1";
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(ipAddress);
        
        //If adding worked
        if(added) {
            //If adding attempt worked
            if(prdao.addAttempt(ipAddress)) {
                //Now lets remove the row
                if(removeRowFromDatabase(ipAddress) == 1) {
                    actual = true;
                }
                else fail("Removing row from database failed");
            }
            else fail("addAttempt(String ipAddress) Method Failed.");
        }
        else fail("No row was added in the database");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddAttemptWithNonExistingIpAddress method, of class PasswordResetDao.
     */
    @Test
    public void testAddAttemptWithNonExistingIpAddress() {
        System.out.println("testAddAttemptWithNonExistingIpAddress");
        String ipAddress = "0:0:0:0:0:1";
        String ipAddress2 = "0:0:1";
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(ipAddress2);
        
        //If adding worked
        if(added) {
            //If adding attempt worked
            if(!prdao.addAttempt(ipAddress)) {
                //Now lets remove the row
                if(removeRowFromDatabase(ipAddress2) == 1) {
                    actual = true;
                }
                else fail("Removing row from database failed");
            }
            else fail("addAttempt(String ipAddress) Method Failed.");
        }
        else fail("No row was added in the database");
        
        assertEquals(actual, expected);
        
    }

    /**
     * Test of testGetAttemptsWithExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testGetAttemptsWithExistingIpAddress() {
        System.out.println("testGetAttemptsWithExistingIpAddress");
        String ipAddress = "0:0:0:0:1:2";
        int expected = 1;
        int actual = 0;
        
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(ipAddress);
        
        //If added
        if(added) {
            //Now lets get attempts
            actual = prdao.getAttempts(ipAddress);
            
            //Lets remove the row
            if(removeRowFromDatabase(ipAddress) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }

    /**
     * Test of testGetAttemptsWithNonExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testGetAttemptsWithNonExistingIpAddress() {
        System.out.println("testGetAttemptsWithNonExistingIpAddress");
        String ipAddress = "0:0:0:0:1";
        String ipAddress2 = "0:0:1";
        int expected = 0;
        int actual = -1;
       
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(ipAddress2);
        
        //If added
        if(added) {
            //Now lets get attempts
            actual = prdao.getAttempts(ipAddress);
            
            //Lets remove the row
            if(removeRowFromDatabase(ipAddress2) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddTimeoutWithExistingIpAddress method, of class PasswordResetDao
     */
    @Test
    public void testAddTimeoutWithExistingIpAddress() {
        System.out.println("testAddTimeoutWithExistingIpAddress");
        String ipAddress = "0:0:0:0:1";
        boolean expected = true;
        boolean actual = false;
       
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(ipAddress);

        //If added
        if(added) {
            //Now lets add timeout 
            if(prdao.addTimeout(ipAddress)) {
                actual = true;
            }
            else fail("addTimeout(String ipAddress) Method Failed.");
            //Lets remove the row
            if(removeRowFromDatabase(ipAddress) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddTimeoutWithNonExistingIpAddress method, of class PasswordResetDao
     */
    @Test
    public void testAddTimeoutWithNonExistingIpAddress() {
        System.out.println("testAddTimeoutWithNonExistingIpAddress");
        String ipAddress = "0:0:0:0:1";
        String ipAddress2 = "0:0";
        boolean expected = true;
        boolean actual = false;
       
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(ipAddress2);

        //If added
        if(added) {
            //Now lets add timeout 
            if(!prdao.addTimeout(ipAddress)) {
                actual = true;
            }
            else fail("addTimeout(String ipAddress) Method Failed.");
            //Lets remove the row
            if(removeRowFromDatabase(ipAddress2) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testGetTimeoutWithExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testGetTimeoutWithExistingIpAddress() {
        System.out.println("testGetTimeoutWithExistingIpAddress");
        String ipAddress = "0:0:0:1";
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = LocalDateTime.now().plusMinutes(15).minusNanos(nanos);
        LocalDateTime actual = null;
        
        //Add new row
        boolean added = prdao.addNewUserAttempt(ipAddress);
        //Add timeout
        boolean addedTimeout = prdao.addTimeout(ipAddress);
        
        //If added
        if(added && addedTimeout) {
            //Lets get the timeout
            actual = prdao.getTimeout(ipAddress);
            
            if(removeRowFromDatabase(ipAddress) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(expected, actual);
    }

    /**
     * Test of testGetTimeoutWithNonExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testGetTimeoutWithNonExistingIpAddress() {
        System.out.println("testGetTimeoutWithNonExistingIpAddress");
        String ipAddress = "0:0:0:1";
        String ipAddress2 = "0:0:1";
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = null;
        LocalDateTime actual = LocalDateTime.now().plusMinutes(15).minusNanos(nanos);
        
        //Add new row
        boolean added = prdao.addNewUserAttempt(ipAddress2);
        //Add timeout
        boolean addedTimeout = prdao.addTimeout(ipAddress2);
        
        //If added
        if(added && addedTimeout) {
            //Lets get the timeout
            actual = prdao.getTimeout(ipAddress);
            
            if(removeRowFromDatabase(ipAddress2) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of testRemoveUserAttemptWithExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testRemoveUserAttemptWithExistingIpAddress() {
        System.out.println("testRemoveUserAttemptWithExistingIpAddress");
        String ipAddress = "0:0:0:0";
        boolean expected = true;
        boolean actual = false;
        
        //Lets a a new row
        boolean added = prdao.addNewUserAttempt(ipAddress);
        
        //If added
        if(added) {
            actual = prdao.removeUserAttempt(ipAddress);
            
            //Check tha row was removed
            if(checkRowWasAddedToDatabase(ipAddress) != -1) fail("Row was not removed");
        }
        else fail("Adding new row failed");
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of testRemoveUserAttemptWithNonExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testRemoveUserAttemptWithNonExistingIpAddress() {
        System.out.println("testRemoveUserAttemptWithNonExistingIpAddress");
        String ipAddress = "0:0:0:0";
        String ipAddress2 = "0:0:1";
        boolean expected = false;
        boolean actual = true;
        
        //Lets a a new row
        boolean added = prdao.addNewUserAttempt(ipAddress2);
        
        //If added
        if(added) {
            actual = prdao.removeUserAttempt(ipAddress);
            
            //Check tha row was removed
            if(removeRowFromDatabase(ipAddress2) != 1) fail("Row was not removed");
        }
        else fail("Adding new row failed");
        
        assertEquals(expected, actual);
    }

    /**
     * Test of testGetCreatedAtWithExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testGetCreatedAtWithExistingIpAddress() {
        System.out.println("testGetCreatedAtWithExistingIpAddress");
        String ipAddress = "0:0:1:1";
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = LocalDateTime.now().minusNanos(nanos);
        LocalDateTime actual = null;
        
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(ipAddress);
        
        //If added
        if(added) {
            actual = prdao.getCreatedAt(ipAddress);
            
            //Remove row
            if(removeRowFromDatabase(ipAddress) != 1) fail("Row was not removed");
        }
        else fail("Row was not added");
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of testGetCreatedAtWithNonExistingIpAddress method, of class PasswordResetDao.
    */
    @Test
    public void testGetCreatedAtWithNonExistingIpAddress() {
        System.out.println("testGetCreatedAtWithNonExistingIpAddress");
        String ipAddress = "0:0:1:1";
        String ipAddress2 = "0:0";
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = null;
        LocalDateTime actual = LocalDateTime.now().minusNanos(nanos);
        
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(ipAddress2);
        
        //If added
        if(added) {
            actual = prdao.getCreatedAt(ipAddress);
            
            //Remove row
            if(removeRowFromDatabase(ipAddress2) != 1) fail("Row was not removed");
        }
        else fail("Row was not added");
        
        assertEquals(expected, actual);
    }

    
    /**
     * This method will get a row from a database using an identifier
     * @param identifier this will be used to know which row to get
     * @return will return 1 on success -1 on failure
     */
    private int checkRowWasAddedToDatabase(String identifier) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = prdao.getConnection();
            ps = con.prepareStatement("SELECT * FROM password_reset WHERE ip_address = ?");
            ps.setString(1, identifier);
            rs = ps.executeQuery();
            
            if(rs.next()) return 1;
            
        } catch(SQLException e) {
            return -1;
        } finally {
            try {
                if(con != null) con.close();
                if(ps != null) ps.close();
                if(rs != null) rs.close();
            } catch(SQLException e) {
                return -1;
            }
        }
        
        return -1;
    }
    
    /**
     * This method will remove a row from a database using an identifier
     * @param identifier this will be used to know which row to delete
     * @return  will return 1 on success -1 on failure
     */
    private int removeRowFromDatabase(String identifier) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = -1;
        
        try {
            con = prdao.getConnection();
            ps = con.prepareStatement("DELETE FROM password_reset WHERE ip_address = ?");
            ps.setString(1, identifier);
            count = ps.executeUpdate();
            
            if(count == 1) return count;
            
        } catch(SQLException e) {
            return count;
        } finally {
            try {
                if(con != null) con.close();
                if(ps != null) ps.close();
                if(rs != null) rs.close();
            } catch(SQLException e) {
                return -1;
            }
        }
        
        return count;
        
    }
}
