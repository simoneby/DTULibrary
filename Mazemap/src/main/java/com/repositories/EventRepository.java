package com.repositories;
import com.models.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Author s183051
@Repository
public interface EventRepository extends CrudRepository<Event,Integer> {
	List<Event> findEventsByCreator(User Creator);
}
