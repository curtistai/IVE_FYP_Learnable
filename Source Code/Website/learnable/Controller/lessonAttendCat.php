<?php
include("mysql.php");
$tbl_name="Lesson_Memeber"; // Table name

// Get Input User
$myusername=$_GET['myusername']; 

// To protect MySQL injection (more detail about MySQL injection)
$myusername = stripslashes($myusername);
$myusername = mysql_real_escape_string($myusername);

$sql="select distinct(courseName) as A from course,lesson,lesson_memeber Where MemberuserID='$myusername' 
and course.courseID=lesson.CoursecourseID and lesson.lessonID=lesson_memeber.LessonlessonID";
$result=mysql_query($sql);

$lesson_cat=array();
$count=mysql_num_rows($result);

if (!$count=mysql_num_rows($result)){
$arr = array ('lesson_cat'=>'No Lesson Available');
}
else{
$counter=1;
$temparr = array($counter++=>'Live');
array_push($lesson_cat,$temparr);

while($row = mysql_fetch_array($result))
{
$temparr = array($counter++=>$row['A']);

array_push($lesson_cat,$temparr);
}
$arr = array ('lesson_cat'=>$lesson_cat);	
}
echo json_encode($arr);

?>