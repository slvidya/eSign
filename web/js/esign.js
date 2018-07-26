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
    var tbAdharName = document.getElementById("tbAdharName").value;
    var slGender = document.getElementById("slGender").options[document.getElementById("slGender").selectedIndex].value;
    var tbDOB = document.getElementById("tbDOB").value;
    var tbEmail = document.getElementById("tbEmail").value;
    var tbMobileNo = document.getElementById("tbMobileNo").value;
    var tbAadhaar = document.getElementById("tbAadhaar").value;
    var tbLoginName = document.getElementById("tbLoginName").value;
    var tbPwd = document.getElementById("tbPwd").value;
    var tbCnfPwd = document.getElementById("tbCnfPwd").value;

    if (tbFName == null || tbFName == "")
    {
        alert("Please Enter First Name");
        document.getElementById("tbFName").focus();
        return false;
    }

    if (tbLName == null || tbLName == "")
    {
        alert("Please Enter Last Name");
        document.getElementById("tbLName").focus();
        return false;
    }

    if (tbAdharName == null || tbAdharName == "")
    {
        alert("Please Enter Name as per Aadhaar Card");
        document.getElementById("tbAdharName").focus();
        return false;
    }

    if (slGender == 0)
    {
        alert("Please Select Gender");
        document.getElementById("slGender").focus();
        return false;
    }
    if (tbDOB == null || tbDOB == "")
    {
        alert("Please Enter DOB");
        document.getElementById("tbDOB").focus();
        return false;
    }
    if (tbEmail == null || tbEmail == "")
    {
        alert("Please Enter Email Id");
        document.getElementById("tbEmail").focus();
        return false;
    }
    if (tbMobileNo == null || tbMobileNo == "")
    {
        alert("Please Enter Mobile Number");
        document.getElementById("tbMobileNo").focus();
        return false;
    }
    if (tbAadhaar == null || tbAadhaar == "")
    {
        alert("Please Enter Aadhaar Number");
        document.getElementById("tbAadhaar").focus();
        return false;
    }

    if (tbLoginName == null || tbLoginName == "")
    {
        alert("Please Set Login Name");
        document.getElementById("tbLoginName").focus();
        return false;
    }
    if (tbPwd == null || tbPwd == "")
    {
        alert("Please Enter Password");
        document.getElementById("tbPwd").focus();
        return false;
    }

    if (tbCnfPwd == null || tbCnfPwd == "")
    {
        alert("Please Enter Confirm Password");
        document.getElementById("tbCnfPwd").focus();
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

/*function eSign() {
 //    alert("in esign");
 var tbAdhar = document.getElementById("tbAdhar").value;
 var tbFileName = document.getElementById("tbFileName").value;
 var chConsent = document.getElementsByName("chConsent")[0].checked;
 
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
 return true;
 }*/

$(function() {

    $('#btneSign').on("click", function(e) {

        var tbFileName = document.getElementById("tbFileName").value;
        var chConsent = document.getElementsByName("chConsent")[0].checked;
        var aspurl = document.getElementById("aspurl").value;
        alert(aspurl);
        var espurl = document.getElementById("espurl").value;
        var sc;

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

        var myForm = $('#eSignForm')[0];
        var data = new FormData(myForm);
        $.blockUI({message: '<img src=\"./Images/Waiting_1.gif\" align=\"middle\" /> Please wait, we are generating the document hash...'});
        $.ajax({
            type: "POST",
            url: "esign.do?method=generateUnsignedEsignRequestXml",
            enctype: 'multipart/form-data',
            //             contentType: "application/xml;",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function(unsignedeSignRequestXML) {
                $(document).ajaxStop($.unblockUI);
              
                var resp=JSON.parse(unsignedeSignRequestXML);
                alert("unsignedeSignRequestXML " + resp[0].response);

                //////////////////get signed eSign Request XML From ASP Server///////////////////
                $.blockUI({message: '<img src=\"./Images/Waiting_1.gif\" align=\"middle\" /> Please wait, we are generating your esign request...'});

                $.ajax({
                    type: "POST",
                    url: aspurl + "/envelopeXML",
                    contentType: "text/plain; charset=utf-8",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: resp[0].response,
                    success: function(signedeSignRequestXML) {
                        alert(signedeSignRequestXML);
                        $(document).ajaxStop($.unblockUI);

                        $.blockUI({message: '<img src=\"./Images/Waiting_1.gif\" align=\"middle\" /> Please wait, we are processing your esign request...'});
                        var form = $('#URL')[0];
                        document.getElementById('msg').value = signedeSignRequestXML;
                        form.action = espurl;
                        form.submit();
                        $(document).ajaxStop($.unblockUI);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        $(document).ajaxStop($.unblockUI);
                        alert("jqXHR.status" + jqXHR.status);
                        alert("errorThrown " + errorThrown);
                        alert("jqXHR.responseText" + jqXHR.responseText);
                        //alert("Error while generating eSign Request XML" + ts.responseText)
                    }
                });
            },
            error: function(ts) {
                $(document).ajaxStop($.unblockUI);
                alert("Error while generating doc hash " + ts.responseText)
            }
        });
        e.preventDefault();

    });
});
