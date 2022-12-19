package com.dis.javalovers.gestionZonasBasicasSaludAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Clase para modelar los datos recibidos del JSON ZonasBasicasSalud
 */
// Cambiar todos los arrobas de abajo por @Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @EqualsAndHashCode @ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ZonaBasicaSalud {
    @JsonProperty("codigo_geometria")
    private String codigo_geometria;
    @JsonProperty("zona_basica_salud")
    private String zona_basica_salud;
    @JsonProperty("tasa_incidencia_acumulada_ultimos_14dias")
    private Number tasa_incidencia_acumulada_ultimos_14dias;
    @JsonProperty("tasa_incidencia_acumulada_total")
    private Number tasa_incidencia_acumulada_total;
    @JsonProperty("casos_confirmados_totales")
    private Number casos_confirmados_totales;
    @JsonProperty("fecha_informe")
    private String fecha_informe;

}
