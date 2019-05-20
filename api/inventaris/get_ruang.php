<?php

require_once 'koneksi.php';

$sql = "CALL getRuang()";

$result = mysqli_query($con, $sql);

$data = array();

while($row = mysqli_fetch_assoc($result)) {
	$data["data"][] = $row;
}

echo json_encode($data);

mysqli_close($con);

?>