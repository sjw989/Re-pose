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

user.post('/login', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    connection.query('SELECT * FROM repose_user WHERE userId=? AND userPw=?',
    [req.body.userId, req.body.userPw],
    function(error, info){
        if(error == null){
            console.log("user Id is exist, id: ", res.userId);
            console.log("user Id is exist, pw: ", res.userPw);
            // console.log("info! :",info);

            if(info.length == 0) {
                res.status(201).json(error);
                console.log("empty obj");
            }
            else {
                res.json(info[0])
                console.log("success select data for database  !!");
            }
        }else{
            // console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

user.post('/idcheck', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    connection.query('SELECT * FROM repose_user WHERE userId=?',
    [req.body.userId],
    function(error, info){
        // console.log("info! :",info.length);
        if(error == null){
            if(info.length == 0) {
                res.status(200).json(error);
                console.log("user Id: not existence");
            }
            else {
                res.status(201).json(error);
                console.log("user Id: existence");
            }
        }else{
            // console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

//UPDATE IMPLIMENT
//pose
user.post('/update/pose', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    connection.query('UPDATE repose_user SET pose=? WHERE userId=?',
    [req.body.pose, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

//medal
user.post('/update/medal', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    connection.query('UPDATE repose_user SET medal=? WHERE userId=?',
    [req.body.medal, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

//pose
user.post('/update/weekday', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    connection.query('UPDATE repose_user SET weekday=? WHERE userId=?',
    [req.body.weekday, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

//hour
user.post('/update/hour', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    connection.query('UPDATE repose_user SET hour=? WHERE userId=?',
    [req.body.hour, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

module.exports = user;