package Daointerfaces;

public interface PaymentDetailsDaoInterface {

    /**
     * Inserts a payment details of the user into the payment details table
     * Which are taken at registration
     * @param userID owner of the card
     * @param cardNumber card number
     * @param cardCvv card cvv
     * @param cardOwner card owner's name
     * @param expirationDate card expiration date
     * @return true if added, false if not
     */    
    boolean insertPaymentDetails(int userID, String cardNumber, String cardCvv, String cardOwner, String expirationDate);

    /**
     * @param userID owner of the card
     * @param cardCvv card security code
     * @return null if not found or invalid code, array [cardNumber, owner, expiration] if correct CVV
     */
     String[] getUserPaymentDetails(int userID, String cardCvv);

    /**
     * Check if user already has payment details
     * @param userID owner of the card
     * @return false if not found, true if has one
     */
    boolean userHasPaymentDetails(int userID); 
 
}
