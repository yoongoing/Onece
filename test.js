var userId = "";
var userPublicKey = "";
var setCommand1 = "peer chaincode query -n Onece -c '{\"Args\":[\"set\",\""
var setCommand2 = "\",\"";
var setCommand3 = "\"]}' -C myc";



userId = "hello2121";
userPublicKey = 01231231;

console.log(setCommand1+userId+setCommand2+userPublicKey+setCommand3);