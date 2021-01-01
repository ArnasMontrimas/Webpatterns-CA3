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
        <h1><%= bundle.getString("ploans_title") %></h1>
        
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
              <h4 class="text-black-50"><%= bundle.getString("ploans_none") %></h4>
              <a href="loans.jsp" class="btn btn-outline-primary"><%= bundle.getString("ploans_see_current") %></a>
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
                    <strong><%= bundle.getString("ploans_loaned") %>&nbsp;</strong><%= dateF.format(loan.getLoanStarted()) %>
                </p>
                <p>
                    <strong><%= bundle.getString("ploans_returned") %>&nbsp;</strong><%= dateF.format(loan.getLoanReturned()) %>
                </p>
                <p>
                  <strong><%= bundle.getString("ploans_paid") %>&nbsp;</strong><%= curF.format(loan.getFeesPaid()) %>
                </p>

                <%
                OpinionsDao oDao = new OpinionsDao();
                Opinion opinion = oDao.checkIfUserHasOpinion(user.getUserID(), book.getBookID());
                if (opinion != null) {
                  // Display rating
                %>
                  <div>
                    <strong><%= bundle.getString("ploans_your_rating") %>&nbsp;</strong><div class="starrr rated" data-rating="<%= opinion.getRating() %>"></div>
                  </div>
                <%
                } else {
                %>
                  <button class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#rateBook<%= loan.getLoanID() %>"><%= bundle.getString("ploans_rate") %></button>
                  <div class="modal fade rate-book-modal" id="rateBook<%= loan.getLoanID() %>" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title"><%= bundle.getString("ploans_rate_comment") %> <strong><%= book.getBookName() %></strong></h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="<%= bundle.getString("close") %>"></button>
                        </div>
                        <div class="modal-body">
                          <p><%= bundle.getString("ploans_explain_rate") %></p>

                          <form action="controller">
                            <input type="hidden" name="action" value="rateBook">
                            <input type="hidden" name="bookId" value="<%= book.getBookID() %>">
                            <input type="hidden" name="rating" value="">

                            <label><%= bundle.getString("ploans_rate") %></label>
                            <div class="starrr"></div>
                            
                            <div class="form-floating mt-2">
                              <textarea class="form-control" placeholder="<%= bundle.getString("ploans_leave_comment") %>" id="comment<%= loan.getLoanID() %>" name="comment"></textarea>
                              <label for="comment<%= loan.getLoanID() %>"><%= bundle.getString("ploans_comment") %></label>
                            </div>
                          </form>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><%= bundle.getString("cancel") %></button>
                          <button type="button" class="btn btn-primary submit-button"><%= bundle.getString("submit") %></button>
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

