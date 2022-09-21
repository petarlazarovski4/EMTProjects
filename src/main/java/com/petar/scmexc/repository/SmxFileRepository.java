package com.marco.scmexc.repository;

import com.marco.scmexc.models.domain.SmxFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmxFileRepository extends JpaRepository<SmxFile, Long> {
}
