package br.com.omegabotz.controllers;

import br.com.omegabotz.model.Airport;
import br.com.omegabotz.model.dto.AirPortDTO;
import br.com.omegabotz.services.AirPortsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class AirPortSearchController {

    private AirPortsService airPortsService;

    @Autowired
    public AirPortSearchController(AirPortsService airPortsService) {
        this.airPortsService = airPortsService;
    }

    @GetMapping("/airports")
    public List<AirPortDTO> getFlightBySourceAndTarget() {
        return airPortsService.findAll().map(this::toDTO).collect(Collectors.toList());
    }

    private AirPortDTO toDTO(Airport airport) {
        return AirPortDTO.builder()
                .airport(airport.getName())
                .build();
    }

}
