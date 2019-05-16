
const http = require('http');
const url = require('url');
const fs = require('fs');
var exec = require("child_process").exec;
var command = "peer chaincode query -n Onece -c '{\"Args\":[\"get\",\"hyemin\"]}' -C myc"

var string;
var list;
var id = 'yeonwook'



var app = http.createServer((request, response) => {
	var _url = request.url;
	var queryData = url.parse(_url,true).query;

	console.log("there is some connect");
	exec(command, function (err, stdout, stderr) {


		response.end(stdout)
 
		//Simple response to user whenever localhost:3003 is accessed
	
		
	  });
	
	
	
	

    

})

app.listen(7800,'172.19.0.5');
