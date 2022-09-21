package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.SmxUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmxUserRepository extends JpaRepository<SmxUser, Long> {
    Optional<SmxUser> findSmxUserByEmailOrUsername(String email, String username);
    Optional<SmxUser> findSmxUserByEmail(String email);

    @Query("select u from SmxUser u where lower(u.firstName) like %?1% or lower(u.lastName) like %?1% or lower(u.username) like %?1%")
    Page<SmxUser> getPagedUsersByNameContains(String searchQuery, Pageable pageable );
}
