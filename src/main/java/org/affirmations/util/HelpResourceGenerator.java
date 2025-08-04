package org.affirmations.util;

import org.affirmations.model.HelpResource;

import java.util.ArrayList;
import java.util.List;

public class HelpResourceGenerator {
    public static List<HelpResource> generateHelpResources() {
        List<HelpResource> resources = new ArrayList<>();
        resources.add(new HelpResource(
                "National Helpline",
                "24/7 support for mental health emergencies.",
                "1-800-123-4567",
                "helpline"
        ));
        resources.add(new HelpResource(
                "Online Chat Support",
                "Connect instantly with a trained counselor online.",
                "https://www.betterhelp.com/",
                "chat"
        ));
        resources.add(new HelpResource(
                "Local Therapist Directory",
                "Find licensed therapists in your area.",
                "https://www.betterhelp.com/",
                "directory"
        ));
        resources.add(new HelpResource(
                "Crisis Text Line",
                "Text-based support for people in crisis.",
                "Text HOME to 741741",
                "text"
        ));
        resources.add(new HelpResource(
                "Community Support Group",
                "Weekly meetings for peer support and sharing.",
                "https://communitysupportconnections.org",
                "group"
        ));
        resources.add(new HelpResource(
                "Youth Hotline",
                "Specialized help for children and teens.",
                "1-800-987-6543",
                "helpline"
        ));
        resources.add(new HelpResource(
                "Emergency Services",
                "Call in case of immediate danger or emergency.",
                "911",
                "emergency"
        ));
        return resources;
    }
}