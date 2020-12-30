<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

<link href="./css/style.css" rel="stylesheet">

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">

<%
Locale clientLocale = (Locale) session.getAttribute("currentLocale");
ResourceBundle bundle = null;
if(clientLocale == null){
  clientLocale = request.getLocale();
  session.setAttribute("currentLocale", clientLocale);
  bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
  session.setAttribute("Bundle", bundle);
} else {
  bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
}

NumberFormat numF = NumberFormat.getInstance(clientLocale);
DateFormat dateF = DateFormat.getDateInstance(DateFormat.DEFAULT,clientLocale);
NumberFormat curF = NumberFormat.getCurrencyInstance(clientLocale);
%> 