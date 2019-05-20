<?php

require_once 'koneksi.php';

$kode = $_GET['kode'];
$tgl = $_GET['tgl'];

$sql = "SELECT * FROM tb_peminjaman WHERE KodePinjaman_='$kode' AND StatusPinjam_='1' AND TglKembali_>='$tgl'";
$result = mysqli_query($con, $sql);
$check = mysqli_fetch_array($result);

if (isset($check)) {
	echo "true";
} else {
	$sql2 = "UPDATE tb_peminjaman SET StatusPinjam_='3' WHERE KodePinjaman_='$kode'";
	$result2 = mysqli_query($con, $sql2);
	if ($sql2) {
		echo "date-";
	}
	echo "false";
}
mysqli_close($con);

?>