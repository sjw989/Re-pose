var express = require('express');
var mysql = require('mysql');

var connection = mysql.createConnection({
    user: 'repose_user1',
    password: 'Qwer1234!',
    database: 'repose',
    host: 'localhost'
});

module.exports = {
    connection
};