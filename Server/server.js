const http = require('http');
var NodeRSA = require('node-rsa');
var btoa = require('btoa');
var str2ab = require('string-to-arraybuffer')
const arrayBufferToHex = require('array-buffer-to-hex')
const constants = require('constants');






const crypto = require('crypto');
const url = require('url');
const fs = require('fs');
const exec1  = require("child_process").execSync;
const exec2 = require("child_process").exec;


var setCommand1 = "peer chaincode invoke -n Onece -c '{\"Args\":[\"set\",\""
var setCommand2 = "\",\"";
var setCommand3 = "\"]}' -C myc";
var getCommand1 = "peer chaincode query -n Onece -c '{\"Args\":[\"get\",\"";
var getCommand2 = "\"]}' -C myc";
var setCommand = "";
var getCommand = "";
var responseForResister = "";


var FCM = require('fcm-node');
var serverKey = 'AAAA1i0JV7I:APA91bFCZeqWJbdpko7tnQIf_bPkMthWz6WJlDGHfK3NnjAhPO0fL33fzQxNmMUlw4Mu51hV5-0jtelED8Ozvib8VQILy0W9dECVSC0KY463AUKr9uPY-m4OkhySPkalvbtrIg8aIQAZ'


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
	var isUserIdAlreayIn = false;
	var valideUserIdAndName = false;
	var client_token;
	var userPublicKey;


	function myFunction() {
		exec2(getCommand, function (err, stdout, stderr) {
			var result = stdout	
			if( result.toString().trim() === userPublicKey.toString() ){
				response.end("OK");
				console.log("ok...!");
				console.log("good it is resisterd");
			}else{
				response.end("Bad request");
				console.log("bad it isn't resisterd");
			}
		});
	}
	
	const writeUserData = function(userId, userName, userToken,userPw) {
		return new Promise(function(resolve,reject){
			resolve(firebase.database().ref('jeff/' + userId).set({
				token : userToken,
				name : userName,
				password:userPw
			  }));
		})
	}



	const isUserIdIn = function(userId) {
		return new Promise(function(resolve,reject){
			resolve(
				firebase.database().ref('jeff/' + userId).once('value').then(function(data) {
					if(data.val() == null){
						isUserIdAlreayIn = false;
					}else{
						isUserIdAlreayIn = true;
					}
				})
			)
		})
	}

	const readUserNameAndId = function(userId,userName) {
		return new Promise(function(resolve,reject){
			resolve(
				firebase.database().ref('jeff/' + userId).once('value').then(function(data) {
					if( data.val()==null){
						valideUserIdAndName = false;
					}else if(data.val().name == userName){
						valideUserIdAndName = true;
					}else{
						valideUserIdAndName = false;
					}
				})
			)
		})
	}

	const readUserToken = function(userId) {
		return new Promise(function(resolve,reject){
			resolve(
				firebase.database().ref('jeff/' + userId).once('value').then(function(data) {
					client_token = data.val().token 
				})
			)
		})
	}


	const writeResponse = function(userId) {
		return new Promise(function(resolve,reject){
			resolve(firebase.database().ref('jeff/' + userId+"/compelete").set(true));
		})
	}



	


	



	var _url = request.url;
	var queryData = url.parse(_url,true).query;
   

	if (queryData.method==="r"){
		
		var userId = queryData.id;
		var userPw = queryData.password;
		userPublicKey =  queryData.publickey;
		var userToken = queryData.token;
		var userName = queryData.name;

		
	
		async function readUserId(){
			await isUserIdIn(userId)

			if(!isUserIdAlreayIn){
				
				writeUserData(userId, userName,userToken,userPw);

				setCommand = setCommand1 + userId + setCommand2 + userPublicKey + setCommand3;
				getCommand = getCommand1 + userId + getCommand2 ;
				
			

				exec1(setCommand, function (err, stdout, stderr) {});
				setTimeout( myFunction, 2000);	
			}
		}
		
		readUserId();
	
	}else if(queryData.method === "a"){
		
		var userId = queryData.id;
		var userName = queryData.name;


		var nonce = crypto.randomBytes(16).toString('hex');
		response.writeContinue();
		function execPromise(command) {
			return new Promise(function(resolve, reject) {
				exec2(command, (error, stdout, stderr) => {
					if (error) {
						reject(error);
						return;
					}
					resolve(userPublicKey=stdout.toString());
				});
			});
		}
		
		function sendmessage(pubkey){
			

			var buf = new Buffer(pubkey,'hex');
			var base64String = buf.toString('base64');
		
			function hexToBase64(str) {
				return btoa(String.fromCharCode.apply(null,
				  str.replace(/\r|\n/g, "").replace(/([\da-fA-F]{2}) ?/g, "0x$1 ").replace(/ +$/, "").split(" "))
				);
			}
			var PUB = '-----BEGIN PUBLIC KEY-----\n'+base64String+'\n-----END PUBLIC KEY-----';
			var key = new NodeRSA();

			
			key.importKey(PUB,'pkcs8-public-pem');


		
			
			var base64Nonce = hexToBase64(nonce);


			/// 지금 형태의 암호화 건들지 말것!
		
		

			const encryptedPassword = crypto.publicEncrypt({
				key: PUB,
				padding : constants.RSA_PKCS1_PADDING
			} ,  Buffer.from(base64Nonce,'base64'))

			encnonce = arrayBufferToHex(encryptedPassword);
			encnonce =hexToBase64(encnonce)

			console.log("--------------------------------------------------")
			console.log("Nonce is:  "+nonce);
			console.log("--------------------------------------------------")

			var push_data = {
				// 수신대상
				to: client_token,
				// App이 실행중이지 않을 때 상태바 알림으로 등록할 내용
				
				// 메시지 중요도
				priority: "high",
				
				// App 패키지 이름
				restricted_package_name: "com.example.capstone",
				// App에게 전달할 데이터
				data: {
					title: "Onece",
					body: "인증을 진행해 주세요!",
					sound: "default",
					click_action: "MainActivity",
					num1: encnonce,
					id:userId
				}
			};
			
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

		function promiseSendMessage(key) {
			return new Promise(function(resolve, reject) {
					resolve(
						sendmessage(key)
					)
				})
		}


	
		function readNonce(userId,nonce) {
			return new Promise(function promise(resolve, reject) {
				resolve(
					firebase.database().ref('jeff/' + userId+"/nonce").once('value').then(function firebase(data) {
						userNonce = data.val();
						return userNonce;
					}).then(value =>value)
				)
			})
		}
	

		async function checkIdAndName(){
			await readUserNameAndId(userId,userName);
			
			if(valideUserIdAndName){
				await readUserToken(userId);
				getCommand = getCommand1 + userId + getCommand2 ;
				var result = await execPromise(getCommand);
				await promiseSendMessage(userPublicKey);
				
				for(i = 1 ; i<=4 ; i++){
					setTimeout( 
						async function(){ 
							var usernonce = await readNonce(userId,nonce);
							await console.log(usernonce);
							if(nonce == usernonce){
								await writeResponse(userId);
								response.end("OK");

							}
						},i*2000);
				}




			}else{
				response.end("Bad request!!!!!");
			}
		}

		


		checkIdAndName();
		
		
	
		
	}
    

})

app.listen(9000,'172.19.0.5');



