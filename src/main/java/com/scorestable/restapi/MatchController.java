package com.scorestable.restapi;

import com.scorestable.restapi.Match;
import com.scorestable.restapi.MatchService;
import com.scorestable.restapi.MatchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "get", description = "GET methods of Match APIs")
    @GetMapping
    public List<List<MatchDTO>> getAllBooks() {
        List<MatchDTO> matches = matchService.getAllBooks();
        return (List<List<MatchDTO>>) ResponseEntity.ok(matches);
    }
    @Tag(name = "get", description = "GET by id methods of Match ")
    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getBookById(@PathVariable Long id) {
        MatchDTO product = matchService.getBookById(id);
        return ResponseEntity.ok(product);
    }
    @Operation(summary = "Create a Match",
            description = "Create a Match. The response is  object with id, title,author, and isbn.")
    @PostMapping
    public ResponseEntity<MatchDTO> createBook(@RequestBody MatchDTO matchDTO) {
        MatchDTO createdMatch = matchService.createBook(matchDTO);
        return ResponseEntity.ok(createdMatch);
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Match.class)) }),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        matchService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
