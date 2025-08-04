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
@Table(name = "intention")
public class Intention {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intentionText;
    private String date;
    @ManyToOne
    private User user;

    public Intention(String intentionText, String date, User user) {
        this.intentionText = intentionText; this.date = date; this.user = user;
    }
}