/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Daos.BookDao;
import Daos.LoanDao;
import Dtos.Loan;
import Dtos.User;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Malo
 */
public class ReturnLoanCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String forwardToJspPage = "loans.jsp";
        
        User user = (User) session.getAttribute("user");
        String bookIdStr = request.getParameter("bookId");
        int bookId = 0;
        try {
          bookId = Integer.parseInt(bookIdStr);
        } catch (Exception e) {
          session.setAttribute("errorMessage", e.getMessage());
        }

        if (bookId != 0) {
          LoanDao loanDao = new LoanDao();
          BookDao bookDao = new BookDao();
          Loan loan = loanDao.getLoan(user.getUserID(), bookId);
          session.setAttribute("message", loan.calculateFees() + ", " + loan.getLoanID());
          
          if (loanDao.returnLoan(loan)) {
            session.setAttribute("message", "Successfully returned <strong>" + bookDao.getBookByID(loan.getLoanBook()).getBookName() + "</strong>");
          }
        }
        // session.setAttribute("message", Integer.toString(bookId));
        
        return forwardToJspPage;
    }
    
}
