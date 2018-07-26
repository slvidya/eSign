<%-- 
    Document   : eSign
    Created on : 15 Mar, 2018, 5:27:31 PM
    Author     : Vidya
--%>
<%@page import="com.integra.utils.ContextBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>eSign Response Page</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="css/StyleSheet_Home.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
            <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
            <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
            <script type="text/javascript" src="js/jquery-ui.js"></script>
            <script type="text/javascript" src="js/bootstrap.min.js"></script>
            <script type="text/javascript" src="js/esign.js"></script>
            <script type="text/javascript" src="js/asp_context.js"></script>
            <!--<script type="text/javascript" src="js/readfile.js"></script>-->
            <script type="text/javascript" src="js/sha256js.js"></script>
            <script type="text/javascript" src="js/makeServerCall.js"></script>

            <script type="text/javascript">
                $(document).ready(function() {
                    var aspurl = document.getElementById("aspurl").value;
                        $.ajax({
                        type: "GET",
//                        url: "https://182.72.231.117:9945/iASP/rest/services/download",
                        url: aspurl+ "/download",
                        contentType: "text/plain",
                        crossDomain: true,
                        xhrFields: {
                            withCredentials: true
                        },
                        success: function(aspResp) {
//                            alert("aspResp" + aspResp);
                            $.ajax({
                                type: "POST",
                                url: "esign.do?method=signDoc",
                                data: aspResp,
                                //                    processData: false,
                                contentType: "text/plain",
                                cache: false,
                                timeout: 600000,
                                success: function(resp) {
//                                    alert(resp);
                                    var responseJson = JSON.parse(resp);
//                                    alert(responseJson);
                                    var response = responseJson[0].response;
//                                    alert(response);
                                    if (response == "Inavlid Session")
                                    {
                                        alert("Session Timed Out");
                                        window.location.replace(aspclient_contextPath + "/Login.jsp");
                                    }
                                    var responseJson = JSON.parse(resp);
                                    var esignStatus = responseJson[0].esignStatus;
                                    var x509Certificate = responseJson[0].userX509Certificate;

//                                    alert("esignStatus " + esignStatus);
                                    if (esignStatus == 1)
                                    {
                                        document.getElementById("results").style.display = '';
                                        document.getElementById("output").style.display = '';
                                    } else {
                                        document.getElementById("results").style.display = '';
                                        document.getElementById("failure").style.display = '';
                                    }
                                },
                                error: function(ts) {
                                    //                        $(document).ajaxStop($.unblockUI);
                                    alert("Attaching Signature to Doc Failed");
                                }
                            });
                        },
                        error: function(ts) {
                            //                $(document).ajaxStop($.unblockUI);
                            alert("Response Download Failed ");
                        }
                    });
//                    e.preventDefault();
                });</script>
    </head>
    <body class="bg" onload="" style="background-color:lavender">
        <%
            ContextBean contextBean = (ContextBean) application.getAttribute("contextpaths");
        %>
        <!-- DIV - (Header)  -->
        <%@include file="/Header.jsp" %>
        <%@include file="/NavBar.jsp" %>
        <!-- DIV - (eSign)  -->
        <div id="divSupLogin">
            <div class="row" style="margin-top:50px;">
                <div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="color:#002166;margin-top:50px">
                    <form name="eSignResponseForm" method="get">
                        <div class="col-md-offset-3 col-md-6 col-sm-offset-1 col-sm-10 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">
                            <table class="table" id="results" style="display:none">
                                <tr class="row">
                                    <td class="col-md-12 col-sm-12 col-xs-12" align="center">
                                        <!--<a href="https://182.72.231.117:9945/iSign/rest/servcies/download">Click to Download the Response</a>-->
                                        <div id="output" style="display:none">
                                            <h4 style="color: green;"> E-Sign request was successful!</h4>
                                            <a href='${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/esign.do?method=downloadFile'><b>Download Signed Document</b></a>
                                            <!--<a href='https://182.72.231.117:9945/iSign/esign.do?method=downloadFile'><b>Download Response</b></a>-->
                                        </div>

                                        <div id="failure"  style="display:none">
                                            <h4 style="color: red;"> E-Sign request was not successful! Please try again..</h4>
                                            <h5><%=request.getSession().getAttribute("failureReason")%></h5>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <input type="hidden" name="aspurl" id="aspurl" value="<%=contextBean.getAspContextPath()%>"/>
                    </form>
                </div>

            </div>
        </div>
    </body>
</html>
