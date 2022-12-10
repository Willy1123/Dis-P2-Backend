package com.dis.javalovers.gestionZonasBasicasSaludAPI.controllers;

import com.dis.javalovers.gestionZonasBasicasSaludAPI.models.ZonaBasicaSalud;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
// Clase que guarda los datos recibidos en un ArrayList
public class DatosZBSalud {
    public ArrayList<ZonaBasicaSalud> data;
}
