package com.repositories;
import com.models.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Author s192671
@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

}
