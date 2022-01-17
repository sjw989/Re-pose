var express = require('express');
var mysql = require('mysql');
const bodyParser = require('body-parser')
var user = express.Router();
var dontUpload = require('./../dontUpload');
const nodemailer = require('nodemailer');

user.use(bodyParser.urlencoded({extended: true}))
user.use(bodyParser.json())

var connection = dontUpload.connection

connection.connect();

user.post('/', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    console.log(req.body.userPw);
    console.log(req.body.userEmail);
    connection.query('insert into repose_user(userId, userPw, userEmail, pose, medal, weekday, hour, confirmNum, premium, joinDate) values (?,?,?,?,?,?,?,?,?,?);',
    [req.body.userId, req.body.userPw, req.body.userEmail, req.body.pose, req.body.medal, req.body.weekday, req.body.hour, req.body.confirmNum, req.body.premium, req.body.JoinDate],
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

user.post('/emailcheck', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userEmail);
    connection.query('SELECT * FROM repose_user WHERE userEmail=?',
    [req.body.userEmail],
    function(error, info){
        // console.log("info! :",info.length);
        if(error == null){
            if(info.length == 0) {
                res.status(201).json(error);
                console.log("user email: not existence");
            }
            else {
                res.status(200).json(error);
                console.log(info[0]);
                //메일 보내는 코드 req.body.userEmail
                const main = async () => {
                    let transporter = nodemailer.createTransport(dontUpload.transporterInfo);
                  
                    // send mail with defined transport object
                    let info_ = await transporter.sendMail({
                      from: `"Re-pose Team" <'reposesender@gmail.com'>`,
                      to: info[0].userEmail,
                      subject: 'Repose Auth Information',
                      text: `id: ${info[0].userId}\n pw: ${info[0].userPw}`,
                      html: `<b>id: ${info[0].userId}\n pw: ${info[0].userPw}</b>`,
                    });
                  
                    console.log('Message sent: %s', info_.messageId);
                    // Message sent: <b658f8ca-6296-ccf4-8306-87d57a0b4321@example.com>
                  
                    // res.status(200).json({
                    //   status: 'Success',
                    //   code: 200,
                    //   message: 'Sent Auth Email',
                    // });
                  };
                  
                  main().catch(console.error);
                console.log("user email: existence");
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
    connection.query('UPDATE repose_user SET pose=? WHERE userId=?',
    [req.body.pose, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
            res.status(200).json(error);
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
    connection.query('UPDATE repose_user SET medal=? WHERE userId=?',
    [req.body.medal, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
            res.status(200).json(error);
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

//weekday
user.post('/update/weekday', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    connection.query('UPDATE repose_user SET weekday=? WHERE userId=?',
    [req.body.weekday, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
            res.status(200).json(error);
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
    connection.query('UPDATE repose_user SET hour=? WHERE userId=?',
    [req.body.hour, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
            res.status(200).json(error);
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

//confirmNum
user.post('/update/confirmNum', function(req, res, next) {
    console.log(req.body);
    console.log(req.body.userId);
    connection.query('UPDATE repose_user SET confirmNum=? WHERE userId=?',
    [req.body.confirmNum, req.body.userId],
    function(error, info){
        console.log("info! :",info);
        // console.log("console check success!!");
        if(error == null){
            // console.log("res: ", res);
            console.log("user Id is exist, id: ", res.userId);
            console.log("success updata data for database  !!");
            res.status(200).json(error);
        }else{
            console.log("error exist!! :",error);
            res.status(503).json(error);
        } 
    });
})

module.exports = user;