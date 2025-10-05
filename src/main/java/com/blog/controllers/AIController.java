package com.blog.controllers;

import com.blog.responses.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {
    private ChatClient client;

    public AIController(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @GetMapping("/generate-content")
    public ResponseEntity<SuccessResponse> generateContent(@RequestParam String title) {
        String content = client
                .prompt("You are an expert blog writer. Write a complete blog post based on the title: "+ title +" .Output the blog in clean HTML format with:\n" +
                "- One <h1> main headline that matches or improves the title.\n" +
                "- 2–4 <h2> subheadings dividing key sections.\n" +
                "- Under each subheading, use <p> paragraphs of well-written text (150–200 words each).\n" +
                "- Use <strong> or <em> tags where emphasis is helpful.\n" +
                "- End with a short <h3> conclusion and a final <p>.\n" +
                "\n" +
                "Do not include any CSS or JavaScript, html or body tag—only semantic HTML with body's tags only.\n")
                .call().content();
        SuccessResponse response = SuccessResponse
                .builder()
                .message("Content generated successfully")
                .success(true)
                .data(content)
                .build();
        return ResponseEntity.ok(response);
    }
}
