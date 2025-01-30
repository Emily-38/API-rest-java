package com.scorestable.restapi;

import com.scorestable.restapi.Match;
import com.scorestable.restapi.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    private MatchDTO mapToDTO(Match match) {
        return new MatchDTO(match.getId(), match.getTitle(), match.getAuthor(), match.getIsbn());
    }

    // Convert ProductDTO to Product entity
    private Match mapToEntity(MatchDTO matchDTO) {
        return new Match(matchDTO.getTitle(), matchDTO.getAuthor(), matchDTO.getIsbn());
    }


    public List<MatchDTO> getAllBooks() {
        return matchRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MatchDTO getBookById(Long id) {
        Match match = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDTO(match);
    }

    public MatchDTO createBook(MatchDTO matchDTO) {
        Match match = mapToEntity(matchDTO);
        Match savedMatch = matchRepository.save(match);
        return mapToDTO(savedMatch);
    }

    public void deleteBook(Long id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }
}