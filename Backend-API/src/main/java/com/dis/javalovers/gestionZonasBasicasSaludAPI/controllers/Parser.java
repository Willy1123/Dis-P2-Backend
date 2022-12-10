package com.dis.javalovers.gestionZonasBasicasSaludAPI.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.gson.Gson;


import java.io.FileNotFoundException;
import java.io.FileReader;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Parser {
    Gson gson = new Gson();
    DatosZBSalud datos = new DatosZBSalud();

    public DatosZBSalud ExtraerDatos(String direccion) {
        try {
            // creamos el objeto Java en función de los datos recividos por el Json
            System.out.println("antes de entrar al parser");
            datos = gson.fromJson(new FileReader(direccion), DatosZBSalud.class);
            System.out.println("entramos en el parser");
        } catch (FileNotFoundException e) {
            System.err.println("NOOOO entramos en el parser");
        }

        return datos;
    }


}
