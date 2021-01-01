<%@ page import="dtos.*" %>
<%@ page import="daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if not logged in
    User user = (User)session.getAttribute("user");
    if (user == null) {
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
      <h1>Edit Profile</h1>

      <% if (session.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger" role="alert">
          ${sessionScope.errorMessage}
        </div>
      <%
        }
        session.removeAttribute("errorMessage");
      %>

      <form action="controller">
        <input type="hidden" name="action" value="editProfile">

        <div>
          <label for="username" class="form-label mb-0">Username</label>
          <input type="text" class="form-control" id="username" name="username" placeholder="Username" value="<%= user.getUsername() %>" required>
        </div>

        <div class="mt-4">
          <input type="submit" class="btn btn-primary" value="Save Profile" />
          <a href="profile.jsp" class="btn btn-outline-danger">Cancel</a>
        </div>
      </form>
    </main>
  </body>
</html>

