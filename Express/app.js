var http = require('http');
var request = require('request')
var url = require('url');
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
    var _url = req.url;
    var queryData = url.parse(_url,true).query;
    var userId =queryData.id;
    var userName = queryData.name;
    
    
    var location  = "http://172.17.69.82:9000/"
    var qs = "?method=a&name="+userName+"&id="+userId;


    console.log(location+qs);
    

    request(location+qs,
        function (error, response, body) {
            res.end(body);
    });
 
    
});


app.listen(3001,'localhost', function(){ 
     console.log('App Listening on port 3001'); 
});


