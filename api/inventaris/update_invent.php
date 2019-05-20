<?php

require_once 'koneksi.php';

$idinvent = $_GET['idinvent'];
$nama = $_GET['nama'];
$kondisi = $_GET['kondisi'];
$ket = $_GET['ket'];
$jumlah = $_GET['jumlah'];
$jenis = $_GET['jenis'];
$ruang = $_GET['ruang'];

$sql = "UPDATE tb_inventaris SET NamaInvent_='$nama', KondisiInvent_='$kondisi', KetInvent_='$ket', Jumlah_='$jumlah', IDJenis_='$jenis', IDRuang_='$ruang' WHERE IDInvent_='$idinvent'";

$result = mysqli_query($con, $sql);

if ($result) {
	echo "Berhasil";
} else {
	echo mysqli_error($con);
}

mysqli_close($con);

?>