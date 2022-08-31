package dev.conner.services;

import dev.conner.entities.Score;
import dev.conner.exceptions.NegativePointsException;
import dev.conner.exceptions.ScoreNotFoundException;
import dev.conner.repos.ScoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService{

    @Autowired
    ScoreRepo scoreRepo;

    @Override
    public Score saveOrUpdateScore(Score score) {
        score.setInitials(score.getInitials().toUpperCase()); //must be uppercase
        if(score.getInitials().length() > 3){ //only 3 initials
            score.setInitials(score.getInitials().substring(0,3));
        }
        if(score.getPoints() < 0){ //not negative
            throw new NegativePointsException();
        }
        return this.scoreRepo.save(score);
    }

    @Override
    public Score getScoreById(int id) {
        Optional<Score> possibleScore = this.scoreRepo.findById(id);
        if(possibleScore.isPresent()){
            return possibleScore.get();
        }else{
            throw new ScoreNotFoundException();
        }
    }

    @Override
    public List<Score> getAllScores() {
        return this.scoreRepo.findAll(Sort.by(Sort.Direction.DESC, "points"));
    }

    @Override
    public List<Score> getAllScoresByInitials(String initials) {
        if(initials.length() > 3){ //only 3 initials
            initials = initials.substring(0,3);
        }
        return this.scoreRepo.findScoresByInitials(initials, Sort.by(Sort.Direction.DESC, "points"));
    }

    @Override
    public void deleteScore(int id) {
        Optional<Score> possibleScore = this.scoreRepo.findById(id);
        if(!possibleScore.isPresent()){
            throw new ScoreNotFoundException();
        }
        this.scoreRepo.deleteById(id);
    }
}
