<%-- 
    Document   : errorpage
    Created on : 10 Jul, 2018, 6:30:49 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <body>
        <h1>Oops! Something wrong happened.</h1>
        <h2>click <a href="http://localhost:8080/iSign">here</a> to login</h2>
        <p><%exception.printStackTrace();%></p>
    </body>
</html>
