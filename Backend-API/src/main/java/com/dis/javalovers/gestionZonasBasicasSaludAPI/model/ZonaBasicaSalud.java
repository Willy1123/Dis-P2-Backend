package com.dis.javalovers.gestionZonasBasicasSaludAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * Clase para modelar los datos recibidos del JSON ZonasBasicasSalud
 */
// Cambiar todos los arrobas de abajo por @Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @EqualsAndHashCode @ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ZonaBasicaSalud {
    @JsonProperty("Código geometria")
    private String codigo_geometria;
    @JsonProperty("Zona básica de salud")
    private String zona_basica_salud;
    @JsonProperty("Tasa de incidencia acumulada (últimos 14 dias)")
    private Number tasa_incidencia_acumulada_ultimos_14dias;
    @JsonProperty("Tasa incidencia acumulada (total)")
    private Number tasa_incidencia_acumulada_total;
    @JsonProperty("Casos confirmados totales")
    private Number casos_confirmados_totales;
    @JsonProperty("Fecha del Informe")
    private String fecha_informe;

}
