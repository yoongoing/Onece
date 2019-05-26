var http = require('http');
var fs = require('fs');
var url = require('url');

http.createServer(function (req, res) {
    var _url = req.url;
    var queryData = url.parse(_url,true).query;

    var template = `
    <!DOCTYPE html> 
    <html> 
    <head> 
        <meta charset="utf-8"> 
        <title>Onece</title> 
        <link  rel="stylesheet" type="text/css" href="stylesheet"> 
    </head> 
    <body> 
        <h1>Onece</h1> 
        <p>${queryData.name}(${queryData.id})님, 반갑습니다!</p> 
    
    </body> 
    </html>
    `;
    
    res.writeHead(200);
    res.end(template);
    
}).listen(1337, '127.0.0.1');
console.log('Server running at http://127.0.0.1:1337/');