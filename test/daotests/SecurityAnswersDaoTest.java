
package daotests;

import dtos.*;
import daos.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samue
 */
public class SecurityAnswersDaoTest {

    private final SecurityAnswersDao sdao = new SecurityAnswersDao("creative_library_test");

    // Testing when the user answers a security question correctly
    // Should return true becasue the answer corresponds to the security question
    // Testing all 3 types of questions
    @Test
    public void testValidateCorrectSecurityAnswer() {
        int userID = 39;
        String plain_text_answer_school = "test1";
        String type = "schoolAnswer";

        assertEquals(true,sdao.validateSecurityQuestion(plain_text_answer_school, type,39));

        String plain_text_answer_food = "test2";
        type = "foodAnswer";

        assertEquals(true,sdao.validateSecurityQuestion(plain_text_answer_food,type,39));

        String plain_text_answer_place = "test3";
        type = "placeAnswer";

        assertEquals(true,sdao.validateSecurityQuestion(plain_text_answer_place,type,39));

    }

    // Testing when the user answers a security question incorrectly
    // Should return false becasue the answer does not corresponds to the security question
    // Testing all 3 types of questions
    @Test
    public void testValidateIncorrectSecurityAnswer() {
        int userID = 39;
        String plain_text_answer_school = "badtest1";
        String type = "schoolAnswer";

        assertEquals(false,sdao.validateSecurityQuestion(plain_text_answer_school, type,39));

        String plain_text_answer_food = "badtest2";
        type = "foodAnswer";

        assertEquals(false,sdao.validateSecurityQuestion(plain_text_answer_food,type,39));

        String plain_text_answer_place = "badtest3";
        type = "placeAnswer";

        assertEquals(false,sdao.validateSecurityQuestion(plain_text_answer_place,type,39));
    }
    // Testing to insert the security answers
    @Test
    public void testInsertSecurityAnswers() {
      // This method is tested in UserDao but I need to insert a test user to 
      // Also insert his security answers
      UserDao dao = new UserDao("creative_library_test");
      
      System.out.println("Security Answers Test");
      String username = "TEST_USERNAME";
      String password = dao.hashPassword("TEST_PASSWORD");
      String email = "TEST_EMAIL";
      // Inserting the user into test db here
      int id = dao.registerUser(username,email,password);
      
      assertEquals(true,sdao.insertSecurityAnswers(id, "Test1", "Test2", "Test3"));

    }
}
