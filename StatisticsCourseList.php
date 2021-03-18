<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con =mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $userID = $_GET["userID"]; //해당 유저아이디를 입력 받아서 스케줄테이블에서 검색

  $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitle, COURSE.courseDivide,
      COURSE.coursePersonnel, COUNT(SCHEDULE.courseID), COURSE.courseCredit FROM SCHEDULE, COURSE WHERE SCHEDULE.courseID
      IN (SELECT SCHEDULE.courseID FROM SCHEDULE WHERE SCHEDULE.userID = '$userID') AND SCHEDULE.courseID = COURSE.courseID
      GROUP BY SCHEDULE.courseID"); //COUNT(SCHEDULE.courseID):그 강의를 신청한 다른 사람의 수
  $response = array();
  while($row = mysqli_fetch_array($result))
  {
    array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1], "courseTitle"=>$row[2], "courseDivide"=>$row[3],
    "coursePersonnel"=>$row[4], "COUNT(SCHEDULE.courseID)"=>$row[5], "courseCredit"=>$row[6]));
  }

  echo json_encode(array("response"=>$response)); //, JDON_UNESCAPED_UNICODE
  mysqli_close($con);

 ?>
