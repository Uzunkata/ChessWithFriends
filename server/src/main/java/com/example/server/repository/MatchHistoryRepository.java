package com.example.server.repository;

import com.example.server.model.MatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchHistoryRepository extends JpaRepository<MatchHistory, Long> {
    MatchHistory findByGameHash(String gameHash);

}
