package Daos;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import Daointerfaces.PaymentDetailsDaoInterface;

public class PaymentDetailsDao extends Dao implements PaymentDetailsDaoInterface {
    private static final String PAYMENT_SALT = ",TT~u]26_b$X?+;t";

    public PaymentDetailsDao() {
        super();
    }
    
    public PaymentDetailsDao(String databaseName) {
        super(databaseName);
    }
   
    public PaymentDetailsDao(String databaseName,String poolName) {
        super(databaseName,poolName);
    }

    /**
     * Inserts a payment details of the user into the payment details table
     * Which are taken at registration
     * @param userID owner of the card
     * @param cardNumber card number
     * @param cardOwner card owner's name
     * @param expirationDate card expiration date
     * @return true if added, false if not
     */
    @Override
    public boolean insertPaymentDetails(int userID, String cardNumber, String cardCvv, String cardOwner, String expirationDate) {
        Connection con = null;
        PreparedStatement ps = null;

        try{
            con = getConnection();
            // Simple insert here
            ps = con.prepareStatement("INSERT into payment_details" +
                    "(userId, cardNumber, cardOwner, expirationDate, cardNumberSum)" +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, userID);

            // Create key with salt and cipher
            byte[] key = (cardCvv + PAYMENT_SALT).getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            // Transform key to 16 bytes
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            Key aesKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");

            // Encrypt data
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            ps.setBytes(2, cipher.doFinal(cardNumber.getBytes()));
            ps.setBytes(3, cipher.doFinal(cardOwner.getBytes()));
            ps.setBytes(4, cipher.doFinal(expirationDate.getBytes()));

            // Sum all card numbers to help verification when decrypt
            int sum = 0;
            for (int i = 0; i < cardNumber.length(); i++){
                sum += Character.getNumericValue(cardNumber.charAt(i));
            }
            ps.setBytes(5, cipher.doFinal(Integer.toString(sum).getBytes()));

            ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @param userID owner of the card
     * @param cardCvv card security code
     * @return null if not found or invalid code, array [cardNumber, owner, expiration] if correct CVV
     */
    @Override
    public String[] getUserPaymentDetails(int userID, String cardCvv) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] details = null;

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM payment_details WHERE userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if (rs.next()){
                // Try to decrypt data with CVV (key)
                // Create key and cipher
                byte[] key = (cardCvv + PAYMENT_SALT).getBytes(StandardCharsets.UTF_8);
                MessageDigest sha = MessageDigest.getInstance("SHA-256");
                // Transform key to 16 bytes
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                Key aesKey = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES");

                cipher.init(Cipher.DECRYPT_MODE, aesKey);

                // Sum of card numbers
                int cardNumberSum = Integer.parseInt(new String(cipher.doFinal(rs.getBytes("cardNumberSum"))));

                details = new String[] {
                        //    Card number
                        new String(cipher.doFinal(rs.getBytes("cardNumber"))),
                        //    Card Owner
                        new String(cipher.doFinal(rs.getBytes("cardOwner"))),
                        //    Expiration Date
                        new String(cipher.doFinal(rs.getBytes("expirationDate"))),
                };

                // Verification of decryption
                // Check if card numbers sum = cardNumberSum
                int sumCheck = 0;
                for (int i = 0; i < details[0].length(); i++){
                    sumCheck += Character.getNumericValue(details[0].charAt(i));
                }

                if (sumCheck != cardNumberSum) {
                    details = null;
                }
            }
        } catch (BadPaddingException e) {
            // Wrong decrypting key
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return details;
    }

    /**
     * Check if user already has payment details
     * @param userID owner of the card
     * @return false if not found, true if has one
     */
    @Override
    public boolean userHasPaymentDetails(int userID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean found = false;

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM payment_details WHERE userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if (rs.next()){
                found = true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return found;
    }

}
