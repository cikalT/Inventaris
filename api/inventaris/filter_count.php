<?php

require_once 'koneksi.php';

$tglawal = $_GET['tglawal'];
$tglakhir = $_GET['tglakhir'];

$sql = "SELECT
	(SELECT COUNT(*)
	FROM tb_peminjaman WHERE TglPinjam_ BETWEEN '$tglawal' AND '$tglakhir'
	) AS Total_,
    (SELECT COUNT(*)
	FROM tb_peminjaman WHERE StatusPinjam_='1' AND TglPinjam_ BETWEEN '$tglawal' AND '$tglakhir'
	) AS Dipinjam_,
	 (SELECT COUNT(*)
	FROM tb_peminjaman WHERE StatusPinjam_='2' AND TglPinjam_  BETWEEN '$tglawal' AND '$tglakhir'
	) AS Dikembalikan_,
     (SELECT COUNT(*)
	FROM tb_peminjaman WHERE StatusPinjam_='3' AND TglPinjam_  BETWEEN '$tglawal' AND '$tglakhir'
	) AS Terlambat_
	FROM DUAL";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
		$data["data"][] = $row;
	}	

echo json_encode($data);

mysqli_close($con);

?>