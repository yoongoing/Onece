var http = require('http');
var fs = require('fs');
var url = require('url');
var querystring = require('querystring');
var express = require('express'); 
var bodyParser = require('body-parser'); 

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
    console.log(name+id);
    res.render('template', {title: 'GET',name: name, id:id});//views디렉토리안에 있는 index.ejs 파일 
});

app.listen(3001, function(){ 
     console.log('App Listening on port 3001'); 
});


