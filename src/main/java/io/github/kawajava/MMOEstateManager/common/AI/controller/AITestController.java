package io.github.kawajava.MMOEstateManager.common.AI.controller;

import io.github.kawajava.MMOEstateManager.common.AI.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AITestController {

    private final AIService aiService;

    public record ChatRequest(String prompt) {}

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest req) {
        return ResponseEntity.ok(aiService.chat(req.prompt()));
    }
}
