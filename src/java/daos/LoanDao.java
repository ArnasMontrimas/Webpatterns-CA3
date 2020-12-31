
package daos;

import daointerfaces.LoanDaoInterface;
import dtos.Book;
import dtos.Loan;
import dtos.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author samue
 * @author Arnas
 * @author grallm
 */
public class LoanDao extends Dao implements LoanDaoInterface{
    
    public LoanDao() {
        super();
    }
    
    public LoanDao(String databaseName) {
        super(databaseName);
    }

     /**
     * Gets all the active user loans or null if no active loans
     * Returns an ArrayList of <code>Loan</code> objects.
     * 
     * @param user The user Object
     * @return ArrayList of <code>Loan</code> objects or null.
     */
    @Override
    public ArrayList<Loan> getAllActiveUserLoans(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Loan> loans = new ArrayList<>();
        BookDao bdao = new BookDao();

        //Null check
        if(user == null) return loans;
        
        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loans WHERE userId = ? AND returned IS NULL");
            ps.setInt(1,user.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int bookID = rs.getInt("bookId");
                Book book = bdao.getBookByID(bookID);
                
                loans.add(new Loan(
                  rs.getInt("id"),
                  user.getUserID(),
                  book.getBookID(),
                  rs.getDate("starts"),
                  rs.getDate("ends"),
                  rs.getDate("returned"),
                  rs.getDouble("feesPaid")
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
                System.out.println("Exception occured in the finally section of the getAllActiveUserLoans() method: " + e.getMessage());
            }
        }
        return loans;
    }
       /**
     * Gets all the previous user loans for a specific user
     * This is NOT including current active loans this is all the loans since joining the library
     * where the user has loaned and returned the a book.
     * Returns an ArrayList of <code>Loan</code> objects.
     * 
     * @param user The user Object
     * @return ArrayList of <code>Loan</code> objects or null.
     */
    @Override
    public ArrayList<Loan> getAllPreviousUserLoans(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Loan> loans = new ArrayList<>();
        BookDao bdao = new BookDao();

        //Null check
        if(user == null) return loans;
        
        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loans WHERE userId = ? AND returned IS NOT NULL");
            ps.setInt(1,user.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int bookID = rs.getInt("bookId");
                Book book = bdao.getBookByID(bookID);
                
                loans.add(new Loan(
                  rs.getInt("id"),
                  user.getUserID(),
                  book.getBookID(),
                  rs.getDate("starts"),
                  rs.getDate("ends"),
                  rs.getDate("returned"),
                  rs.getDouble("feesPaid")
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
                System.out.println("Exception occured in the finally section of the getAllActiveUserLoans() method: " + e.getMessage());
            }
        }
        return loans;
    }
    
    /**
     * This method loans a book to a specified user for a specified number of days
     * @param bookID books id number
     * @param days number of days for loan
     * @param userID users id number
     * @return 0 or -1 on failure
     */
    @Override
    public int loanBook(int bookID, int days, int userID) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;

        if(days < 0) return -1;
        
        try{
            con = getConnection();
            ps = con.prepareStatement("INSERT into loans VALUES(NULL, ?, ?, current_timestamp(), DATE_ADD(current_timestamp(), INTERVAL ? DAY), DEFAULT, DEFAULT)");
            ps.setInt(1,userID);
            ps.setInt(2,bookID);
            ps.setInt(3,days);
            ps.executeUpdate();
            
            
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
        return returnValue;
    }

    /**
     * Get a user's specific loan not returned
     * @param userId User's loans to check
     * @param bookId Book to check if user's loans
     * @return null if not found
     */
    @Override
    public Loan getActiveLoan(int userId, int bookId) {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      Loan loan = null;
      
      try{
          con = getConnection();
          ps = con.prepareStatement("SELECT * FROM loans WHERE bookId = ? AND userId = ? AND returned IS NULL;");
          ps.setInt(1, bookId);
          ps.setInt(2, userId);
          rs = ps.executeQuery();
          
          if(rs.next()) {
              loan = new Loan(
                rs.getInt("id"),
                userId,
                bookId,
                rs.getDate("starts"),
                rs.getDate("ends"),
                rs.getDate("returned"),
                rs.getDouble("feesPaid")
              );
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
      
      return loan;
    }
    
    /**
     * Check if a user loans a specific book
     * @param userId User's loans to check
     * @param bookId Book to check if user's loans
     * @return false if not found
     */
    @Override
    public boolean checkIfLoaned(int userId, int bookId) {
        Loan loan = getActiveLoan(userId, bookId);
        return loan != null && loan.getLoanReturned() == null;
    }

    /**
     * Return a loan
     * @param loan Loan to be returned
     * @return true if success, false if failure
     */
    @Override
    public boolean returnLoan(Loan loan) {
      Connection con = null;
      PreparedStatement ps = null;
      int rowsAffected = 0;
      
      if(loan == null) return false;
      
      try{
          con = getConnection();
          // If increase is wanted
          ps = con.prepareStatement("UPDATE loans SET returned = NOW(), feesPaid = ? WHERE id = ?");
          ps.setDouble(1, loan.calculateFees());
          ps.setInt(2, loan.getLoanID());
          rowsAffected = ps.executeUpdate();
          
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

}
