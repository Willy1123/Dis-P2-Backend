package com.dis.javalovers.gestionZonasBasicasSaludAPI.controllers;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.Config;

public class LectorJson {

    static DatosZBSalud datos = new DatosZBSalud();

    /* Actualizamos los datos del objeto datos, y al ser estática no hay que instanciar la clase */
    public static void Update() {
        try {
        Parser parser = new Parser();
        //Parseamos los datos del json a objetos java
        System.out.println("antes de leer el json");
        datos = parser.ExtraerDatos(Config.RUTA_ZBSALUD);
        System.out.println("datos leídos");

        } catch (Exception e) {
        System.err.println("Error al leer los datos del json");
        //return false;
        }
    }
//    public ArrayList<ZonasBasicasSalud> leerJsonZBS(String fichero) {
//        try {
//            // leermo el fichero que le pasemos y lo carga en un reader
//            Reader readerZBS = Files.newBufferedReader(Paths.get(fichero));
//            ArrayList<ZonasBasicasSalud> listaZBS = new Gson().fromJson(readerZBS, new TypeToken<ArrayList<ZonasBasicasSalud>>() {}.getType());
//            // cerramos el reader
//            readerZBS.close();
//            return listaZBS;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            // si no se ha leído nada, devuelve un array vacío
//            return new ArrayList<>();
//        }
//    }
}
