package org.affirmations.dto;

import lombok.*;
import org.affirmations.model.HelpResource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class HelpResourceDto {

    private String name;
    private String description;
    private String contact;
    private String type; // e.g., "helpline", "chat", etc.

    public static HelpResourceDto fromModel(HelpResource helpResource){
        return HelpResourceDto.builder()
                .name(helpResource.getName())
                .description(helpResource.getDescription())
                .contact(helpResource.getContact())
                .type(helpResource.getType())
                .build();
    }

}