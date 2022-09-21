package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findAllByQuestion_Id(Long questionID);
}
