package org.affirmations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.affirmations.model.Affirmation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AffirmationDto {

    private Long id;

    @NotBlank(message = "Message must not be blank")
    @Size(max = 255, message = "Message must be at most 255 characters")
    private String message;

    @NotBlank(message = "Category must not be blank")
    @Size(max = 50, message = "Category must be at most 50 characters")
    private String category;

    public static AffirmationDto fromModel(Affirmation affirmation) {
        return AffirmationDto.builder()
                .id(affirmation.getId())
                .message(affirmation.getMessage())
                .category(affirmation.getCategory())
                .build();
    }

    public static Affirmation toModel(AffirmationDto dto) {
        return Affirmation.builder()
                .id(dto.id)
                .message(dto.message)
                .category(dto.category)
                .build();
    }
}