package com.repositories;

import com.models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {
	Set<Question> findBySurveyId(int surveyId);
}
