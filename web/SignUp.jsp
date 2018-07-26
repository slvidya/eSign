<%-- 
    Document   : SignUp
    Created on : 25 Feb, 2018, 11:12:58 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>SignUp Page</title>
        <link rel="stylesheet" href="css/jquery-ui.css" />
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

            <script language="javascript">
                var errorMessage = "<%=request.getAttribute("error")%>";
//                alert(errorMessage);
//                document.getElementById('error_msg').innerHtml = "errorMessage";
                if (errorMessage != 'null') {
                    alert(errorMessage);
                    $("#error_msg").text(errorMessage);
//                    document.getElementById("error_msg").innerHtml = errorMessage;
//                    alert("Value set" + document.getElementById(result).value);
                    //$("#result").text(errorMessage);
                }
                $(function() {
                    var date = new Date();
                    var year = date.getFullYear();

                    $("#tbDOB").datepicker({
                        changeMonth: true,
                        changeYear: true,
                        maxDate: new Date(),
                        yearRange: '1900:'+''+year+''
                    });
                });

            </script> 

    </head>

    <body class="bg" onload="" style="background-color:lavender">
        <!-- DIV - (Header)  -->

        <%@include file="/Header.jsp" %>
        <!-- DIV - (Navigation bar)  -->
        <div class="" id="divNavbar">
            <nav class="navbar navbar-default">
                <div class="container-fluid" id="divNavbarStyle">
                    <ul class="nav navbar-nav row">
                        <!--<li><a class="navbarLinkStyle" href="#">Home</a></li>-->
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="Login.jsp" class="btn btn-default "><span class="glyphicon glyphicon-chevron-left"></span> Back</a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <!-- DIV - (Sign Up)  -->
        <div id="">
            <div class="row">
                <div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="color:#002166;margin-top:50px">
                    <form name="signUpForm" action="register.do?method=register" method="post" onSubmit="return register()">

                        <div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">
                            <!--<div class="col-md-offset-3 col-md-6 col-sm-offset-1 col-sm-10 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">-->
                            <label class="col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10" style="text-align:center;font-size:20px;margin-top:20px;">Sign Up</label>
                            <span id="error_msg" style="color: red;" align="center"></span>
                            <table class="table">
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblFname">First Name *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbFName" name="tbFName" placeholder="First Name" autofocus="autofocus"/>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblMname">Middle Name </label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbMName" name="tbMName" placeholder="Middle Name" />
                                    </td>
                                </tr>
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblLname">Last Name *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbLName" name="tbLName" placeholder="Last Name" />
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblAdharName">Name as in AADHAAR *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbAdharName" name="tbAdharName" placeholder="Name as in Adhar Card" />
                                    </td>
                                </tr>
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblGender">Gender *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <select id="slGender" name="slGender">
                                            <option value="0">-- Select --</option>
                                            <option value="M">Male</option>
                                            <option value="F">Female</option>
                                            <option value="O">Other</option>                                            
                                        </select>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblDOB">Date of Birth *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <!--<input type="text" id="tbDOB" name="tbDOB" placeholder="DOB" />-->
                                        <input type="text" id="tbDOB" name="tbDOB" placeholder="MM/DD/YYYY"/>
                                    </td>
                                </tr>
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblEmail">Email-ID *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="email" id="tbEmail" name="tbEmail" placeholder="Email-ID" />
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblMobileNo">Mobile No. *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbMobileNo" name="tbMobileNo" placeholder="Mobile No" onkeypress="return onlyNumbers(event)" maxlength="10"/>
                                    </td>
                                </tr>
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblAdhaar">Aadhaar Number *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbAadhaar" name="tbAadhaar" placeholder="Aadhaar Number"  onkeypress="return onlyNumbers(event)" maxlength="12"/>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblLoginName">Login Name *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbLoginName" name="tbLoginName" placeholder="Login Name" />
                                    </td>
                                </tr>
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblPwd">Password *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="password" id="tbPwd" name="tbPwd" placeholder="Password" />
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblCnfPwd">Confirm Password *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="password" id="tbCnfPwd" name="tbCnfPwd" placeholder="Confirm Password" />
                                    </td>
                                </tr>
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3">
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <input class="btn btn-primary" type="submit" id="btnSignUp" style="margin-top:0px;margin-bottom:20px" value="Submit"/>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input class="btn btn-primary" type="button" id="btnCancelSignUp" style="margin-top:0px;margin-bottom:20px" value="Cancel" onclick="location.href = 'Login.jsp'" />
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%@ include file="/Footer.jsp"%>
    </body>
</html>
