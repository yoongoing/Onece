
const http = require('http');
const url = require('url');
const fs = require('fs');
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



    

})

app.listen(7777,'192.168.1.72');
