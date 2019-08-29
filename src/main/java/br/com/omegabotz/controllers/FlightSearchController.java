package br.com.omegabotz.controllers;

import br.com.omegabotz.model.FlightRequestFilter;
import br.com.omegabotz.model.dto.FlightDTO;
import br.com.omegabotz.services.FlightService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class FlightSearchController {

    private FlightService flightService;

    @Autowired
    public FlightSearchController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public List<FlightDTO> getFlightBySourceAndTarget(@RequestParam("source") String airPortSource, @RequestParam("target") String airPortTarget, String date) {
        log.debug("[FlightSearchController]: Received request. Params -> Source:[{}]. Target:[{}]. Date:[{}]", airPortSource, airPortTarget, date);
        return flightService.getFlights(FlightRequestFilter.builder()
                .airPortSource(airPortSource)
                .airPortTarget(airPortTarget)
                .date(date)
                .build());

    }

}
