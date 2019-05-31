var http = require('http');
var fs = require('fs');
var url = require('url');
var querystring = require('querystring');
var parser = require('http-request-parser');

var i =1;
var server = http.createServer(function (req, res) {
    
    res.writeHead(200);
    res.end(1);
    
})
server.on('request', function(request,response){
    // var dataObj = JSON.parse(request);
    // console.log(dataObj);
    // console.log(request);
});
server.on('connection',function(request){
    // console.log(socket);
    console.log(i + 'connection: '+request);
    i++;
});

server.on('data',function(chunk){
    console.log(chunk);
});

// server.on('data', function(request){
//     console.log(i+ request);
//     i++;
// });
server.listen(3000, '127.0.0.1');
console.log('Server running at http://127.0.0.1:3000/');