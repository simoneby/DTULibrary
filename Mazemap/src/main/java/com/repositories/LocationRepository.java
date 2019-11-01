package com.repositories;
import com.models.*;
import org.springframework.data.repository.CrudRepository;
public interface LocationRepository extends CrudRepository<LocationOfUsers,Integer> {
LocationOfUsers findLocationOfUsersByUser(User user);
}
