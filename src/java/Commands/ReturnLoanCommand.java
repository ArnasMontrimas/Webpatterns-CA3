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
import Daos.PaymentDetailsDao;
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
        } catch (Exception e) {}

        if (bookId != 0) {
          LoanDao loanDao = new LoanDao();
          BookDao bookDao = new BookDao();
          Loan loan = loanDao.getActiveLoan(user.getUserID(), bookId);

          // Check of returned too late
          if (loan.calculateFees() > 0) {
            PaymentDetailsDao pDetailsDao = new PaymentDetailsDao();
            
            String cardCvv = request.getParameter("cardCvv");

            // Check security code
            if (cardCvv != null && !cardCvv.isEmpty()) {
              String[] pDetails = pDetailsDao.getUserPaymentDetails(user.getUserID(), cardCvv);

              if (pDetails != null && loanDao.returnLoan(loan)) {
                session.setAttribute("message", "Successfully returned <strong>" + bookDao.getBookByID(loan.getLoanBook()).getBookName() + "</strong>");
              } else {
                session.setAttribute("errorMessage", "The card security code is invalid");
              }
            } else {
              session.setAttribute("errorMessage", "Missing card security code");
            }
          } else {
            if (loanDao.returnLoan(loan)) {
              session.setAttribute("message", "Successfully returned <strong>" + bookDao.getBookByID(loan.getLoanBook()).getBookName() + "</strong>");
            }
          }
        }
        
        return forwardToJspPage;
    }
    
}
