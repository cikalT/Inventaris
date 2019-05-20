<?php

require_once 'koneksi.php';

$idinvent = $_GET['idinvent'];

$sql = "DELETE FROM tb_inventaris WHERE IDInvent_='$idinvent'";

$result = mysqli_query($con, $sql);

if ($result) {
	echo "Berhasil";
} else {
	echo mysqli_error($con);
}

mysqli_close($con);

?>