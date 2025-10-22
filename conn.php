<?php

class Conexion
{

    public function connect()
    {
        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "SOA";

        try {

            $connect = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        } catch (Exception $e) {
            die("Fallo : " . $e->getMessage());
        }

        return $connect;
    }
}
