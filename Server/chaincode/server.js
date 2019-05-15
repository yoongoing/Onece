
const http = require('http');
const url = require('url');
const fs = require('fs');
var exec = require("child_process").exec;
var command = "peer chaincode query -n Onece -c '{\"Args\":[\"query\",\"hymein\"]}' -C myc"

var string;
var list;
var id = 'yeonwook'

var app = http.createServer((request, response) => {
	var _url = request.url;
	var queryData = url.parse(_url,true).query;
	
	
	console.log(queryData.id);
	if(queryData.id == id) {
		response.end("true");
	} else {
		response.end("false");	
	}

	exec(command, function (err, stdout, stderr) {



		//Print stdout/stderr to console
	
		console.log(stdout);
	
		console.log(stderr);
	
	
	
		//Simple response to user whenever localhost:3003 is accessed
	
		res.render('cmd', { title: 'Express', data: stdout });
	
	  });
	
	
	

    

})

app.listen(7777,'192.168.1.72');
