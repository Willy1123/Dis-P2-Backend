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

import java.util.ArrayList;
import java.util.List;

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
    @PostMapping(value = "/ZonaBasicaSalud",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade una nueva Zona Básica de Salud al Sistema",
            description = "Añade una nueva zona básica de salud siempre que los datos introducidos sean correctos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArrayList<ZonaBasicaSalud>> addZBS(@RequestBody int indice, ZonaBasicaSalud datosNuevaZBS) {

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
        return null;
    }

}