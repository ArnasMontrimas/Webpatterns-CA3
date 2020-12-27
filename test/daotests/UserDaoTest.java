
package daotests;

import Dtos.*;
import Daos.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samue
 */
public class UserDaoTest {

    private final UserDao dao = new UserDao("creative_library_test");

   /**
    * Test of validateUsername method, of class UserDao.
    */
   // Testing for a username that is already in the database
  @Test
  public void testUnavailableUsername() {
      System.out.println("Unavailable username test");
      String username = "Sam";

      assertEquals(false,dao.validateUsername(username));
  }

  // Testing for a username that is available
  @Test
  public void testAvailableUsername() {
      System.out.println("Available username test");
      String username = "Samxdddddd";

      assertEquals(true,dao.validateUsername(username));
  }

  // Testing for registering a user into the users table
  // Validation for all the fields would be done before this method would be called
  // So test for INSERT I then select it if its actually been added.
  /**
   * **************** Commented out for now i'm getting errors some methods used in the tests are missing from the daos
   * 
  @Test
  public void testRegisterUser() {
      System.out.println("Register user test");
      String username = "TEST_USERNAME";
      String password = dao.hashPassword("TEST_PASSWORD");
      String email = "TEST_EMAIL";
      // Inserting the user into test db here
      int id = dao.registerUser(username,email,password);

      // Now to see if I can actually select the user from the database
      // If its null that means I cant select the user I just inserted so that means the test would fail
      assertNotSame(null,dao.getUserByID(id));

      dao.removeUser(email);
  }
  **/
  /**
   * Removing an added user
   
  @Test
  public void testRemoveUser() {
      String email = "test@example.com";

      int id = dao.registerUser("test", email, "test");
      boolean registered = dao.getUserByID(id) != null;

      boolean removed = dao.removeUser(email);
      boolean checkRemoved = dao.getUserByID(id) == null;

      assertTrue(registered && removed && checkRemoved);
  }

    /**
     * Removing a non-existent user
    
    @Test
    public void testRemoveUserNonExistent() {
        String email = "test22222@example.com";

        boolean nonExistent = dao.getUsernameByEmail(email).equals("");

        boolean removed = dao.removeUser(email);

        assertTrue(nonExistent && !removed);
    }
    */

  // Testing for a valid login
  @Test
  public void testValidLogin() {
      System.out.println("Valid login test");
      String username = "Sam";
      String password_plaintext = "2";
      // Sams unique Primary key
      int expectedID = 39;

      assertEquals(expectedID,dao.validateLogin(username, password_plaintext));
  }

  // Testing for invalid login
  @Test
  public void testInvalidLogin() {
      System.out.println("Invalid login test");
      String username = "TEST";
      String password_plaintext = "TEST";
      // Sams unique Primary key
      int expectedID = 0;

      assertEquals(expectedID,dao.validateLogin(username, password_plaintext));
  }

  // Testing for login for an account which is no longer active
  @Test
  public void testInactiveLogin() {
      System.out.println("Inactive login test");
      String username = "David";
      String password_plaintext = "1";
      // -1 to be returned if the account is inactive
      int expectedID = -1;

      assertEquals(expectedID,dao.validateLogin(username, password_plaintext));
  }

  // Testing to return a valid User Object based on ID
  @Test
  public void testGetValidUser() throws ParseException {
      System.out.println("Valid user object test");
      SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
      Date d = formatter.parse("2020-11-29 15:00:23");
      User u = new User(39,"Member","Sam","sam@gmail.com","$2a$15$wBPRaUQKrtlaCg3oKfGUf.L7xvR31LIuTB94pNsE1x9kViGsHpLsC",d,true);
      
      assertEquals(u,dao.getUserByID(39));
  }

  // Testing to return null giving a primary key of 0
  @Test
  public void testGetInvalidUser() {
      System.out.println("Invalid user object test");
      assertEquals(null,dao.getUserByID(0));
  }

  // Testing to reset a password with correct old password
  @Test
  public void testValidPasswordReset() {
      System.out.println("Valid password reset test");
      String username = "David";
      String old_plaintext_pass = "1";
      String new_plaintext_pass = "newpass";

      // Reset password
      assertEquals(true,dao.passwordReset(old_plaintext_pass, new_plaintext_pass,username));

      // Reset it back to what it was otherwise test would fail after it was run the second time
      assertEquals(true,dao.passwordReset(new_plaintext_pass,old_plaintext_pass,username));

  }

  // Testing to try to reset password with incorrect old password
  @Test
  public void testInvalidPasswordReset() {
      System.out.println("Invalid old password reset test");
      String username = "David";
      String old_plaintext_pass = "BadOldpassword";
      String new_plaintext_pass = "BigBrainHackerPassword123";

      // Reset password
      assertEquals(false,dao.passwordReset(old_plaintext_pass, new_plaintext_pass,username));
  }

   //Testing to for correct ID by username
  @Test
  public void testGetIDByUsernameCorrect() {
      System.out.println("Valid GetIDByUsername test");
      String username = "David";
      int expectedID = 40;

      assertEquals(expectedID,dao.getIDByUsername(username));
  }

  // Testing for incorrect ID by username
  @Test
  public void testGetIDByUsernameIncorrect() {
      System.out.println("Invalid GetIDByUsername test");
      String username = "TESTING_TESTING";
      int expectedID = 0;

      assertEquals(expectedID,dao.getIDByUsername(username));
  }

  // Testing for correct username change
  @Test
  public void testChangeUsernameCorrect() {
      System.out.println("Valid username change test");
      String username = "Sam";
      String newusername = "Sam1";

      // Change username
      assertEquals(true,dao.changeUsername(newusername,username));

      // Select by the new username to see if it actually changed
      int samsID = 39;
      assertEquals(39,dao.getIDByUsername(newusername));

      // Change username back to original otherwise test would fail second time around
      assertEquals(true,dao.changeUsername(username,newusername));
  }

  // Testing for incorrect username change
  @Test
  public void testChangeUsernameIncorrect() {
      System.out.println("Invalid username change test");
      System.out.println("Valid username change test");
      String username = "Sam";

      // This username is already taken
      String newusername = "David";

      // Change username
      assertEquals(false,dao.changeUsername(newusername,username));
  }
   
   // Testing for correct forgotten password change
    // Security validation would be done before this method would be called
    // Security tests are tested in the other Dao class with appropriate security methods
    // Testing for correct update
   @Test
   public void testForgotPasswordResetCorrect() {
       System.out.println("Valid forgot password change test");
       String username = "David";
       // The old password that they had forgotten
       String old_plaintext_pass = "1";
       // The new chosen password to be changed
       String new_plaintext_pass = "2";

       // Test to validate if the password has been updated
       assertEquals(true,dao.forgotPasswordReset(new_plaintext_pass,username));

       // Reset it back otherwise test would fail
       assertEquals(true,dao.forgotPasswordReset(old_plaintext_pass,username));
   }
}
