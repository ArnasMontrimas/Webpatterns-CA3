/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daotests;

import daos.LoanDao;
import daos.UserDao;
import dtos.Book;
import dtos.Loan;
import dtos.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arnas
 */
public class LoanDaoTest {
    LoanDao dao = new LoanDao("library_test");
    UserDao udao = new UserDao("library_test");
    
    //This method will test getAllActiveUserLoans
    @Test
    public void testGetAllActiveUserLoansValid() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //Check if there is a loan        
        assertFalse(dao.getAllActiveUserLoans(u).isEmpty());
            
        //Check that loan data matches the loan added
        assertEquals(dao.getActiveLoan(u.getUserID(), b.getBookID()), dao.getAllActiveUserLoans(u).get(0));
        
        //Remove user we added
        udao.removeUser(u.getEmail());
        
        //Remove loan added
        dao.returnLoan(dao.getAllActiveUserLoans(u).get(0));
    }
     
    //This method will test getAllActiveUserLoans
    @Test
    public void testGetAllActiveUserLoansInvalidNoLoansPresent() {
        //Register user
        User u = udao.registerUser("test", "test", "test");
        
        //Empty ArrayList should be returned if no loans found
        assertTrue(dao.getAllActiveUserLoans(u).isEmpty());
        
        //Remove user we added
        udao.removeUser(u.getEmail());
    }
    
    //This method will test getAllActiveUserLoans
    @Test
    public void testGetAllActiveUserLoansInvalidFields() {
        //Empty ArrayList should be returned if user object is null
        assertTrue(dao.getAllActiveUserLoans(null).isEmpty());
    }

    //This method will test getAllPreviousUserLoans
    @Test
    public void testGetAllPreviousUserLoansValid() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //Get loan for comparison before it becomes in-active
        Loan loan = dao.getActiveLoan(u.getUserID(), b.getBookID());
        
        //Return the loan
        dao.returnLoan(loan);
        
        //Check that there is a loan        
        assertFalse(dao.getAllPreviousUserLoans(u).isEmpty());
        
        //Get the loan again
        Loan loan2 = dao.getAllPreviousUserLoans(u).get(0);
        
        assertTrue(loan2.getLoanReturned() != null);
        
        //Remove user we added
        udao.removeUser(u.getEmail());
        
    }
    
    //This method will test getAllPreviousUserLoans
    @Test
    public void testGetAllPreviousUserLoansInvalidFields() {
        //Should be empty since user is null
        assertTrue(dao.getAllPreviousUserLoans(null).isEmpty());
    }
    
    //This method will test getAllPreviousUserLoans
    @Test
    public void testGetAllPreviousUserLoansInvalidUserHasNoLoans() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Should be empty since no loans for that user
        assertTrue(dao.getAllPreviousUserLoans(u).isEmpty());
        
        //Remove user we added
        udao.removeUser(u.getEmail());
        
    }
    
    //This method will test getAllPreviousUserLoans
    @Test
    public void testGetAllPreviousUserLoansInvalidUserOnlyHasActiveLoans() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //User has only active loans no previous loans should be empty
        assertTrue(dao.getAllPreviousUserLoans(u).isEmpty());
        
        //Remove user we added
        udao.removeUser(u.getEmail());
        
    }

    //This method will test loanBook
    @Test
    public void testLoanBookValid() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        
        //Loan the book should return 0 if loaned
        assertTrue(dao.loanBook(b.getBookID(), 1, u.getUserID()) == 0);
        
        //Get loan for comparison
        Loan loan = dao.getActiveLoan(u.getUserID(), b.getBookID());
        
        //Check that there is a loan        
        assertFalse(dao.getAllActiveUserLoans(u).isEmpty());
        
        //Check that loaned book is the same as book inserted in the database
        assertEquals(loan, dao.getActiveLoan(u.getUserID(), b.getBookID()));
        
        //Remove user we added
        udao.removeUser(u.getEmail());
    }
    
    //This method will test loanBook
    @Test
    public void testLoanBookInvalidFieldDays() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        
        //Loan the book should return -1 if not loaned loaned
        assertTrue(dao.loanBook(b.getBookID(), -1, u.getUserID()) == -1);
        
        //Remove user we added
        udao.removeUser(u.getEmail());
    }
    
    //This method will test getActiveLoan
    @Test
    public void testGetActiveLoanValid() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        
        //Laon a book
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //Check if that we can get the loan    
        assertNotNull(dao.getActiveLoan(u.getUserID(), b.getBookID()));
            
        //Remove user we added
        udao.removeUser(u.getEmail());
        
        //Remove loan added
        dao.returnLoan(dao.getAllActiveUserLoans(u).get(0));
        
    }
    
    //This method will test getActiveLoan
    @Test
    public void testGetActiveLoanInvalidFields() {
        //Check if that we can get the loan    
        assertNull(dao.getActiveLoan(-1, -1));
    }

    //This method will test checkIfLoaned
    @Test
    public void testCheckIfLoanedValid() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        
        //Laon a book
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //Check if that the book was loand   
        assertTrue(dao.checkIfLoaned(u.getUserID(), b.getBookID()));
            
        //Remove user we added
        udao.removeUser(u.getEmail());
        
    }
    
    //This method will test checkIfLoaned
    @Test
    public void testCheckIfLoanedInvalidLoanWasReturned() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        
        //Laon a book
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //Return the loan
        dao.returnLoan(dao.getActiveLoan(u.getUserID(), b.getBookID()));
        
        //Check if that the book was loand   
        assertFalse(dao.checkIfLoaned(u.getUserID(), b.getBookID()));
            
        //Remove user we added
        udao.removeUser(u.getEmail());
        
    }
    
    //This method will test checkIfLoaned
    @Test
    public void testCheckIfLoanedInvalidFields() {   
        assertFalse(dao.checkIfLoaned(-1, -1));
    }
    
    //This method will test returnLoan
    @Test
    public void testReturnLaonValid() {
        //Lets register a user
        User u = udao.registerUser("test", "test", "test");
        
        //Created a book
        Book b = new Book(1,"test","test","test","test","test","test",1,"test");
        
        //Laon a book
        dao.loanBook(b.getBookID(), 1, u.getUserID());
        
        //Return the loan should return true
        assertTrue(dao.returnLoan(dao.getActiveLoan(u.getUserID(), b.getBookID())));
        
        //Lets check loan does not exists anymore
        assertFalse(dao.checkIfLoaned(u.getUserID(), b.getBookID()));    
        
        //Remove user we added
        udao.removeUser(u.getEmail());
        
    }
    
    //This method will test returnLoan
    @Test
    public void testReturnLaonInvalidFieldIsNull() {
        //Return the loan should return false as loan does not exist
        assertFalse(dao.returnLoan(null));
        
    }
    
    //This method will test returnLoan
    @Test
    public void testReturnLaonInvalidLoanDoesNotExist() throws ParseException {
        //Rnadom date object
        String myDate = "2014/10/29 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(myDate);
        
        //Lets create fake loan
        Loan loan = new Loan(1,1,1,date,date,date,00.00);
        
        //Return the loan should return false as loan does not exist
        assertFalse(dao.returnLoan(loan));
        
    }

}
