package dev.conner.repos;

import dev.conner.entities.Score;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepo extends JpaRepository<Score, Integer> {

    List<Score> findScoresByInitials(String initials, Sort points);
}
