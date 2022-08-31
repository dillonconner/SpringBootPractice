package dev.conner.services;

import dev.conner.entities.Score;

import java.util.List;

public interface ScoreService {

    Score saveOrUpdateScore(Score score);

    Score getScoreById(int id);

    List<Score> getAllScores();

    List<Score> getAllScoresByInitials(String initials);

    void deleteScore(int id);
}
