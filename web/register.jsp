<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if already logged in
    if (session.getAttribute("user") != null) {
        response.sendRedirect("loans.jsp");
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
        <form class="mt-5" action="controller" method="post">
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
              <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" required>
            </div>

            <div class="col-12">
              <label for="username" class="form-label">Username</label>
              <input type="text" class="form-control" id="username" name="username" placeholder="Name12345" required>
            </div>

            <div class="col-12" id="passwordContainer">
              <label for="password" class="form-label mb-0">Password</label>

              <div class="d-flex flex-column mb-1">
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> must be at least 12 characters long</small>
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> must contain both upper and lowercase letters (First letter can't be capitalized)</small>
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> must have at least one number and one symbol (NOT at end)</small>
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> can't contain your username</small>
              </div>

              <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>

              <div id="pwdStrengthMeter">
                <div class="mt-2">Your password is <span>Invalid</span></div>
                <div>
                  <div class="meter-level"></div>
                  <div class="meter-level"></div>
                  <div class="meter-level"></div>
                  <div class="meter-level"></div>
                </div>
              </div>
            </div>
      
            <h4 class="fw-normal mt-4">Payment details</h4>
            <div class="col-12 mt-2">
              <label for="cardNumber" class="form-label">Card Number</label>
              <input type="text" pattern="^[0-9]{16}$" maxlength="16" class="form-control" id="cardNumber" name="cardNumber" placeholder="1234567812345678" required>
            </div>
      
            <div class="col-12">
              <label for="ownerName" class="form-label">Card owner name</label>
              <input type="text" pattern="^[A-Za-z\s-]+$" class="form-control" id="ownerName" name="ownerName" placeholder="Joe Doe" required>
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
              <button class="btn btn-outline-primary mt-2" type="submit" id="registerSubmit" disabled>Register</button>
            </div>
          </div>
        </form>
      </main>
    </body>
    
    <script src="./js/zxcvbn.js"></script>
    <script src="./js/register.js"></script>
</html>