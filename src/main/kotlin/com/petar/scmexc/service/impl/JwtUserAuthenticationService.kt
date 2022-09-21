package com.marco.scmexc.service.impl

import com.marco.scmexc.models.domain.SmxUser
import com.marco.scmexc.models.exceptions.user.InvalidPasswordException
import com.marco.scmexc.security.JwtTokenProvider
import com.marco.scmexc.security.UserPrincipal
import com.marco.scmexc.service.UserAuthenticationService
import com.marco.scmexc.service.UserService
import com.marco.scmexc.models.auth.ChangePasswordPayload
import com.marco.scmexc.models.auth.JwtAuthenticationResponse
import com.marco.scmexc.models.auth.LoginPayload
import com.marco.scmexc.models.domain.Role
import com.marco.scmexc.models.dto.UserDto
import com.marco.scmexc.models.exceptions.user.NoPermissionException
import com.marco.scmexc.models.exceptions.user.UserNotFoundException
import com.marco.scmexc.models.response.UserResponse
import com.marco.scmexc.services.CourseService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class JwtUserAuthenticationService(
        private val userService: UserService,
        private val authenticationManager: AuthenticationManager,
        private val passwordEncoder: PasswordEncoder,
        private val jwtTokenProvider: JwtTokenProvider,
        private val courseService: CourseService
) : UserAuthenticationService {

    override fun authenticateUser(loginPayload: LoginPayload): JwtAuthenticationResponse {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginPayload.email,
                loginPayload.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtTokenProvider.generateToken(authentication)

        return JwtAuthenticationResponse(jwt)
    }

    override fun checkPassword(userPrincipal: UserPrincipal, password: String): Boolean {
        val user = userService.getUserById(userPrincipal.id)
        return (passwordEncoder.matches(password, user.password))
    }

    override fun changePassword(userPrincipal: UserPrincipal, password: ChangePasswordPayload): UserResponse {
        val user = userService.getUserById(userPrincipal.id)

        if(!passwordEncoder.matches(password.oldPassword, user.password)) throw InvalidPasswordException()

        val newEncryptedPassword = passwordEncoder.encode(password.password)
        user.password = newEncryptedPassword
        val updated = userService.saveUser(user)
        return UserResponse.of(updated.id, updated.username, updated.firstName, updated.lastName, updated.email, updated.role.name);
    }

    override fun registerUser(newUser: UserDto): UserResponse {
        val user: SmxUser = userService.createUser(newUser)
        return UserResponse.of(user.id, user.username, user.firstName, user.lastName, user.email, user.role.name);
    }

    override fun updateUserDetails(user: UserDto, userPrincipal: UserPrincipal): UserResponse {
        val currentUser: SmxUser = userService.getUserById(userPrincipal.id);
        if(user.id != null) {
            if(user.id == currentUser.id || userPrincipal.hasRole(Role.SUPER_ADMIN) || userPrincipal.hasRole(Role.ADMIN)) {
                val userToUpdate: SmxUser = userService.getUserById(user.id);
                userToUpdate.username = user.username;
                userToUpdate.email = user.email;
                userToUpdate.firstName = user.firstName
                userToUpdate.lastName = user.lastName;
                if((userPrincipal.hasRole(Role.SUPER_ADMIN) || userPrincipal.hasRole(Role.ADMIN)) && user.role != null) {
                    val role: Role = Role.valueOf(user.role);
                    userToUpdate.role = role;
                    if(user.moderatingCourses != null) {
                        val courses = courseService.findAllByIdIn(user.moderatingCourses);
                        userToUpdate.moderatingCourses.clear();
                        userToUpdate.moderatingCourses.addAll(courses)
                    }
                    if(!role.equals(Role.MODERATOR)) {
                        userToUpdate.moderatingCourses.clear();
                    }
                }
                val updated =  userService.saveUser(userToUpdate);
                return UserResponse.of(updated.id, updated.username, updated.firstName, updated.lastName, updated.email,updated.role.name);
            } else throw NoPermissionException();
        } else throw UserNotFoundException();
    }

}
