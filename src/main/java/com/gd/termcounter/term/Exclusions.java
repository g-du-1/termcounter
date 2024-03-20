package com.gd.termcounter.term;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class Exclusions {
    protected static final Set<String> excludedWords = new HashSet<>();

    static {
        excludedWords.addAll(Arrays.asList(
            "and", "a", "the", "to", "of", "in", "our", "with", "you", "for",
            "we", "software", "will", "are", "on", "be", "is", "an", "working",
            "as", "development", "work", "your", "that", "have", "by", "team",
            "from", "this", "which", "using", "can", "if", "part", "all", "within",
            "their", "also", "or", "at", "-", "experience", "more", "&", "other",
            "new", "help", "code", "people", "one", "but", "not", "who", "about",
            "role", "technology", "us", "where", "any", "skills", "best", "it",
            "time", "across", "opportunity", "such", "make", "into", "join", "up",
            "including", "need", "application", "take", "days", "looking", "every",
            "company", "how", "per", "ensure", "based", "both", "well", "most", "•",
            "technologies", "knowledge", "–", "teams", "do", "what", "environment",
            "please", "understanding", "opportunities", "apply", "abilities", "ability",
            "diversity", "through", "you'll", "·", "years", "status", "we're", "strong",
            "/", "they", "offer", "value", "equal", "global", "employer", "leave", "gender",
            "use", "day", "so", "disability", "around", "location", "able", "benefits",
            "good", "contribute", "future", "candidates", "would", "great", "right", "world",
            "expertise", "way", "like", "has", "them", "sexual", "range", "familiarity",
            "values", "age", "build", "develop", "uk", "salary", "developing", "building",
            "projects", "we’re", "you’ll", "learning", "life", "culture", "key", "industry",
            "*", "high", "career", "learn", "excellent", "diverse", "some", "committed",
            "out", "employees", "over", "create", "want", "needs", "get", "access",
            "employee", "these", "recruitment", "innovative", "inclusive", "may", "success",
            "developers", "year", "being", "providing", "power", "include", "related",
            "members", "highly", "professional", "personal", "when", "successful", "creating",
            "innovation", "know", "together", "plus"
        ));
    }

    private Exclusions() {
        throw new IllegalStateException("Utility class");
    }
}
