package com.repositories;

import com.controllers.SurveyController;
import com.models.SurveyAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyAnswerRepository extends CrudRepository<SurveyAnswer,Integer> {
}
