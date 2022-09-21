package com.marco.scmexc.service

import com.marco.scmexc.models.domain.SmxUser
import com.marco.scmexc.models.dto.UserDto
import com.marco.scmexc.models.response.SelectOptionResponse
import com.marco.scmexc.models.response.UserResponse
import com.marco.scmexc.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {

    fun createUser(newUser: UserDto): SmxUser

    fun modifyUser(userPrincipal: UserPrincipal, modifiedUser: UserDto): SmxUser

    fun modifyUser(userId: Long, modifiedUser: UserDto): SmxUser

    fun getUserByEmail(email: String): SmxUser

    fun getUserById(id: Long): SmxUser

    fun getUserResponseById(id: Long, userPrincipal: UserPrincipal): UserResponse

    fun saveUser(user: SmxUser): SmxUser

    fun getAllUsersPaged(pageable: Pageable, searchQuery: String): Page<SmxUser>

    fun getModeratingCourses(id: Long): List<SelectOptionResponse>

}
