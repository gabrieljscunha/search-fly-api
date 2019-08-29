package br.com.omegabotz.repositories;

import br.com.omegabotz.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirPortsRepository extends CrudRepository<Airport, Long> {

}
