
const http = require('http');
const url = require('url');
const fs = require('fs');
const exec1  = require("child_process").execSync;
const exec2 = require("child_process").exec;


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


var firebase = require("firebase");
var firebaseConfig = {
    apiKey: "AIzaSyCSHdmNBd0BDhJ9RRGe6JmT0He1nBCO2T8",
    authDomain: "pushserver-b0722.firebaseapp.com",
    databaseURL: "https://pushserver-b0722.firebaseio.com",
    projectId: "pushserver-b0722",
    storageBucket: "pushserver-b0722.appspot.com",
    messagingSenderId: "919878588338",
    appId: "1:919878588338:web:a2948e81e2caf85a"
  };

firebase.initializeApp(firebaseConfig);
var db = firebase.database();
var ref = db.ref("server/saving-data/fireblog");
  // Initialize Firebase


// Get a database ref
//  qurey string 형식
//  url? method=~&id=~publickey=~&
// 	method가 r이면 최초등록
//  method가 a면 private key 검증

var app = http.createServer((request, response) => {
	
	
	function writeUserData(userId, publickey) {
		firebase.database().ref('jeff/' + userId).set({
		  publickey : publickey
		});
	}

	var _url = request.url;
	var queryData = url.parse(_url,true).query;


	if (queryData.method==="r"){
		
		userId = queryData.id;
		userPublicKey = queryData.publickey
		writeUserData(userId, userPublicKey);

		setCommand = setCommand1 + userId + setCommand2 + userPublicKey + setCommand3;
		getCommand = getCommand1 + userId + getCommand2 ;
		
		function myFunction() {
			exec2(getCommand, function (err, stdout, stderr) {
				var result = stdout	
				if( result.toString().trim() === userPublicKey.toString() ){
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

		exec1(setCommand, function (err, stdout, stderr) {});
		setTimeout( myFunction, 2000);
		
		
		

		

	
		

	}
    

})

app.listen(9000,'172.19.0.5');
