package com.marco.scmexc.web;

import com.marco.scmexc.models.domain.Course;
import com.marco.scmexc.models.requests.CourseRequest;
import com.marco.scmexc.models.response.CourseResponse;
import com.marco.scmexc.models.response.SelectOptionResponse;

import java.util.List;

public interface CourseMapper {

    List<CourseResponse> getAllCoursesByName(String name);

    CourseResponse getCourseById(Long id);

    CourseResponse addNewCourse(CourseRequest request);

    List<SelectOptionResponse> getAllCoursesOption();
}
