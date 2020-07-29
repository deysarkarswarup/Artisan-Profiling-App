<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$phno=$_GET["phoneno"];
		$ar = $_GET["artform"];
		$st=$con->prepare("UPDATE `artisans` set `artform`=? where `phoneno`=?");
		$st->bind_param("ss", $ar, $phno);
		$st->execute();
		echo "successful!";
		?>

<!--?php
$servername="localhost";
$mysql_user="root";
$mysql_pass="";
$dbname="Artisans-Profiling";
$conn=mysqli_connect($servername, $mysql_user, $mysql_pass, $dbname);
if($conn){
echo("connection success");
}else{
echo("connection not success");
 
}
if($_SERVER['REQUEST_METHOD']=='POST'){
$phoneno=$_POST['phoneno'];
$artform=$_POST['artform'];

$query="INSERT INTO `Artisans(`artform`) VALUES ('$artform') where phoneno='$phoneno'";
if(mysqli_query($conn, $query)){
echo("Data Submitted successfully");
}else{
echo("error in Submission");
}
}else{
echo("error in request method");
}
?-->