
package Dtos;

import java.util.Objects;

public class SecurityAnswers {
    
    //Variables which model Security Answers table.
    private int id;
    private int userID;
    private int questionsID;
    private String schoolAnswer;
    private String foodAnswer;
    private String placeAnswer;

    // Provide both options for constructors
    public SecurityAnswers(int id, int userID, int questionsID, String schoolAnswer, String foodAnswer, String placeAnswer) {
        this.id = id;
        this.userID = userID;
        this.questionsID = questionsID;
        this.schoolAnswer = schoolAnswer;
        this.foodAnswer = foodAnswer;
        this.placeAnswer = placeAnswer;
    }

    public SecurityAnswers(int userID, int questionsID, String schoolAnswer, String foodAnswer, String placeAnswer) {
        this.userID = userID;
        this.questionsID = questionsID;
        this.schoolAnswer = schoolAnswer;
        this.foodAnswer = foodAnswer;
        this.placeAnswer = placeAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getQuestionsID() {
        return questionsID;
    }

    public void setQuestionsID(int questionsID) {
        this.questionsID = questionsID;
    }

    public String getSchoolAnswer() {
        return schoolAnswer;
    }

    public void setSchoolAnswer(String schoolAnswer) {
        this.schoolAnswer = schoolAnswer;
    }

    public String getFoodAnswer() {
        return foodAnswer;
    }

    public void setFoodAnswer(String foodAnswer) {
        this.foodAnswer = foodAnswer;
    }

    public String getPlaceAnswer() {
        return placeAnswer;
    }

    public void setPlaceAnswer(String placeAnswer) {
        this.placeAnswer = placeAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityAnswers a = (SecurityAnswers) o;
        return id == a.id && userID == a.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID);
    }

    @Override
    public String toString() {
        return "SecurityAnswers{" +
                "id=" + id +
                ", userID=" + userID +
                ", questionsID=" + questionsID +
                ", schoolAnswer='" + schoolAnswer + '\'' +
                ", foodAnswer='" + foodAnswer + '\'' +
                ", placeAnswer='" + placeAnswer + '\'' +
                '}';
    }
}
