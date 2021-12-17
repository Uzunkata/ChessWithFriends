package com.example.server.repository;

import com.example.server.model.Verified;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedRepository extends JpaRepository<Verified, Long> {

    Verified findByHash(String hash);
}
