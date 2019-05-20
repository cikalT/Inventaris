<?php

require_once 'koneksi.php';

$sql = "CALL getCount()";

$response = mysqli_query($con, $sql);
$result = array();
$result['total'] = array();

if (mysqli_num_rows($response) == 1) {
	$row = mysqli_fetch_assoc($response);
	$index['JumlahOperator_'] = $row['JumlahOperator_'];
	$index['JumlahPegawai_'] = $row['JumlahPegawai_'];
	$index['JumlahPinjaman_'] = $row['JumlahPinjaman_'];
	array_push($result['total'], $index);
	$result['success'] = "1";
	$result['message'] = "success";
	echo json_encode($result);
}

mysqli_close($con);

?>