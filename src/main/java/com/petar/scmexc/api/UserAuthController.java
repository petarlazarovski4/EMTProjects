package com.marco.scmexc.api;

import com.marco.scmexc.models.auth.ChangePasswordPayload;
import com.marco.scmexc.models.auth.JwtAuthenticationResponse;
import com.marco.scmexc.models.auth.LoginPayload;
import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.dto.UserDto;
import com.marco.scmexc.models.response.UserResponse;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.service.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class UserAuthController {

    private final UserAuthenticationService service;

    public UserAuthController(UserAuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    private ResponseEntity loginUser(@RequestBody LoginPayload payload) {
        JwtAuthenticationResponse token = service.authenticateUser(payload);
        return ResponseEntity.ok(token);
    }

    @PostMapping("user/password/change")
    public UserResponse changePassword(@CurrentUser UserPrincipal userPrincipal, @RequestBody ChangePasswordPayload passwordPayload) {
        return service.changePassword(userPrincipal, passwordPayload);
    }

    @PostMapping("/register")
    private UserResponse registerUser(@RequestBody UserDto newUser) {
        return service.registerUser(newUser);
    }

    @PostMapping("/user/details")
    public UserResponse updateUserDetails(@RequestBody UserDto userDetails, @CurrentUser UserPrincipal userPrincipal) {
        return service.updateUserDetails(userDetails, userPrincipal);
    }
}
