
const http = require('http');
const url = require('url');
const fs = require('fs');
var exec = require("child_process").exec;
var command = "peer chaincode query -n Onece -c '{\"Args\":[\"query\",\"hymein\"]}' -C myc"

var string;
var list;
var id = 'yeonwook'

exec(command, function (err, stdout, stderr) {



	//Print stdout/stderr to console

	console.log(stdout);

	console.log(stderr);



	//Simple response to user whenever localhost:3003 is accessed

	response.end(stdout);	
  });

var app = http.createServer((request, response) => {
	var _url = request.url;
	var queryData = url.parse(_url,true).query;
	
	
	console.log(queryData.id);
	
	
	
	
	

    

})

app.listen(7788,'0.0.0.0');
