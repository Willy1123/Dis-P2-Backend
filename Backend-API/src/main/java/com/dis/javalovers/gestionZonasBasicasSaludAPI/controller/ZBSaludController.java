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

    @GetMapping("/ZonasBasicasSalud/{codigo_geometria}")
    @Operation(summary = "Devuelve Zonas Básicas de Salud por Código de Geometría",
            description = "Devuelve los datos pertinentes de la zona básica de salud seleccionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK!"),
            @ApiResponse(responseCode = "404", description = "Not OK! - Not Found")
    })
    public static ResponseEntity<List<ZonaBasicaSalud>> zBSaludId_GET(@PathVariable("codigo_geometria") String codigo) {
        try {
            // creamos una lista tipo ZonaBasicaSalud donde guardamos el json leído.
            List<ZonaBasicaSalud> listaZBS = new JsonDAO().leerJsonZBS();
            // creamos otra  lista para ir guardando los elementos que coincidan con el código de geometría introducido
            List<ZonaBasicaSalud> encontrado = new ArrayList<>();
            // recorremos la listaZBS en busca de los elementos que contengas el código de geometría introducido
            // y si lo encuentra los va agregando a la lista de encontrado
            for (ZonaBasicaSalud zbs : listaZBS) {
                if (zbs.getCodigo_geometria().equals(codigo)) {
                    encontrado.add(zbs);
                }
            }
            // si la lista de encontrado no está vacía, significa que hay resultados y devuelve un OK
            if (!encontrado.isEmpty()) {
                return new ResponseEntity<>(encontrado, HttpStatus.OK);
            }
            // en caso contrario devuelve un Not Found
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST (ADD/UPDATE)
    @PostMapping(value = "/ZonaBasicaSalud",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Añade o modifica una Zona Básica de Salud al Sistema",
            description = "Añade una nueva zona básica de salud si \"nuevoCampo\" es true (se dio al botón de crear nuevo en el front)" +
                    "en el caso de que \"nuevoCampo\" sea false significa que simplemente estamos modificando un elemento existente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ZonaBasicaSalud>> addZBS(@RequestBody boolean nuevoCampo, int indice, ZonaBasicaSalud getDatosZBS) {

        // creamos una lista tipo ZonaBasicaSalud donde guardamos el json leído.
        List<ZonaBasicaSalud> listaZBS = jsonDAO.leerJsonZBS();
        // instanciamos el objeto nuevaZBS donde guardaremos los nuevos datos introducidos.
        ZonaBasicaSalud nuevaZBS = new ZonaBasicaSalud();

        // añadimos los datos del objeto que vamos a crear
        nuevaZBS.setZona_basica_salud(getDatosZBS.getZona_basica_salud());
        nuevaZBS.setTasa_incidencia_acumulada_ultimos_14dias(getDatosZBS.getTasa_incidencia_acumulada_ultimos_14dias());
        nuevaZBS.setTasa_incidencia_acumulada_total(getDatosZBS.getTasa_incidencia_acumulada_total());
        nuevaZBS.setCasos_confirmados_totales(getDatosZBS.getCasos_confirmados_totales());
        nuevaZBS.setCasos_confirmados_ultimos_14dias(getDatosZBS.getCasos_confirmados_ultimos_14dias());
        nuevaZBS.setFecha_informe(getDatosZBS.getFecha_informe());

        // si la zona básica de salud introducida existe dentro de la lista, entonces el nuevo cód de geometría será
        // igual al correspondiente de dicha zona básica de salud
        if (listaZBS.stream().anyMatch(o -> o.getZona_basica_salud().equals(getDatosZBS.getZona_basica_salud()))) {

            // para ello usamos el método "filter" del objeto "stream" (de tipo zonasBasicasSalud) para filtrar el elemento
            // que cumpla que el valor introducido de zona_basica_salud existe en la listaZBS y al encontrarlo usamos
            // "findFirst" para encontrar el valor del elemento Codigo_geometría correspondiente al mismo objeto que el de
            // Zona_basica_salud
            Optional<String> id = listaZBS.stream()
                    .filter(o -> o.getZona_basica_salud().equals(getDatosZBS.getZona_basica_salud()))
                    .map(ZonaBasicaSalud::getCodigo_geometria)
                    .findFirst();
            // antes de que accedamos al valor de "id" tenemos que comprobar si el objeto Optional tiene un valor o no,
            // para ello usamos el método isPresent(). Tras ello simplemente añadimos el valor del codigo_geometría que
            // tendrá el nuevo objeto zonasBasicasSalud.
            if (id.isPresent()) {
                nuevaZBS.setCodigo_geometria(id.get());
            }
        }

        // en caso contrario miramos el código de geometría más alto registrado y le sumamos 1, y el valor de este será
        // el código de geometría para todas las zonas básicas de salud con el mismo nombre.
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

        // si es un nuevo campo (se le dio al botón de crear en el front)
        if (nuevoCampo) {
            // añadimos el nuevo objeto a la lista
            listaZBS.add(nuevaZBS);
        }

        // en caso contrario, simplemente estamos modicicando un elemento existente
        else {
            // por lo que modificamos los datos del elemento seleccionado
            listaZBS.set(indice, nuevaZBS);
        }
        // guardamos el json con los cambios realizados
        jsonDAO.guardarJsonZBS(listaZBS);
        System.out.println("guardado el registro " + listaZBS.size() + " correctamente.");

        // si no hay ningún problema, devuelve un OK
        return new ResponseEntity<>(listaZBS, HttpStatus.OK);
    }

    // Delete de 1 elemento de la lista
    @DeleteMapping("/ZBS/{elemento}")
    @Operation(summary = "Borra un elemento por su posición en la lista", description = "Borra el elemento que corresponda con la posición del elemento en la lista")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ZonaBasicaSalud>> zbs_Delete(@PathVariable("elemento") int indice) {
        List<ZonaBasicaSalud> listaZBS = jsonDAO.leerJsonZBS();

        // guardamos el objeto a borrar para poder mostrarlo por consola posteriormente
        ZonaBasicaSalud zBS_Delete = listaZBS.get(indice);

        // eliminamos el objeto correspondiente a la posición de la lista introducido
        listaZBS.remove(indice);
        // mostramos por consola el objeto borrado
        System.out.println("Borrado el elemento: " + indice + "\n" +
                "Código de Geometría: \"" + zBS_Delete.getCodigo_geometria() + "\"\n" +
                "Zona Básica de Salud: \"" + zBS_Delete.getZona_basica_salud() + "\"\n" +
                "Tasa Incidencia Acc (Ultimos 14 días): \"" + zBS_Delete.getTasa_incidencia_acumulada_ultimos_14dias() + "\"\n" +
                "Tasa Incidencia Acc (Total): \"" + zBS_Delete.getTasa_incidencia_acumulada_total() + "\"\n" +
                "Casos Confirmados (Totales): \"" + zBS_Delete.getCasos_confirmados_totales() + "\"\n" +
                "Casos Confirmados (Últimos 14 días): \"" + zBS_Delete.getCasos_confirmados_ultimos_14dias() + "\"\n" +
                "Fecha de Informe: \"" + zBS_Delete.getFecha_informe() + "\"\n"
        );
        // guardamos la lista modificada
        jsonDAO.guardarJsonZBS(listaZBS);
        // devolvemos un nuevo ResponseEntity de la lista y un estado de CREATED
        return new ResponseEntity<>(listaZBS, HttpStatus.CREATED);

    }

}