<?php
// script untuk mencari pegawai berdasarkan nama pegawai dan dikirim dalam bentuk json
require_once 'koneksi.php';

$nama = $_GET['nama'];

$sql = "SELECT * FROM tb_pegawai WHERE NamaPegawai_ LIKE '%".$nama."%'";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
	$data["data"][] = $row;
}

echo json_encode($data);

mysqli_close($con);

?>