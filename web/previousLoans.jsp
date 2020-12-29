<%@ page import="Dtos.*" %>
<%@ page import="Daos.*" %>
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
        <title>Online Library</title>
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
            <div class="card mx-2 px-0" style="width: 18rem;">
              <a href="controller?action=searchBook&query=<%= URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8) %>">
                <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
              </a>
              <div class="card-body">
                <a class="text-decoration-none text-dark" href="controller?action=searchBook&query=<%= URLEncoder.encode(book.getBookName(), StandardCharsets.UTF_8) %>"><h5 class="card-title mb-0"><%= book.getBookName() %></h5></a>
                <h6><%= book.getAuthor() %></h6>

                <p>
                    <strong>Loaned:&nbsp;</strong><%= loan.getLoanStarted() %>
                </p>
                <p>
                    <strong>Returned:&nbsp;</strong><%= loan.getLoanEnds() %>
                </p>
                <p>
                  <strong>Fees Paid:&nbsp;</strong><%= loan.getFeesPaid() %>€
                </p>
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

