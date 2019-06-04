var http = require('http');
var request = require('request');
var fs = require('fs');
var url = require('url');
var querystring = require('querystring');
var express = require('express'); 
var bodyParser = require('body-parser'); 
//192.168.10.5:9000
// var options = {
//     host: '127.0.0.1',
//     port: 3001,
//     path: '/',
//     method:'GET',
// };
function handleResponse(response,body){
    var serverdata = '';
    response.on('data', function (chunk) {//연결 서버로 부터 받아오는 데이터가 있다면 출력해주기!
        serverdata+=chunk;
    });
    response.on('end',function(){
        console.log(serverdata);
    });
    res
}

var app = express(); 

app.use(express.static('public')); 
app.use(bodyParser.urlencoded({extended : true})); 
app.set('view engine', 'ejs');//ejs 템플릿 엔진  연동 

app.get('/', function (req, res) { 
    var id = req.query.id;
    var name = req.query.name;
    res.render('get_index');//views디렉토리안에 있는 index.ejs 파일 
});

app.get('/template', function (req, res) { 
    var id = req.query.id;
    var name = req.query.name;
    var _url = req.url;
    var queryData = url.parse(_url,true).query;
    var responses = '';
    var Toserver = '?method=a&name='+name+'&id='+id;
    var location  = "http://172.17.69.82:9000/"

    console.log(name+id);
    console.log(location+Toserver);
    

    request(location+Toserver,function(error,response,body){
        
        console.log(response);
        console.log(body);

        
        if(body != "OK") res.render('template', {title: 'GET',name: name, id:id, demo:'disabled'});
        else res.render('template', {title: 'GET',name: name, id:id, demo:''});
    });

});
app.listen(3001, function(){ 
     console.log('App Listening on port 3001'); 
});


