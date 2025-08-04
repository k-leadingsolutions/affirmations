package org.affirmations.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "affirmation")
public class Affirmation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String category;
    private String submittedBy;

    public Affirmation(String message, String category, String submittedBy) {
        this.message = message; this.category = category; this.submittedBy = submittedBy;
    }

}