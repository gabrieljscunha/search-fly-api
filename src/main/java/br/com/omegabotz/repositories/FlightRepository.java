package br.com.omegabotz.repositories;

import br.com.omegabotz.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {

    List<Flight> findByAirPortSourceAndAirPortTargetAndDate(String source, String target, String date);

}
