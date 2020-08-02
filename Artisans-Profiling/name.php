<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$phno=$_GET["phoneno"];
		$nm = $_GET["name"];
		$st=$con->prepare("UPDATE `artisans` set `name`=? where `phoneno`=?");
		$st->bind_param("ss", $nm, $phno);
		$st->execute();
		echo "data uploaded successfully!";
		?>