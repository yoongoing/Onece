var http = require('http');
var proxy = require('http-proxy');
var url = require('url');

var proxyServer= proxy.createProxyServer({target:'http://127.0.0.1:3001'});
proxyServer.listen(3002);

var server = http.createServer(function (req, res) {
    console.log(req.url);
    proxyServer.web(req, res, { target: req.url });
    proxyServer.on('error', function(e) {
    console.log("Error in proxy call");
    });
});

server.listen(9000);