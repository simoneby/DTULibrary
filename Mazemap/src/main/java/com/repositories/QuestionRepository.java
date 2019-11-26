package com.repositories;

import com.controllers.SurveyController;
import com.models.Question;
import com.models.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {
	Set<Question> findBySurvey(Survey survey);
}
