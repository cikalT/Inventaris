<?php

require_once 'koneksi.php';

$nama = $_GET['nama'];
$username = $_GET['username'];
$password = $_GET['password'];

$sql = "INSERT INTO tb_petugas (NamaPetugas_, IDLevel_, Username_, Password_) VALUES
		('$nama', '2', '$username', '$password')";

$result = mysqli_query($con, $sql);

if ($result) {
	echo "Berhasil";
} else {
	echo mysqli_error($con);
}

mysqli_close($con);

?>