var r = require('request')
var http = require('http');
var url = require('url');
var express = require('express'); 
var bodyParser = require('body-parser'); 

var app = express(); 

app.use(express.static('public')); 
app.use(bodyParser.urlencoded({extended : true})); 
app.set('view engine', 'ejs');//ejs 템플릿 엔진  연동 

app.get('/', function (req, res) { 
    res.render('get_index');//views디렉토리안에 있는 index.ejs 파일 
});







app.get('/template', function (req, res) { 
    var _url = req.url;
    var queryData = url.parse(_url,true).query;
    var userId =queryData.id;
    var userName = queryData.name;
    
    console.log(userId);
    console.log(userName)



    
      var location  = 'http://192.168.1.27:9000/'
      var qs = '?method=a&name='+userName+'&id='+userId;
      const string = location+qs;
  
      http.get(string, (resp) => {
        let data = '';
      
        // A chunk of data has been recieved.
        resp.on('data', (chunk) => {
          data += chunk;
        });
        
        
        console.log(data);


            res.end("sdkhflaksdhflaksdjhflaksdjfhalksdjfhaslkdfjhz1 lkjh");

   
 
    
    });
});


app.listen(3030,'localhost', function(error,response,request){ 
     console.log('App Listening on port 3001'); 

     
  
});
