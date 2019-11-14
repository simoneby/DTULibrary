package com.repositories;
import com.models.*;
import java.util.List;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface FilteredUserRepository extends UserRepository{
	  List<User> findUsersByEmail(String email);
	  User findUserByEmail(String email);
	  User findUserByStudentnr(String studentnr);
	  void setName(String name);
}
