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
	  // @Modifying
	  // @Query("update User u set u.name = ?1 where u.studentnr = ?2")
	  int setFixedNameFor(String name, String studentnr);
}
