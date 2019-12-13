package com.repositories;
import com.models.*;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event,Integer> {
	List<Event> findEventsByCreator(User Creator);
}
