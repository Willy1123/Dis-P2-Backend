package com.dis.javalovers.gestionZonasBasicasSaludAPI.dao;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.Config;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.DataZBS_60;
import com.dis.javalovers.gestionZonasBasicasSaludAPI.model.ZonaBasicaSalud_60;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class JsonDAO_60 {
    private static JsonDAO_60 jsonDAO_60;

    // Para comprobar si hay instancias creadas (Método Singleton)
    public static JsonDAO_60 getInstance() {
        if (jsonDAO_60 == null) {
            jsonDAO_60 = new JsonDAO_60();
        }
        return jsonDAO_60;
    }

    public List<ZonaBasicaSalud_60> leerJsonZBS_60() {

        try (
                // leermo el fichero que le pasemos y lo carga en un reader
                BufferedReader readerZBS_60 = new BufferedReader(new FileReader(Config.RUTA_ZBSALUD_MAYORES60))
        ) {

            // convertimos el reader en un objeto JSON
            DataZBS_60 listaZBS_60 = new Gson().fromJson(readerZBS_60, DataZBS_60.class);
            // devuelve la lista de "data" del json
            System.out.println("Elementos de la lista: " + listaZBS_60.getData().toArray().length);
            return listaZBS_60.getData();
        } catch (Exception ex) {
            System.err.println("Error al leer el fichero!");
            ex.printStackTrace();
            // si no se ha leído nada, devuelve un array vacío
            return null;
        }
    }

    public void guardarJsonZBS_60(List<ZonaBasicaSalud_60> nuevaZBS_60) {
        try (
                BufferedWriter output = new BufferedWriter(new FileWriter(Config.RUTA_ZBSALUD_MAYORES60, false))
        ) {
            DataZBS_60 dataZBS = new DataZBS_60(nuevaZBS_60);
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