package com.repositories;
import com.models.*;
import java.util.List;
import org.springframework.data.repository.*;

public interface FilteredUserRepository extends UserRepository{
	  List<User> findUsersByEmail(String email);
	  User findUserByEmail(String email);
	  User findUserByStudentNumber(String studentnr);
}
