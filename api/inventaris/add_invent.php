<?php

require_once 'koneksi.php';

$nama = $_GET['nama'];
$kondisi = $_GET['kondisi'];
$ket = $_GET['ket'];
$jumlah = $_GET['jumlah'];
$jenis = $_GET['jenis'];
$ruang = $_GET['ruang'];
$idpetugas = $_GET['idpetugas'];

$string = substr(str_shuffle("0123456789"), 0, 6);
$kode = "BG".$string;

$sql = "INSERT INTO tb_inventaris (NamaInvent_, KondisiInvent_, KetInvent_, Jumlah_, IDJenis_, IDRuang_, KodeInvent_, IDPetugas_) VALUES
		('$nama', '$kondisi', '$ket', '$jumlah', '$jenis', '$ruang','$kode', '$idpetugas')";

$result = mysqli_query($con, $sql);

if ($result) {
	echo "Berhasil";
} else {
	echo mysqli_error($con);
}

mysqli_close($con);

?>