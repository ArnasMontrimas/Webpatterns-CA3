package daotests;

import Dtos.*;
import Daos.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arnas
 */
public class UserDaoTest {

    private final UserDao dao = new UserDao("library_test");

  
    /**
     * Testing Registering a user with valid fields
     */
    @Test
    public void testRegisterUserValid() {
      User user = dao.registerUser("test","test","test");

      assertEquals(user,dao.getUserById(user.getUserID()));

      dao.removeUser(user.getEmail());
    }
    
    /**
     * Testing Registering a user with invalid fields
     */
    @Test
    public void testRegisterUserInvalidFields() {
      String username = null;
      String password = dao.hashPassword(null);
      String email = null;
      
      User user = dao.registerUser(username,email,password);

      assertEquals(user,null);
    }
    
    /**
    * Removing an added user
    */
    @Test
    public void testRemoveUserValid() {
      String email = "test@example.com";

      User user = dao.registerUser("test", email, "test");
      boolean registered = dao.getUserById(user.getUserID()) != null;

      boolean removed = dao.removeUser(email);
      boolean checkRemoved = dao.getUserById(user.getUserID()) == null;

      assertTrue(registered && removed && checkRemoved);
      
      dao.removeUser(user.getEmail());
    }
    
    /**
    * Removing a user which does not exist
    */
    @Test
    public void testRemoveUserInvalidUser() {
      String email = "test@example.com";

      boolean removed = dao.removeUser(email);
      
      assertFalse(removed);
    }
    
    /**
    * Removing a user but email is null
    */
    @Test
    public void testRemoveUserInvalidEmail() {
      String email = null;

      boolean removed = dao.removeUser(email);
      
      assertFalse(removed);
    }
    

    // Testing for a valid login
    @Test
    public void testValidateLoginValidUser() {
      User expectedUser = dao.registerUser("test", "test", "test");
      
      assertEquals(expectedUser,dao.validateLogin("test", "test"));
      
      dao.removeUser(expectedUser.getEmail());
    }

    // Testing for invalid login
    @Test
    public void testValidateLoginInvalidUser() {
      assertNull(dao.validateLogin("rando", "rasputin"));
    }



    // Testing to reset a password with correct old password
    @Test
    public void testForgotPasswordResetValid() {
        User user = dao.registerUser("test", "test", "test");

        // Reset password
        assertEquals(true,dao.passwordReset("test", "test2",user.getEmail()));

        // Reset it back to what it was otherwise test would fail after it was run the second time
        assertEquals(true,dao.passwordReset("test2","test",user.getEmail()));
        
        dao.removeUser(user.getEmail());
    }

    // Testing to try to reset password with incorrect old password
    @Test
    public void testForgotPasswordResetInvalidUser() {
        // Reset password
        assertEquals(false,dao.passwordReset("404", "222","404"));
    }

    //Testing to for correct ID by email
    @Test
    public void testGetUserIdByEamilValid() {
      User user = dao.registerUser("test", "test", "test");
      
      assertEquals(dao.getUserIdByEamil(user.getEmail()),dao.getUserIdByEamil(user.getEmail()));
      
      dao.removeUser(user.getEmail());
    }

    // Testing for incorrect ID by email
    @Test
    public void testtestGetUserIdByEamilInvalidEmail() {
      assertEquals(-1,dao.getUserIdByEamil("404"));
    }
    
    // Testing for incorrect ID by email
    @Test
    public void testtestGetUserIdByEamilInvalidFieldNull() {
      assertEquals(-1,dao.getUserIdByEamil(null));
    }
    
    //Testing generating and setting new password
    @Test
    public void testGenerateSetNewPasswordValid() {
        User user = dao.registerUser("test", "test", dao.hashPassword("test"));
       
        //Now new password has been set
        String tmpPassword = dao.generateSetNewPassword(user.getUserID());
        
        //If password was changed checking that password is equal to old one should fail
        assertNotEquals(user.getPassword(), dao.hashPassword(tmpPassword));
        
        dao.removeUser(user.getEmail());
        
    }
    
    //Testing chnage username
    @Test
    public void testChangeUsernameValid() {
        User u = dao.registerUser("test", "test", "test");
        String old = u.getUsername();
        
        dao.changeUsername(u, "lol");
        u = dao.getUserById(u.getUserID());
        
        assertEquals("lol", u.getUsername());
        
        dao.removeUser(u.getEmail());
    }
    
    //Testing chnage username
    @Test
    public void testChangeUsernameInvalidFields() {
        assertFalse(dao.changeUsername(null, null));
    }
    
    //Testing validate email
    @Test
    public void testValidateEmailValid() {
        User u = dao.registerUser("test", "test", "test");
        
        assertTrue(dao.validateEmail(u.getEmail()));
        
        dao.removeUser(u.getEmail());
    }
    
    //Testing validate email
    @Test
    public void testValidateEmailInvalidFields() {
        assertFalse(dao.validateEmail(null));
    }
    
    //Test getting user by id
    @Test
    public void testGetUserByIdValid() {
        User u = dao.registerUser("test", "test", "test");
        
        assertEquals(u, dao.getUserById(u.getUserID()));
        
        dao.removeUser(u.getEmail());
    }
    
    //Test getting user by id
    @Test
    public void testGetUserByIdInvalidFields() {
        User u = dao.registerUser("test", "test", "test");
        
        assertNull(dao.getUserById(-1));
        
        dao.removeUser(u.getEmail());
    }
}
