/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

  function readSingleFile(evt) {
    //Retrieve the first (and only!) File from the FileList object
    var f = evt.target.files[0]; 

    if (f) {
      var r = new FileReader();
      r.onload = function(e) { 
	      var contents = e.target.result;
              alert(contents)
              var propnames = f.getOwnPropertyNames();
                alert( "Got the file.n" 
              +"name: " + f.name + "n"
              +"type: " + f.type + "n"
              +"size: " + f.size + " bytesn"
              + "starts with: " + contents.substr(1, contents.indexOf("n"))
        );  
      }
      r.readAsText(f);
    } else { 
      alert("Failed to load file");
    }
  }

//  document.getElementById('fileinput').addEventListener('change', readSingleFile, false);
