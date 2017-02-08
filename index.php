<?php

//Importamos Slim

    require 'Slim/Slim.php';
    \Slim\Slim::registerAutoloader();
    
//Creamos instancia Slim 
    
    $app = new \Slim\Slim();
    
// Especificamos el tipo de contenido
       
    $app->contentType('text/html; charset=utf-8');
    
// Definimos conexion de la base de datos.
    
    define('BD_SERVIDOR','mysql.hostinger.es');
    define('BD_NOMBRE','u523327463_movil');
    define('BD_USUARIO','u523327463_user1');
    define('BD_PASSWORD','alvaretyrichar');
    
//Nos conectamos a la base de datos con PDO
    
    $db = new PDO('mysql:host=' . BD_SERVIDOR . ';dbname=' . BD_NOMBRE . ';charset=utf8', BD_USUARIO, BD_PASSWORD);
    
//Si se accede al directorio raiz mostramos información del servidor web
$app->get('/',function()use ($app){
    $app->response->setStatus(200);
    //En este caso mostramos una respuesta en texto plano
    echo "Servidor activo.";
});
    
//get de moviles
    
    $app->get('/movil/', function () use ($app,$db){
        
        try{
            
            //Preparamos la consulta
            
                $consulta = $db->prepare("select id_movil, marca,modelo,procesador,ram,almacenamiento, stock,sistemaOperativo, precio from Movil");
                
            //Ejecutamos la consulta
                
                $consulta->execute();
                
            //Almacenamos los resultados de la consulta en un array
                
                $resultados = $consulta->fetchAll(PDO::FETCH_ASSOC);
                
            //Comprobamos si los resultados tienen un valor diferente a false
                
                if($resultados){
                    
                    //Establecemos el tipo de datos a enviar
                    
                        $app->response()->header('Content-Type', 'application/json');
                        
                    //Devolvemos el array como Json
                        
                        echo json_encode($resultados);
                    
                }
                
                else{
                    
                    throw new PDOException($consulta->errorInfo()[2]);
                
                }
        }
        
        catch (Exception $ex) {

            $app->response()->setStatus(404);
            echo $e->getMessage();
            
        }
        
    });
    
    //get de clientes
    
    $app->get('/cliente/', function () use ($app,$db){
        
        try{
            
            //Preparamos la consulta
            
                $consulta = $db->prepare("select id_cliente,nombre,apellidos,dni,cp,pais,comunidadAutonoma,provincia,domicilio,telefono from Cliente where activo=1");
                
            //Ejecutamos la consulta
                
                $consulta->execute();
                
            //Almacenamos los resultados de la consulta en un array
                
                $resultados = $consulta->fetchAll(PDO::FETCH_ASSOC);
                
            //Comprobamos si los resultados tienen un valor diferente a false
                
                if($resultados){
                    
                    //Establecemos el tipo de datos a enviar
                    
                        $app->response()->header('Content-Type', 'application/json');
                        
                    //Devolvemos el array como Json
                        
                        echo json_encode($resultados);
                    
                }
                
                else{
                    
                    throw new PDOException($consulta->errorInfo()[2]);
                
                }
        }
        
        catch (Exception $ex) {

            $app->response()->setStatus(404);
            echo $e->getMessage();
            
        }
        
    });
    
    //Borrado de clientes
    
        $app->delete('/cliente/:id',function($id)use($app,$db){
            
            try{
                
                //Preparamos la consulta
                
                    $consulta = $db->prepare("update Cliente set activo = 0 where id_cliente= $id");
                    
                //Ejecutamos la consulta
                
                    $resultado = $consulta->execute();
                    
                //Si ha eliminado la fila lo mostramos
                    
                if ($resultado){
                    if ($consulta->rowCount()==1){
                         echo "Cliente borrado.";
                    }
                    else {
                        //No existe el cliente
                         $app->response()->setStatus(404);
                          echo "No se ha podido eliminar el cliente.";
                    }
           
                }
                else {
                    //En caso de error Mysql
                    throw new PDOException($consulta->errorInfo()[2]);
                }
                
            } catch (Exception $ex) {

                $app->response()->setStatus(404);
                echo $e->getMessage();
                
            }
            
        });
    
    $app->run();
    
   