package com.example.server.repository;

import com.example.server.model.MatchHistory;
import com.example.server.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchHistoryRepository extends JpaRepository<MatchHistory, Long> {
    MatchHistory findByGameHash(String gameHash);
    List<MatchHistory> findByPlayer1(User user);
    List<MatchHistory> findByPlayer2(User user);


    List<MatchHistory> findAllByPlayer1OrPlayer2(User user1, User user2, Sort sort);
}
