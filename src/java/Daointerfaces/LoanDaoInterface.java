package Daointerfaces;

import Dtos.Loan;
import Dtos.User;
import java.util.ArrayList;

public interface LoanDaoInterface {
    
     /**
     * Gets all the active user loans or null if no active loans
     * Returns an ArrayList of <code>Loan</code> objects.
     * 
     * @param user The user Object
     * @return ArrayList of <code>Loan</code> objects or null.
     */
    ArrayList<Loan> getAllActiveUserLoans(User user);
    
    
    /**
     * Loans a book to a specified user
     * @param bookID book id to be loaned
     * @param days number of days the loan will last
     * @param userID the user id th whom loan is assigned
     * @return int integer number representing different errors and if succseful or not
     */
    int loanBook(int bookID,int days,int userID);

    /**
     * Get a user's specific loan
     * @param userID User's loans to check
     * @param bookID Book to check if user's loans
     * @return null if not found
     */
    Loan getLoan(int userId, int bookId);
    
    /**
     * Check if a user loans a specific book
     * @param userID User's loans to check
     * @param bookID Book to check if user's loans
     * @return 
     */
    boolean checkIfLoaned(int userID, int bookID);
    
    /**
     * 
     * @param user
     * @return 
     */
    ArrayList<Loan> getAllPreviousUserLoans(User user);

    /**
     * Return a loan
     * @param loan Loan to be returned
     * @return true if success, false if failure
     */
    boolean returnLoan(Loan loan);
}
