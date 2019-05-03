const http = require('http'); // 서버를 만드는 모듈 불러옴
console.log('Server starting...'); 


function onAir(request, response) {  
    response.writeHead(200, {'Content-Type' : 'text/plain'});
    response.write('Connected...');
    response.end();
}
http.createServer(onAir).listen(8888);

console.log('Server succesful');