package com.repositories;
import com.models.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<LocationOfUsers,Integer> {
    LocationOfUsers findLocationOfUsersByUser(User user);
}
