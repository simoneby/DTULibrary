package com.repositories;

import com.controllers.SurveyController;
import com.models.Question;
import com.models.Survey;
import com.models.User;
import com.repositories.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface SurveyRepository extends CrudRepository<Survey,Integer> {


    Set<Survey> findByIdGreaterThanEqual(int id);
    Set<Survey> findByCreator(User user);
    Survey findById(int id);
    Survey findByName(String string);

}
