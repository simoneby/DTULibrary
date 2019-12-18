package com.repositories;

import com.controllers.SurveyController;
import com.models.Question;
import com.models.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

//@Author s191772, s154666
@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {
	Set<Question> findBySurvey(Survey survey);
	Set<Question> findByIdGreaterThanEqual(int id);
}
