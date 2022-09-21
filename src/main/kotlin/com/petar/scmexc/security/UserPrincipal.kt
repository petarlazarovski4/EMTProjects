package com.marco.scmexc.security

import com.fasterxml.jackson.annotation.JsonIgnore
import com.marco.scmexc.models.domain.Role
import com.marco.scmexc.models.domain.SmxUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserPrincipal(
    val id: Long,
    private val username: String,
    @JsonIgnore
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun hasRole(role: Role): Boolean {
        return this.authorities.stream()
                    .map { it.authority }
                    .anyMatch { it == role.name }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPrincipal

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


    companion object {
        fun create(user: SmxUser): UserPrincipal {
            return UserPrincipal(
                user.id,
                user.email,
                user.password,
                listOf(SimpleGrantedAuthority(user.role.name))
            )
        }
    }

}
