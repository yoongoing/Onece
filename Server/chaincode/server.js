
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

var FCM = require('fcm-node');
var serverKey = 'AIzaSyCSHdmNBd0BDhJ9RRGe6JmT0He1nBCO2T8';
var client_token = ""

var push_data = {
    // 수신대상
    to: client_token,
    // App이 실행중이지 않을 때 상태바 알림으로 등록할 내용
    notification: {
        title: "Hello Node",
        body: "Node로 발송하는 Push 메시지 입니다.",
        sound: "default",
        click_action: "FCM_PLUGIN_ACTIVITY",
        icon: "fcm_push_icon"
    },
    // 메시지 중요도
	priority: "high",
	
    // App 패키지 이름
    restricted_package_name: "com.example.capstone",
    // App에게 전달할 데이터
    data: {
        num1: 2000,
        num2: 3000
    }
};


var fcm = new FCM(serverKey);




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


// 파이어 베이스 데이터셋
// 유저ID
//   - firebase app token = push 알람을 위한 어플리케이션 fcm 토큰 ->
//   	로그인이나 최초가입시에 한번 정해지면 잘 바뀌지 않음
//   - nonceDecription 유저의 privateKey 인증을 위한 서버-유저간의
// 	  데이터 검증을 위한 FIELD 
// 	  유저는 전송받은 NONCE 값 (자신의 PUBLIC KEY로 ENCRIPTION된)
// 	  을  PRIVATE KEY로 DECRIPTION후에 FIREBASE DATABASE 에다가 올려놓으면
// 	  서버는 가서 데이터를 가져온 후에 
// 	  자신이 생성한 nonce 값과 비교후에 맞는 사용자인지 아닌지 구분해줌
//   



var app = http.createServer((request, response) => {
	
	function writeUserData(userId, publickey, userToken) {
		firebase.database().ref('jeff/' + userId).set({
		  publickey : publickey,
		  token : userToken
		});
	}

	var _url = request.url;
	var queryData = url.parse(_url,true).query;


	if (queryData.method==="r"){
		
		userId = queryData.id;
		userPublicKey = queryData.publickey
		var userToken = queryData.token;
		writeUserData(userId, userPublicKey,userToken);

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
		console.log(queryData.token);
	
	}else if(queryData.method === "a"){
		
		
		fcm.send(push_data, function(err, response) {
			if (err) {
				console.error('Push메시지 발송에 실패했습니다.');
				console.error(err);
				return;
			}
		
			console.log('Push메시지가 발송되었습니다.');
			console.log(response);
		});
	}
    

})

app.listen(9000,'172.19.0.5');
