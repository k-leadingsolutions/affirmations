package org.affirmations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.affirmations.model.Feedback;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDto {
    private String username;
    private String text;
    private String createdAt;

    public static FeedbackDto fromModel(Feedback feedback) {
        return FeedbackDto.builder()
                .username(feedback.getUsername())
                .text(feedback.getText())
                .createdAt(feedback.getCreatedAt())
                .build();
    }

    public static Feedback toModel(FeedbackDto dto) {
        return Feedback.builder()
                .username(dto.getUsername())
                .text(dto.getText())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}