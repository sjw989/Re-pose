var express = require('express')
var http = require('http');
var app = express();

app.use(express.urlencoded({extended: false}))
app.get(`/`, (req, res) => {
  console.log(req.body);
  res.send({"result": "GET 호출"});
})

app.post(`/`, (req, res) => {
  console.log(req.body);
  res.send({"result": "POST 호출"});
})

app.put(`/:id`, (req, res) => {
  console.log(`내용 PrimaryKey : ${req.params.id}`)
  console.log(req.body);
  res.send({"result": "UPDATE 호출"});
})

app.delete(`/:id`, (req, res) => {
  console.log(req.params.id);
  console.log(req.path)
  res.send({"result": "DELETE 호출"});
})

app.listen(3000, () => {
  console.log(`서버 실행, 포트 번호 3000`);
});
