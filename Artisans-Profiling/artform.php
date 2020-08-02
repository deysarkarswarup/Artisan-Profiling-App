<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$phno=$_GET["phoneno"];
		$ar = $_GET["artform"];
		$st=$con->prepare("UPDATE `artisans` set `artform`=? where `phoneno`=?");
		$st->bind_param("ss", $ar, $phno);
		$st->execute();
		echo "data uploaded successfully!";
		?>