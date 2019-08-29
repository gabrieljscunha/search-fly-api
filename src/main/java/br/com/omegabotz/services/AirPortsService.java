package br.com.omegabotz.services;

import br.com.omegabotz.model.Airport;
import br.com.omegabotz.repositories.AirPortsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AirPortsService {

    @Autowired
    private AirPortsRepository repository;

    public Stream<Airport> findAll(){
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

    public void saveAll(List<Airport> airports){
        repository.saveAll(airports);
    }

}
