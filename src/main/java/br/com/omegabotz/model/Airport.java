package br.com.omegabotz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class Airport implements Serializable {

    @Id
    @JsonIgnore
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @JsonProperty("name")
    private String name;

}
