/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
document.getElementById('tbFileName').addEventListener('change', handleFileSelect, false);

var reader;

function abortRead() {
    reader.abort();
}

function errorHandler(evt) {
    switch (evt.target.error.code) {
        case evt.target.error.NOT_FOUND_ERR:
            alert('File Not Found!');
            break;
        case evt.target.error.NOT_READABLE_ERR:
            alert('File is not readable');
            break;
        case evt.target.error.ABORT_ERR:
            break; // noop
        default:
            alert('An error occurred reading this file.');
    }
    ;
}


function handleFileSelect(evt) {

    reader = new FileReader();
    reader.onerror = errorHandler;
    reader.onabort = function(e) {
        alert('File read cancelled');
    };
    reader.onload = function(e) {
        var contents = e.target.result;
        alert("contents " + contents);
    }

    // Read in the image file as a binary string.
//    reader.readAsBinaryString(evt.target.files[0]);
    reader.readAsText(evt.target.files[0]);
}


/*var CallBackFunction = function(content)
 {
 return content;
 };
 
 
 
 //Tested in Mozilla Firefox browser, Chrome
 function ReadFileAllBrowsers(FileElement, CallBackFunction)
 {
 //alert("FileElement "+FileElement.value);
 alert("reading file wait..");
 try
 {
 var file = FileElement.files[0];
 var contents = "";
 
 if (file) {
 var reader = new FileReader();
 
 reader.readAsText(file, "UTF-8");
 reader.onload = function(evt)
 {
 contents = CallBackFunction(evt.target.result);
 };
 reader.onerror = function (evt) {
 alert("Error reading file");
 };
 }
 }
 catch (Exception)
 {
 var fall_back =  ieReadFile(FileElement.value);
 if(fall_back !== false)
 {
 contents = CallBackFunction(fall_back);
 }
 }
 // alert(contents);
 return contents;
 }
 
 ///Reading files with Internet Explorer
 function ieReadFile(filename)
 {
 try
 {
 //alert("in ieReadFile");
 var fso  = new ActiveXObject("Scripting.FileSystemObject");
 var fh = fso.OpenTextFile(filename, 1);
 var contents = fh.ReadAll();
 //alert("contents "+contents);
 fh.Close();
 return contents;
 }
 catch (Exception)
 {
 alert(Exception);
 return false;
 }
 }
 */