package com.example.giner.proyectopmm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

//Tarea asíncrona que nos permite realizar la conexión de red en segundo plano para operaciones con alumnos
public class TareaRest extends AsyncTask <Void, String, String>{

    private Context contexto;
    private int codigoOperacion;
    private String operacionREST;
    private String urlRecurso;
    private String parametrosPOST;
    private TareaRestListener actividadOyente;
    private ProgressDialog progressDialog;
    private int codigoRespuestaHttp;

    //Este constructor nos permite configurar la tarea (abajo las explicaciones)
    public TareaRest(Context contexto, int codigoOperacion, String operacionREST, String urlRecurso, String parametrosPOST, TareaRestListener actividadOyente) {
        this.contexto=contexto;
        //Fijamos el requestcode para luego devolvérselo a la activity y que sepa para qué nos llamó
        this.codigoOperacion=codigoOperacion;
        // - Fijemos la operación sobre alumnos que se solicitará al servidor
        this.operacionREST = operacionREST;
        // - Fijemos el urlRecurso sobre el cuál se realizará la operación
        this.urlRecurso =urlRecurso;
        // - Fijemos el parámetro POST que usaremos en inserciones y modificaciones
        this.parametrosPOST=parametrosPOST;
        // - Suscribamos la actividad que nos llama a nuestro evento de finalización
        // con el código HTTP de resultado y, en su caso, el objeto JSON de respuesta
        this.actividadOyente=actividadOyente;
        //Instanciamos el progress dialog
        progressDialog = new ProgressDialog(contexto);
    }

    //Gracias a esta interface podremos comunicarle a la actividad que nos ha invocado el resultado
    //de la petición al servidor y, en su caso, el objeto JSON
    public interface TareaRestListener {
        void onTareaRestFinalizada(int codigoOperacion, int codigoRespuestaHttp, String respuestaJson);
    }

    protected void onPreExecute() {

        //Lanzamos el diálogo de comienzo de la comunicación
        progressDialog.setMessage("Espera un momento...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    // Esto se ejecutará en un hilo secundario después de onPreExecute
    protected String doInBackground(Void... vacio) {

        /************ Construcción de una petición POST al servidor y obtención de la respuesta***********/
        BufferedReader bufferLectura = null;
        HttpURLConnection conexion = null;
        String cuerpoRecibido = null;

        try {

            // Creamos un objeto URL que contendrá la web donde realizar peticiones
            URL url = new URL(urlRecurso);

            //Creamos la conexión con el servidor web
            conexion = (HttpURLConnection) url.openConnection();
            //Definimos cuánto tiempo esperaremos respuesta del servidor
            conexion.setConnectTimeout(5000);
            //Establecemos el tipo de petición que realizaremos (GET, POST,PUT,DELETE)
            conexion.setRequestMethod(operacionREST);

            if (operacionREST.equals("POST") || operacionREST.equals("PUT")){
                //Activamos el método POST
                conexion.setDoOutput(true);
                //Creamos un flujo de salida de datos
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conexion.getOutputStream());
                //Escribimos los datos (valor de editText)
                outputStreamWriter.write(parametrosPOST);
                //Limpiamos el flujo de salida de datos
                outputStreamWriter.flush();
                //Cerramos el flujo
                outputStreamWriter.close();
            }

            // Obtenemos la respuesta del servidor
            //Instanciamos el buffer de lectura a través del flujo de entrada de la conexión
            codigoRespuestaHttp = conexion.getResponseCode();
            bufferLectura = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            //Usamos un constructor de Strings
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // Vamos leyendo línea a línea la respuesta del servidor hasta que no quedan líneas
            while ((line = bufferLectura.readLine()) != null) {
                // Añadimos las líneas al constructor de Strings
                stringBuilder.append(line + "\n");
            }
            // Añadimos las respuesta del servidor al String msgContenido
            cuerpoRecibido = stringBuilder.toString();
        } catch (Exception ex) {
            //Mostramos el error (servidor no existente, servidor no responde, etc.)
            publishProgress(ex.toString());
        } finally {
            //Finalizamos las conexiones y los flujos de datos
            conexion.disconnect();
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (Exception ex) {
                    //Mostramos el error
                    publishProgress("Error al cerrar el buffer de lectura: "+ex.toString());
                }
            }

            //Devolvemos el mensaje (respuesta o error) para que onPostExecute lo trate
            return cuerpoRecibido;
        }
    }

    //Esto se ejecuta en el hilo principal y cuando el hilo secundario ha acabado su tarea
    protected void onPostExecute(String cuerpoRecibido) {
        // Cerramos el diálogo de progreso
        progressDialog.dismiss();
        //Llamamos a la actividad que se nos ha suscrito para devolverle el código http de respuesta
        //y, en su caso, el objeto JSON de respuesta
        actividadOyente.onTareaRestFinalizada(codigoOperacion,codigoRespuestaHttp,cuerpoRecibido);
    }

    @Override
    protected void onProgressUpdate(String... error) {
        //Mostramos las incidencias de conexión con un Toast en la pantalla de la actividad que nos llamó
        Toast.makeText(contexto, error[0], Toast.LENGTH_LONG).show();
    }
}
