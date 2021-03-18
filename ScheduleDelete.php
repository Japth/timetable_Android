<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con =mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $userID = $_POST["userID"];
  $courseID = $_POST["courseID"];

  $statement = mysqli_prepare($con, "DELETE FROM SCHEDULE WHERE userID = '$userID' AND courseID = '$courseID'");
  $statement = mysqli_prepare($con, "DELETE FROM SCHEDULE WHERE userID = ? AND courseID = ?");
  mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
 ?>
