<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$phno=$_GET["phoneno"];
		$nm = $_GET["name"];
		echo $phno;
		echo $nm;
		$st=$con->prepare("UPDATE `artisans` set `name`=? where `phoneno`=?");
		$st->bind_param("ss", $nm, $phno);
		$st->execute();
		echo "successful!";
		?>