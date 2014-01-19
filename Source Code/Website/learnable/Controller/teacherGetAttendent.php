<?php
date_default_timezone_set('Asia/Hong_Kong');

include("mysql.php");
$tbl_name="Lesson_Memeber"; // Table name

// Get Input User
$mylesson=$_GET['mylesson']; 

// To protect MySQL injection (more detail about MySQL injection)
$mylesson = stripslashes($mylesson);
$mylesson = mysql_real_escape_string($mylesson);

$sql="SELECT * FROM $tbl_name Where LessonlessonID='$mylesson'";
$result=mysql_query($sql);

$count=mysql_num_rows($result);

echo "<table border='1' width='100%'>";
echo "<tr><th>Student</th><th>login Time</th><th>Logout Time</th></tr>";
while($row = mysql_fetch_array($result))
{
 if ($row['loginTime']==NULL ){
  echo "<tr><td>".$row['MemberuserID']."</td><td>N/A</td><td>N/A</td></tr>";
 }else if ($row['logoutTime']==NULL){
   echo "<tr><td>".$row['MemberuserID']."</td><td>".date('Y-m-d H:i:s', $row['loginTime'])."</td><td>N/A</td></tr>";
 }else{
  echo "<tr><td>".$row['MemberuserID']."</td><td>".date('Y-m-d H:i:s', $row['loginTime'])."</td><td>".date('Y-m-d H:i:s', $row['logoutTime'])."</td></tr>";

 }
}
echo "</table>";
echo '<a href="javascript:window.reload()">Reload</a>';

?>