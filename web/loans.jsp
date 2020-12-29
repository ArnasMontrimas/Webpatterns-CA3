<%@ page import="Dtos.*" %>
<%@ page import="Daos.*" %>
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
        <title>Online Library</title>
        <%@include file="head.jsp" %>
    </head>
    <body>
      <%@include file="navbar.jsp" %>

      <main class="container pt-3 pb-5">
        <h1>Current Loans</h1>
        
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
              <h4 class="text-black-50">No loaned books.</h4>
              <a href="books.jsp" class="btn btn-outline-primary">See all books</a>
            </div>
          <%
            } else {
              BookDao bookDao = new BookDao();
              for (Loan loan: loans) {
                Book book = bookDao.getBookByID(loan.getLoanBook());
          %> 
            <div class="card mx-2 px-0" style="width: 18rem;">
              <a href="controller?action=searchBook&query=<%= URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8) %>">
                <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
              </a>
              <div class="card-body">
                <a class="text-decoration-none text-dark" href="controller?query=<%= URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8) %>"><h5 class="card-title mb-0"><%= book.getBookName() %></h5></a>
                <h6><%= book.getAuthor() %></h6>

                <p>
                    <strong>Loaned:&nbsp;</strong><%= loan.getLoanStarted() %>
                </p>
                <p>
                    <%
                      boolean returnLate = (new Date()).compareTo(loan.getLoanEnds()) > 0;
                    %>
                    <strong>Return date:&nbsp;</strong><span class="<%= returnLate ? "text-danger" : "" %>"><%= loan.getLoanEnds() %></span>
                </p>
                
                <%
                if (returnLate) {
                %>
                  <p>
                    <strong>Fees:&nbsp;</strong><span class="text-danger"><%= loan.calculateFees() %>€</span>
                  </p>
                <%
                }
                %>

                <a href="controller?action=returnLoan&bookId=<%= book.getBookID() %>" class="btn btn-primary">Return book</a>
              </div>
            </div>
          <%
              }
            }
          %>
        </div>
      </main>
    </body>
</html>
