<?php

require_once 'koneksi.php';

$tglawal = $_GET['tglawal'];
$tglakhir = $_GET['tglakhir'];

$sql = "SELECT tb_peminjaman.IDPeminjaman_, tb_peminjaman.IDInvent_, tb_peminjaman.IDPegawai_, tb_peminjaman.Jumlah_, tb_peminjaman.TglPinjam_, tb_peminjaman.TglKembali_, tb_peminjaman.StatusPinjam_, tb_peminjaman.KodePinjaman_, tb_inventaris.NamaInvent_, tb_pegawai.NamaPegawai_ FROM ((tb_peminjaman
INNER JOIN tb_inventaris ON tb_peminjaman.IDInvent_ = tb_inventaris.IDInvent_)
INNER JOIN tb_pegawai ON tb_peminjaman.IDPegawai_ = tb_pegawai.IDPegawai_) WHERE tb_peminjaman.TglPinjam_ BETWEEN '$tglawal' AND '$tglakhir' ORDER BY tb_peminjaman.TglPinjam_ ASC";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
	$data["data"][] = $row;
}

echo json_encode($data);

mysqli_close($con);

?>