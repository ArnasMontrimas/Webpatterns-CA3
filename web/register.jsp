<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if already logged in
    if (session.getAttribute("user") != null) {
        response.sendRedirect("index.jsp");
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

      <main class="container pb-5">
        <form class="mt-5" action="controller">
          <input type="hidden" name="action" value="register">
          
          <div class="d-flex align-items-center justify-content-between mb-2">
            <h2 class="fw-normal">Register to the Online Library</h1>
            <a class="btn btn-outline-primary" href="login.jsp">I already have an account</a>
          </div>
          
          <% if (session.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" role="alert">
              ${sessionScope.errorMessage}
            </div>
          <%
            }
            session.removeAttribute("errorMessage");
          %>
          
          <div class="row g-3">
            <div class="col-12">
              <label for="email" class="form-label">Email</label>
              <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com">
            </div>
      
            <div class="col-12">
              <label for="password" class="form-label">Password</label>
              <input type="password" class="form-control" id="password" name="password" placeholder="1234 Main St" required>
            </div>
      
            <div class="col-12">
              <label for="username" class="form-label">Username</label>
              <input type="text" class="form-control" id="username" name="username" placeholder="Name12345" required>
            </div>
      
            <h4 class="fw-normal mt-4">Payment details</h3>
              <div class="col-12 mt-2">
                <label for="cardNumber" class="form-label">Card Number</label>
                <input type="text" pattern="^[0-9]{16}$" maxlength="16" class="form-control" id="cardNumber" name="cardNumber" placeholder="1234567812345678" required>
              </div>
      
              <div class="col-12">
                <label for="ownerName" class="form-label">Card owner name</label>
                <input type="text" class="form-control" id="ownerName" name="ownerName" placeholder="Joe Doe" required>
              </div>

              <div class="col-md-3">
                <label for="expirationDate" class="form-label">Expiration date</label>
                <input type="text" pattern="^[0-9]{2}\/[0-9]{2}$" class="form-control" id="expirationDate" name="expirationDate" placeholder="03/23" required>
              </div>
              <div class="col-md-3">
                <label for="cardCvv" class="form-label">Security Code</label>
                <input type="text" pattern="^[0-9]{3,4}$" maxlength="4" class="form-control" id="cardCvv" name="cardCvv" placeholder="1243" required>
              </div>
      
              <div>
                <button class="btn btn-outline-primary mt-2" type="submit">Register</button>
              </div>
          </div>
        </form>
      </main>
    </body>
</html>