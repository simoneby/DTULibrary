package com.repositories;

import com.models.QuestionAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer,Integer> {

}
