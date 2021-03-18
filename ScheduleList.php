<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con =mysqli_connect("localhost","jandpth","password","jandpth");
  mysqli_set_charset($con,"utf8");

  $userID = $_GET["userID"];

  $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseTime, COURSE.courseProfessor, COURSE.courseTitle, Course.courseCredit
    FROM USER, COURSE, SCHEDULE WHERE USER.userID='$userID' AND USER.userID=SCHEDULE.userID
    AND SCHEDULE.courseID=COURSE.courseID"); //해당 유저가 가지고 있는 스케줄의 강의 목록을 뽑아옴
  $response=array();
  while($row=mysqli_fetch_array($result))
  {
    array_push($response, array("courseID"=>$row[0], "courseTime"=>$row[1], "courseProfessor"=>$row[2], "courseTitle"=>$row[3],
        "courseCredit"=>$row[4]));
  }
  echo json_encode(array("response"=>$response)); //,JSON_UNESCAPED_UNICODE
  mysqli_close($con);
 ?>
