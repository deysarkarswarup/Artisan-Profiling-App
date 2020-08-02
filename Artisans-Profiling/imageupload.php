<?php
if(isset($_POST["encoded_string"])){
 	
 	$phone = $_POST["phone"];
	$encoded_string = $_POST["encoded_string"];
	$image_name = $_POST["image_name"];
	
	$decoded_string = base64_decode($encoded_string);
	
	$path = 'images/'.$image_name;
	
	$file = fopen($path, 'wb');
	
	$is_written = fwrite($file, $decoded_string);
	fclose($file);
	
	if($is_written > 0) {
		
		
		$query = "INSERT INTO photos(phoneno,name,path) VALUES('" . $phone . "', '" . $image_name . "','" . $path . "')";
		$conn = mysqli_connect('localhost', 'root', '','artisans-profiling');
		$result = mysqli_query($conn, $query) ;
		
		if($result){
			echo "data uploaded successfully!";
		}else{
			echo "failed";
		}
		
		mysqli_close($conn);
	}
 }
?>