package com.marco.scmexc.security
import com.marco.scmexc.repository.SmxUserRepository
import com.marco.scmexc.models.exceptions.user.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CustomUserDetailsService(
    val repository: SmxUserRepository
) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        // Let people login with either username or email
        val user = repository.findSmxUserByEmail(email)
            .orElseThrow(::UserNotFoundException)
        return UserPrincipal.create(user)
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = repository.findById(id).orElseThrow(::UserNotFoundException)
        return UserPrincipal.create(user)

    }
}
