package com.repositories;
import com.models.*;
import org.springframework.data.repository.CrudRepository;
public interface EventRepository extends CrudRepository<Event,Integer> {

}
