<?php
//전체에서 가장 인기있는 강의 목록 출력
  header("Content-Type: text/html; charset=UTF-8");
  $con =mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitle, COURSE.courseProfessor, COURSE.courseCredit
      , COURSE.courseDivide, COURSE.coursePersonnel, COURSE.courseTime FROM COURSE, SCHEDULE WHERE COURSE.courseID = SCHEDULE.courseID
      GROUP BY SCHEDULE.courseID ORDER BY COUNT(SCHEDULE.courseID) DESC LIMIT 5;"); //가장 많이 신청한 강의 5개를 내림차순으로 출력

  $response = array();
  while($row = mysqli_fetch_array($result))
  {
    array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1], "courseTitle"=>$row[2], "courseProfessor"=>$row[3],
    "courseCredit"=>$row[4], "courseDivide"=>$row[5], "coursePersonnel"=>$row[6], "courseTime"=>$row[7]));
  }

  echo json_encode(array("response"=>$response)); //,JSON_UNESCAPED_UNICODE
  mysqli_close($con);
 ?>
