<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if already logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
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
    </body>
</html>
