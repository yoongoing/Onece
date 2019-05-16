
const http = require('http');
const url = require('url');
const fs = require('fs');
var exec1 = require("child_process").exec;
var exec2 = require("child_process").exec;

var userId = "";
var userPublicKey = "";
var setCommand1 = "peer chaincode invoke -n Onece -c '{\"Args\":[\"set\",\""
var setCommand2 = "\",\"";
var setCommand3 = "\"]}' -C myc";
var getCommand1 = "peer chaincode query -n Onece -c '{\"Args\":[\"get\",\"";
var getCommand2 = "\"]}' -C myc";

var setCommand = "";
var getCommand = "";


var responseForResister = "";
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
		
		setCommand = setCommand1 + userId + setCommand2 + userPublicKey + setCommand3;
		
		exec1(setCommand, function (err, stdout, stderr) {});
		getCommand = getCommand1+userId+getCommand2;
		
		var result = "";

		console.log("still checkoing!");
		console.log(getCommand);
		exec2(getCommand, function (err, stdout, stderr) {
			
			console.log(stdout);
			result = stdout;
			console.log(result);
			console.log(typeof(result));
			console.log(userPublickey);


			if(result.trim() === userPublicKey.toString() ){
				responseForResister="user publickey is resisterd";
				response.end(responseForResister);
				console.log("good it is resisterd");
			}else{
				responseForResister = "user publickey isn't resisterd";
				response.end(responseForResister);
				console.log("bad it isn't resisterd");
			}
		});

		





	}

	
	
	

    

})

app.listen(7900,'172.19.0.5');
