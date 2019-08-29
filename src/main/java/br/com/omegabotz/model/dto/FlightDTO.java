package br.com.omegabotz.model.dto;

import br.com.omegabotz.model.Flight;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class FlightDTO {

    @JsonProperty("origem")
    private String airPortSource;

    @JsonProperty("destino")
    private String airPortTarget;

    @JsonProperty("saida")
    private String exit;

    @JsonProperty("chegada")
    private String arrival;

    @JsonProperty("trechos")
    private List<Flight> flights;

}
