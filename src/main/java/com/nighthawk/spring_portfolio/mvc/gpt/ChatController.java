package com.nighthawk.spring_portfolio.mvc.gpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatGPTService chatGPTService;

    // Wrapper class for request
    public static class ChatRequest {
        private List<ChatGPTService.Message> messages;

        // Getters and setters
        public List<ChatGPTService.Message> getMessages() {
            return messages;
        }

        public void setMessages(List<ChatGPTService.Message> messages) {
            this.messages = messages;
        }
    }

    @PostMapping
    public String getChatGPTResponse(@RequestBody ChatRequest chatRequest) {
        try {
            return chatGPTService.getResponse(chatRequest.getMessages());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing request: " + e.getMessage();
        }
    }
}
