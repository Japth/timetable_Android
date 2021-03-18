<?php
  //id 중복 체크하는 파일
  header("Content-Type: text/html; charset=UTF-8");
  $con=mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $userID=$_POST["userID"];

  $statement=mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ?");
  mysqli_stmt_bind_param($statement, "s", $userID);
  mysqli_stmt_execute($statement);
  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $userID, $userPassword, $userGender, $userMajor, $userEmail); //API 버전 문제로 주석 처리

  $response=array();
  $response["success"]=true;

  while(mysqli_stmt_fetch($statement))
  {
      $response["success"]=false; //해당 사용자 id가 존재할때 false값을 넣어줘서 회원가입 할 수 없는 정보임을 알려줌
      $response["userID"]=$userID;
  }

  echo json_encode($response);
?>
