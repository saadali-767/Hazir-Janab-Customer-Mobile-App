<?php

include("db_config.php");
$response = array();

if(isset($_POST['first_name']) && 
   isset($_POST['last_name']) &&
   isset($_POST['email']) &&
   isset($_POST['password']) &&
   isset($_POST['address']) &&
   isset($_POST['gender']) &&
   isset($_POST['CNIC']) &&
   isset($_POST['phone_number'])) {

    $first_name = $_POST['first_name'];
    $last_name = $_POST['last_name'];
    $email = $_POST['email'];
    $password = $_POST['password']; // Consider hashing this password before storing
    $address = $_POST['address'];
    $gender = $_POST['gender'];
    $CNIC = $_POST['CNIC'];
    $phone_number = $_POST['phone_number'];

    // Assuming you want to store the password securely
   // $hashed_password = password_hash($password, PASSWORD_DEFAULT);

    // Prepared statement to avoid SQL Injection
    $stmt = $conn->prepare("INSERT INTO vendor (first_name, last_name, gender, CNIC, password, email, phone_number, Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    // Bind parameters to the prepared statement
    $stmt->bind_param("ssssssss", $first_name, $last_name, $gender, $CNIC, $password, $email, $phone_number, $address);

    // Execute the prepared statement
    if($stmt->execute()){
        $response['Status'] = 1;
        $response['id'] = $conn->insert_id;
        $response['Message'] = "Signup successful";
    } else {
        $response['Status'] = 0;
        $response['Message'] = "Data insertion failed: " . $stmt->error;
    }

    // Close the statement
    $stmt->close();
} else {
    $response['Status'] = 0;
    $response['Message'] = "Required fields missing";
}
// Close the connection
$conn->close();

echo json_encode($response);

?>
