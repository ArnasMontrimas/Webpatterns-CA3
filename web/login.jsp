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
        <form class="mt-5 m-auto" style="max-width: 500px;" action="controller">
          <input type="hidden" name="action" value="login">
          
          <h1 class="h3 mb-3 fw-normal"><%= bundle.getString("login_page_message") %></h1>

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

          <label for="email" class="visually-hidden"><%= bundle.getString("email") %></label>
          <input type="email" id="email" name="email" class="form-control" placeholder="<%= bundle.getString("email") %>" required autofocus>
          <label for="password" class="visually-hidden"><%= bundle.getString("password") %></label>
          <input type="password" id="password" name="password" class="form-control mt-2" placeholder="<%= bundle.getString("password") %>" required>

          <div class="d-flex justify-content-end">
            <a class="text-decoration-none" href="resetPassword.jsp"><%= bundle.getString("login_forgot_password") %></a>
          </div>

          <button class="w-100 btn btn-lg btn-primary mt-3" type="submit"><%= bundle.getString("login") %></button>
          <a class="w-100 btn btn-lg btn-outline-primary mt-2" href="register.jsp"><%= bundle.getString("register") %></a>
        </form>
      </main>
    </body>
</html>