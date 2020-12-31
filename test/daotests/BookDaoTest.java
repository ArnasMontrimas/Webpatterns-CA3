/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daotests;

import daos.BookDao;
import dtos.Book;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arnas
 */
public class BookDaoTest {
    BookDao dao = new BookDao("library_test");
    
    //This method will test getAllBooks
    @Test
    public void testGetAllBooksValid() {
        assertFalse(dao.getAllBooks().isEmpty());
    }
    
    //This method will test getAllBooksByGenre
    @Test
    public void testGetAllBooksByGenreValid() {
        //Check we got books by that genre
        assertFalse(dao.getAllBooksByGenre("Thriller").isEmpty());
    
        //Create all books of that genre
        Book b = new Book(8, "themidnightstar.jpg", "The Midnight Star", "678-1-60309-925-4", "She's turned her back on those who have betrayed her and achieved the ultimate revenge: victory. Her reign as the White Wolf has been a triumphant one, but the darkness within her has begun to spiral out of control, threatening to destroy everything.", "Marie Lu", "Macmillan Publishers", 47, "Thriller");
    
        //Check book returned is the correct one
        assertEquals(dao.getAllBooksByGenre("Thriller").get(0), b);
    }
    
    //This method will test getAllBooksByGenre
    @Test
    public void testGetAllBooksByGenreInvalidFieldIsNull() {
        assertTrue(dao.getAllBooksByGenre(null).isEmpty());
    }
    
    //This method will test getAllBooksByGenre
    @Test
    public void testGetAllBooksByGenreInvalidFieldGenreDoesNotExists() {
        assertTrue(dao.getAllBooksByGenre("404").isEmpty());
    }
    
    //This method will test getBookByID
    @Test
    public void testGetBookByIDValid() {
        assertNotNull(dao.getBookByID(8));
        
        //Create a book for comparison
        Book b = new Book(8, "themidnightstar.jpg", "The Midnight Star", "678-1-60309-925-4", "She's turned her back on those who have betrayed her and achieved the ultimate revenge: victory. Her reign as the White Wolf has been a triumphant one, but the darkness within her has begun to spiral out of control, threatening to destroy everything.", "Marie Lu", "Macmillan Publishers", 47, "Thriller");
    
        //Check book returned is the correct one
        assertEquals(dao.getBookByID(8), b);
    }
    
    //This method will test getBookByID
    @Test
    public void testGetBookByIDInvlidFieldNoBookById() {
        assertNull(dao.getBookByID(-1));
    }
    
    //This method will test searchBooks
    @Test public void testSearchBooksValid() {
        assertFalse(dao.searchBooks("The Midnight Star").isEmpty());
        
        //Create a book for comparison
        Book b = new Book(8, "themidnightstar.jpg", "The Midnight Star", "678-1-60309-925-4", "She's turned her back on those who have betrayed her and achieved the ultimate revenge: victory. Her reign as the White Wolf has been a triumphant one, but the darkness within her has begun to spiral out of control, threatening to destroy everything.", "Marie Lu", "Macmillan Publishers", 47, "Thriller");
    
        //Check book returned is the correct one
        assertEquals(dao.getBookByID(8), b);
    }
    
    //This method will test searchBooks
    @Test public void testSearchBooksInvalidFieldIsNull() {
        assertTrue(dao.searchBooks(null).isEmpty());
    }
    
    //This method will test searchBooks
    @Test public void testSearchBooksValidInvalidNothingFound() {
        assertTrue(dao.searchBooks("404").isEmpty());
    }
    
    //This method will test updateBookQuantity
    @Test
    public void testUpdateBookQuantityValidIncrease() {
        //Get book
        Book b = dao.getBookByID(1);
        int expected = b.getQuantityInStock()+1;
        
        //Make sure method worked
        assertTrue(dao.updateBookQuantity(1, 1, true));
        
        //Get book after update
        Book b1 = dao.getBookByID(1);
        int actual = b1.getQuantityInStock();
        
        //Check if quantity actually increased
        assertEquals(expected,actual);
    }
    
    //This method will test updateBookQuantity
    @Test
    public void testUpdateBookQuantityValidDecrease() {
        //Get book
        Book b = dao.getBookByID(1);
        int expected = b.getQuantityInStock()-1;
        
        //Make sure method worked
        assertTrue(dao.updateBookQuantity(1, 1, false));
        
        //Get book after update
        Book b1 = dao.getBookByID(1);
        int actual = b1.getQuantityInStock();
        
        //Check if quantity actually decreased
        assertEquals(expected,actual);
    }
    
    //This method will test updateBookQuantity
    @Test
    public void testUpdateBookQuantityInvalidFieldBookDoesNotExists() {
        assertFalse(dao.updateBookQuantity(-1, 1, false));
    }
    
    //This method will test updateBookQuantity
    @Test
    public void testUpdateBookQuantityInvalidFieldQuantity() {
        assertFalse(dao.updateBookQuantity(-1, -1, false));
    }
    
    //This method will test getAllAvailableGenres
    @Test
    public void testGetAllAvailableGenresValid() {
        assertFalse(dao.getAllAvailableGenres().isEmpty());
        
        //Lets create String[] off genres to compare
        String[] genres = {"Fantasy", "Romance", "Thriller", "History", "Adventure"};
        
        Assert.assertArrayEquals(dao.getAllAvailableGenres().toArray(), genres);
    }
}
