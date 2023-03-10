package com.dis.javalovers.gestionZonasBasicasSaludAPI.dao;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.Config;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.DataZBS;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.ZonaBasicaSalud;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonDAO extends ArrayList<ZonaBasicaSalud> {
    private static JsonDAO jsonDAO;

    // Para comprobar si hay instancias creadas (Método Singleton)
    public static JsonDAO getInstance() {
        if (jsonDAO == null) {
            jsonDAO = new JsonDAO();
        }
        return jsonDAO;
    }

    public List<ZonaBasicaSalud> leerJsonZBS() {

        try (
                // leermo el fichero que le pasemos dentro de "resources" y lo carga en un reader
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(Config.RUTA_ZBSALUD))))
        ) {

            // convertimos el reader en un objeto JSON
            DataZBS listaZBS = new Gson().fromJson(reader, new TypeToken<DataZBS>()
            {}.getType());

            // devuelve la lista de "data" del json
            System.out.println("Elementos de la lista: " + listaZBS.getData().toArray().length);
            return listaZBS.getData();
        } catch (Exception ex) {
            System.err.println("Error al leer el fichero!");
            ex.printStackTrace();
            // si no se ha leído nada, devuelve un array vacío
            return null;
        }
    }

    public void guardarJsonZBS(List<ZonaBasicaSalud> nuevaZBS) {
        try (
                BufferedWriter output = new BufferedWriter(new FileWriter(Config.RUTA_ZBSALUD, false))
        ) {
            DataZBS dataZBS = new DataZBS(nuevaZBS);
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(dataZBS, output);

        } catch (IOException e) {
            System.err.println("Fallo al escribir en el Json ZonasBásicas Salud");
            throw new RuntimeException(e);
        }


    }
}
