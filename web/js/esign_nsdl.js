/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function onlyNumbers(event) {
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;
}
var loginName;
function register()
{
//    alert("In registerUser");
    var tbFName = document.getElementById("tbFName").value;
    var tbMName = document.getElementById("tbMName").value;
    var tbLName = document.getElementById("tbLName").value;
    var tbEmail = document.getElementById("tbEmail").value;
    var tbMobileNo = document.getElementById("tbMobileNo").value;
    var tbAadhaar = document.getElementById("tbAadhaar").value;
    var tbLoginName = document.getElementById("tbLoginName").value;
    var tbPwd = document.getElementById("tbPwd").value;
    var tbCnfPwd = document.getElementById("tbCnfPwd").value;

    if (tbFName == null || tbFName == "")
    {
        alert("Please Enter First Name");
        return false;
    }

    if (tbLName == null || tbLName == "")
    {
        alert("Please Enter Last Name");
        return false;
    }
    if (tbEmail == null || tbEmail == "")
    {
        alert("Please Enter Email Id");
        return false;
    }

    if (tbMobileNo == null || tbMobileNo == "")
    {
        alert("Please Enter Mobile Number");
        return false;
    }
    if (tbAadhaar == null || tbAadhaar == "")
    {
        alert("Please Enter Aadhaar Number");
        return false;
    }

    if (tbLoginName == null || tbLoginName == "")
    {
        alert("Please Set Login Name");
        return false;
    }
    if (tbPwd == null || tbPwd == "")
    {
        alert("Please Enter Password");
        return false;
    }

    if (tbCnfPwd == null || tbCnfPwd == "")
    {
        alert("Please Enter Confirm Password");
        return false;
    }
    if (tbPwd != tbCnfPwd)
    {
        alert("Passwords do not match");
        return false;
    }
    return true;
}

function login() {
    var loginId = document.getElementById("tbLoginId").value;
    var loginPwd = document.getElementById("tbLoginPwd").value;
    if (loginId == null || loginId == "")
    {
        alert("Please Enter Login Name");
        return false;
    }
    if (loginPwd == null || loginPwd == "")
    {
        alert("Please Enter Password");
        return false;
    }
    loginName = loginId;
    return true;
}

$(function() {
    alert("esign nsdl");
    $('#btneSign').on("click", function(e) {
        var tbAdhar = document.getElementById("tbAdhar").value;
        var tbFileName = document.getElementById("tbFileName").value;
        var chConsent = document.getElementsByName("chConsent")[0].checked;
        var sc;

        if (tbAdhar == null || tbAdhar == "")
        {
            alert("Please Enter Aadhaar Number");
            return false;
        }
//    alert(check(tbAdhar));
        if (check(tbAdhar) === false)
        {
            alert("Invalid Aadhaar Number.Please Recheck");
            document.getElementById('tbAdhar').value = "";
            document.getElementById("tbAdhar").focus();
            return false;
        }
        if (tbFileName == null || tbFileName == "")
        {
            alert("Please Select a File");
            return false;
        }
        if (!chConsent)
        {
            alert("Please confirm consent");
            return false;
        }
        if (chConsent)
            sc = "Y";
        else
            sc = "N";

        var form = $('#eSignForm')[0];
        var data = new FormData(form);
        $.blockUI({message: '<img src=\"./Images/Waiting_1.gif\" align=\"middle\" /> Please wait, we are generating the doc hash...'});
        $.ajax({
            type: "POST",
            url: "esign.do?method=generateHash",
            enctype: 'multipart/form-data',
            //             contentType: "application/xml;",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function(aspResp) {
                $(document).ajaxStop($.unblockUI);
                alert("success" + aspResp);
                var responseJson = JSON.parse(aspResp);
                var response = responseJson[0].response;
                if (response == "Inavlid Session")
                {
                    alert("Session Timed Out");
                    window.location.replace(aspclient_contextPath + "/Login.jsp");
                }

                if (response == "Exception occured")
                {
                    alert("Error while generating doc hash");
                    return false;
                }
                var ASPInput = {
                    'uid': tbAdhar,
                    'dochash': response,
                    'sc': sc
                }
                $.blockUI({message: '<img src=\"./Images/Waiting_1.gif\" align=\"middle\" /> Please wait, we are generating your esign request...'});
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/iASP/rest/services/esign",
                    contentType: "application/json; charset=utf-8",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: JSON.stringify(ASPInput),
                    success: function(ASPOutput) {
                        $(document).ajaxStop($.unblockUI);
                        alert("Success " + ASPOutput);
                        var inputData = {
                            "msg": ASPOutput,
                            "obj": "588928507592"
                        }

                        $.blockUI({message: '<img src=\"./Images/Waiting_1.gif\" align=\"middle\" /> Please wait, we are processing your esign request...'});
                        $.ajax({
                            type: "POST",
                            url: "https://14.142.129.242/EsignAuth/getEkycDetails",
//                            contentType: "application/x-www-form-urlencoded; charset=utf-8",
                            contentType: "application/json; charset=utf-8",
                            xhrFields: {
                                withCredentials: true
                            },
                            crossDomain: true,
                            data: JSON.stringify(inputData),
                            success: function(esignRes) {
                                alert(JSON.stringify(esignRes));
                                $(document).ajaxStop($.unblockUI);
                                alert("success esp response " + esignRes);
                            },
                            error: function(jqXHR, exception) {
                                $(document).ajaxStop($.unblockUI);
                                alert("Error from esp status " + jqXHR.status);
                                alert("Error status " + exception);
                            }
                        });

                    },
                    error: function(ts) {
                        $(document).ajaxStop($.unblockUI);
                        alert("eSign Request Failed" + ts.responseText)
                    }
                });
            },
            error: function(ts) {
                $(document).ajaxStop($.unblockUI);
                alert("Doc Hash generation failed" + ts.responseText)
            }
        });
        e.preventDefault();
    });
});
