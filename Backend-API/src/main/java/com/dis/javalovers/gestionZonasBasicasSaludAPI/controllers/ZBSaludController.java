package com.dis.javalovers.gestionZonasBasicasSaludAPI.controllers;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.models.ZonaBasicaSalud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Tag(description = "Provee los datos de las Zonas Básicas de Salud", name = "Controlador Zonas Básicas de Salud")
public class ZBSaludController {

    // GET
    @GetMapping("/ZonasBasicasSalud")
    @Operation(summary = "Devuelve una lista de todas las Zonas Básicas de Salud",
            description = "Devuelve los datos pertinentes de las zonas básicas de salud. No ejecutar en la web de Swagger ya que crashea la web")
    @ResponseStatus(HttpStatus.OK)
    public static ArrayList<ZonaBasicaSalud> zBSalud_GET() {
//        ArrayList<ZonasBasicasSalud> listaZBS = new LectorJson().leerJsonZBS("./src/main/resources/Json/Covid19-TIA_ZonasBásicasSalud.json");
//        return listaZBS;
        return LectorJson.datosZBS.data;
    }

    @GetMapping("/ZonaBasicaSalud/{codigo_geometria}")
    @Operation(summary = "Devuelve Zonas Básicas de Salud por Código de Geometría",
            description = "Devuelve los datos pertinentes de la zona básica de salud seleccionada")
    @ResponseStatus(HttpStatus.OK)
    public static ZonaBasicaSalud zBSaludId_GET(@PathVariable("codigo_geometria") String codigo) {
        // devuelve los datos correspondientes al código de geometría introducido;
        for (ZonaBasicaSalud zbs : LectorJson.datosZBS.data) {
            if (zbs.getCodigo_geometria().equals(codigo)) {
                return zbs;
            }
        }
        return null;
    }

    // POST
    @PostMapping(value = "/ZonaBasicaSalud",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade una nueva Zona Básica de Salud al Sistema",
            description = "Añade una nueva zona básica de salud siempre que los datos introducidos sean correctos")
    @ResponseStatus(HttpStatus.CREATED)
    public static ResponseEntity<ArrayList<ZonaBasicaSalud>> addZBS(@RequestBody ZonaBasicaSalud nuevaZBS) {
        LectorJson.datosZBS.data.add(nuevaZBS);
        return new ResponseEntity<>(LectorJson.datosZBS.data, HttpStatus.CREATED);

    }

    // DELETE
    @DeleteMapping("/ZonaBasicaSalud/{codigo_geometria}")
    @Operation(summary = "Borra una zona básica de salud por su Código de Geometría",
            description = "Borra la zona básica de salud que corresponda con el Código de Geometría de la zona básica de salud introducido")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity zBS_elim(@PathVariable("codigo_geometria") String codigo) {
        // Creamos una nueva instancia de Zona Básica de Salud donde se guardará la ZBS a Borrar
        ZonaBasicaSalud zbsABorrar = new ZonaBasicaSalud();
        for (ZonaBasicaSalud zbs : LectorJson.datosZBS.data) {
            //Si el Código de geometría de la ZBS es igual al código introducido y existe, se guarda en zbsABorrar
            if (zbs.getCodigo_geometria().equals(codigo)) {
                zbsABorrar = zbs;
            }
        }
        if (zbsABorrar == null || zbsABorrar.getCodigo_geometria().equals("0")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            // Borra la Zona básica de salud
            LectorJson.datosZBS.data.remove(zbsABorrar);
        } catch (Exception e){
            System.err.println("No se pudo borrar la Zona Básica de Salud");
            throw e;
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
