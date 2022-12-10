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
        return LectorJson.datos.data;
    }

    @GetMapping("/ZonaBasicaSalud/{codigo_geometria}")
    @Operation(summary = "Devuelve Zonas Básicas de Salud por Código de Geometría",
            description = "Devuelve los datos pertinentes de la zona básica de salud seleccionada")
    @ResponseStatus(HttpStatus.OK)
    public static ZonaBasicaSalud zBSaludId_GET(@PathVariable("codigo_geometria") String codigo) {
        // devuelve los datos correspondientes al código de geometría introducido;
        for (ZonaBasicaSalud zbs : LectorJson.datos.data) {
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
        LectorJson.datos.data.add(nuevaZBS);
        return new ResponseEntity<>(LectorJson.datos.data, HttpStatus.CREATED);

    }
}
