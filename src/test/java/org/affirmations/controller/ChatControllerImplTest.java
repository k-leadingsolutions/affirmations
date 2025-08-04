package org.affirmations.controller;

import org.affirmations.dto.ChatMessageDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Test
    void testSendSetsTimestamp() {
        ChatControllerImpl controller = new ChatControllerImpl();
        ChatMessageDto msg = ChatMessageDto.builder()
                .username("Alice")
                .message("Hello!")
                .build();

        ChatMessageDto result = controller.send(msg);

        assertNotNull(result.getTimestamp());
        assertEquals("Alice", result.getUsername());
        assertEquals("Hello!", result.getMessage());
    }

    @Test
    void testSendReturnsSameMessageObject() {
        ChatControllerImpl controller = new ChatControllerImpl();
        ChatMessageDto msg = ChatMessageDto.builder()
                .username("Bob")
                .message("Affirmation!")
                .build();

        ChatMessageDto result = controller.send(msg);

        assertSame(msg, result);
    }
}