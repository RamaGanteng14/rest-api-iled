package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findByScheduleId(String scheduleId);
    List<Question> findByTraineeId(String traineeId);
}
