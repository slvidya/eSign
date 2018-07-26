<%-- 
    Document   : AdminHome
    Created on : 26 Feb, 2018, 12:34:15 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "no-cache");
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
    }
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>AdminHome Page</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="css/StyleSheet_Home.css" />
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jsonparser.js"></script>
        <script src="js/Base64EncodeDecode.js"></script>
    </head>
    <body class="bg" onload="" style="background-color:lavender">
        <!-- DIV - (Header)  -->
        <%@include file="/Header.jsp" %>
        <!-- DIV - (Navigation bar)  -->
        <%@include file="/AdminNavBar.jsp" %>
        <!-- DIV - (About eSign)  -->
        <%@include file="/About.jsp" %>
        <!-- DIV - (Footer)  -->
        <%@include file="/Footer.jsp" %>
    </body>
</html>
