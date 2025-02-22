package daointerfaces;

import java.util.ArrayList;

import dtos.Book;

public interface BookDaoInterface {
    
    /**
     * Get All Books
     * @return Returns an ArrayList of <code>Book</code> objects.
     */
    ArrayList<Book> getAllBooks();
    
     /**
     * Method to search books by ISBN, name or Author
     * 
     * @param query Search query string
     * @return ArrayList of <code>Book</code> objects.
     */
    ArrayList<Book> searchBooks(String query);
    
     /**
     * Gets the Book object by the book id (primary key).
     * Null if no book by that id
     * 
     * @param bookID The id of the book
     * @return Returns <code>Book</code> object or null.
     */
    Book getBookByID(int bookID);
    
     /**
     * Update Book quantity by book id
     * If increase is true then the quantity will be increased
     * If increase is false then the quantity will be decreased
     * 
     * @param quantity The quantity to increase or decrease
     * @param bookID The book id
     * @param increase To increase or decrease the quantity
     * @return Returns true if quantity was updated false otherwise
     */
    boolean updateBookQuantity(int bookID,int quantity,boolean increase);
    
    
    /**
     * Get All Books by the specified genre
     * @param genre The genre of the book
     * @return Returns an ArrayList of <code>Book</code> objects.
     */
    ArrayList<Book> getAllBooksByGenre(String genre);
    
    /**
     * Get all genres with books
     * @return ArrayList of genres (string)
     */
    ArrayList<String> getAllAvailableGenres();
}
