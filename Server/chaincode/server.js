
const http = require('http');
const url = require('url');
const fs = require('fs');
var exec = require("child_process").exec;
var userId = "";
var userPublicKey = "";
var setCommand1 = "peer chaincode query -n Onece -c '{\"Args\":[\"set\",\""
var setCommand2 = "\",\"";
var setCommand3 = "\"]}' -C myc";
var getCommand = "peer chaincode query -n Onece -c '{\"Args\":[\"get\",\"hyemin\"]}' -C myc";
var command = ""

var string;
var list;
var id = 'yeonwook'

//  qurey string 형식
//  url? method=~&id=~publickey=~&
// 	method가 r이면 최초등록
//  method가 a면 private key 검증

var app = http.createServer((request, response) => {
	var _url = request.url;
	var queryData = url.parse(_url,true).query;

	if (queryData.method==="r"){
		userId = queryData.id;
		userPublicKey = queryData.publickey

		command = setCommand1 + userId + setCommand2 + userPublicKey + setCommand3;
		console.log("there is some connect");
		exec(command, function (err, stdout, stderr) {


			response.end(stdout)
	
			//Simple response to user whenever localhost:3003 is accessed
		
			
		});
	
	}

	
	
	

    

})

app.listen(7811,'172.19.0.5');
