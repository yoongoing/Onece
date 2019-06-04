var request = require('request')
var url = require('url');
var express = require('express'); 
var bodyParser = require('body-parser'); 
const Ico = require('iconv').Iconv;

var app = express(); 

app.use(express.static('public')); 
app.use(bodyParser.urlencoded({extended : true})); 
app.set('view engine', 'ejs');//ejs 템플릿 엔진  연동 

app.get('/', function (req, res) { 
    res.render('get_index');//views디렉토리안에 있는 index.ejs 파일 
});



function serverAction() {
    return new Promise(function(resolve,reject){
        resolve(
            async function (error, response, body) {
               
            }
            
        ).then(value =>value)
    })
}




app.get('/template', function (req, res) { 
   
    var _url = req.url;
    var queryData = url.parse(_url,true).query;


    var userId =queryData.id.toString('UTF-8');
    var userName = queryData.name.toString('UTF-8');
    
    console.log(userId);

    


    var location  = 'http://192.168.1.27:9000/'
    var qs = '?method=a&name='+userName+'&id='+userId;
   

    request(location+qs,function(error,response,body){
            console.log(error);
            res.end(body);
    })






   
 
    
});


app.listen(3030,'localhost', function(error,response,request){ 
     console.log('App Listening on port 3001'); 
  
});


