<%-- 
    Document   : SummaryReports
    Created on : 26 Feb, 2018, 12:49:56 PM
    Author     : Vidya
--%>

<%@page import="com.integra.utils.ValidateSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@page import="com.integra.model.User"%>
<%@page import="com.integra.utils.ConfigListener"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Summary Reports</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="css/StyleSheet_Home.css" />
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jsonparser.js"></script>
        <script src="js/Base64EncodeDecode.js"></script>
        <script type="text/javascript">
            function displayClientWiseSumRpt(idToShow, firstName)
            {
//                alert("displayClientWiseSumRpt ");
//                alert(firstName);                
                window.location = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/SummaryReports_DrillDown.jsp?loginName=" + firstName;
//                "http://localhost:8080/iSign/SummaryReports_DrillDown.jsp?param=" + firstName;
            }
        </script>

    </head>
    <body class="bg" onload="" style="background-color:lavender">

        <%
            if (!ValidateSession.validateUserSession(request)) {
//                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath()+"/Login.jsp");
                return;
            }

            
            String url = ConfigListener.aspConf.getAsp_databse_url();
            String userName = ConfigListener.aspConf.getAsp_databse_username();
            String password = ConfigListener.aspConf.getAsp_databse_password();
        %>
        <sql:setDataSource
            var="myDS"
            driver="com.mysql.jdbc.Driver"
        url="<%=url%>"
        user="<%=userName%>"
        password="<%=password%>"
            />
        <sql:query var="summaryReport"   dataSource="${myDS}">
            SELECT first_name,total_requests,success_count,failure_count FROM adm_summary_report asr INNER JOIN user_master um ON um.user_id=asr.user_id;
        </sql:query>
        <c:set var="totalCount" scope="session" value="${summaryReport.rowCount}"/>
        <c:set var="perPage" scope="session"  value="10"/>
        <c:set var="pageStart" value="${param.start}"/>
        <c:if test="${empty pageStart or pageStart < 0}">
            <c:set var="pageStart" value="0"/>
        </c:if>
        <c:if test="${totalCount < pageStart}">
            <c:set var="pageStart" value="${pageStart - 10}"/>
        </c:if>


        <!-- DIV - (Header)  -->
        <%@include file="/Header.jsp" %>
        <!-- DIV - (Navigation bar)  -->
        <%@include file="/AdminNavBar.jsp" %>
        <!-- DIV - (Summary Report)  -->
        <div id="divSummaryRpt" style="display:block;">
            <div class="row">
                <div class="col-xs-offset-0 col-xs-12 col-sm-offset-0 col-sm-12 col-md-offset-0 col-md-12 col-lg-offset-0 col-lg-12" style="margin-top:-15px;text-align:center;">
                    <div class="row" style="">
                        <div class="col-xs-offset-3 col-xs-6 col-sm-offset-3 col-sm-6 col-md-offset-3 col-md-6 col-lg-offset-3 col-lg-6">
                            <p><h4><span id=""></span></h4></p>
                        </div>
                        <div class="col-sm-3 col-md-3 col-lg-3" align="right">
                            <p id="txtWelcomeSuffixName" style="color:#8d8d8d;float:right"><h5><b>ADMIN</b></h5></p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="panel panel-primary">
                    <div class="panel-heading" style="text-align:center;font-size: 16px;">
                        <b>Summary Report - Admin</b>
                    </div>
                </div>
                <div style="text-align:left;font-size: 16px;" class="col-sm-6 col-md-6 col-lg-6">
                    <b><a href="#">Total: </a><c:out value="${totalCount}" /></b>
                </div>
                <div style="text-align:right;font-size: 16px;" class="col-sm-6 col-md-6 col-lg-6">
                    <c:if test="${totalCount < pageStart + 10}">
                        <b><a href="?start=${pageStart - 10}">Prev<<</a>${pageStart + 1} - ${totalCount} 
                        <a href="?start=${pageStart + 10}">>>Next</a></b>
                    </c:if>
                    <c:if test="${totalCount > pageStart + 10}">                    
                        <b><a href="?start=${pageStart - 10}">Prev<<</a>${pageStart + 1} - ${pageStart + 10} 
                        <a href="?start=${pageStart + 10}">>>Next</a></b>
                    </c:if>
                </div>

                <div id="" class="col-sm-12 col-md-12 col-lg-12">
                    <!--<table id="" class="table table-bordered styleSubDiv styleRptTable" style="border:2px solid white;font-size:14px">-->
                    <table id="" class="table table-bordered styleSubDiv styleRptTable" style="border:1px solid #002166;background-color:#ccccff;font-size:14px">                        
                        <tr class="row styleTableHdr">
                            <th class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;">User Name</th>
                            <th class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;">Total eSign Requests</th>
                            <th class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;">Total Success Requests</th>
                            <th class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;">Total Failed Requests</th>
                        </tr>
                        <c:forEach var="report" items="${summaryReport.rows}" varStatus="reportCounter"
                                   begin="${pageStart}" end="${pageStart + perPage - 1}">
                            <tr class="row" style="font-size: 16px;">
                                <th class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;"><a href="#" class="" onclick="displayClientWiseSumRpt('divUser_ActRpt', '<c:out value="${report.first_name}"/>')"><u><c:out value="${report.first_name}" /></u></a></th>
                                <td class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;"><c:out value="${report.total_requests}" /></td>
                                <td class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;"><c:out value="${report.success_count}" /></td>
                                <td class="col-sm-3 col-md-3 col-lg-3" style="text-align:center;"><c:out value="${report.failure_count}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <%@include file="/Footer.jsp" %>
    </body>
</html>
