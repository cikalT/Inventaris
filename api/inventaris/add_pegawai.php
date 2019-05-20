<?php

require_once 'koneksi.php';

$nama = $_GET['nama'];
$nip = $_GET['nip'];
$alamat = $_GET['alamat'];
$username = $_GET['username'];
$password = $_GET['password'];

$sql = "INSERT INTO tb_pegawai (NamaPegawai_, Nip_, Alamat_, Username_, Password_) VALUES
		('$nama', '$nip', '$alamat', '$username', '$password')";

$result = mysqli_query($con, $sql);

if ($result) {
	echo "Berhasil";
} else {
	echo mysqli_error($con);
}

mysqli_close($con);

?>