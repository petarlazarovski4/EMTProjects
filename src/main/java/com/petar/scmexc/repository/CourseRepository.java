package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByName(String name);

    List<Course> findAllByIdIn(List<Long> ids);
}
