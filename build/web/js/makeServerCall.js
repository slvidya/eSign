function makeServerCall(path, params) {
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
//                alert(serverResponse);
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

    xmlhttp.open('POST', url, false);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    xmlhttp.send(params);
    return serverResponse;
}
