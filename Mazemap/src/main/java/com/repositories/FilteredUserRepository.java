package com.repositories;
import com.models.*;
import java.util.List;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

//@Author s192671, s154666
@Repository
public interface FilteredUserRepository extends CrudRepository<User, Integer>{
	  List<User> findUsersByEmail(String email);
	  User findUserByEmail(String email);
	  User findUserByStudentnr(String studentnr);
	  User findById(int id);
}
