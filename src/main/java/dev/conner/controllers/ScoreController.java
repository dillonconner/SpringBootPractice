package dev.conner.controllers;

import com.google.gson.Gson;
import dev.conner.entities.Score;
import dev.conner.exceptions.NegativePointsException;
import dev.conner.exceptions.ScoreNotFoundException;
import dev.conner.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class ScoreController {

    private Gson gson = new Gson();

    @Autowired
    ScoreService scoreService;

    @PostMapping("/scores")
    @ResponseBody
    public ResponseEntity<String> createScore(@RequestBody Score score){
        try{
            Score savedScore = this.scoreService.saveOrUpdateScore(score);
            return new ResponseEntity(this.gson.toJson(savedScore), HttpStatus.CREATED);
        }catch(NegativePointsException e){
            return new ResponseEntity("Score cannot have negative points", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/scores/{id}")
    @ResponseBody
    public ResponseEntity<String> getScoreById(@PathVariable String id){
        int scoreId = Integer.parseInt(id);
        try{
            Score retScore = this.scoreService.getScoreById(scoreId);
            return new ResponseEntity(this.gson.toJson(retScore), HttpStatus.CONTINUE);
        }catch(ScoreNotFoundException e){
            return new ResponseEntity("Score with id: " + scoreId + "not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/scores")
    @ResponseBody
    public List<Score> getAllScores(@RequestParam(required = false) String initials){
        if(initials == null){
            return this.scoreService.getAllScores();
        }else{

            return this.scoreService.getAllScoresByInitials(initials.toUpperCase());
        }
    }

    @PutMapping("/scores")
    @ResponseBody
    public ResponseEntity<String> updateScore(@RequestBody Score score){
        try{
            Score savedScore = this.scoreService.saveOrUpdateScore(score);
            return new ResponseEntity(this.gson.toJson(savedScore), HttpStatus.CREATED);
        }catch(NegativePointsException e){
            return new ResponseEntity("Score cannot have negative points", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/scores/{id}")
    public ResponseEntity<String> deleteScore(@PathVariable String id){
        int scoreId = Integer.parseInt(id);
        try{
            this.scoreService.deleteScore(scoreId);
            return new ResponseEntity("Score with id: " + scoreId + "successfully deleted", HttpStatus.CONTINUE);
        }catch(ScoreNotFoundException e){
            return new ResponseEntity("Score with id: " + scoreId + "not found", HttpStatus.NOT_FOUND);
        }
    }
}
