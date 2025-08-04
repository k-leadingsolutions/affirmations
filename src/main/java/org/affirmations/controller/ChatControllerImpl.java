package org.affirmations.controller;

import jakarta.validation.Valid;
import org.affirmations.dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class ChatControllerImpl implements ChatController {

    @Override
    @PreAuthorize("isAuthenticated()")
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageDto send(@Valid ChatMessageDto message) {
        message.setTimestamp(java.time.Instant.now().toString());
        return message;
    }
}