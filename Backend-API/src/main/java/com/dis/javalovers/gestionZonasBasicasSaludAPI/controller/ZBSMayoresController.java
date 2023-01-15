
package com.dis.javalovers.gestionZonasBasicasSaludAPI.controller;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.dao.JsonDAO;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.dao.JsonDAO_60;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.ZonaBasicaSalud;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.ZonaBasicaSalud_60;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(description = "Provee los datos de las Zonas Básicas de Salud para los mayores de 60", name = "Controlador Zonas Básicas de Salud para Mayores de 60")
public class ZBSMayoresController {

    private JsonDAO_60 jsonDAO_60 = JsonDAO_60.getInstance();

    // GET
    @GetMapping("/ZonasBasicasSaludMayores60")
    @Operation(summary = "Devuelve una lista de todas las Zonas Básicas de Salud",
            description = "Devuelve los datos pertinentes de las zonas básicas de salud. No ejecutar en la web de Swagger ya que crashea la web")
    public List<ZonaBasicaSalud_60> zBSalud60_GET() {
        return jsonDAO_60.leerJsonZBS_60();
    }

    // GET
    @GetMapping("/ZonaBasicaSaludMayores60/{codigo_geometria}")
    @Operation(summary = "Devuelve Zonas Básicas de Salud por Código de Geometría",
            description = "Devuelve los datos pertinentes de la zona básica de salud seleccionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK!"),
            @ApiResponse(responseCode = "404", description = "Not OK! - Not Found")
    })
    public static ResponseEntity<List<ZonaBasicaSalud_60>> zBSaludId_GET(@PathVariable("codigo_geometria") String codigo) {
        try {
            // devuelve los datos correspondientes al código de geometría introducido;
            List<ZonaBasicaSalud_60> listaZBS_60 = new JsonDAO_60().leerJsonZBS_60();
            List<ZonaBasicaSalud_60> encontrado = new ArrayList<>();
            for (ZonaBasicaSalud_60 zbs : listaZBS_60) {
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


    // POST
    @PostMapping(value = "/ZonaBasicaSaludMayores_60",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade una nueva Zona Básica de Salud para Mayores de 60 al Sistema",
            description = "Añade una nueva zona básica de salud para mayores de 60 siempre que los datos introducidos sean correctos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArrayList<ZonaBasicaSalud>> addZBS(@RequestBody int indice, ZonaBasicaSalud_60 datosNuevaZBS) {

        // creamos una lista tipo ZonaBasicaSalud donde guardamos el json leído.
        List<ZonaBasicaSalud_60> listaZBS = jsonDAO_60.leerJsonZBS_60();
        ZonaBasicaSalud_60 nuevaZBS = new ZonaBasicaSalud_60();
        // actualizamos los datos del objeto de la lista seleccionado
        nuevaZBS.setCodigo_geometria(datosNuevaZBS.getCodigo_geometria());
        nuevaZBS.setZona_basica_salud(datosNuevaZBS.getZona_basica_salud());
        nuevaZBS.setCasos_confirmados_P60mas_ultimos_14dias(datosNuevaZBS.getTasa_incidencia_acumulada_P60mas_ultimos_14dias());
        nuevaZBS.setCasos_confirmados_P60mas_ultimos_14dias(datosNuevaZBS.getCasos_confirmados_P60mas_ultimos_14dias());
        nuevaZBS.setFecha_informe(datosNuevaZBS.getFecha_informe());
        // añadimos los cambios a la lista
        listaZBS.set(indice, nuevaZBS);

        // guardamos el json con los cambios realizados
        jsonDAO_60.guardarJsonZBS_60(listaZBS);
        System.out.println("guardado en el json correctamente");
        return null;
    }


}
