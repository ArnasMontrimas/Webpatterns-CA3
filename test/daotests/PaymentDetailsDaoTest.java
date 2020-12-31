package daotests;

import daos.PaymentDetailsDao;
import daos.UserDao;
import dtos.User;
import org.junit.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test all methods in PaymentDetailsDao
 * @author grallm, Arnas
 */
public class PaymentDetailsDaoTest {
  PaymentDetailsDao pdao = new PaymentDetailsDao("library_test");
  UserDao udao = new UserDao("library_test");
  
  //This method will test isertPaymentDetails
  @Test
  public void testInsertPaymentDetailsValid() {
      User u = udao.registerUser("test", "test", "test");
      
      boolean expected = pdao.insertPaymentDetails(u.getUserID(), "222222", "22222", "222", "03/20");
      
      assertTrue(expected);
      
      udao.removeUser(u.getEmail());
      
  }
  
  //This method will test isertPaymentDetails
  @Test
  public void testInsertPaymentDetailsInvalidFields() {
      assertFalse(pdao.insertPaymentDetails(-1, null, null, null, null));
  }
  
  //This method will test getUserPaymentDetails
  @Test
  public void testGetUserPaymentDetailsValid() {
      User u = udao.registerUser("test", "test","test");
      
      pdao.insertPaymentDetails(u.getUserID(), "test", "test", "test", "test");
      
      String[] expected = {"test", "test", "test"};
      
      Assert.assertArrayEquals(expected,pdao.getUserPaymentDetails(u.getUserID(), "test"));
      
      udao.removeUser(u.getEmail());
  }
  
  //This method will test getUserPaymentDetails
  @Test
  public void testGetUserPaymentDetailsInvalid() {
      assertNull(pdao.getUserPaymentDetails(-1, null));
  }

  //This method will test userHasPaymentDetails
  @Test
  public void testUserHasPaymentDetailsValid() {
      User u = udao.registerUser("test", "test", "test");
      
      pdao.insertPaymentDetails(u.getUserID(), "test", "test", "test", "test");
      
      assertTrue(pdao.userHasPaymentDetails(u.getUserID()));
      
      udao.removeUser(u.getEmail());
      
  }
  
  //This method will test userHasPaymentDetails
  @Test
  public void testUserHasPaymentDetailsInvalid() {
      assertFalse(pdao.userHasPaymentDetails(-1));
  }
}
