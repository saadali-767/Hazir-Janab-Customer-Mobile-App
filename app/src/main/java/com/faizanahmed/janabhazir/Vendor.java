package com.faizanahmed.janabhazir;


import java.util.Arrays;
import java.util.List;

public class Vendor implements Comparable<Vendor> {
    public String getName() {
        return name;
    }

    private String name;

    public void setText_vendor_occupation(String text_vendor_occupation) {
        this.text_vendor_occupation = text_vendor_occupation;
    }

    public String getText_vendor_occupation() {
        return text_vendor_occupation;
    }

    private String text_vendor_occupation;
    private int reviewCount;
    private int timelinessScoreSum;
    private int qualityOfServiceScoreSum;
    private int expertiseScoreSum;

    private float[] timelinessScores;
    private float[] qualityOfServiceScores;
    private float[] expertiseScores;

    public String getImageUrl() {
        return imageUrl;
    }

    private String imageUrl; // URL of the vendor's image

    public int getTimelinessScore() {
        return timelinessScore;
    }

    public void setTimelinessScore(int timelinessScore) {
        this.timelinessScore = timelinessScore;
    }

    private int timelinessScore = 0;

    public int getQualityOfServiceScore() {
        return qualityOfServiceScore;
    }

    public void setQualityOfServiceScore(int qualityOfServiceScore) {
        this.qualityOfServiceScore = qualityOfServiceScore;
    }

    private int qualityOfServiceScore = 0;

    public int getExpertiseAndKnowledgeScore() {
        return expertiseAndKnowledgeScore;
    }

    public void setExpertiseAndKnowledgeScore(int expertiseAndKnowledgeScore) {
        this.expertiseAndKnowledgeScore = expertiseAndKnowledgeScore;
    }

    private int expertiseAndKnowledgeScore = 0;

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    private List<String> reviews;

    public Vendor(String name, String imageUrl, List<String> reviews) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.reviews = reviews;
        int reviewSize = reviews.size();
        this.timelinessScores = new float[reviewSize];
        this.qualityOfServiceScores = new float[reviewSize];
        this.expertiseScores = new float[reviewSize];
    }

    public int getTotalScore() {
        return timelinessScore + qualityOfServiceScore + expertiseAndKnowledgeScore;
    }

    @Override
    public int compareTo(Vendor other) {
        // Sort in descending order of total score
        return other.getTotalScore() - this.getTotalScore();
    }



    public void addReviewScores(int timeliness, int quality, int expertise) {
        if (reviewCount < reviews.size()) {
            this.timelinessScores[reviewCount] = convertScore(timeliness);
            this.qualityOfServiceScores[reviewCount] = convertScore(quality);
            this.expertiseScores[reviewCount] = convertScore(expertise);
            this.reviewCount++;
        }
    }

    private float convertScore(int score) {
        switch (score) {
            case 0: return 3.0f; // Neutral
            case 1: return 5.0f; // Positive
            case 2: return 1.0f; // Negative
            default: return 0.0f;
        }
    }

    public float getAverageTimelinessScore() {
        return calculateAverage(this.timelinessScores);
    }

    public float getAverageQualityOfServiceScore() {
        return calculateAverage(this.qualityOfServiceScores);
    }

    public float getAverageExpertiseScore() {
        return calculateAverage(this.expertiseScores);
    }

    private float calculateAverage(float[] scores) {
        float totalScore = 0.0f;
        for (float score : scores) {
            totalScore += score;
        }
        return reviewCount > 0 ? totalScore / reviewCount : 0.0f;
    }
}
// Getters and Setters

