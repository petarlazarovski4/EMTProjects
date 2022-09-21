package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.Course;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.domain.SmxUser;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findAllByCourse_Id(Long id);

    // for admins
    Page<Material> findAllByTitleAndCourse_id(String title, Long courseId, Pageable pageable);

    // for basic user
    Page<Material> findAllByTitleAndCourse_idAndCreatedBy(String title, Long courseId,SmxUser user,Pageable pageable);

    // for moderator
    Page<Material> findAllByTitleAndCourse_idAndCourseIn(String title, Long courseId,List<Course> courses,Pageable pageable);

    // for admin
    Page<Material> findAllByCourse_id( Long courseId, Pageable pageable);

    // for basic user
    Page<Material> findAllByCourse_idAndCreatedBy( Long courseId,SmxUser user, Pageable pageable);

    // for moderator
    Page<Material> findAllByCourse_idAndCourseIn( Long courseId,List<Course> courses, Pageable pageable);

    // for admin
    Page<Material> findAllByTitle(String title, Pageable pageable);

    // for basic user
    Page<Material> findAllByTitleAndCreatedBy(String title, SmxUser user,Pageable pageable);

    // for moderator
    Page<Material> findAllByTitleAndCourseIn(String title, List<Course> courses, Pageable pageable);

}
