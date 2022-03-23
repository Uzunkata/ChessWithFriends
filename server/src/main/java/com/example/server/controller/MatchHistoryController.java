package com.example.server.controller;

import com.example.server.model.MatchHistory;
import com.example.server.model.User;
import com.example.server.repository.MatchHistoryRepository;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/match-history")
public class MatchHistoryController {

    @Autowired
    private MatchHistoryRepository matchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/findAll")
    public ResponseEntity<?>findAll(){
        List<MatchHistory> matchHistoryList = matchHistoryRepository.findAll();
        return ResponseEntity.ok().body(matchHistoryList);
    }

    @GetMapping(value="/findAllForUser")
    public ResponseEntity<?>findAllForUser(@RequestParam (value = "username") String username){
        User user = userRepository.findByUsername(username);
        List<MatchHistory> matchHistoryListPlayer1 = matchHistoryRepository.findByPlayer1(user);
        List<MatchHistory> matchHistoryListPlayer2 = matchHistoryRepository.findByPlayer2(user);
        List<MatchHistory> matchHistoryList = new ArrayList<>();
        matchHistoryList.addAll(matchHistoryListPlayer1);
        matchHistoryList.addAll(matchHistoryListPlayer2);
        return ResponseEntity.ok().body(matchHistoryList);
    }


}
