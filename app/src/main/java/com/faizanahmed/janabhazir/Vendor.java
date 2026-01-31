package com.faizanahmed.janabhazir;


import android.util.Log;

import java.util.List;

public class Vendor implements Comparable<Vendor> {


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    private String first_name;
    private  String last_name;
    private  String gender;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String ID;
    private  String CNIC;
    private String base64Image; // Base64 encoded image data
    private  String email;
    private  String phone_number;
    private  String Address;






    public String category() {
        return category;
    }

    private String category;
    private int reviewCount;
    private int timelinessScoreSum;
    private int qualityOfServiceScoreSum;
    private int expertiseScoreSum;

    private float[] timelinessScores;
    private float[] qualityOfServiceScores;
    private float[] expertiseScores;
    private float[] total_score;
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
    //String imageUrl, List<String> reviews
    public Vendor(String ID, String first_name, String last_name, String gender, String CNIC, String base64Image, String email, String phone_number, String Address, String category,List<String> reviews) {
        this.first_name = first_name;
        int reviewSize = reviews != null ? reviews.size() : 0;
        this.ID=ID;
        this.last_name = last_name;
        this.gender = gender;
        this.CNIC = CNIC;
        this.base64Image = base64Image;
        this.email = email;
        this.phone_number = phone_number;
        this.Address = Address;
        this.category = category;
        this.imageUrl = imageUrl;
        this.reviews = reviews;
        this.timelinessScores = new float[reviewSize];
        this.qualityOfServiceScores = new float[reviewSize];
        this.expertiseScores = new float[reviewSize];
        this.timelinessScore = 0; // Initialize to default value
        this.qualityOfServiceScore = 0; // Initialize to default value
        this.expertiseAndKnowledgeScore = 0; // Initialize to default value
        Log.d("VendorDebug", "Constructor: reviews.size(): " + (reviews != null ? reviews.size() : "null"));

    }

    public int getTotalScore() {

        return timelinessScore + qualityOfServiceScore + expertiseAndKnowledgeScore;
    }

    @Override
    public int compareTo(Vendor other) {
        // Sort in descending order of total score
        return other.getTotalScore() - this.getTotalScore();
    }
    public float getOverallRating() {
        return (getAverageTimelinessScore() + getAverageQualityOfServiceScore() + getAverageExpertiseScore()) / 3;
    }


    public void updateReviews(List<String> newReviews) {
        this.reviews = newReviews;
        calculateFinalRatings(); // Recalculate the ratings based on the new reviews

        // Reinitialize score arrays based on the new number of reviews.
        int reviewSize = newReviews != null ? newReviews.size() : 0;
        this.timelinessScores = new float[reviewSize];
        this.qualityOfServiceScores = new float[reviewSize];
        this.expertiseScores = new float[reviewSize];
        // Reset reviewCount if necessary, or handle appropriately
        this.reviewCount = 0;
    }

    public void addReviewScores(int timeliness, int quality, int expertise) {
        if (reviewCount < reviews.size()) {
            float timelinessFloat = convertScore(timeliness);
            float qualityFloat = convertScore(quality);
            float expertiseFloat = convertScore(expertise);

            // Use the converted scores
            this.timelinessScores[reviewCount] = timelinessFloat;
            this.qualityOfServiceScores[reviewCount] = qualityFloat;
            this.expertiseScores[reviewCount] = expertiseFloat;
            this.reviewCount++;
        }
    }


//    public void addReviewScores(int timeliness, int quality, int expertise) {
//        // Assume the scores are already normalized to be out of 5
//        if (reviewCount < reviews.size()) {
//
//            float timelinessFloat = convertScore(timeliness);
//            float qualityFloat = convertScore(quality);
//            float expertiseFloat = convertScore(expertise);
//
//            this.timelinessScores[reviewCount] = timeliness;
//            this.qualityOfServiceScores[reviewCount] = quality;
//            this.expertiseScores[reviewCount] = expertise;
//            this.reviewCount++;
//        }
//    }
    // This method calculates the average scores for each aspect and then computes the final rating out of 5.
    public void calculateFinalRatings() {
        float averageTimeliness = getAverageTimelinessScore();
        float averageQuality = getAverageQualityOfServiceScore();
        float averageExpertise = getAverageExpertiseScore();

        // Calculate the overall average rating out of 5
        float overallAverage = (averageTimeliness + averageQuality + averageExpertise) / 3;

        // Log the overall average for debugging
        Log.d("VendorOverallScore", "Overall Score: " + overallAverage +first_name);

        // You can also store this overall average in a class variable if you want to use it elsewhere
    }



    private float convertScore(int score) {
        switch (score) {
            case 0: return 2.5f; // Neutral
            case 1: return 5.0f;  // Good
            case 2: return 0.0f;  // Bad
            default: return 0.0f; // Default case
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

