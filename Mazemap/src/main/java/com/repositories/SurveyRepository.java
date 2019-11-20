package com.repositories;

import com.controllers.SurveyController;
import com.models.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<Survey,Integer> {
}
