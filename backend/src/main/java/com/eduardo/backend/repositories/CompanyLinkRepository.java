package com.eduardo.backend.repositories;

import com.eduardo.backend.models.CompanyLink;
import com.eduardo.backend.models.Company;
import com.eduardo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyLinkRepository extends JpaRepository<CompanyLink, Long> {
    List<CompanyLink> findByUserId(Long userId);
}
