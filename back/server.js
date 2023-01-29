//express 라이브러리 사용 위해 입력
const express = require('express');
const app = express();

//서버 생성 코드
const http = require('http');
const server = http.createServer(app);

//fs 모듈 file system으로 파일 처리와 관련된 전반적인 작업을 하는 코드
//path 모듈 파일 경로와 관련된 모듈이다.
const fs = require('fs');
const path = require('path');

//cors 이슈 해결에 필요
const cors = require('cors');

//몽구스 몽고db 모듈
const mongoose = require('mongoose');

//mongoDB 주소 mongodb://localhost:27017/mangol은
//로컬주소임 malgol은 데이터베이스명으로 마음대로 지을 수 있음
const db_url = 'mongodb+srv://my_atlas_user:tpehd13@@cluster0.3ksxfgo.mongodb.net/test';
//서버포트
const port = 3000;

//미들웨어로 요청이 오면 응답이 가기전까지 실행되는 함수들을 사용
app.use(express.static(path.join(__dirname + '/public')));

//json 파일을 처리하는데 필요한 미들웨어
app.use(express.urlencoded({ extended: true }));
app.use(express.json())

//cors 이슈를 해결하는데 필요한 미들웨어
app.use(cors());

//몽고 DB 실행
//db_url, {}은 옵션으로 {} 내부 옵션을 이용해 버전 오류를 없앰
mongoose
    .connect(db_url, { useNewUrlParser: true, useUnifiedTopology: true, usefindAndModify: false})
    .then(() => console.log('Successfully connected to mongodb'))
    .catch(e => console.error(e));

//서버 실행
server.listen(port, ()=>{
    console.log('Server is running $(port)');
});