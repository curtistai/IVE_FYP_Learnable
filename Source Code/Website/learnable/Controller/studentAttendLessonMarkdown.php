<?php
date_default_timezone_set('Asia/Hong_Kong');

include("mysql.php");
$tbl_name="Lesson_Memeber"; // Table name

// username and password sent from form 
$myusername=$_GET['myusername']; 
$mylesson=$_GET['mylesson'];
$attendstatus=$_GET['attendstatus'];

// To protect MySQL injection (more detail about MySQL injection)
$myusername = stripslashes($myusername);
$mylesson = stripslashes($mylesson);
$attendstatus = stripslashes($attendstatus);

$myusername = mysql_real_escape_string($myusername);
$mylesson = mysql_real_escape_string($mylesson);
$attendstatus = mysql_real_escape_string($attendstatus);

$currentTime = time();
if ($attendstatus=="1"){ // Attend
mysql_query("UPDATE $tbl_name SET loginTime= $currentTime
WHERE MemberuserID='$myusername' and LessonlessonID='$mylesson'");
}else{
mysql_query("UPDATE $tbl_name SET logoutTime= $currentTime
WHERE MemberuserID='$myusername' and LessonlessonID='$mylesson'");

}

?>
