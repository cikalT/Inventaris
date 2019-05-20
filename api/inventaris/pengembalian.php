<?php

require_once 'koneksi.php';

$kode = $_GET['kode'];

$sql = "UPDATE tb_peminjaman SET StatusPinjam_='2' WHERE KodePinjaman_='$kode'";

$result = mysqli_query($con, $sql);

if ($result) {
	echo "Berhasil";
} else {
	echo mysqli_error($con);
}

?>