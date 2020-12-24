package Daointerfaces;

public interface SecurityAnswersDaoInterface {
    
     /**
     * Inserts the encrypted security answers into the database.
     * 
     * @return boolean true/false
     * @param userID The primary key of the user
     * @param schoolAns The answer to the school security question as Plain Text
     * @param foodAns The answer to the food security question as Plain Text
     * @param placeAns The answer to the place security question as Plain Text
     */
    boolean insertSecurityAnswers(int userID,String schoolAns,String foodAns,String placeAns);
    
    /**
     * Checks the plaintext answer against the hashed db answer to see if it matches and returns true
     * False if the answer doesn't match
     */
    boolean validateSecurityQuestion(String plaintext_answer,String type,int userID);

}
