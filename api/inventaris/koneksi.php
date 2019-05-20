<?php

$host = "localhost";
$username = "root";
$password = "";
$database = "inventaris";

$con = mysqli_connect($host, $username, $password, $database);

if (mysqli_connect_errno($con)) {
    	die("Failed to connect to MySQL: " . mysqli_connect_error());
}

?>