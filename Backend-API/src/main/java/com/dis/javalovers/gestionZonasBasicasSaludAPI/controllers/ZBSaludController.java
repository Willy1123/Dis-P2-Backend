package com.dis.javalovers.gestionZonasBasicasSaludAPI.controllers;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.models.ZonaBasicaSalud;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ZBSaludController {

    // GET
    @GetMapping("/ZonasBasicasSalud")
    @Operation(summary = "Devuelve una lista de todas las Zonas Básicas de Salud",
            description = "Devuelve los datos pertinentes de las zonas básicas de salud")
    @ResponseStatus(HttpStatus.OK)
    public static ArrayList<ZonaBasicaSalud> zBSalud_GET() {
//        ArrayList<ZonasBasicasSalud> listaZBS = new LectorJson().leerJsonZBS("./src/main/resources/Json/Covid19-TIA_ZonasBásicasSalud.json");
//        return listaZBS;
        return LectorJson.datos.data;
    }
}
