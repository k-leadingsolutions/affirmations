package org.affirmations.controller;

import org.affirmations.dto.ChatMessageDto;

public interface ChatController {
    ChatMessageDto send(ChatMessageDto message);
}