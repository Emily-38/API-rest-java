package com.scorestable.restapi;

import com.scorestable.restapi.Match;
import com.scorestable.restapi.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Match> getAllBooks() {
        return matchService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getBookById(@PathVariable Long id) {
        Optional<Match> book = matchService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Match createBook(@RequestBody Match match) {
        return matchService.createBook(match);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        matchService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
