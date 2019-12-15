package com.repositories;

import com.controllers.SurveyController;
import com.models.Survey;
import com.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

//@Author s191772, s154666
@Repository
public interface SurveyRepository extends CrudRepository<Survey,Integer> {
    Set<Survey> findByIdGreaterThanEqual(int id);
    Set<Survey> findByCreator(User user);
    Survey findById(int id);

}
