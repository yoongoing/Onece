



const http = require('http');
const url = require('url');
const fs = require('fs');
var string;
var list;


var app = http.createServer((request, response) => {

    console.log(1231231321);
 
    response.end();

})

app.listen(7777,'192.168.1.72');
