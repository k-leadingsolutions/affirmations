package org.affirmations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 255, message = "Message must be at least 255 characters")
    private String message;
    private String timestamp;
}