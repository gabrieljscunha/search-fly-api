package br.com.omegabotz;

import br.com.omegabotz.model.Airport;
import br.com.omegabotz.model.Flight;
import br.com.omegabotz.repositories.AirPortsRepository;
import br.com.omegabotz.repositories.FlightRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class ApplicationListener {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Value(("${app.load.data.99.path}"))
    private String dataFile99Path;

    @Value(("${app.load.data.uberair.path}"))
    private String dataFileUberAirPath;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirPortsRepository airPortsRepository;

    @EventListener
    public void loadResources(ContextRefreshedEvent evt) throws IOException, URISyntaxException {
        log.info("[Loader]: Load data base resources into Database");

        load99Resource();
        loadUberResources();

        long count = flightRepository.count();
        log.info("[Loader]: Load {} registries in database", count);

        // load table airport
        List<Airport> airports = StreamSupport.stream(flightRepository.findAll().spliterator(), false)
                .map(item -> Airport.builder().name(item.getAirPortSource()).build())
                .distinct()
                .collect(Collectors.toList());

        airPortsRepository.saveAll(airports);

    }

    private void loadUberResources() throws URISyntaxException, IOException {
        Path path = Paths.get(dataFileUberAirPath);
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(Flight.class);

        try(Reader reader = Files.newBufferedReader(path)){
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withSkipLines(0)
                    .withSeparator(',')
                    .withType(Flight.class)
                    .withMappingStrategy(ms)
                    .build();

            List flightsUber = cb.parse();
            log.info("[Loader]: Exists {} flights in UbearAir file", flightsUber.size());
            flightsUber.stream()
                    .forEach(f -> log.info("[Loader]: Data {}", f));

            List<Flight> flights = new ArrayList<>();
            for (Object obj: flightsUber) {
                Flight flight = (Flight)obj;
                flight.setOperator("UberAir");
                flights.add(flight);

            }
            flightRepository.saveAll(flights);
        }
    }

    private void load99Resource() throws IOException {
        System.out.println("Load 99 Resources");
        List<Flight> flights99 = jsonToObject();

        log.info("[Loader]: Exists {} flights in 99 file", flights99.size());
        List<Flight> flights = flights99.stream()
                .map(flight -> {
                    log.info("[Loader]: Data {}", flight);
                    flight.setOperator("99Planes");
                    return flight;
                }).collect(Collectors.toList());

        flightRepository.saveAll(flights);
    }

    private List<Flight> jsonToObject() {
        try {
            return mapper.readValue(jsonFile(), new TypeReference<List<Flight>>() {
            });
        } catch (IOException e) {
            log.error("Error on load data", e);
        }
        return Collections.emptyList();
    }

    private File jsonFile() {
        return new File(dataFile99Path);
    }

}
