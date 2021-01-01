<%@ page import="dtos.*" %>
<%@ page import="daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.Date" %>
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
        <h1>Previous Loans</h1>
        
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
            
            ArrayList<Loan> loans = loanDao.getAllPreviousUserLoans(user);
            
            if (loans.size() == 0) {
          %>
            <div class="d-flex flex-column align-items-center">
              <h4 class="text-black-50">No previously loaned books.</h4>
              <a href="loans.jsp" class="btn btn-outline-primary">See current loans</a>
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
                    <strong>Loaned:&nbsp;</strong><%= dateF.format(loan.getLoanStarted()) %>
                </p>
                <p>
                    <strong>Returned:&nbsp;</strong><%= dateF.format(loan.getLoanReturned()) %>
                </p>
                <p>
                  <strong>Fees Paid:&nbsp;</strong><%= curF.format(loan.getFeesPaid()) %>
                </p>

                <%
                OpinionsDao oDao = new OpinionsDao();
                Opinion opinion = oDao.checkIfUserHasOpinion(user.getUserID(), book.getBookID());
                if (opinion != null) {
                  // Display rating
                %>
                  <div>
                    <strong>Your rating:&nbsp;</strong><div class="starrr rated" data-rating="<%= opinion.getRating() %>"></div>
                  </div>
                <%
                } else {
                %>
                  <button class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#rateBook<%= loan.getLoanID() %>">Rate book</button>
                  <div class="modal fade rate-book-modal" id="rateBook<%= loan.getLoanID() %>" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">Rate and comment book <strong><%= book.getBookName() %></strong></h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          <p>After loaning this book you can rate the book and write a comment for the next readers</p>

                          <form action="controller">
                            <input type="hidden" name="action" value="rateBook">
                            <input type="hidden" name="bookId" value="<%= book.getBookID() %>">
                            <input type="hidden" name="rating" value="">

                            <label>Rate your read</label>
                            <div class="starrr"></div>
                            
                            <div class="form-floating mt-2">
                              <textarea class="form-control" placeholder="Leave a comment here" id="comment<%= loan.getLoanID() %>" name="comment"></textarea>
                              <label for="comment<%= loan.getLoanID() %>">Comment</label>
                            </div>
                          </form>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                          <button type="button" class="btn btn-primary submit-button">Submit</button>
                        </div>
                      </div>
                    </div>
                  </div>
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

        <script src="./js/starrr.js"></script>
        <script src="./js/previousLoans.js"></script>
      </main>
    </body>
</html>

