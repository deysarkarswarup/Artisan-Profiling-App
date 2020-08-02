<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$phno=$_GET["phoneno"];
		$ex = $_GET["experience"];
		$st=$con->prepare("UPDATE `artisans` set `experience`=? where `phoneno`=?");
		$st->bind_param("ss", $ex, $phno);
		$st->execute();
		echo "data uploaded successfully!";
		?>