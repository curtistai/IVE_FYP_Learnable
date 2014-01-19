<?php
include("mysql.php");
$tbl_name="lesson"; // Table name

// Get Input User
$mylesson = $_GET['mylesson']; 
$status= $_GET['status'];



// To protect MySQL injection (more detail about MySQL injection)
$mylesson = stripslashes($mylesson);
$mylesson = mysql_real_escape_string($mylesson);


$result = mysql_query("UPDATE $tbl_name SET lesson_available=$status,lesson_chatRoom='$mylesson',lesson_video_url='$mylesson',lesson_question_url=''
WHERE lessonID='$mylesson'");
//NEED TO CHANGE question url


$arr = array('sucess'=>$result);
echo json_encode($arr);

?>