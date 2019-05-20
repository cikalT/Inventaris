<?php

require_once 'koneksi.php';

$username = $_GET['username'];
$password = $_GET['password'];

$sql = "SELECT * FROM tb_pegawai WHERE Username_='$username' AND Password_='$password'";

$response = mysqli_query($con, $sql);

$result = array();
$result['login'] = array();

if (mysqli_num_rows($response) == 1) {
	$row = mysqli_fetch_assoc($response);
	$index['IDPegawai_'] = $row['IDPegawai_'];
	$index['NamaPegawai_'] = $row['NamaPegawai_'];
	$index['Nip_'] = $row['Nip_'];
	$index['Alamat_'] = $row['Alamat_'];
	$index['Username_'] = $row['Username_'];
	$index['Password_'] = $row['Password_'];
	array_push($result['login'], $index);
	$result['success'] = "1";
	$result['message'] = "success";
	echo json_encode($result);
}
if (mysqli_num_rows($response) == 0) {
 	$result['success'] = "0";
 	$result['message'] = "failed";
 	echo json_encode($result);
}

mysqli_close($con);

?>