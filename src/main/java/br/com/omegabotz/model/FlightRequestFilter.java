package br.com.omegabotz.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Builder
@EqualsAndHashCode
public @Value class FlightRequestFilter {

    private String airPortSource;
    private String airPortTarget;
    private String date;

}
