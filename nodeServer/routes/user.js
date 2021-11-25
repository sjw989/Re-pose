var express = require('express');
var mysql = require('mysql');
const bodyParser = require('body-parser')
var user = express.Router();

user.use(bodyParser.urlencoded({extended: true}))
user.use(bodyParser.json())

var connection = mysql.createConnection({
    user: 'repose_user1',
    password: 'Qwer1234!',
    database: 'repose',
    host: 'localhost'
});

connection.connect();

user.post('/', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    console.log(req.body.userEmail);
    connection.query('insert into repose_user(userId, userPw, userEmail, pose, medal, weekday, hour, confirmNum, premium, joinDate) values (?,?,?,?,?,?,?,?,?,?);',
    [req.body.userId, req.body.userPw, req.body.userEmail, req.body.pose, req.body.medal, req.body.weekday, req.body.hour, req.body.confirmNum, req.body.premium, req.body.joinDate],
    function(error, info){
        console.log("info! :",info);
        console.log("console check success!!");
        if(error == null){
            res.status(200).json(error);
            console.log("success insert data for database!!");
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

module.exports = user;