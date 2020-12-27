
package Daos;

import Daointerfaces.BookDaoInterface;
import Dtos.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author samue
 * @author Malo
 */
public class BookDao extends Dao implements BookDaoInterface{
     
    public BookDao() {
        super();
    }
    
    public BookDao(String databaseName) {
        super(databaseName);
    }

     /**
     * This method will return all the books to be displayed on the page. 
     * 
     * @return ArrayList of <code>Book</code> objects.
     */
    @Override
    public ArrayList<Book> getAllBooks() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Book> books = new ArrayList<>();

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM books");
            rs = ps.executeQuery();
            
            while(rs.next()){    
                books.add(new Book(
                  rs.getInt("id"),
                  rs.getString("imagePath"),
                  rs.getString("bookName"),
                  rs.getString("bookIsbn"),
                  rs.getString("bookDescription"),
                  rs.getString("author"),
                  rs.getString("publisher"),
                  rs.getInt("quantityInStock"),
                  rs.getString("genre")
                ));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getAllBooks() method: " + e.getMessage());
            }
        }
        return books;
    }
    
     /**
     * Get All Books by the specified genre
     * @param genre The genre of the book
     * @return Returns an ArrayList of <code>Book</code> objects.
     */
    @Override
    public ArrayList<Book> getAllBooksByGenre(String genre) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Book> books = new ArrayList<>();

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * from books WHERE genre = ?");
            ps.setString(1,genre);
            rs = ps.executeQuery();
            
            while(rs.next()){
                books.add(new Book(
                  rs.getInt("id"),
                  rs.getString("imagePath"),
                  rs.getString("bookName"),
                  rs.getString("bookIsbn"),
                  rs.getString("bookDescription"),
                  rs.getString("author"),
                  rs.getString("publisher"),
                  rs.getInt("quantityInStock"),
                  rs.getString("genre")
                ));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getAllBooksByGenre() method: " + e.getMessage());
            }
        }
        return books;
    }
    
    /**
     * Gets the Book object by the book id (primary key).
     * Null if no book by that id
     * 
     * @param bookID The id of the book
     * @return Returns <code>Book</code> object or null.
     */
    @Override
    public Book getBookByID(int bookID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = null;

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * from books where id = ?");
            ps.setInt(1,bookID);
            rs = ps.executeQuery();
            
            // Book exists by that id
            if(rs.next()){
                book = new Book(
                  bookID,
                  rs.getString("imagePath"),
                  rs.getString("bookName"),
                  rs.getString("bookIsbn"),
                  rs.getString("bookDescription"),
                  rs.getString("author"),
                  rs.getString("publisher"),
                  rs.getInt("quantityInStock"),
                  rs.getString("genre")
                );   
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getAllBooks() method: " + e.getMessage());
            }
        }
        return book;
    }
        
    /**
     * Method to search books by ISBN, name or Author
     * 
     * @param query Search query string
     * @return ArrayList of <code>Book</code> objects.
     */
    @Override
    public ArrayList<Book> searchBooks(String query) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Book> books = new ArrayList<>();

        try{
            con = getConnection();
            ps = con.prepareStatement("Select * from books where bookName LIKE ? OR bookIsbn LIKE ? OR author LIKE ?");
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ps.setString(3, "%" + query + "%");
            rs = ps.executeQuery();

            while(rs.next())
            {
                books.add(new Book(
                  rs.getInt("id"),
                  rs.getString("imagePath"),
                  rs.getString("bookName"),
                  rs.getString("bookIsbn"),
                  rs.getString("bookDescription"),
                  rs.getString("author"),
                  rs.getString("publisher"),
                  rs.getInt("quantityInStock"),
                  rs.getString("genre")
                ));
            }
        }catch (SQLException e) {
            System.out.println("Exception occured in the selectBooksContainingName() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the selectBooksContainingName() method: " + e.getMessage());
            }
        }
        return books;
    }
        
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
    @Override
    public boolean updateBookQuantity(int bookID,int quantity, boolean increase) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try{
            con = getConnection();
            // If increase is wanted
            if (increase) {
            ps = con.prepareStatement("UPDATE books SET quantityInStock = quantityInStock + ? WHERE id = ?");
            ps.setInt(1,quantity);
            ps.setInt(2,bookID);
            rowsAffected = ps.executeUpdate();   
            } else {
            // Decrease wanted
            ps = con.prepareStatement("UPDATE books SET quantityInStock = quantityInStock - ? WHERE id = ?");
            ps.setInt(1,quantity);
            ps.setInt(2,bookID);
            rowsAffected = ps.executeUpdate();
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
            
                e.printStackTrace();
            }
        }
        return rowsAffected != 0;
    }

    /**
     * Get all genres with books
     * @return ArrayList of genres (string)
     */
    @Override
    public ArrayList<String> getAllAvailableGenres() {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      ArrayList<String> genres = new ArrayList<>();

      try{
          con = getConnection();
          ps = con.prepareStatement("SELECT DISTINCT(genre) from books");
          rs = ps.executeQuery();
          
          while(rs.next()){    
              genres.add(rs.getString("genre"));
          }
      }
      catch(SQLException ex){
          ex.printStackTrace();
      } finally {
          try {
              if (rs != null) {
                  rs.close();
              }
              if (ps != null) {
                  ps.close();
              }
              if (con != null) {
                  freeConnection(con);
              }
          } catch (SQLException e) {
              System.out.println("Exception occured in the finally section of the getAllBooks() method: " + e.getMessage());
          }
      }
      return genres;
    }
}
