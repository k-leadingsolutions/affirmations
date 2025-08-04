package org.affirmations.util;

import org.affirmations.model.Affirmation;

import java.util.ArrayList;
import java.util.List;

public class AffirmationGenerator {
    public static List<Affirmation> generatePositiveAffirmations(String submittedBy) {
        List<Affirmation> affirmations = new ArrayList<>();
        affirmations.add(new Affirmation("You are capable of amazing things.", "motivation", submittedBy));
        affirmations.add(new Affirmation("You have the power to create change.", "empowerment", submittedBy));
        affirmations.add(new Affirmation("You are enough just as you are.", "self-worth", submittedBy));
        affirmations.add(new Affirmation("Mistakes help you grow.", "growth", submittedBy));
        affirmations.add(new Affirmation("You deserve happiness and success.", "wellbeing", submittedBy));
        affirmations.add(new Affirmation("Your potential is limitless.", "motivation", submittedBy));
        affirmations.add(new Affirmation("You are resilient and strong.", "strength", submittedBy));
        affirmations.add(new Affirmation("You bring value to the world.", "self-worth", submittedBy));
        return affirmations;
    }
}