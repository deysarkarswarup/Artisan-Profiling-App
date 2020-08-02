<?php
		$con=new mysqli("localhost","root","","artisans-profiling");
		$st_check=$con->prepare("select * from artisans where phoneno=?");
		$st_check->bind_param("s", $_GET["phoneno"]);
		$st_check->execute();
		$rs=$st_check->get_result();
		if($rs->num_rows==0)
		{
		$st=$con->prepare("insert into artisans(phoneno) values(?)");
		$st->bind_param("s", $_GET["phoneno"]);
		$st->execute();
		echo "data uploaded successfully!";
		}
		else
			echo "User already exists!";
		?>