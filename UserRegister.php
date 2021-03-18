<?php

  header("Content-Type: text/html; charset=UTF-8");
  $con=mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $userID=$_POST["userID"];
  $userPassword=$_POST["userPassword"];
  $userGender=$_POST["userGender"];
  $userMajor=$_POST["userMajor"];
  $userEmail=$_POST["userEmail"];

  //include "./password.php";

  //password_hash:php에서 기본적으로 제공하는 함수 //(암호와 하고싶은 정보, PASSWORD_DEFAULT)
  //해당 정보가 자동으로 암호화 되어 checkedPassword 안에 들어감
  $checkedPassword = password_hash("$userPassword", PASSWORD_DEFAULT);
  $statement=mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ? ,?)");
  //암호화가 이루어진 패스워드를 넣음
  mysqli_stmt_bind_param($statement, "sssss", $userID, $checkedPassword, $userGender, $userMajor, $userEmail);
  mysqli_stmt_execute($statement);

  $response=array();
  $response["success"]=true; //success 변수에 true값을 넣어줘 회원에게 리스폰스해서 회원가입이 성공했다는 것을 알려줌

  echo json_encode($response);

 ?>
