package com.marco.scmexc.api;

import com.marco.scmexc.models.requests.CourseRequest;
import com.marco.scmexc.models.response.CourseResponse;
import com.marco.scmexc.models.response.SelectOptionResponse;
import com.marco.scmexc.web.CourseMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseMapper mapper;

    public CourseController(CourseMapper courseMapper) {
        this.mapper = courseMapper;
    }

    @GetMapping("/all")
    public List<CourseResponse> getAllCourses(@RequestParam(required = false) String name) {
        return mapper.getAllCoursesByName(name);
    }

    @GetMapping("{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return mapper.getCourseById(id);
    }

    @PostMapping("/add")
    public CourseResponse addNewCourse(@RequestBody CourseRequest request){
        return mapper.addNewCourse(request);
    }

    @GetMapping("/option")
    public List<SelectOptionResponse> getAllCourseOption() {
        return mapper.getAllCoursesOption();
    }

}
