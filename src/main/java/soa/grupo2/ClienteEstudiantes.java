/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soa.grupo2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

/**
 *
 * @author ramir
 */
public class ClienteEstudiantes {

    private final Gson gson = new Gson();
    //1.Crear un cliente HTTPClient.
    private final HttpClient cliente = HttpClient.newHttpClient();
    private final String ApiUrl = "http://localhost/SOAEjemploGrupo2/src/main/java/api/api.php";

    public ArrayList<Estudiante> obtenerEstudiantes() {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionGet = HttpRequest.newBuilder()
                    .uri(new URI(ApiUrl))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = cliente.send(peticionGet, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Estudiante>>() {
                }.getType();
                ArrayList<Estudiante> estudianteLista = gson.fromJson(jsonRespuesta, tipoArray);
                return estudianteLista;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public boolean registrarEstudiante(Estudiante estudiante) {
        String parametros = construirParametros(estudiante);
        try {
            // Construir la petición POST
            HttpRequest peticionPost = HttpRequest.newBuilder()
                    .uri(new URI(ApiUrl))
                    .header("Content-Type",
                            "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(parametros))
                    .build();
            // 1. enviar la petición con el cliente
            // 2. almacenar la respuesta
            HttpResponse<String> respuesta = cliente.send(peticionPost,
                    BodyHandlers.ofString());
            if (respuesta.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean eliminarEstudiante(String cedula) {
        String urlBorrar = construirConsulta("CEDULA=" + cedula);
        try {
            // 1. Construir la petición DELETE
            HttpRequest peticionDelete = HttpRequest.newBuilder()
                    .uri(new URI(urlBorrar))
                    .DELETE()
                    .build();
            // 2. Enviar la petición con el cliente
            // 3. Almacenar la respuesta
            HttpResponse<String> respuesta = cliente.send(peticionDelete,
                    BodyHandlers.ofString());
            // 4. Programar utilizando la respuesta
            if (respuesta.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean editarEstudiante(Estudiante estudiante) {
        String urlEditar = construirConsulta(construirParametros(estudiante));
        try {
            // 1. Construir la petición PUT
            HttpRequest peticionPut = HttpRequest.newBuilder()
                    .uri(new URI(urlEditar))
                    .PUT(BodyPublishers.noBody())
                    .build();
            // 2. Enviar la petición con el cliente
            // 3. Almacenar la respuesta
            HttpResponse<String> respuesta = cliente.send(peticionPut,
                    BodyHandlers.ofString());
            // 4. Programar utilizando la respuesta
            if (respuesta.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<Estudiante> buscarEstudiantes(String cedula) {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            String urlBuscar = construirConsulta("CEDULA=" + cedula);
            HttpRequest peticionGetBuscar = HttpRequest.newBuilder()
                    .uri(new URI(urlBuscar))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = cliente.send(peticionGetBuscar, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Estudiante>>() {
                }.getType();
                ArrayList<Estudiante> estudianteLista = gson.fromJson(jsonRespuesta, tipoArray);
                return estudianteLista;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public String construirConsulta(String parametros) {
        return ApiUrl + "?" + parametros;
    }

    public String construirParametros(Estudiante estudiante) {
        return "CEDULA=" + estudiante.getCEDULA()
                + "&NOMBRE=" + estudiante.getNOMBRE()
                + "&APELLIDO=" + estudiante.getAPELLIDO()
                + "&DIRECCION=" + estudiante.getDIRECCION()
                + "&TELEFONO=" + estudiante.getTELEFONO();
    }
}
