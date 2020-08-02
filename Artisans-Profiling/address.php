<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$phno=$_GET["phoneno"];
		$ad = $_GET["address"];
		$st=$con->prepare("UPDATE `artisans` set `address`=? where `phoneno`=?");
		$st->bind_param("ss", $ad, $phno);
		$st->execute();
		echo "data uploaded successfully!";
		?>