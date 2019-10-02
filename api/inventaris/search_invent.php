<?php
// script untuk mencari inventaris berdasarkan nama inventaris dan dikirim dalam bentuk json
require_once 'koneksi.php';

$nama = $_GET['nama'];

$sql = "SELECT tb_inventaris.IDInvent_, tb_inventaris.NamaInvent_, tb_inventaris.KondisiInvent_, tb_inventaris.KetInvent_, tb_inventaris.Jumlah_, tb_inventaris.IDJenis_, tb_inventaris.IDRuang_, tb_inventaris.TglRegis_, tb_inventaris.KodeInvent_, tb_jenis.NamaJenis_, tb_ruang.NamaRuang_ FROM ((tb_inventaris
INNER JOIN tb_jenis ON tb_inventaris.IDJenis_ = tb_jenis.IDJenis_)
INNER JOIN tb_ruang ON tb_inventaris.IDRuang_ = tb_ruang.IDRuang_) WHERE tb_inventaris.NamaInvent_ LIKE '%".$nama."%'
";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
	$data["data"][] = $row;
}

echo json_encode($data);

mysqli_close($con);

?>