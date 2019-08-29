package br.com.omegabotz.services;

import br.com.omegabotz.model.Flight;
import br.com.omegabotz.model.FlightRequestFilter;
import br.com.omegabotz.model.dto.FlightDTO;
import br.com.omegabotz.repositories.AirPortsRepository;
import br.com.omegabotz.repositories.FlightRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.impl.Jdk14Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class FlightService {

    private FlightRepository flightRepository;
    private AirPortsRepository airPortsRepository;

    private static final Integer MAX_TIME = 43200;

    @Autowired
    public FlightService(FlightRepository flightRepository, AirPortsRepository airPortsRepository){
        this.flightRepository = flightRepository;
        this.airPortsRepository = airPortsRepository;
    }

    @Cacheable("flights")
    public List<FlightDTO> getFlights(FlightRequestFilter flightQuery){
        List<Flight> flights = flightRepository.findByAirPortSourceAndAirPortTargetAndDate(
                flightQuery.getAirPortSource(), flightQuery.getAirPortTarget(), flightQuery.getDate());

        String exitStr = String.format("%sT%s:00", flightQuery.getDate(), flights.stream()
                .map(Flight::getExit)
                .limit(1)
                .collect(Collectors.joining(",")));
        String arrivalStr = String.format("%sT%s:00", flightQuery.getDate(), flights.stream()
                .map(Flight::getArrival)
                .limit(1)
                .distinct()
                .collect(Collectors.joining(",")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime exitTime = LocalDateTime.parse(exitStr);
        LocalDateTime arrivalTime = LocalDateTime.parse(arrivalStr);

        if (Duration.between(exitTime, arrivalTime).getSeconds() <= MAX_TIME)
        {
            return Arrays.asList(FlightDTO
                    .builder()
                    .airPortSource(flightQuery.getAirPortSource())
                    .airPortTarget(flightQuery.getAirPortTarget())
                    .exit(exitTime.format(formatter))
                    .arrival(arrivalTime.format(formatter))
                    .flights(flights)
                    .build());
        }else{
            // Horario entre os voos deve ser inferior à 12 horas
            log.info("[Warning]: Time between flights must be less than 12 hours");
            return null;
        }
    }

}
