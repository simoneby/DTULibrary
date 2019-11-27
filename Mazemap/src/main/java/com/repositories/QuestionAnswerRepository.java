package com.repositories;

import com.models.QuestionAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer,Integer> {
	Set<QuestionAnswer> findByQuestionId(int question_id);
}
