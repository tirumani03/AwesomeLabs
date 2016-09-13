<?php

/**
 * @author sarath
 * @copyright 2016
 * 10.0.2.2
 */

 include('config.php');
 $response =array();
  $name=$address=$phone=$email=$pin="";
   $name = isset($_POST['Name']) ? mysqli_real_escape_string($con,$_POST['Name']) : null;
  $address = isset($_POST['Address']) ? mysqli_real_escape_string($con,$_POST['Address']) : null;
   $phone = isset($_POST['Phone_number']) ? mysqli_real_escape_string($con,$_POST['Phone_number']) : null;
   $email = isset($_POST['Email']) ? mysqli_real_escape_string($con,$_POST['Email']) : null;
   $pin = isset($_POST['Pin']) ? mysqli_real_escape_string($con,$_POST['Pin']) : null;
   if (is_null($name) || $name=="")  {
    $response['description'] = "Name is required";
  } elseif (!preg_match("/^[a-zA-Z ]*$/",$name)) {
        $name=null;
       $response['description'] = "Only letters and white space allowed in Name";  
    }
   elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $email=null;
      $response['description'] = "Invalid email format";     
    }
   
   elseif(!preg_match('/^[0-9]{10}$/', $phone))
    {
       $phone=null;
      $response['description'] = 'Invalid Number!';
     
    }
  else{
       $r= mysqli_query($con,"CALL insert_user('$name','$address','$phone','$email','$pin');");
      if($r)
       $response['description']='User added';
       else 
       $response['description']='User already Exists';
       }
  
 echo json_encode($response);
 

?>