package br.com.omegabotz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class Flight {

    // voo
    @Id
    @JsonIgnore
    @JsonProperty("voo")
    @CsvBindByPosition(position = 0)
    private String flightId;

    // origem
    @JsonProperty("origem")
    @CsvBindByPosition(position = 1)
    private String airPortSource;

    // destino
    @JsonProperty("destino")
    @CsvBindByPosition(position = 2)
    private String airPortTarget;

    // data
    @JsonIgnore
    @JsonProperty("data_saida")
    @CsvBindByPosition(position = 3)
    private String date;

    // saida
    @JsonProperty("saida")
    @CsvBindByPosition(position = 4)
    private String exit;

    // chegada
    @JsonProperty("chegada")
    @CsvBindByPosition(position = 5)
    private String arrival;

    @JsonProperty("operadora")
    private String operator;

    // preço
    @JsonProperty("valor")
    @CsvBindByPosition(position = 6)
    private String value;

}
