package com.example.server.controller;

import com.example.server.dto.MatchHistoryDto;
import com.example.server.model.MatchHistory;
import com.example.server.model.User;
import com.example.server.repository.MatchHistoryRepository;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @GetMapping(value="/findAllForUser")
    public ResponseEntity<?>findAllForUser(@RequestParam (value = "username") String username){
        User user = userRepository.findByUsername(username);
//        List<MatchHistory> matchHistoryListPlayer1 = matchHistoryRepository.findByPlayer1(user);
//        List<MatchHistory> matchHistoryListPlayer2 = matchHistoryRepository.findByPlayer2(user);
//        List<MatchHistory> matchHistoryList = new ArrayList<>();
//        matchHistoryList.addAll(matchHistoryListPlayer1);
//        matchHistoryList.addAll(matchHistoryListPlayer2);
        List <MatchHistory> matchHistoryList = matchHistoryRepository.findAllByPlayer1OrPlayer2(user, user, Sort.by(Sort.Direction.DESC, "dateCreated"));

        List<MatchHistoryDto> matchHistoryDtoList = new ArrayList<>();

        for(MatchHistory matchHistory: matchHistoryList){
            MatchHistoryDto matchHistoryDto = new MatchHistoryDto();
            MatchHistory matchHistoryTemp = matchHistoryRepository.findByGameHash(matchHistory.getGameHash());

            matchHistoryDto.setGameHash(matchHistoryTemp.getGameHash());
            matchHistoryDto.setPlayer1(matchHistoryTemp.getPlayer1().getUsername());
            matchHistoryDto.setDateCreated(matchHistoryTemp.getDateCreated());

            if(matchHistoryTemp.getPlayer2() != null) {
                matchHistoryDto.setPlayer2(matchHistoryTemp.getPlayer2().getUsername());
            }else {
                matchHistoryDto.setPlayer2("");
            }
            matchHistoryDto.setStatus(matchHistoryTemp.getStatus());

            if(matchHistoryTemp.getWinner() != null) {
                matchHistoryDto.setWinner(matchHistoryTemp.getWinner().getUsername());
            }else {
                matchHistoryDto.setWinner("");
            }

            matchHistoryDtoList.add(matchHistoryDto);
        }

        return ResponseEntity.ok().body(matchHistoryDtoList);
    }

}
