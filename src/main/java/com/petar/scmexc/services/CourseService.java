package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Course;
import com.marco.scmexc.models.exceptions.CourseNotFoundException;
import com.marco.scmexc.models.requests.CourseRequest;
import org.springframework.stereotype.Service;
import com.marco.scmexc.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(String name) {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(()->new CourseNotFoundException(id));
    }

    public Course addNewCourse(CourseRequest request) {
        Course course = request.id == null ? new Course() : getCourseById(request.id);
        course.setName(request.name);
        course.setCode(request.code);
        course.setDescription(request.description);
        if(course.getId() == null) {
            course.setDateCreated(LocalDate.now());
        }
        course.setDateLastModified(LocalDate.now());
        return courseRepository.save(course);
    }

    public List<Course> findAllByIdIn(List<Long> ids) {
        return courseRepository.findAllByIdIn(ids);
    }

    public void saveAll(List<Course> courses) {
        courseRepository.saveAll(courses);
    }
}
