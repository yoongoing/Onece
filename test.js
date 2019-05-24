var firebase = require("firebase");
var firebaseConfig = {
    apiKey: "AIzaSyCSHdmNBd0BDhJ9RRGe6JmT0He1nBCO2T8",
    authDomain: "pushserver-b0722.firebaseapp.com",
    databaseURL: "https://pushserver-b0722.firebaseio.com",
    projectId: "pushserver-b0722",
    storageBucket: "pushserver-b0722.appspot.com",
    messagingSenderId: "919878588338",
    appId: "1:919878588338:web:a2948e81e2caf85a"
  };
  // Initialize Firebase
firebase.initializeApp(firebaseConfig);

// Get a database reference to our blog
var db = firebase.database();
var ref = db.ref("server/saving-data/fireblog");
firebase.database().ref('jeff').on("child_added", function(snapshot, prevChildKey) {
    var newPost = snapshot.key;
    console.log(newPost);
  });

function SetSensorInterval(value) { 

    firebase.database().ref().child('sensor').set({"interval":value});   

    console.log("SetSensorInterval", value);

}