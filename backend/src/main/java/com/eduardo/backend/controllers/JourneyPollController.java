package com.eduardo.backend.controllers;

import com.eduardo.backend.enums.LinkStatus;
import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.models.Journey;
import com.eduardo.backend.models.JourneyPoll;
import com.eduardo.backend.repositories.CompanyLinkRepository;
import com.eduardo.backend.repositories.JourneyPollRepository;
import com.eduardo.backend.repositories.JourneyRepository;
import com.eduardo.backend.repositories.UserRepository;
import com.eduardo.backend.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/journeys/{journeyId}/polls")
public class JourneyPollController {

    private final JourneyRepository journeyRepository;
    private final JourneyPollRepository pollRepository;
    private final UserRepository userRepository;
    private final CompanyLinkRepository companyLinkRepository;

    public JourneyPollController(JourneyRepository journeyRepository,
                                 JourneyPollRepository pollRepository,
                                 UserRepository userRepository,
                                 CompanyLinkRepository companyLinkRepository) {
        this.journeyRepository = journeyRepository;
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.companyLinkRepository = companyLinkRepository;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> list(@PathVariable Long journeyId) {
        Journey j = journeyRepository.findById(journeyId).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));

        var polls = pollRepository.findByJourneyIdOrderByCreatedAtDesc(journeyId).stream()
                .map(p -> Map.<String, Object>of(
                        "id", p.getId(),
                        "question", p.getQuestion(),
                        "createdAt", p.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(polls);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@PathVariable Long journeyId, @RequestBody Map<String, String> body) {
        var user = SecurityUtils.getCurrentUserOrThrow(userRepository);
        Journey j = journeyRepository.findById(journeyId).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));

        var cl = companyLinkRepository.findByUserIdAndStatus(user.getId(), LinkStatus.APPROVED)
                .orElseThrow(() -> new BadRequestException("Usuário não possui vínculo aprovado"));
        if (!j.getCompanyLink().getCompany().getId().equals(cl.getCompany().getId())) {
            throw new BadRequestException("Jornada não pertence à mesma empresa do usuário");
        }

        String q = body.getOrDefault("question", "");
        if (q.isBlank()) throw new BadRequestException("question é obrigatório");

        JourneyPoll p = JourneyPoll.builder()
                .journey(j)
                .question(q)
                .createdAt(LocalDateTime.now())
                .build();

        p = pollRepository.save(p);

        return ResponseEntity.ok(Map.of("id", p.getId(), "question", p.getQuestion(), "createdAt", p.getCreatedAt()));
    }
}
