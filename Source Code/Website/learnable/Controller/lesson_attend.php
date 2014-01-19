<?php
include("mysql.php");
$tbl_name="Lesson_Memeber"; // Table name

// Get Input User
$myusername=$_GET['myusername']; 
$status=$_GET['status'];
$courseName = $_GET['courseName'];

// To protect MySQL injection (more detail about MySQL injection)
$myusername = stripslashes($myusername);
$myusername = mysql_real_escape_string($myusername);
$status = stripslashes($status);
$status = mysql_real_escape_string($status);
$courseName = stripslashes($courseName);
$courseName = mysql_real_escape_string($courseName);

$sql="SELECT LessonlessonID,lessonName FROM $tbl_name,lesson";
$sql2=",course";
$sql3=" WHERE lesson_available=$status and 
MemberuserID='$myusername' and $tbl_name.LessonlessonID=Lesson.lessonID";
$sql4=" and courseName='$courseName' and course.courseID=lesson.CoursecourseID";

if ($courseName==null)
{
$result=mysql_query($sql.$sql3);
}else{
$result=mysql_query($sql.$sql2.$sql3.$sql4);
}


$lesson_arr=array();
$lessonid_arr=array();
$count=mysql_num_rows($result);

if (!$count=mysql_num_rows($result)){
$arr = array ('lesson'=>'No Lesson Available');
}
else{
while($row = mysql_fetch_array($result))
{
$lesson_temp_arr = array('lessonID'=>$row['LessonlessonID'],'lessonName'=>$row['lessonName']);
//array_push($lessonid_arr,$row['LessonlessonID']);
array_push($lesson_arr,$lesson_temp_arr);

}
$arr = array ('lesson'=>$lesson_arr);	
}
echo json_encode($arr);

?>