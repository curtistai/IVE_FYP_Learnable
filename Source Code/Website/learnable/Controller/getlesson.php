<?php
include("mysql.php");
$tbl_name="lesson"; // Table name

// Get Input User
$mylesson=$_GET['mylesson']; 

// To protect MySQL injection (more detail about MySQL injection)
$mylesson = stripslashes($mylesson);
$mylesson = mysql_real_escape_string($mylesson);

$sql="SELECT * FROM $tbl_name WHERE lessonID='$mylesson'";
$result=mysql_query($sql);



$row = mysql_fetch_array($result);

$arr = array ('lessonid'=>$row['lessonID'],'CoursecourseID'=>$row['CoursecourseID'],'lessonName'=>$row['lessonName'],
'lessonDesc'=>$row['lessonDesc'],'lesson_chatRoom'=>$row['lesson_chatRoom'],'lesson_pic_url'=>$row['lesson_pic_url'],'lesson_video_url'=>$row['lesson_video_url'],
'lesson_question_url'=>$row['lesson_question_url'],'lesson_ppt_url'=>$row['lesson_ppt_url'],'lesson_available'=>$row['lesson_available'],'lesson_time'=>$row['lesson_time']);
echo json_encode($arr);

?>