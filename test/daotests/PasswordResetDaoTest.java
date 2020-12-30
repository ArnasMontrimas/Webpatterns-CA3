/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daotests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Daos.PasswordResetDao;
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
    private final PasswordResetDao prdao = new PasswordResetDao("library_prod_test");

    /**
     * Test of addNewUserAttempt method, of class PasswordResetDao.
     * Here there is only 1 test as the user_id is not being checked if its a valid user_id or not
     * but with every request to reset password, user_id has to match with the one in the database
     * if it does not a new row will be created (will be interpreted as new client if no match found)
     */
    @Test
    public void testAddNewUserAttempt() {
        int id = 1;
        boolean actual = prdao.addNewUserAttempt(id);
        boolean expected = true;
        
        //This will check that the method worked
        if(actual) {
            //Now lets check that the row was actually added in the database
            if(checkRowWasAddedToDatabase(id) == 1){
                //Now lets remove the row and make sure it works
                //This is here so there are no conflicts when running multiple tests
                if(removeRowFromDatabase(id) == 1) {
                    actual = true;
                }
                else fail("Row was not removed from database");
            }
            else fail("No Row was added in the databse");
        }
        else fail("addNewUserAttempt(String id) Method Failed.");
        
        assertEquals(actual,expected);
        
    }
    
    /**
     * Test of testGetidWithExistingid method, of class PasswordResetDao.
     */
    @Test
    public void testGetidWithExistingid() {
        int id = 1;
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(id);
        
        //If adding worked
        if(added) {
            //Now lets check that added user_id is the correct one
            //This method gets the user_id and also checks if its equal to the one provided as parameter
            if(prdao.getUserid(id)) {
                //Everything works lets remove the added row
                if(removeRowFromDatabase(id) == 1) {
                    actual = true;
                }
                else fail("Row was not removed from database");
            }
            else fail("getid(String id) Method failed");
        }
        else fail("No Row was added in the databse");
        
        assertEquals(actual, expected);
    }

    /**
     * Test of testGetidWithNonExistingid method, of class PasswordResetDao.
     */
    @Test
    public void testGetidWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(id2);
        
        //If adding worked
        if(added) {
            //Now lets check that added user_id is the correct one
            //This should return false as no user_id should be found
            if(!prdao.getUserid(id)) {
                //Everything works lets remove the added row
                if(removeRowFromDatabase(id2) == 1) {
                    actual = true;
                }
                else fail("Row was not removed from database");
            }
            else fail("getid(String id) Method failed");
        }
        else fail("No Row was added in the databse");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddAttemptWithExistingid method, of class PasswordResetDao.
     */
    @Test
    public void testAddAttemptWithExistingid() {
        int id = 1;
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(id);
        
        //If adding worked
        if(added) {
            //If adding attempt worked
            if(prdao.addAttempt(id)) {
                //Now lets remove the row
                if(removeRowFromDatabase(id) == 1) {
                    actual = true;
                }
                else fail("Removing row from database failed");
            }
            else fail("addAttempt(String id) Method Failed.");
        }
        else fail("No row was added in the database");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddAttemptWithNonExistingid method, of class PasswordResetDao.
     */
    @Test
    public void testAddAttemptWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        boolean expected = true;
        boolean actual = false;
        
        //We will add a new row to the database
        boolean added = prdao.addNewUserAttempt(id2);
        
        //If adding worked
        if(added) {
            //If adding attempt worked
            if(!prdao.addAttempt(id)) {
                //Now lets remove the row
                if(removeRowFromDatabase(id2) == 1) {
                    actual = true;
                }
                else fail("Removing row from database failed");
            }
            else fail("addAttempt(String id) Method Failed.");
        }
        else fail("No row was added in the database");
        
        assertEquals(actual, expected);
        
    }

    /**
     * Test of testGetAttemptsWithExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testGetAttemptsWithExistingid() {
        int id = 1;
        int expected = 1;
        int actual = 0;
        
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(id);
        
        //If added
        if(added) {
            //Now lets get attempts
            actual = prdao.getAttempts(id);
            
            //Lets remove the row
            if(removeRowFromDatabase(id) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }

    /**
     * Test of testGetAttemptsWithNonExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testGetAttemptsWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        int expected = 0;
        int actual = -1;
       
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(id2);
        
        //If added
        if(added) {
            //Now lets get attempts
            actual = prdao.getAttempts(id);
            
            //Lets remove the row
            if(removeRowFromDatabase(id2) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddTimeoutWithExistingid method, of class PasswordResetDao
     */
    @Test
    public void testAddTimeoutWithExistingid() {
        int id = 1;
        boolean expected = true;
        boolean actual = false;
       
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(id);

        //If added
        if(added) {
            //Now lets add timeout 
            if(prdao.addTimeout(id)) {
                actual = true;
            }
            else fail("addTimeout(String id) Method Failed.");
            //Lets remove the row
            if(removeRowFromDatabase(id) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testAddTimeoutWithNonExistingid method, of class PasswordResetDao
     */
    @Test
    public void testAddTimeoutWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        boolean expected = true;
        boolean actual = false;
       
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(id2);

        //If added
        if(added) {
            //Now lets add timeout 
            if(!prdao.addTimeout(id)) {
                actual = true;
            }
            else fail("addTimeout(String id) Method Failed.");
            //Lets remove the row
            if(removeRowFromDatabase(id2) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(actual, expected);
    }
    
    /**
     * Test of testGetTimeoutWithExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testGetTimeoutWithExistingid() {
        int id = 1;
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        LocalDateTime expected = LocalDateTime.now().plusMinutes(15);
        LocalDateTime actual = null;
        
        //Add new row
        boolean added = prdao.addNewUserAttempt(id);
        //Add timeout
        boolean addedTimeout = prdao.addTimeout(id);
        
        //If added
        if(added && addedTimeout) {
            //Lets get the timeout
            actual = prdao.getTimeout(id);
            
            if(removeRowFromDatabase(id) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(expected.getMinute(), actual.getMinute());
    }

    /**
     * Test of testGetTimeoutWithNonExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testGetTimeoutWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = null;
        LocalDateTime actual = LocalDateTime.now().plusMinutes(15).minusNanos(nanos);
        
        //Add new row
        boolean added = prdao.addNewUserAttempt(id2);
        //Add timeout
        boolean addedTimeout = prdao.addTimeout(id2);
        
        //If added
        if(added && addedTimeout) {
            //Lets get the timeout
            actual = prdao.getTimeout(id);
            
            if(removeRowFromDatabase(id2) != 1) fail("Removing row failed");
        }
        else fail("Adding row to database failed");
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of testRemoveUserAttemptWithExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testRemoveUserAttemptWithExistingid() {
        int id = 1;
        boolean expected = true;
        boolean actual = false;
        
        //Lets a a new row
        boolean added = prdao.addNewUserAttempt(id);
        
        //If added
        if(added) {
            actual = prdao.removeUserAttempt(id);
            
            //Check tha row was removed
            if(checkRowWasAddedToDatabase(id) != -1) fail("Row was not removed");
        }
        else fail("Adding new row failed");
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of testRemoveUserAttemptWithNonExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testRemoveUserAttemptWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        boolean expected = false;
        boolean actual = true;
        
        //Lets a a new row
        boolean added = prdao.addNewUserAttempt(id2);
        
        //If added
        if(added) {
            actual = prdao.removeUserAttempt(id);
            
            //Check tha row was removed
            if(removeRowFromDatabase(id2) != 1) fail("Row was not removed");
        }
        else fail("Adding new row failed");
        
        assertEquals(expected, actual);
    }

    /**
     * Test of testGetCreatedAtWithExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testGetCreatedAtWithExistingid() {
        int id = 1;
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = LocalDateTime.now().minusNanos(nanos);
        LocalDateTime actual = null;
        
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(id);
        
        //If added
        if(added) {
            actual = prdao.getCreatedAt(id);
            
            //Remove row
            if(removeRowFromDatabase(id) != 1) fail("Row was not removed");
        }
        else fail("Row was not added");
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of testGetCreatedAtWithNonExistingid method, of class PasswordResetDao.
    */
    @Test
    public void testGetCreatedAtWithNonExistingid() {
        int id = 1;
        int id2 = 2;
        //We remove the nanoseconds as when we get time from databse nanos is not included
        //Add if the LocalDateTime object created here and the one added to the database do not get created simultaniously the nano seconds will be different so we remove them
        int nanos = LocalDateTime.now().getNano();
        LocalDateTime expected = null;
        LocalDateTime actual = LocalDateTime.now().minusNanos(nanos);
        
        //Lets add a new row
        boolean added = prdao.addNewUserAttempt(id2);
        
        //If added
        if(added) {
            actual = prdao.getCreatedAt(id);
            
            //Remove row
            if(removeRowFromDatabase(id2) != 1) fail("Row was not removed");
        }
        else fail("Row was not added");
        
        assertEquals(expected, actual);
    }

    
    /**
     * This method will get a row from a database using an identifier
     * @param identifier this will be used to know which row to get
     * @return will return 1 on success -1 on failure
     */
    private int checkRowWasAddedToDatabase(int identifier) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = prdao.getConnection();
            ps = con.prepareStatement("SELECT * FROM password_reset WHERE user_id = ?");
            ps.setInt(1, identifier);
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
    private int removeRowFromDatabase(int identifier) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = -1;
        
        try {
            con = prdao.getConnection();
            ps = con.prepareStatement("DELETE FROM password_reset WHERE user_id = ?");
            ps.setInt(1, identifier);
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
