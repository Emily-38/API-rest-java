package com.scorestable.restapi;

import com.scorestable.restapi.Match;
import com.scorestable.restapi.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllBooks() {
        return matchRepository.findAll();
    }

    public Optional<Match> getBookById(Long id) {
        return matchRepository.findById(id);
    }

    public Match createBook(Match match) {
        return matchRepository.save(match);
    }

    public void deleteBook(Long id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }
}