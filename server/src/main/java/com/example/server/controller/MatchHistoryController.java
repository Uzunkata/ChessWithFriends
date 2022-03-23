package com.example.server.controller;

import com.example.server.model.MatchHistory;
import com.example.server.repository.MatchHistoryRepository;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/match-history")
public class MatchHistoryController {

    @Autowired
    private MatchHistoryRepository matchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/findALL")
    public ResponseEntity<?>findALL(){
        List<MatchHistory> matchHistoryList = matchHistoryRepository.findAll();
        return ResponseEntity.ok().body(matchHistoryList);
    }


}
