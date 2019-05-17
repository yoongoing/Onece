
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

//  qurey string 형식
//  url? method=~&id=~publickey=~&
// 	method가 r이면 최초등록
//  method가 a면 private key 검증

var app = http.createServer((request, response) => {
	
	var _url = request.url;
    var queryData = url.parse(_url,true).query;
    var reqes = "http://172.19.0.4:9308/?method=r&id="
    var uid = "";
    var pkey = "&publickey=";
    uid  = queryData.id;
    pkey += queryData.publickey
	console.log(reqes+uid+pkey)

    http.get(reqes+uid+pkey, (resp) => {
    let data = '';

    // A chunk of data has been recieved.
    resp.on('data', (chunk) => {
        data += chunk;
    });

    // The whole response has been received. Print out the result.
    resp.on('end', () => {
        console.log(JSON.parse(data).explanation);
    });

    }).on("error", (err) => {
    console.log("Error: " + err.message);
    });
        

})

app.listen(9310,'192.168.1.54 ');
