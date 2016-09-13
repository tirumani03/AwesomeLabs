<?php

/**
 * @author sarath
 * @copyright 2016
 * 10.0.2.2
 */

 include('config.php');
 $response =array();
    $ID = isset($_GET['id']) ? mysqli_real_escape_string($con,$_GET['id']) : "";
   // $desc=$_POST['description'];
   // $price=$_POST['price'];
    $sql="CALL retrieve_user($ID);";
   $r= mysqli_query($con,$sql);
  while($row = mysqli_fetch_array($r,MYSQLI_BOTH))
  {
    array_push($response,array("Id"=>$row[0],"Name"=>$row[1],"Address"=>$row[2],"Phone_number"=>$row[3],"Email"=>$row[4],"pin"=>$row[5]));
  }
  if($response){
 echo json_encode(array("users"=>$response,"msg"=>"success"));}
 else 
 echo json_encode(array("msg"=>"invalid user Id"));
 mysqli_close($con);

?>