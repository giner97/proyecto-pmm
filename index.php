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
            
                $consulta = $db->prepare("select id_movil, marca,modelo,procesador,ram,precio,stock from Movil where activo=1");
                
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
            
                $consulta = $db->prepare("select id_cliente,nombre,apellidos,dni,provincia,telefono from Cliente where activo=1");
                
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
        
    //Modifica cliente
        
        $app->put('/cliente/:id',function($id)use($app,$db){
            
            try{  
            
                //Obtenemos el contenido de la peticion

                    $cuerpoPeticion = $app->request()->getBody();
                    $cliente = json_decode($cuerpoPeticion);

                //Obetenemos los atributos del objeto cliente

                    $nombreCliente=$cliente->nombre;
                    $apellidosCliente=$cliente->apellidos;
                    $dniCliente=$cliente->dni;
                    $provinciaCliente=$cliente->provincia;
                    $telefonoCliente=$cliente->telefono;
                    
                //Preparamos la consulta
                    
                    $consulta = $db->prepare("update Cliente set nombre='$nombreCliente', apellidos='$apellidosCliente', dni='$dniCliente', provincia = '$provinciaCliente', telefono='$telefonoCliente' where id_cliente=$id");
                    
                //Ejecutamos la consulta
                    
                    $resultado = $consulta->execute();
            
                    if ($resultado){
                        
                        if ($consulta->rowCount()==1){
                             echo "Cliente modificado";
                        }
                        else {
                            //No existía el cliente
                             $app->response()->setStatus(404);
                              echo "No existe el cliente especificado";
                        }

                    }
                    else {
                        //En caso de error Mysql
                        throw new PDOException($consulta->errorInfo()[2]);
                    }

            } catch (PDOException $e) {
                $app->response()->setStatus(404);
                //Si no hay resultados getmessage() devolverá una cadena vacía
                echo $e->getMessage();
            }    
             
        });
        
    //Insercion de Clientes
        
        $app->post('/cliente/',function()use($app,$db){
            
            try{
               
            //Obtenemos el contenido de la peticion 
               
                $cuerpoPeticion = $app->request()->getBody();
                $cliente = json_decode($cuerpoPeticion);
                   
            //Obtengo todos los atributos del objeto Cliente
                   
                $nombreCliente=$cliente->nombre;
                $apellidosCliente=$cliente->apellidos;
                $dniCliente=$cliente->dni;
                $provinciaCliente=$cliente->provincia;
                $telefonoCliente=$cliente->telefono; 
                
            //Preparamos la consulta
                
                $consulta = $db->prepare("Insert into Cliente(nombre,apellidos,dni,provincia,telefono,activo) values('$nombreCliente','$apellidosCliente','$dniCliente','$provinciaCliente','$telefonoCliente',1);");
            
            //Ejecutamos la consulta
                    
                $resultado = $consulta->execute();    
            
                if ($resultado){

                    if ($consulta->rowCount()==1){
                        echo "Cliente añadido";
                    }
                    else {
                        //No existía el cliente
                            $app->response()->setStatus(404);
                            echo "Error en la insercion";
                    }

                }

                else {
                    //En caso de error Mysql
                        throw new PDOException($consulta->errorInfo()[2]);
                }

            } catch (PDOException $e) {
                $app->response()->setStatus(404);
                //Si no hay resultados getmessage() devolverá una cadena vacía
                echo $e->getMessage();
            }
            
           });
		   
	//Insercion de Compras
        
        $app->post('/compra/',function()use($app,$db){
            
            try{
               
            //Obtenemos el contenido de la peticion 
               
                $cuerpoPeticion = $app->request()->getBody();
                $compra = json_decode($cuerpoPeticion);
                   
            //Obtengo todos los atributos del objeto Compra
                   
                $id_cliente=$compra->id_cliente;
                $id_movil=$compra->id_movil;
                $imei=$compra->imei;
                
            //Preparamos la consulta
                
                $consulta = $db->prepare("Insert into Compra(id_cliente,id_movil,imei)values('$id_cliente','$id_movil','$imei');");
            
            //Ejecutamos la consulta
                    
                $resultado = $consulta->execute();    
            
                if ($resultado){

                    if ($consulta->rowCount()==1){
                        echo "Cliente añadido";
                    }
                    else {
                        //No existía el cliente
                            $app->response()->setStatus(404);
                            echo "Error en la insercion";
                    }

                }

                else {
                    //En caso de error Mysql
                        throw new PDOException($consulta->errorInfo()[2]);
                }

            } catch (PDOException $e) {
                $app->response()->setStatus(404);
                //Si no hay resultados getmessage() devolverá una cadena vacía
                echo $e->getMessage();
            }
            
           });
		   
	//Borrado de moviles
    
        $app->delete('/movil/:id',function($id)use($app,$db){
            
            try{
                
                //Preparamos la consulta
                
                    $consulta = $db->prepare("update Movil set activo = 0 where id_movil= $id");
                    
                //Ejecutamos la consulta
                
                    $resultado = $consulta->execute();
                    
                //Si ha eliminado la fila lo mostramos
                    
                if ($resultado){
                    if ($consulta->rowCount()==1){
                         echo "Movil borrado.";
                    }
                    else {
                        //No existe el cliente
                         $app->response()->setStatus(404);
                          echo "No se ha podido eliminar el movil.";
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

    //Modifica moviles
        
        $app->put('/movil/:id',function($id)use($app,$db){
            
            try{  
            
                //Obtenemos el contenido de la peticion

                    $cuerpoPeticion = $app->request()->getBody();
                    $movil = json_decode($cuerpoPeticion);

                //Obetenemos los atributos del objeto cliente
				
					$stock=$movil->stock;
                    $precio=$movil->precio;
                    
                //Preparamos la consulta
                    
                    $consulta = $db->prepare("update Movil set stock='$stock', precio='$precio' where id_movil=$id");
                    
                //Ejecutamos la consulta
                    
                    $resultado = $consulta->execute();
            
                    if ($resultado){
                        
                        if ($consulta->rowCount()==1){
                             echo "Movil modificado";
                        }
                        else {
                            //No existía el cliente
                             $app->response()->setStatus(404);
                              echo "No existe el movil especificado";
                        }

                    }
                    else {
                        //En caso de error Mysql
                        throw new PDOException($consulta->errorInfo()[2]);
                    }

            } catch (PDOException $e) {
                $app->response()->setStatus(404);
                //Si no hay resultados getmessage() devolverá una cadena vacía
                echo $e->getMessage();
            }    
             
        });
		
		//Insercion de moviles
        
        $app->post('/movil/',function()use($app,$db){
            
            try{
               
            //Obtenemos el contenido de la peticion 
               
                $cuerpoPeticion = $app->request()->getBody();
                $movil = json_decode($cuerpoPeticion);
                   
            //Obtengo todos los atributos del objeto Compra
                   
                $marca=$movil->marca;
                $modelo=$movil->modelo;
                $procesador=$movil->procesador;
				$stock=$movil->stock;
                $ram=$movil->ram;
                $precio=$movil->precio;
                
            //Preparamos la consulta
                
                $consulta = $db->prepare("Insert into Movil(marca,modelo,procesador,ram,precio,stock,activo)values('$marca','$modelo','$procesador','$ram','$precio','$stock',1);");
            
            //Ejecutamos la consulta
                    
                $resultado = $consulta->execute();    
            
                if ($resultado){

                    if ($consulta->rowCount()==1){
                        echo "Movil añadido";
                    }
                    else {
                        //No existía el cliente
                            $app->response()->setStatus(404);
                            echo "Error en la insercion";
                    }

                }

                else {
                    //En caso de error Mysql
                        throw new PDOException($consulta->errorInfo()[2]);
                }

            } catch (PDOException $e) {
                $app->response()->setStatus(404);
                //Si no hay resultados getmessage() devolverá una cadena vacía
                echo $e->getMessage();
            }
            
           });
		   
	//Consulta moviles comprados por un usuario
	
	$app->get('/consultamovil/:id', function ($id) use ($app,$db){
        
        try{
            
            //Preparamos la consulta
            
                $consulta = $db->prepare("select mo.id_movil, mo.marca, mo.modelo, mo.procesador, mo.ram, mo.precio, mo.stock from Movil mo inner join Compra co on mo.id_movil=co.id_movil where id_cliente=$id;");
                
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
	
	//Consulta clientes que han comprados un movil
	
	$app->get('/consultacliente/:id', function ($id) use ($app,$db){
        
        try{
            
            //Preparamos la consulta
            
                $consulta = $db->prepare("select cl.id_cliente,cl.nombre,cl.apellidos,cl.dni,cl.provincia, cl.telefono from Cliente cl inner join Compra co on cl.id_cliente=co.id_cliente where id_movil=$id;");
                
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
		   
	
	
    $app->run();
    
   
