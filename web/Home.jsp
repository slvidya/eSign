<%-- 
    Document   : Home
    Created on : 25 Feb, 2018, 11:35:53 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "no-cache");
//    if (session.getAttribute("user") == null) {
//        response.sendRedirect(request.getContextPath() + "/Login.jsp");
//    }
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Home Page</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="css/StyleSheet_Home.css" />
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jsonparser.js"></script>
        <script src="js/Base64EncodeDecode.js"></script>

    </head>
    <body class="bg" style="background-color:lavender">
        <!-- DIV - (Header)  -->
        <%@include file="/Header.jsp" %>
        <!-- DIV - (Navigation bar)  -->
        <%@include file="/NavBar.jsp" %>

        <!-- DIV - (About eSign)  -->
        <%@include file="/About.jsp" %>
        <%@ include file="/Footer.jsp"%>
    </body>
</html>
