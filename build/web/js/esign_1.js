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
    return true;
//    var myForm = document.forms[0];
//    myForm.action = "login.do?method=login";
//    myForm.submit();
//                     
//    var parameter = "{'loginId':'" + loginId + "','loginPwd':'" + loginPwd + "'}";
//    var subcontextpath = "login.do?method=login";
//    alert("request " + parameter);
//    var resp = makeServerCall(subcontextpath, parameter);
//    alert("resp " + resp);
}

function eSign() {
//    alert("in esign");
    var tbAdhar = document.getElementById("tbAdhar").value;
    var tbFileName = document.getElementById("tbFileName").value;
    var chConsent = document.getElementsByName("chConsent")[0].checked;

    if (tbAdhar == null || tbAdhar == "")
    {
        alert("Please Enter Aadhaar Number");
        return false;
    }
    alert(check(tbAdhar));
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
    var form = $('#eSignForm')[0];
    var data = new FormData(form);
    return true;
}

$(function() {

    $('#btneSign').on("click", function(e) {
        var tbAdhar = document.getElementById("tbAdhar").value;
        var tbFileName = document.getElementById("tbFileName").value;
        var chConsent = document.getElementsByName("chConsent")[0].checked;
        var sc;
        if (chConsent)
            sc = "Y";
        else
            sc = "N";
        if (tbAdhar == null || tbAdhar == "")
        {
            alert("Please Enter Aadhaar Number");
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

        var form = $('#eSignForm')[0];
        var data = new FormData(form);
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
//                alert("aspResp" + aspResp);
                var responseJson = JSON.parse(aspResp);
                var docHash = responseJson[0].response;
                alert(docHash);
                if (docHash == "Exception occured")
                {
                    alert("Error while generating doc hash");
                    return false;
                }
                var ASPInput = {
                    'uid': tbAdhar,
                    'dochash': docHash,
                    'sc': sc
                }
                //////////////////get eSign Request XML From ASP Server///////////////////
                $.ajax({
                    type: "POST",
                    url: "https://182.72.231.117:9945/iASP/rest/services/esign",
                    contentType: "application/json; charset=utf-8",
//                                headers: {
//                                    'Access-Control-Allow-Origin': '*'
//                                },
//                                headers: {'Access-Control-Allow-Origin': 'https://172.72.231.117:9945'},
                    xhrFields: {
                        withCredentials: true
                    },
                    data: JSON.stringify(ASPInput),
                    success: function(aspRespXML) {
                        alert("Success from esign_1" + aspRespXML);
                        $.ajax({
                            type: "POST",
                            url: "https://es-staging.cdac.in/esignlevelone/2.0/signdoc",
                            contentType: "application/xml; charset=utf-8",
                            xhrFields: {
                                withCredentials: true
                            },
//                                headers: {'Access-Control-Allow-Origin': 'http://10.10.10.72'},
                            crossDomain: true,
                            data: aspRespXML,
                            success: function(esignRes) {
//                                alert("success : espUrl response " + esignRes);
                                var espResp = esignRes.responseXml;
                                var aspUrl = esignRes.responseUrl;
                                var status = esignRes.status;

                                alert("espResp =" + esignRes.responseXml);
                                alert("aspUrl = " + esignRes.responseUrl);
                                alert("status = " + esignRes.status);

                                if (status == 1) {
                                    xhrFields: {
                                        withCredentials: true
                                    }
                                    if (aspUrl !== "NA")
                                        window.location.replace(aspUrl);
                                    else
                                        alert("Invalid Auth URL");
                                } else if (status == 0) {

                                    $.ajax({
                                        type: "POST",
                                        url: "esign.do?method=error",
                                        contentType: "application/xml; charset=utf-8",
                                        xhrFields: {
                                            withCredentials: true
                                        },
                                        //headers: {'Access-Control-Allow-Origin': 'http://10.10.10.72'},
                                        crossDomain: true,
                                        data: esignRes,
                                        success: function(esignRes) {
                                        }, error: function(ts) {

                                        }
                                    });
                                }

                            },
                            error: function(ts) {
                                alert("Error From CDAC while signing doc hash" + ts.responseText)
                            }
                        });

                    },
                    error: function(ts) {
                        alert("Error while generating eSign Request XML" + ts.responseText)
                    }
                });
            },
            error: function(ts) {
                alert("Error while generating doc hash" + ts.responseText)
            }
        });
        e.preventDefault();
    });
});

$(function() {

    $('#btnDownload').on("click", function(e) {
        $.ajax({
            type: "GET",
            url: "https://182.72.231.117:9945/iASP/rest/services/download",
            contentType: "text/plain",
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            success: function(aspResp) {
                alert("aspResp" + aspResp);
                $.ajax({
                    type: "POST",
                    url: "esign.do?method=signDoc",
                    data: aspResp,
//                    processData: false,
                    contentType: "text/plain",
                    cache: false,
                    timeout: 600000,
                    success: function(resp) {
                        alert("resp" + resp);
                        xhrFields: {
                            withCredentials: true
                        }
                    },
                    error: function(ts) {
                        alert("Error " + ts.responseText);
                    }
                });
            },
            error: function(ts) {
                alert("Error " + ts.responseText);
            }
        });
        e.preventDefault();
    });
});
