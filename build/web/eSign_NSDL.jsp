<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>NSDL Test Page</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<script>            
            function test() {
				alert("testing..");
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

		var form = $('#URL')[0];
        var data = new FormData(form);
		var resp1 = makeMultiPartCall("http://localhost:8080/iSign/esign.do?method=generateHash",data);
		alert(resp1);


				var url = "http://localhost:8080/iASP/rest/services/esign";
				var params = "{'uid':'588928507592','dochash':'127b2b95c3571e6465bffff986ff3e1f78eede356456c124f88629a761fa9cd9','sc':'Y'}";
				//var request="{'uid':'"+adharnovalue+"','pid':'"+encodeURIComponent(pid)+"','skey':'"+encodeURIComponent(skey)+"','hmac':'"+encodeURIComponent(hmac)+"','ci':'"+ci+"','ts':'"+ts+"'}";
                var resp = makeServerCall(url,params);
				alert(resp);
					
					document.getElementById('obj').value="588928507592";
					document.getElementById('msg').value=resp;
				
//				var nsdl_url = "https://121.240.246.49/esign/2.0/signdoc/esignauth/";
	//			var nsdl_params = "{'obj':'588928507592','msg':'"+resp+"'}";
                //var ndsl_resp = makeServerCall(nsdl_url,nsdl_params);
		//		var ndsl_resp = makeMultiPartCall(nsdl_url,nsdl_params);
			//	alert(nsdl_resp);

            }

function makeServerCall(path,params) {
                //alert("makeServerCall "+subcontextpath+requestParameter);
                var xmlhttp;
                var url = path;
                //    alert(url);
                var serverResponse;

                if (window.XMLHttpRequest)
                {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp = new XMLHttpRequest();
                }
                else
                {// code for IE6, IE5
                    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange = function()
                {

				if (xmlhttp.readyState === 4)
                    {

					if (xmlhttp.status === 200)
                        {

                            serverResponse = xmlhttp.responseText;
                            			alert(serverResponse);
                            var msgLength = serverResponse.length;

                            if (msgLength === null || msgLength === 0)
                            {
                                alert("Could not connect to Server");
                                return false;
                            }
                        }
                        xmlhttp = null;
                    }
                };

				xmlhttp.open('POST', url,false);
				xmlhttp.setRequestHeader("Content-type", "application/json");
                xmlhttp.send(params);
                return serverResponse;
            }
			
			
function makeMultiPartCall(subcontextpath,requestParameter){
    alert("makeServerCall "+subcontextpath+requestParameter);    
    var xmlhttp ;
	var url = subcontextpath;
        //alert(url);
        var serverResponse;

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
           xmlhttp.onreadystatechange = function()
               {
                if (xmlhttp.readyState === 4)
				{
					if (xmlhttp.status === 200)
					{

                                        serverResponse = xmlhttp.responseText;
										alert("responseMessage "+serverResponse);
                                        var msgLength = serverResponse.length;
                                            if (msgLength == null || msgLength == 0)
                                                {
                                                alert("Could not connect to Server");
                                                return false;
                                                }
					     /*for (var i = 0; i < msgLength; i++) {
                                                var responseJson = responseMessage[i];
                                                //alert(responseJson);
        					serverResponse = eval("(" + responseJson.response + ")");
                                               }*/
                    }
                    xmlhttp=null;
				}
               };
               
    xmlhttp.open("POST", url, false);
    xmlhttp.setRequestHeader('Content-Type', 'multipart/form-data');
//    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(requestParameter);
    
    return serverResponse;
    
}
</script>
</head>
<body>
<form id="URL" name="URL" method="POST" enctype="multipart/form-data" action="https://121.240.246.49:443/esign/2.0/signdoc/esignauth/">

<div class="col-md-offset-1 col-md-10 col-sm-offset-0 col-sm-12 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">
                            <!--<div class="col-md-offset-3 col-md-6 col-sm-offset-1 col-sm-10 col-xs-offset-0 col-xs-12" style="border:1px solid #002166;background-color:#ccccff">-->
                            <label class="col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10" style="text-align:center;font-size:20px;margin-top:20px;">eSign</label>
                            <table class="table">
                                <tr class="row">
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="right">
                                        <label id="lblAdhar">Aadhaar Number *</label>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input type="text" id="tbAdhar" name="tbAdhar" placeholder="Enter Aadhaar Number" autofocus="autofocus" maxlength="12"/>
                                    </td>
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
                                        <input type="checkbox" id="chConsent" name="chConsent"/>
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
                                        <input class="btn btn-primary" type="submit" id="btneSign" style="margin-top:0px;margin-bottom:20px" value="eSign" onclick="test();"/>
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3" align="left">
                                        <input class="btn btn-primary" type="button" id="btnCanceleSign" style="margin-top:0px;margin-bottom:20px" value="Cancel" onclick="location.href = 'Home.jsp'" />
                                    </td>
                                    <td class="col-md-3 col-sm-3 col-xs-3">
                                        <input type="hidden" name="obj" id="obj" value=""/>

                                        <input type="hidden" name="msg" id="msg" value=""/>
                                    </td>
                                </tr>
                            </table>
                        </div>
<!--<input type="hidden" name="obj" id="obj" value=""/>
<input type="hidden" name="msg" id="msg" value=""/>

<input type="Submit" value="Submit" name="Submit" onclick="test();">-->

</form>
</body>
</html>