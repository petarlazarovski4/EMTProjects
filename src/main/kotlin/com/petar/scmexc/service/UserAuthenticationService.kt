package com.marco.scmexc.service

import com.marco.scmexc.models.domain.SmxUser
import com.marco.scmexc.security.UserPrincipal
import com.marco.scmexc.models.auth.ChangePasswordPayload
import com.marco.scmexc.models.auth.JwtAuthenticationResponse
import com.marco.scmexc.models.auth.LoginPayload
import com.marco.scmexc.models.dto.UserDto
import com.marco.scmexc.models.response.UserResponse
import org.springframework.http.ResponseEntity

interface UserAuthenticationService {

    fun authenticateUser(loginPayload: LoginPayload) : JwtAuthenticationResponse

    fun checkPassword(userPrincipal: UserPrincipal, password: String): Boolean

    fun changePassword(userPrincipal: UserPrincipal, password: ChangePasswordPayload): UserResponse

    fun registerUser(newUser: UserDto) : UserResponse

    fun updateUserDetails(user: UserDto, userPrincipal: UserPrincipal): UserResponse
}
