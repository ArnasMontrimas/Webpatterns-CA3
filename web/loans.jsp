<%@ page import="dtos.*" %>
<%@ page import="daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if not logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp" %>
    </head>
    <body>
      <%@include file="navbar.jsp" %>

      <main class="container pt-3 pb-5">
        <h1><%= bundle.getString("loans_title") %></h1>
        
        <% if (session.getAttribute("errorMessage") != null) { %>
          <div class="alert alert-danger" role="alert">
            ${sessionScope.errorMessage}
          </div>
        <%
          }
          session.removeAttribute("errorMessage");
        %>
        <% if (session.getAttribute("message") != null) { %>
          <div class="alert alert-success" role="alert">
            ${sessionScope.message}
          </div>
        <%
          }
          session.removeAttribute("message");
        %>

        <div class="row justify-content-around">
          <%
            LoanDao loanDao = new LoanDao();
            User user = (User) session.getAttribute("user");
            
            ArrayList<Loan> loans = loanDao.getAllActiveUserLoans(user);
            
            if (loans.size() == 0) {
          %>
            <div class="d-flex flex-column align-items-center">
              <h4 class="text-black-50"><%= bundle.getString("loans_no_loans") %></h4>
              <a href="books.jsp" class="btn btn-outline-primary"><%= bundle.getString("books_see_all") %></a>
            </div>
          <%
            } else {
              BookDao bookDao = new BookDao();
              for (Loan loan: loans) {
                Book book = bookDao.getBookByID(loan.getLoanBook());
          %> 
            <div class="card mx-2 mb-5 px-0" style="width: 18rem;">
              <a href="controller?action=searchBook&query=<%= URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8.toString()) %>">
                <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
              </a>
              <div class="card-body">
                <a class="text-decoration-none text-dark" href="controller?action=searchBook&query=<%= URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8.toString()) %>"><h5 class="card-title mb-0"><%= book.getBookName() %></h5></a>
                <h6><%= book.getAuthor() %></h6>

                <p>
                    <strong><%= bundle.getString("loans_starts") %>&nbsp;</strong><%= dateF.format(loan.getLoanStarted()) %>
                </p>
                <p>
                    <%
                      boolean returnLate = (new Date()).compareTo(loan.getLoanEnds()) > 0;
                    %>
                    <strong><%= bundle.getString("loans_return") %>&nbsp;</strong><span class="<%= returnLate ? "text-danger" : "" %>"><%= dateF.format(loan.getLoanEnds()) %></span>
                </p>
                
                <%
                if (returnLate) {
                  double fees = loan.calculateFees();
                %>
                  <p>
                    <strong><%= bundle.getString("loans_fees") %>&nbsp;</strong><span class="text-danger"><%= curF.format(fees) %></span>
                  </p>
                  <button class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#payFeesModal<%= loan.getLoanID() %>"><%= bundle.getString("loans_return_fees") %></button>
                  
                  <div class="modal fade return-loan-fees-modal" id="payFeesModal<%= loan.getLoanID() %>" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title"><%= bundle.getString("loans_return_fees") %></h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="<%= bundle.getString("close") %>"></button>
                        </div>
                        <div class="modal-body">
                          <p><%= bundle.getString("loans_why_fees") %> <span class="text-danger"<%= curF.format(fees) %></span></p>

                          <form action="controller">
                            <input type="hidden" name="action" value="returnLoan">
                            <input type="hidden" name="bookId" value="<%= book.getBookID() %>">
                            
                            <label class="form-label" for="cardCvv"><%= bundle.getString("loans_cvv_card") %></label>
                            <input type="text" maxlength="4" pattern="^[0-9]{3,4}$" name="cardCvv" class="form-control" placeholder="<%= bundle.getString("register_card_card_cvv") %>" required>
                            <div class="invalid-feedback"><%= bundle.getString("register_invalid_card_cvv") %></div>
                          </form>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><%= bundle.getString("cancel") %></button>
                          <button type="button" class="btn btn-primary submit-button"><%= bundle.getString("loans_confirm_return") %></button>
                        </div>
                      </div>
                    </div>
                  </div>
                <%
                } else {
                %>
                  <a href="controller?action=returnLoan&bookId=<%= book.getBookID() %>" class="btn btn-primary"><%= bundle.getString("loans_return_book") %></a>
                <%
                }
                %>
              </div>
            </div>
          <%
              }
            }
          %>
        </div>
      </main>

      <script src="./js/loans.js"></script>
    </body>
</html>
