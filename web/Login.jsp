<%--
    Document   : HomePage
    Created on : 25 Feb, 2018, 11:05:12 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Login Page</title>
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
            </script>
            <script language="javascript">
                var errorMessage = "<%=request.getAttribute("error")%>";
                if (errorMessage != 'null') {
                    alert(errorMessage);
//                    $("#error_msg").text(errorMessage);
                }
            </script> 
    </head>

    <body class="bg" onload="" style="background-color:lavender">

        <!-- DIV Header -->
        <%@ include file="/Header.jsp"%>

        <!-- DIV - (Login)  -->
        <div id="divSupLogin">
            <div class="row" style="margin-top:50px;">
                <div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="color:#002166;margin-top:50px">
                    <form name="loginForm" action="login.do?method=login" method="post" onSubmit="return login()">
                        <div class="col-md-offset-3 col-md-6 col-sm-offset-1 col-sm-10 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">
                            <label class="col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10" style="text-align:center;font-size:20px;margin-top:20px;">Login</label>
                            <div style="margin-top:30px;">

                                <table class="table">
                                    <tr class="row">
                                        <td class="col-md-4 col-sm-4 col-xs-4" align="right">
                                            <label id="lblLoginId">User Name *</label>
                                        </td>
                                        <td class="col-md-8 col-sm-8 col-xs-8" align="left">
                                            <input type="text" name="tbLoginId" id="tbLoginId" placeholder="User Name" autofocus="autofocus"/>
                                            <!--<label id="lblLoginId"></label>-->
                                        </td>
                                    </tr>
                                    <tr class="row">
                                        <td class="col-md-4 col-sm-4 col-xs-4" align="right">
                                            <label id="lblLoginPwd">Password *</label>
                                        </td>
                                        <td class="col-md-8 col-sm-8 col-xs-8" align="left">
                                            <input type="password" name="tbLoginPwd" id="tbLoginPwd" placeholder="Password" /><br />
                                            <!--<div style="margin-top:5px"><a id="linkIdFPwd" href="#">Forgot Password ?</a></div>-->
                                            <label id="lblLoginPwd"></label>
                                        </td>
                                    </tr>
                                </table>
                                <table class="table">
                                    <tr>
                                        <td>
                                            <div class="col-md-offset-3 col-md-3 col-sm-6 col-xs-6" align="right">
                                                <input class="btn btn-primary" type="submit" id="btnLoginAsSuper" style="margin-top:-40px;margin-bottom:20px" value="Login"/>
                                            </div>

                                            <div class="col-md-3 col-sm-6 col-xs-6" align="left">
                                                <input class="btn btn-primary" type="button" id="btnCancelLoginAsSuper" style="margin-top:-40px;margin-bottom:20px" value="Sign Up" onclick="location.href = 'SignUp.jsp'" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="col-md-offset-4 col-md-3 col-sm-6 col-xs-6" align="center">
                                                <input class="btn btn-primary" type="button" id="btneSignAsGuest" style="margin-top:-40px;margin-bottom:20px" value="eSign As Guest" onclick="location.href = 'eSignGuest.jsp'" />
                                            </div>
                                        </td>
                                    </tr>

                                </table>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
        <%@ include file="/Footer.jsp"%>
    </body>
</html>
