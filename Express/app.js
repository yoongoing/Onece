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
<<<<<<< HEAD
    var id = req.query.id;
    var name = req.query.name;
    res.render('get_index');//views디렉토리안에 있는 index.ejs 파일
=======
    res.render('get_index');//views디렉토리안에 있는 index.ejs 파일 
>>>>>>> 2da4af29b6cff1b1a15491e8c2a451c22514fdd3
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
    var userId =queryData.id.toString();
    var userName = queryData.name.toString();
    iconv =  new Ico('EUC-KR','UTF-8');
    
    console.log(userId);
    console.log(userName)

    
    var encodedName =  iconv.convert(userName).toString();


    console.log(encodedId);
    console.log(encodedName);

    var location  = 'http://192.168.1.27:9000/'
    var qs = '?method=a&name='+encodedName+'&id='+userId;
   

    request(location+qs,function(error,response,body){
            console.log(error);
            res.end(body);
    })






   
 
    
});


<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> hyemin2
app.listen(3001, function(){ 
=======
app.listen(3030,'localhost', function(error,response,request){ 
>>>>>>> 2da4af29b6cff1b1a15491e8c2a451c22514fdd3
     console.log('App Listening on port 3001'); 
  
});






