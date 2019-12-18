package com.repositories;

import com.models.QuestionAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

//@Author s191772, s154666
@Repository
public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer,Integer> {
	Set<QuestionAnswer> findByQuestionId(int question_id);
	Set<QuestionAnswer> findByIdGreaterThanEqual(int id);
}
