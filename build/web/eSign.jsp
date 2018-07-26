<%-- 
    Document   : eSign
    Created on : 15 Mar, 2018, 5:27:31 PM
    Author     : Vidya
--%>
<%@page import="com.integra.model.User"%>
<%@page import="com.integra.utils.ContextBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="errorpage.jsp"%>

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
        <title>eSign Page</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="css/StyleSheet_Home.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
            <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">

                <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
                <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
                <script type="text/javascript" src="js/jquery-ui.js"></script>
                <script type="text/javascript" src="js/jquery.blockUI.js"></script>
                <script type="text/javascript" src="js/bootstrap.min.js"></script>
                <script type="text/javascript" src="js/validateaadhaar.js"></script>
                <script type="text/javascript" src="js/esign.js"></script>
                <script type="text/javascript" src="js/asp_context.js"></script>
                <script type="text/javascript" src="js/sha256js.js"></script>
                <script type="text/javascript" src="js/makeServerCall.js"></script>
                <script type="text/javascript">
                    function noBack()
                    {
                        window.history.forward(1);
                    }
                    noBack();
                    window.onload = noBack;
                    window.onpageshow = function(evt) {
                        if (evt.persisted)
                            noBack()
                    }
                    window.onunload = function() {
                        void (0)
                    }

                    function enableeSign()
                    {
                        var chConsent = document.getElementsByName("chConsent")[0].checked;
                        if (chConsent) {
                            document.getElementById("btneSign").disabled=false;
                        } else {
                            document.getElementById("btneSign").disabled=true;
                        }
                    }

                </script>
                </head>
                <body class="bg" onload="" style="background-color:lavender">
                    <%
                        User user = (User) session.getAttribute("user");
                        ContextBean contextBean = (ContextBean) application.getAttribute("contextpaths");
//                        System.out.println(contextBean.getAspContextPath());
//                        System.out.println(contextBean.getEspContextPath());
                    %>
                    <!-- DIV - (Header)  -->
                    <%@include file="/Header.jsp" %>
                    <%@include file="/NavBar.jsp" %>
                    <!-- DIV - (eSign)  -->
                    <div id="">
                        <div class="row">
                            <div class="col-xs-offset-0 col-xs-12 col-sm-offset-0 col-sm-12 col-md-offset-0 col-md-12 col-lg-offset-0 col-lg-12" style="margin-top:-15px;text-align:center;">
                                <div class="row" style="">
                                    <div class="col-xs-offset-3 col-xs-6 col-sm-offset-3 col-sm-6 col-md-offset-3 col-md-6 col-lg-offset-3 col-lg-6">
                                        <p><h4><span id=""><b></b></span></h4></p>
                                    </div>
                                    <div class="col-sm-3 col-md-3 col-lg-3" align="right">
                                        <p id="txtWelcomeSuffixName" style="color:#8d8d8d;float:right"><h5><b><%=user.getFirstName()%></b></h5></p>
                                        <!--<p id="txtWelcomeSuffixName" style="color:#8d8d8d;float:right"><h5><b>ASP Client ID: IMS</b></h5></p>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="color:#002166;margin-top:50px">
                                <form name="eSignForm" id="eSignForm" method="POST" enctype="multipart/form-data">

                                    <!-- onSubmit="return eSign();" action="esign.do?method=esign"--> 
                                    <div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">
                                        <!--<div class="col-md-offset-3 col-md-6 col-sm-offset-1 col-sm-10 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">-->
                                        <label class="col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10" style="text-align:center;font-size:20px;margin-top:20px;">eSign</label>
                                        <table class="table">
                                            <tr class="row">
                                                <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                                    <label id="lblFilename">Choose File *</label>
                                                </td>
                                                <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                                    <input type="file" id="tbFileName" name="tbFileName"/>
                                                </td>
                                            </tr>
                                        </table>
                                        <table class="table">
                                            <tr class="row">
                                                <td class="col-sm-1 col-md-1 col-lg-1" align="right">
                                                    <input type="checkbox" id="chConsent" name="chConsent" class="checkmark" onclick="enableeSign();"/>
                                                </td>
                                                <td class="col-sm-11 col-md-11 col-lg-11">
                                                    <label id="lblConsent">I hereby give my consent for using e-KYC data from Aadhaar 
                                                        for the purpose of signing selected data and generating Digital Signature Certificate (DSC).*</label>

                                                    <!--I give my consent to iSign to use my Aadhaar number & OTP to fetch my e-KYC details from UIDAI for digitally signing this document using the eSign service.-->
                                                </td>
                                            </tr>
                                        </table>
                                        <table class="table">
                                            <tr class="row">
                                                <td class="col-md-3 col-sm-3 col-xs-3">
                                                </td>
                                                <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                                    <input class="btn btn-primary" type="submit" id="btneSign" style="margin-top:0px;margin-bottom:20px" value="eSign" disabled/>
                                                </td>
                                                <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                                    <input class="btn btn-primary" type="button" id="btnCanceleSign" style="margin-top:0px;margin-bottom:20px" value="Cancel" onclick="location.href = 'Home.jsp'"/>
                                                </td>
                                                <td class="col-md-3 col-sm-3 col-xs-3">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </form>
                                <form id="URL" name="URL" method="POST" enctype="multipart/form-data">
                                    <input type="hidden" name="obj" id="obj" value=""/>
                                    <input type="hidden" name="msg" id="msg" value=""/>
                                    <input type="hidden" name="aspurl" id="aspurl" value="<%=contextBean.getAspContextPath()%>"/>
                                    <input type="hidden" name="espurl" id="espurl" value="<%=contextBean.getEspContextPath()%>"/>
                                </form>

                            </div>
                        </div>
                    </div>
                    <%@include file="/Footer.jsp" %>
                </body>
                </html>
