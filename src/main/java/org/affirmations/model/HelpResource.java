package org.affirmations.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "resource")
public class HelpResource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String contact;
    private String type; // e.g., "helpline", "chat", etc.

    public HelpResource(String name, String description, String contact, String type) {
        this.name = name; this.description = description; this.contact = contact; this.type = type;
    }
}