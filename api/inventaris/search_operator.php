<?php
// script untuk mencari petugas berdasarkan nama petugas dan dikirim dalam bentuk json
require_once 'koneksi.php';

$nama = $_GET['nama'];

$sql = "SELECT * FROM tb_petugas WHERE IDLevel_='2' AND NamaPetugas_ LIKE '%".$nama."%'";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
	$data["data"][] = $row;
}

echo json_encode($data);

mysqli_close($con);

?>