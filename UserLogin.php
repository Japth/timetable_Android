<?php

  header("Content-Type: text/html; charset=UTF-8");
  $con=mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $userID=$_POST["userID"];
  $userPassword=$_POST["userPassword"]; //아이디와 패스워드를 매개변수로 받음

  $statement=mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ?"); //해당 사용자가 존재하는지 검사
  mysqli_stmt_bind_param($statement, "s", $userID); //문자열 형태로 매개변수 보내줌
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  //아이디 존재하는지 체크 후 해당 아이디가 가지고 있는 암호화된 비밀번호와 정보들을 가져옴
  mysqli_stmt_bind_result($statement, $userID, $checkedPassword, $userGender, $userMajor, $userEmail); //나온 결과값에서 해당 정보를 추출해서 존재한다면 success값을 true로 변환

  $response=array();
  $response["success"]=false;

  while(mysqli_stmt_fetch($statement))
  {
    //사용자가 접속을 시도할 때 사용한 비말번호가 암호화된 비밀번호와 같다면 로그인 함
    if(password_verify($userPassword, $checkedPassword))
    {
      $response["success"] = true;
      $response["userID"] = $userID;
    }
  }

  echo json_encode($response);
  ?>
