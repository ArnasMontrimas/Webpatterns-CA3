
package Daos;

import Daointerfaces.LoanDaoInterface;
import Dtos.Book;
import Dtos.Loan;
import Dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author samue
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

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loans WHERE loanUserID = ? AND loanReturned IS NULL");
            ps.setInt(1,user.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int bookID = rs.getInt("loanBookID");
                Book book = bdao.getBookByID(bookID);
                
                loans.add(new Loan(
                        rs.getInt("loanID"),
                        user,
                        book,
                        rs.getDate("loanStarted"),
                        rs.getDate("loanEnds"),
                        rs.getDate("loanReturned"),
                        rs.getDouble("fees")
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

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loans WHERE loanUserID = ? AND loanReturned IS NOT NULL");
            ps.setInt(1,user.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                int bookID = rs.getInt("loanBookID");
                Book book = bdao.getBookByID(bookID);
                
                loans.add(new Loan(
                        rs.getInt("loanID"),
                        user,
                        book,
                        rs.getDate("loanStarted"),
                        rs.getDate("loanEnds"),
                        rs.getDate("loanReturned"),
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
     * @return 0
     */
    @Override
    public int loanBook(int bookID, int days, int userID) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement("INSERT into loans VALUES(NULL,?,?,current_timestamp(),DATE_ADD(current_timestamp(),INTERVAL ? DAY),DEFAULT,DEFAULT)");
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
     * 
     * @param bookID
     * @return 
     */
    @Override
    public boolean checkIfLoaned(int bookID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loans WHERE loanBookID = ?;");
            ps.setInt(1, bookID);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                return true;
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
        
        return false;
    }
}
