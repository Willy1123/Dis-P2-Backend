package com.dis.javalovers.gestionZonasBasicasSaludAPI.controller;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.Config;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    // POST
    @PostMapping(value = "/ZonaBasicaSalud",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade una nueva Zona Básica de Salud al Sistema",
            description = "Añade una nueva zona básica de salud siempre que los datos introducidos sean correctos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArrayList<ZonaBasicaSalud>> addZBS(@RequestBody int indice, ZonaBasicaSalud nuevaZBS) {
        ZonaBasicaSalud zonaBasicaSalud = new ZonaBasicaSalud("1000", "Mi casa",
                1000.0, 666.6, 777.7, "2022/12/13 23:00:00");
        List<ZonaBasicaSalud> listaZBS = jsonDAO.leerJsonZBS();
        ZonaBasicaSalud posList = listaZBS.get(indice);
        posList.setZona_basica_salud("1000");
        posList.setZona_basica_salud("Mi casa");
        posList.setTasa_incidencia_acumulada_ultimos_14dias(1000.0);
        posList.setTasa_incidencia_acumulada_total(666.6);
        posList.setCasos_confirmados_totales(777.7);
        posList.setFecha_informe("2022/12/13 23:00:00");
//        listaZBS.add(zonaBasicaSalud);
//        System.out.println("Agregado a la lista la zona de salud: " + zonaBasicaSalud);
//        for (ZonaBasicaSalud zbs : listaZBS) {
//            if(zbs.equals(viejaZBS)) {
//                //listaZBS.remove(zbs);
//                //listaZBS.add(nuevaZBS);
//                listaZBS.add(zonaBasicaSalud);
//                System.out.println("Agregado a la lista la zona de salud: " + zonaBasicaSalud);
//            }
//            //System.out.println(zbs);
//
//        }
        jsonDAO.guardarJsonZBS(listaZBS);
        System.out.println("guardado en el json correctamente");
        return null;
    }

//    // DELETE
//    @DeleteMapping("/ZonaBasicaSalud/{codigo_geometria}")
//    @Operation(summary = "Borra una zona básica de salud por su Código de Geometría",
//            description = "Borra la zona básica de salud que corresponda con el Código de Geometría de la zona básica de salud introducido")
//    @ResponseStatus(HttpStatus.OK)
//    public void zBS_elim(@PathVariable("codigo_geometria") String codigo) {
//        // Creamos una nueva instancia de Zona Básica de Salud donde se guardará la ZBS a Borrar
//        ZonaBasicaSalud zbsABorrar = new ZonaBasicaSalud();
//        for (ZonaBasicaSalud zbs : LectorJson.datosZBS.data) {
//            //Si el Código de geometría de la ZBS es igual al código introducido y existe, se guarda en zbsABorrar
//            if (zbs.getCodigo_geometria().equals(codigo)) {
//                zbsABorrar = zbs;
//            }
//        }
////        if (zbsABorrar == null || zbsABorrar.getCodigo_geometria().equals("0")) {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
//
//        try {
//            // Borra la Zona básica de salud
//            LectorJson.datosZBS.data.remove(zbsABorrar);
//        } catch (Exception e){
//            System.err.println("No se pudo borrar la Zona Básica de Salud");
//            throw e;
//        }
//        //return new ResponseEntity<>(HttpStatus.OK);
//
//    }
}
