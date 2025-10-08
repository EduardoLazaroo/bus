package com.eduardo.backend.repositories;

import com.eduardo.backend.models.CompanyLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyLinkRepository extends JpaRepository<CompanyLink, Long> {
}