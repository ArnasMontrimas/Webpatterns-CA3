package daotests;

import daos.PaymentDetailsDao;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test all methods in PaymentDetailsDao
 * @author grallm
 */
public class PaymentDetailsDaoTest {
  PaymentDetailsDao pdao = new PaymentDetailsDao("library_test");

  /**
   * Test inserting a card
   
  @Test
  public void createPaymentDetails() {
    pdao.insertPaymentDetails(40, "1234567812345678", "123", "David Poutnik", "2021-01-03");

    // Check if item has been added
    assertTrue(pdao.userHasPaymentDetails(40));

    pdao.removePaymentDetails(40);
  }

  /**
   * Test removing a card
  
  @Test
  public void removePaymentDetails() {
    pdao.insertPaymentDetails(41, "1234567812345678", "123", "David Poutnik", "2021-01-03");
    boolean added = pdao.userHasPaymentDetails(41);

    boolean removed = pdao.removePaymentDetails(41);
    boolean checkRemoved = pdao.userHasPaymentDetails(41);

    // Check if item has been removed
    assertTrue(added && removed && !checkRemoved);
  }

  /**
   * Test removing a non-existent card
   
  @Test
  public void removePaymentDetailsNonExistent() {
    boolean exists = pdao.userHasPaymentDetails(1000);

    boolean removed = pdao.removePaymentDetails(1000);

    // Check if no item has been removed
    assertTrue(!exists && !removed);
  }

  /**
   * Testing checkUserHasPaymentDetails with user having payment card
   */
  @Test
  public void checkUserHasPaymentDetails() {
    // Check if item has been added
    assertTrue(pdao.userHasPaymentDetails(39));
  }

  /**
   * Testing checkUserHasPaymentDetails with user not having payment card
   */
  @Test
  public void checkUserHasPaymentDetailsNotHaving() {
    // Check if item has been added
    assertFalse(pdao.userHasPaymentDetails(139));
  }

  /**
   * Decrypting card details with correct key (CVV)
   */
  @Test
  public void decryptPaymentDetails() {
    assertArrayEquals(pdao.getUserPaymentDetails(39, "123"), new String[] {
            "1234567812345678",
            "Sam Poutnik",
            "2020-12-03"
    });
  }

  /**
   * Trying to decrypt card details with wrong key (CVV)
   */
  @Test
  public void wrongKeyDecryptPaymentDetails() {
     assertNull(pdao.getUserPaymentDetails(39, "234"));
  }
}
