package com.dis.javalovers.gestionZonasBasicasSaludAPI.dao;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.Config;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.DataZBS;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.ZonaBasicaSalud;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class JsonDAO {
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
             // leermo el fichero que le pasemos y lo carga en un reader
             BufferedReader readerZBS = new BufferedReader(new FileReader(Config.RUTA_ZBSALUD))
        ) {

            // convertimos el reader en un objeto JSON
            DataZBS listaZBS = new Gson().fromJson(readerZBS, DataZBS.class);
            // devuelve la lista de "data" del json
            return listaZBS.getData();
        } catch (Exception ex) {
            System.err.println("Error al leer el fichero!");
            ex.printStackTrace();
            // si no se ha leído nada, devuelve un array vacío
            return null;
        }
    }
}
