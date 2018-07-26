<%-- 
    Document   : test
    Created on : 2 Apr, 2018, 7:17:39 PM
    Author     : Vidya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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

            var espResp = {
        	'info': null,
                'responseUrl': 'NA',
                'responseXml': '<EsignResp errCode=\"112\" errMsg=\"Aadhaar number does not have both email and mobile\" resCode=\"db058693-e9bd-4c56-a88b-1b6aab3584c6\" status=\"0\" ts=\"2018-04-19T19:11:03.620\" txn=\"fd3f65f0-4d84-4943-aa5d-6daa044aba76\"><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></CanonicalizationMethod><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></SignatureMethod><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></Transform></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></DigestMethod><DigestValue>PdT0C5e01gr9MUB2ggS5Ck8Zr3A=</DigestValue></Reference></SignedInfo><SignatureValue>1m7+LHfuIiZ3Zu55lygTaaAkwgkBj5pN73bHx6tpbfRsDXvEI+oEGC5Sgb2YGakq6B/DqbfRM7WRzkB/zvTvqrIR73EOv/Qz1J2A2Php+qpQvttU1iGwbfRfK0fTnYQhB+3cJp29lRC+0lWN3fQM5/iKyzeGz96YCWE/ebUZ7Zj3J6yTIMRQrF+I2GInHwK8sRkwdeSJc4qrhBKrfdRY0L2+eu+N9xMNgCZas68C77QQjYynPHzY+No++2il6sPQDI7cw3zFhywxQpRAKobvjOgM85mznKz+VlvW4bf1RR0uU5PDnt+v14cYrlsfr6/8NLjjI8o03B7jORBNHuw/Ew==</SignatureValue></Signature></EsignResp>',
                'status': '0'
            }

            function test() {
                $.ajax({
                    type: "POST",
                    url: "esign.do?method=signDoc",
                    contentType: "application/xml;",
                    data: JSON.stringify(espResp),
                    success: function(insertionResp) {
                        alert("insertionResp" + insertionResp);
                    },
                    error: function(ts) {
                        alert("Error while inserting error details to DB" + ts.responseText)
                    }
                });
            }
        </script>
    </head>
    <body onload='test();'>
        <h1>Hello World!</h1>
    </body>
</html>
