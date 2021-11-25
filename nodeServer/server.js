var express = require('express')
var http = require('http');
const logger = require('morgan');
const bodyParser = require('body-parser')
var app = express();
var user = require('./routes/user');

app.use('/user',user);
app.use(logger('dev'));
app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json())

app.listen(3000, () => {
  console.log(`서버 실행, 포트 번호 3000`);
});
