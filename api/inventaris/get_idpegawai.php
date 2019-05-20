<?php

require_once 'koneksi.php';

$nama = $_GET['nama'];

$sql = "SELECT * FROM tb_pegawai WHERE NamaPegawai_='$nama'";

$response = mysqli_query($con, $sql);
$result = array();
$result['pegawai'] = array();

if (mysqli_num_rows($response) == 1) {
	$row = mysqli_fetch_assoc($response);
	$index['IDPegawai_'] = $row['IDPegawai_'];
	$index['NamaPegawai_'] = $row['NamaPegawai_'];
	$index['Nip_'] = $row['Nip_'];
	$index['Alamat_'] = $row['Alamat_'];
	array_push($result['pegawai'], $index);
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