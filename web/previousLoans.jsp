<%@ page import="Dtos.*" %>
<%@ page import="Daos.*" %>
<%@ page import="java.util.ArrayList" %>
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

      <main class="container pt-3 pb-5" id="booksPage">
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
            
            for (Loan loan: loans) {
              Book book = loan.getLoanBook();
          %> 
            <div class="card mx-2" style="width: 18rem;">
              <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
              <div class="card-body">
                <h5 class="card-title mb-0"><%= book.getBookName() %></h5>
                <h6><%= book.getAuthor() %></h6>
                <p class="card-text"><%= book.getBookDescription() %></p>
              </div>
            </div>
          <%
            }
          %>
        </div>
      </main>
    </body>
</html>

