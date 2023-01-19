
package com.dis.javalovers.gestionZonasBasicasSaludAPI.controller;

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

    // GET, lista completa
    @GetMapping("/ZonasBasicasSaludMayores60")
    @Operation(summary = "Devuelve una lista de todas las Zonas Básicas de Salud para mayores de 60",
            description = "Devuelve los datos pertinentes de las zonas básicas de salud. No ejecutar en la web de Swagger ya que crashea la web")
    public List<ZonaBasicaSalud_60> zBSalud60_GET() {
        return jsonDAO_60.leerJsonZBS_60();
    }

    // GET, busqueda por codigo_geometria
    @GetMapping("/ZonasBasicasSaludMayores60/{codigo_geometria}")
    @Operation(summary = "Devuelve Zonas Básicas de Salud por Código de Geometría",
            description = "Devuelve los datos pertinentes de la zona básica de salud seleccionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK!"), @ApiResponse(responseCode = "404", description = "Not OK! - Not Found")
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
    @PostMapping(value = "/ZonaBasicaSaludMayores_60", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade una nueva Zona Básica de Salud para Mayores de 60 al Sistema",
            description = "Añade una nueva zona básica de salud para mayores de 60 siempre que los datos introducidos sean correctos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ZonaBasicaSalud_60>> addZBS(@RequestBody boolean nuevoCampo, int indice, ZonaBasicaSalud_60 datosNuevaZBS) {

        // creamos una lista tipo ZonaBasicaSalud_60 donde guardamos el json leído.
        List<ZonaBasicaSalud_60> listaZBS = jsonDAO_60.leerJsonZBS_60();
        // creamos un elemento de tipo ZonaBasicaSalud_60 para guardar el nuevo
        ZonaBasicaSalud_60 nuevaZBS = new ZonaBasicaSalud_60();
        // actualizamos los datos del objeto de la lista seleccionado
        //nuevaZBS.setCodigo_geometria(datosNuevaZBS.getCodigo_geometria());
        nuevaZBS.setZona_basica_salud(datosNuevaZBS.getZona_basica_salud());
        nuevaZBS.setCasos_confirmados_P60mas_ultimos_14dias(datosNuevaZBS.getTasa_incidencia_acumulada_P60mas_ultimos_14dias());
        nuevaZBS.setCasos_confirmados_P60mas_ultimos_14dias(datosNuevaZBS.getCasos_confirmados_P60mas_ultimos_14dias());
        nuevaZBS.setFecha_informe(datosNuevaZBS.getFecha_informe());
        boolean found = false;
        int max_cod_geo = 0;

        for (ZonaBasicaSalud_60 zn : listaZBS) {
            if (zn.getZona_basica_salud().equals(nuevaZBS.getZona_basica_salud()) ) {
                nuevaZBS.setCodigo_geometria(zn.getCodigo_geometria());
                found = true;
                break; // Si hemos encontrado una coincidencia terminamos el for
            }
            if (Integer.parseInt(zn.getCodigo_geometria()) > max_cod_geo) {
                max_cod_geo = Integer.parseInt(zn.getCodigo_geometria());
            }
        }

        // Si no hemos encontrado ninguna coincidencia, cogeremos el codigo de geometria mayor y le sumaremos 1 para hacer el nuevo elemento el final
        if(!found) {
            nuevaZBS.setCodigo_geometria(String.valueOf(max_cod_geo+1));
        }

        if (nuevoCampo) {
            // añadimos el nuevo objeto a la lista si se dio al boton de crear en el for
            listaZBS.add(nuevaZBS);
        }

        // en caso contrario, simplemente estamos modicicando un elemento existente
        else {
            // por lo que modificamos los datos del elemento seleccionado
            listaZBS.set(indice, nuevaZBS);
        }

        // guardamos el json con los cambios realizados
        jsonDAO_60.guardarJsonZBS_60(listaZBS);
        System.out.println("Guardado en el json correctamente");
        // si no hay ningún problema, devuelve un OK
        return new ResponseEntity<>(listaZBS, HttpStatus.OK);
    }

    // Delete de 1 elemento de la lista
    @DeleteMapping("/ZBS_Mayores60/{elemento}")
    @Operation(summary = "Borra un elemento por su posición en la lista", description = "Borra el elemento que corresponda con la posición del elemento en la lista")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ZonaBasicaSalud_60>> zbs_Delete(@PathVariable("elemento") int indice) {
        List<ZonaBasicaSalud_60> listaZBS = jsonDAO_60.leerJsonZBS_60();

        // guardamos el objeto a borrar para poder mostrarlo por consola posteriormente
        ZonaBasicaSalud_60 zBS_Delete = listaZBS.get(indice);

        // eliminamos el objeto correspondiente a la posición de la lista introducido
        listaZBS.remove(indice);
        // mostramos por consola el objeto borrado
        System.out.println("Borrado el elemento: " + indice + "\n" +
                "Código de Geometría: \"" + zBS_Delete.getCodigo_geometria() + "\"\n" +
                "Zona Básica de Salud: \"" + zBS_Delete.getZona_basica_salud() + "\"\n" +
                "Tasa Incidencia Acc (Ultimos 14 días): \"" + zBS_Delete.getTasa_incidencia_acumulada_P60mas_ultimos_14dias() + "\"\n" +
                "Casos Confirmados (Últimos 14 días): \"" + zBS_Delete.getCasos_confirmados_P60mas_ultimos_14dias() + "\"\n" +
                "Fecha de Informe: \"" + zBS_Delete.getFecha_informe() + "\"\n"
        );
        // guardamos la lista modificada
        jsonDAO_60.guardarJsonZBS_60(listaZBS);
        // devolvemos un nuevo ResponseEntity de la lista y un estado de CREATED
        return new ResponseEntity<>(listaZBS, HttpStatus.CREATED);
    }

    // Delete todos los elementos con el mismo código
    @DeleteMapping("/ZonaBS_Mayores/{codigo_geometria}")
    @Operation(summary = "Borra Elementos por el Cód de Geometría", description = "Borra elemento que corresponda con el código de geometría introducido")
    public ResponseEntity<List<ZonaBasicaSalud_60>> zbs_Delete(@PathVariable("codigo_geometria") String codigo) {
        // guardamos en una lista todos los elementos del json
        List<ZonaBasicaSalud_60> listaZBS = jsonDAO_60.leerJsonZBS_60();
        // copiamos la lista anterior para compararla más adelante
        List<ZonaBasicaSalud_60> copiaLista = new ArrayList<>(listaZBS);

        int indice;
        int numElementosBorrados = 0;

        // borramos todos los elementos de la lista cuyo código de geometría sea igual al introducido
        listaZBS.removeIf(o -> o.getCodigo_geometria().equals(codigo));

        // Mostrar elementos eliminados por consola -> Puede tardar si hay muchos elementos
        for (ZonaBasicaSalud_60 zona : copiaLista) {
            // si el elmento actual no está en la listaZBS, quiere decir que se borró por tanto lo mostramos por consola
            if (!listaZBS.contains(zona)) {
                indice = copiaLista.indexOf(zona);
                System.out.println("Borrado el elemento: " + indice + "\n" +
                        "Código de Geometría: \"" + zona.getCodigo_geometria() + "\"\n" +
                        "Zona Básica de Salud: \"" + zona.getZona_basica_salud() + "\"\n" +
                        "Tasa Incidencia Acc (Ultimos 14 días): \"" + zona.getTasa_incidencia_acumulada_P60mas_ultimos_14dias() + "\"\n" +
                        "Casos Confirmados (Últimos 14 días): \"" + zona.getCasos_confirmados_P60mas_ultimos_14dias() + "\"\n" +
                        "Fecha de Informe: \"" + zona.getFecha_informe() + "\"\n"
                );
                numElementosBorrados++;
            }
        }

        // guardamos la lista modificada
        jsonDAO_60.guardarJsonZBS_60(listaZBS);
        System.out.println("Número de elentos borrados: " + numElementosBorrados);
        System.out.println("Elementos totales tras la eliminación: " +listaZBS.size());
        // devolvemos un nuevo ResponseEntity de la lista y un estado de CREATED
        return new ResponseEntity<>(listaZBS, HttpStatus.CREATED);

    }

}
