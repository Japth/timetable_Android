<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con=mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $result=mysqli_query($con, "SELECT * FROM NOTICE ORDER BY noticeDate DESC;"); //내림차순 //최신 날짜 순
  $response = array();

  while($row = mysqli_fetch_array($result))
  {
    array_push($response, array("noticeContent"=>$row[0], "noticeName"=>$row[1], "noticeDate"=>$row[2]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);
 ?>
