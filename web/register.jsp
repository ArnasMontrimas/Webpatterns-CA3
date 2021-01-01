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
        <%@include file="head.jsp" %>
    </head>
    <body>
      <%@include file="navbar.jsp" %>

      <main class="container pb-5">
        <form class="mt-5" action="controller" method="post">
          <input type="hidden" name="action" value="register">
          
          <div class="d-flex align-items-center justify-content-between mb-2">
            <h2 class="fw-normal"><%= bundle.getString("register_page_message") %></h1>
            <a class="btn btn-outline-primary" href="login.jsp"><%= bundle.getString("register_login_redirect") %></a>
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
              <label for="email" class="form-label"><%= bundle.getString("email") %></label>
              <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" required>
            </div>

            <div class="col-12">
              <label for="username" class="form-label"><%= bundle.getString("username") %></label>
              <input type="text" class="form-control" id="username" name="username" placeholder="Name12345" required>
            </div>

            <div class="col-12" id="passwordContainer">
              <label for="password" class="form-label mb-0"><%= bundle.getString("password") %></label>

              <div class="d-flex flex-column mb-1">
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_length") %></small>
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_cases") %></small>
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_symbols") %></small>
                <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_username") %></small>
              </div>

              <input type="password" class="form-control" id="password" name="password" placeholder="<%= bundle.getString("password") %>" required>

              <div id="pwdStrengthMeter" data-levels-text="<%= bundle.getString("register_password_levels") %>">
                <div class="mt-2"><%= bundle.getString("register_password_meter_text") %> <span></span></div>
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
              <label for="cardNumber" class="form-label"><%= bundle.getString("register_card_number") %></label>
              <input type="text" pattern="^[0-9]{16}$" maxlength="16" class="form-control" id="cardNumber" name="cardNumber" placeholder="1234567812345678" data-error-message="<%= bundle.getString("register_invalid_card_number") %>" required>
            </div>
      
            <div class="col-12">
              <label for="ownerName" class="form-label"><%= bundle.getString("register_card_card_owner") %></label>
              <input type="text" pattern="^[A-Za-z\s-]+$" class="form-control" id="ownerName" name="ownerName" placeholder="Joe Doe" data-error-message="<%= bundle.getString("register_invalid_card_owner") %>" required>
            </div>

            <div class="col-md-3">
              <label for="expirationDate" class="form-label"><%= bundle.getString("register_card_card_cvv") %></label>
              <input type="text" pattern="^[0-9]{2}\/[0-9]{2}$" class="form-control" id="expirationDate" name="expirationDate" placeholder="03/23" data-error-message="<%= bundle.getString("register_invalid_card_cvv") %>" required>
            </div>
            <div class="col-md-3">
              <label for="cardCvv" class="form-label"><%= bundle.getString("register_card_card_date") %></label>
              <input type="text" maxlength="4" class="form-control" id="cardCvv" name="cardCvv" placeholder="1243" data-error-message="<%= bundle.getString("register_invalid_card_date") %>" required>
            </div>
      
            <div>
              <button class="btn btn-outline-primary mt-2" type="submit" id="registerSubmit" disabled><%= bundle.getString("register") %></button>
            </div>
          </div>
        </form>
      </main>
    </body>
    
    <script src="./js/zxcvbn.js"></script>
    <script src="./js/register.js"></script>
</html>