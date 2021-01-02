package daotests;

import org.junit.Test;
import static org.junit.Assert.*;

import daos.OpinionsDao;


public class OpinionsDaoTest {
  OpinionsDao opinionsDao = new OpinionsDao("library_test");

  /**
   * Testing adding an opinion for a book
   */
  @Test
  public void testAddOpinion() {
    // Checking if this opinion doesn't exist before
    assertNull(opinionsDao.checkIfUserHasOpinion(1, 1));
     
    assertTrue(opinionsDao.addOpinion(1, 1, 5, "What an awesome read ?! Best book ever"));

    // Checking if this opinion doesn't exist after
    assertNotNull(opinionsDao.checkIfUserHasOpinion(1, 1));
    opinionsDao.removeOpinion(1, 1);
  }

  /**
   * Testing removing an existant opinion
   */
  @Test
  public void testRemoveOpinion() {
    assertTrue(opinionsDao.addOpinion(1, 2, 5, "What an awesome read ?! Best book ever"));
    assertTrue(opinionsDao.removeOpinion(1, 2));
  }

  /**
   * Testing removing a nonexistant opinion
   */
  @Test
  public void testRemoveOpinionNonExistant() {
    assertNull(opinionsDao.checkIfUserHasOpinion(1, 3));
    assertFalse(opinionsDao.removeOpinion(1, 3));
  }

  /**
   * Testing getting an existant opinion
   */
  @Test
  public void testGettingOpinion() {
    assertNotNull(opinionsDao.checkIfUserHasOpinion(2, 3));
  }

  /**
   * Testing getting a nonexistant opinion
   */
  @Test
  public void testGettingOpinionNonExistant() {
    assertNull(opinionsDao.checkIfUserHasOpinion(2, 2));
  }

  /**
   * Testing getting all opinions for a book
   */
  @Test
  public void testGettingBookOpinion() {
    assertEquals(opinionsDao.getBookOpinions(3).toString(), "[Opinion{id=1, userId=2, bookId=3, date=2020-12-30, rating=4, comment=''}]");
  }

  /**
   * Testing getting all opinions for a book where there are none
   */
  @Test
  public void testGettingBookOpinionNone() {
    assertTrue(opinionsDao.getBookOpinions(4).isEmpty());
  }
}
