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

/*임시구현시 작성한 코드

// app.get(`/`, (req, res) => {
//   console.log(req.body);
//   console.log("get호출 성공");
//   res.send({"result": "GET 호출"});
// })

// app.post(`/`, (req, res) => {
//   console.log(req.body);
//   res.send({"result": "POST 호출"});
// })

// app.put(`/:id`, (req, res) => {
//   console.log(`내용 PrimaryKey : ${req.params.id}`)
//   console.log(req.body);
//   res.send({"result": "UPDATE 호출"});
// })

// app.delete(`/:id`, (req, res) => {
//   console.log(req.params.id);
//   console.log(req.path)
//   res.send({"result": "DELETE 호출"});
// })

*/
app.listen(3000, () => {
  console.log(`서버 실행, 포트 번호 3000`);
});
