var FCM = require('fcm-node');

var serverKey ='AAAA1i0JV7I:APA91bFCZeqWJbdpko7tnQIf_bPkMthWz6WJlDGHfK3NnjAhPO0fL33fzQxNmMUlw4Mu51hV5-0jtelED8Ozvib8VQILy0W9dECVSC0KY463AUKr9uPY-m4OkhySPkalvbtrIg8aIQAZ'; 
var client_token = "919878588338";

var push_data = {
	to : client_token,
	notification: {
		title: "Hello Node",
		body: "node로 발송하는 Push 메시지 입니다.",
		sound: "default",
		click_action: "FCM_PLUGIN_ACTIVITY",
		icon: "fcm_push_icon"
	},
	priority: "high",
    // App 패키지 이름
    restricted_package_name: "study.cordova.fcmclient",
    // App에게 전달할 데이터
    data: {
        message: "test"
    }
};


var fcm = new FCM(serverKey);

fcm.send(push_data, function(err, response){
	if(err){
		console.error('메시지 발송 실패.');
		console.error(err);
		return;
	}
	console.log('메시지 발송 완료.');
	console.log(response);
});