/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.BookDao;
import daos.LoanDao;
import daos.PaymentDetailsDao;
import dtos.Loan;
import dtos.User;

import javax.servlet.http.HttpSession;

/**
 *
 * @author grallm
 */
public class ReturnLoanCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        Locale clientLocale = (Locale) session.getAttribute("currentLocale");
        ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);

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
                session.setAttribute("message", bundle.getString("returncmd_success") + " <strong>" + bookDao.getBookByID(loan.getLoanBook()).getBookName() + "</strong>");
              } else {
                session.setAttribute("errorMessage", bundle.getString("returncmd_cvv_incorrect"));
              }
            } else {
              session.setAttribute("errorMessage", bundle.getString("returncmd_cvv_missing"));
            }
          } else {
            if (loanDao.returnLoan(loan)) {
              session.setAttribute("message", bundle.getString("returncmd_success") + " <strong>" + bookDao.getBookByID(loan.getLoanBook()).getBookName() + "</strong>");
            }
          }
        }
        
        return forwardToJspPage;
    }
    
}
