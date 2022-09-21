package com.marco.scmexc.api;


import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.dto.UserDto;
import com.marco.scmexc.models.response.SelectOptionResponse;
import com.marco.scmexc.models.response.UserResponse;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private ResponseEntity<SmxUser> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    private ResponseEntity<SmxUser> getCurrentUser(@CurrentUser UserPrincipal userPrincipal){
        return ResponseEntity.ok(userService.getUserById(userPrincipal.getId()));
    }

    @GetMapping("/paged")
//    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    private ResponseEntity<Page<SmxUser>> getAllUsersPaged(@RequestParam(required = false, defaultValue = "", name = "q") String searchQuery,
             Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsersPaged(pageable, searchQuery));
    }

    @GetMapping("{id}")
    private UserResponse getUserById(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserResponseById(id, userPrincipal);
    }

    @GetMapping("{id}/courses")
    private ResponseEntity<List<SelectOptionResponse>> getModeratingCoursesByUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getModeratingCourses(id));
    }
}
