package org.affirmations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.affirmations.model.Intention;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class IntentionDto {

    @NotBlank(message = "Intention must not be blank")
    @Size(max = 255, message = "Size must not be bigger than 255")
    private String intentionText;

    @NotBlank(message = "Date must not be blank")
    private String date;

    public static IntentionDto fromModel(Intention intention){
        return IntentionDto.builder()
                .intentionText(intention.getIntentionText())
                .date(intention.getDate())
                .build();
    }

    public static Intention toModel(IntentionDto dto){
        return  Intention.builder()
                .intentionText(dto.getIntentionText())
                .date(dto.getDate())
                .build();
    }
}