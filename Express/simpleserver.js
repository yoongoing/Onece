var http = require('http');
var fs = require('fs');
var url = require('url');
var i =1;
var server = http.createServer(function (req, res) {
    res.writeHead(200);
    res.end('hi');
    
})

server.on('connection',function(socket){
    console.log(i + 'connection: '+ socket.localAddress +':'+socket.localPort);
    i++;
});

// server.on('data', function(request){
//     console.log(i+ request);
//     i++;
// });
server.listen(3000, '127.0.0.1');
console.log('Server running at http://127.0.0.1:3000/');