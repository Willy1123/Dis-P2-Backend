package com.dis.javalovers.gestionZonasBasicasSaludAPI.controller;


import com.dis.javalovers.gestionZonasBasicasSaludAPI.dao.JsonDAO;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.ZonaBasicaSalud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Tag(description = "Provee los datos de las Zonas Básicas de Salud", name = "Controlador Zonas Básicas de Salud")
public class ZBSaludController {

    private JsonDAO jsonDAO = JsonDAO.getInstance();
    // GET
    @GetMapping("/ZonasBasicasSalud")
    @Operation(summary = "Devuelve una lista de todas las Zonas Básicas de Salud",
            description = "Devuelve los datos pertinentes de las zonas básicas de salud. No ejecutar en la web de Swagger ya que crashea la web")
    public List<ZonaBasicaSalud> zBSalud_GET() {
        return jsonDAO.leerJsonZBS();
    }

    @GetMapping("/ZonaBasicaSalud/{codigo_geometria}")
    @Operation(summary = "Devuelve Zonas Básicas de Salud por Código de Geometría",
            description = "Devuelve los datos pertinentes de la zona básica de salud seleccionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK!"),
            @ApiResponse(responseCode = "404", description = "Not OK! - Not Found")
    })
    public static ResponseEntity<List<ZonaBasicaSalud>> zBSaludId_GET(@PathVariable("codigo_geometria") String codigo) {
        try {
            // devuelve los datos correspondientes al código de geometría introducido;
            List<ZonaBasicaSalud> listaPokemons = new JsonDAO().leerJsonZBS();
            List<ZonaBasicaSalud> encontrado = new ArrayList<>();
            for (ZonaBasicaSalud zbs : listaPokemons) {
                if (zbs.getCodigo_geometria().equals(codigo)) {
                    encontrado.add(zbs);
                }
            }
            if (!encontrado.isEmpty()) {
                return new ResponseEntity<>(encontrado, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST (Update)
    @PostMapping(value = "/ZonaBasicaSaludUpdate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modifica una Zona Básica de Salud del Sistema",
            description = "Modifica una zona básica de salud existente siempre que los datos introducidos sean contengan el formato correcto")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ZonaBasicaSalud>> updateZBS(@RequestBody int indice, ZonaBasicaSalud datosNuevaZBS) {

        // creamos una lista tipo ZonaBasicaSalud donde guardamos el json leído.
        List<ZonaBasicaSalud> listaZBS = jsonDAO.leerJsonZBS();
        ZonaBasicaSalud nuevaZBS = new ZonaBasicaSalud();
        // actualizamos los datos del objeto de la lista seleccionado
        nuevaZBS.setCodigo_geometria(datosNuevaZBS.getCodigo_geometria());
        nuevaZBS.setZona_basica_salud(datosNuevaZBS.getZona_basica_salud());
        nuevaZBS.setTasa_incidencia_acumulada_ultimos_14dias(datosNuevaZBS.getTasa_incidencia_acumulada_ultimos_14dias());
        nuevaZBS.setTasa_incidencia_acumulada_total(datosNuevaZBS.getTasa_incidencia_acumulada_total());
        nuevaZBS.setCasos_confirmados_totales(datosNuevaZBS.getCasos_confirmados_totales());
        nuevaZBS.setCasos_confirmados_ultimos_14dias(datosNuevaZBS.getCasos_confirmados_ultimos_14dias());
        nuevaZBS.setFecha_informe(datosNuevaZBS.getFecha_informe());
        // añadimos los cambios a la lista
        listaZBS.set(indice, nuevaZBS);

        // guardamos el json con los cambios realizados
        jsonDAO.guardarJsonZBS(listaZBS);
        System.out.println("guardado en el json correctamente");
        return new ResponseEntity<>(listaZBS, HttpStatus.OK);
    }

    // POST (ADD)
    @PostMapping(value = "/ZonaBasicaSaludNew",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade una nueva Zona Básica de Salud al Sistema",
            description = "Añade una nueva zona básica de salud siempre que los datos introducidos sean correctos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ZonaBasicaSalud>> addZBS(@RequestBody ZonaBasicaSalud datosNuevaZBS) {

        // creamos una lista tipo ZonaBasicaSalud donde guardamos el json leído.
        List<ZonaBasicaSalud> listaZBS = jsonDAO.leerJsonZBS();
        ZonaBasicaSalud nuevaZBS = new ZonaBasicaSalud();

        // añadimos los datos del objeto que vamos a crear
        nuevaZBS.setZona_basica_salud(datosNuevaZBS.getZona_basica_salud());
        nuevaZBS.setTasa_incidencia_acumulada_ultimos_14dias(datosNuevaZBS.getTasa_incidencia_acumulada_ultimos_14dias());
        nuevaZBS.setTasa_incidencia_acumulada_total(datosNuevaZBS.getTasa_incidencia_acumulada_total());
        nuevaZBS.setCasos_confirmados_totales(datosNuevaZBS.getCasos_confirmados_totales());
        nuevaZBS.setCasos_confirmados_ultimos_14dias(datosNuevaZBS.getCasos_confirmados_ultimos_14dias());
        nuevaZBS.setFecha_informe(datosNuevaZBS.getFecha_informe());

        // si la zona básica de salud introducida existe dentro de la lista, entonces el nuevo cód de geometría será
        // igual al correspondiente de dicha zona básica de salud
        if (listaZBS.stream().anyMatch(o -> o.getZona_basica_salud().equals(datosNuevaZBS.getZona_basica_salud()))) {

            // para ello usamos el método "filter" del objeto "stream" (de tipo zonasBasicasSalud) para filtrar el elemento
            // que cumpla que el valor introducido de zona_basica_salud existe en la listaZBS y al encontrarlo usamos
            // "findFirst" para encontrar el valor del elemento Codigo_geometría correspondiente al mismo objeto que el de
            // Zona_basica_salud
            Optional<String> id = listaZBS.stream()
                    .filter(o -> o.getZona_basica_salud().equals(datosNuevaZBS.getZona_basica_salud()))
                    .map(ZonaBasicaSalud::getCodigo_geometria)
                    .findFirst();
            // antes de que accedamos al valor de "id" tenemos que comprobar si el objeto Optional tiene un valor o no,
            // para ello usamos el método isPresent(). Tras ello simplemente añadimos el valor del codigo_geometría que
            // tendrá el nuevo objeto zonasBasicasSalud.
            if (id.isPresent()) {
                nuevaZBS.setCodigo_geometria(id.get());
            }
        }

        // en caso contrario miramos el código más alto registrado y le sumamos 1
        else {
            int max = Integer.MIN_VALUE;
            for (ZonaBasicaSalud zbs : listaZBS) {
                int codActual = Integer.parseInt(zbs.getCodigo_geometria());
                if ( codActual > max) {
                    max = codActual;
                }
            }
            nuevaZBS.setCodigo_geometria(String.valueOf(max + 1));
        }

        // añadimos los cambios a la lista
        listaZBS.add(nuevaZBS);

        // guardamos el json con los cambios realizados
        jsonDAO.guardarJsonZBS(listaZBS);
        System.out.println("guardado el registro " + listaZBS.size() + " correctamente.");
        return new ResponseEntity<>(listaZBS, HttpStatus.OK);
    }

}
