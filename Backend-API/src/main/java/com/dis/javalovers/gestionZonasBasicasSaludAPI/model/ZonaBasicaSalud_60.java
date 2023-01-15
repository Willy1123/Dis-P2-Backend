package com.dis.javalovers.gestionZonasBasicasSaludAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@AllArgsConstructor @NoArgsConstructor @Getter @Setter @EqualsAndHashCode @ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ZonaBasicaSalud_60 {
        @JsonProperty("codigo_geometria")
        private String codigo_geometria;
        @JsonProperty("zona_basica_salud")
        private String zona_basica_salud;
        @JsonProperty("tasa_incidencia_acumulada_P60mas_ultimos_14dias")
        private Number tasa_incidencia_acumulada_P60mas_ultimos_14dias;
        @JsonProperty("casos_confirmados_P60mas_ultimos_14dias")
        private Number casos_confirmados_P60mas_ultimos_14dias;
        @JsonProperty("fecha_informe")
        private String fecha_informe;

}
