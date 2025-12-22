package com.eduardo.backend.controllers;

import com.eduardo.backend.exceptions.BadRequestException;
import com.eduardo.backend.models.Journey;
import com.eduardo.backend.models.JourneyNotice;
import com.eduardo.backend.repositories.CompanyLinkRepository;
import com.eduardo.backend.repositories.JourneyNoticeRepository;
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
@RequestMapping("/api/journeys/{journeyId}/notices")
public class JourneyNoticeController {

    private final JourneyRepository journeyRepository;
    private final JourneyNoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final CompanyLinkRepository companyLinkRepository;

    public JourneyNoticeController(JourneyRepository journeyRepository,
                                   JourneyNoticeRepository noticeRepository,
                                   UserRepository userRepository,
                                   CompanyLinkRepository companyLinkRepository) {
        this.journeyRepository = journeyRepository;
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
        this.companyLinkRepository = companyLinkRepository;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> list(@PathVariable Long journeyId) {
        // ensure journey exists
        Journey j = journeyRepository.findById(journeyId).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));

        var notices = noticeRepository.findByJourneyIdOrderByCreatedAtDesc(journeyId).stream()
                .map(n -> Map.<String, Object>of(
                        "id", n.getId(),
                        "message", n.getMessage(),
                        "createdAt", n.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(notices);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@PathVariable Long journeyId, @RequestBody Map<String, String> body) {
        var user = SecurityUtils.getCurrentUserOrThrow(userRepository);
        Journey j = journeyRepository.findById(journeyId).orElseThrow(() -> new BadRequestException("Jornada não encontrada"));

        // optional: verify user belongs to same company as journey
        var cl = companyLinkRepository.findByUserIdAndStatus(user.getId(), com.eduardo.backend.enums.LinkStatus.APPROVED)
                .orElseThrow(() -> new BadRequestException("Usuário não possui vínculo aprovado"));
        if (!j.getCompanyLink().getCompany().getId().equals(cl.getCompany().getId())) {
            throw new BadRequestException("Jornada não pertence à mesma empresa do usuário");
        }

        String msg = body.getOrDefault("message", "");
        if (msg.isBlank()) throw new BadRequestException("message é obrigatório");

        JourneyNotice n = JourneyNotice.builder()
                .journey(j)
                .message(msg)
                .createdAt(LocalDateTime.now())
                .build();

        n = noticeRepository.save(n);

        return ResponseEntity.ok(Map.of("id", n.getId(), "message", n.getMessage(), "createdAt", n.getCreatedAt()));
    }
}
