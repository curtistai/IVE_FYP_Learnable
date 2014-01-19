<?php
include("mysql.php");
$tbl_name="member"; // Table name

// username and password sent from form 
$myusername=$_POST['myusername']; 
$mypassword=$_POST['mypassword'];


// To protect MySQL injection (more detail about MySQL injection)
$myusername = stripslashes($myusername);
$mypassword = stripslashes($mypassword);
$myusername = mysql_real_escape_string($myusername);
$mypassword = mysql_real_escape_string($mypassword);

$sql="SELECT * FROM $tbl_name WHERE userID='$myusername' and userPassWord='$mypassword'";
$result=mysql_query($sql);

// Mysql_num_row is counting table row
$count=mysql_num_rows($result);
// If result matched $myusername and $mypassword, table row must be 1 row

if($count==1){
// Register $myusername, $mypassword and redirect to file "loginSuccess.php"
session_register("myusername");
session_start();
$sessionId = session_id();//得到sessionid

$row = mysql_fetch_array($result);
$arr = array ('validation'=>'success','sessionid'=>$sessionId,'position'=>$row['userPosition']);
mysql_query("UPDATE $tbl_name SET userSession='$sessionId'
WHERE userID='$myusername'");
echo json_encode($arr);
}
else {
session_start();
session_destroy();
$arr = array ('validation'=>'fail','sessionid'=>0);
echo json_encode($arr);
}
?>
