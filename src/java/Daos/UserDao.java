package Daos;

import ExtraFunctionalityInterfaces.SendMailInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Daointerfaces.UserDaoInterface;
import Dtos.User;
import Encryption.BCrypt;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserDao extends Dao implements UserDaoInterface, SendMailInterface {
    private static int workload = 10;
    
    public UserDao() {
        super();
    }
    
    public UserDao(String databaseName) {
        super(databaseName);
    }
    
    /**
     * This method can be used to generate a string representing an account password
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
     * A workload of 15 is a very reasonable safe default as of 2020.
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     * @param password_plaintext The account plaintext password as provided during account creation,
     * or when changing an account password.
     * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
     */
    @Override
    public String hashPassword(String password_plaintext) {
            String salt = BCrypt.gensalt(workload);
            String hashed_password = BCrypt.hashpw(password_plaintext, salt);

            return(hashed_password);
    }
    
    /**
     * This method can be used to verify a computed hash from a plaintext (e.g. during a login
     * request) with that of a stored hash from a database. The password hash from the database
     * must be passed as the second variable.
     * @param password_plaintext The account plaintext password, as provided during a login request
     * @param stored_hash The account stored password hash, retrieved from the authorization database
     * @return boolean - true if the password matches the password of the stored hash, false otherwise
     */
    @Override
    public boolean checkPassword(String password_plaintext, String stored_hash) {
            boolean password_verified = false;

            if(null == stored_hash || !stored_hash.startsWith("$2a$"))
                    throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

            password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

            return(password_verified);
    }

    /**
     * Register a user
     *
     * @param username The username for the account
     * @param email The email used as login for the account
     * @param password The password for the account
     * @return User if succeed, null if not
     */
    @Override
    public User registerUser(String username, String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        User user = null;

        try{
            con = getConnection();
            // Simple insert here
            ps = con.prepareStatement("INSERT INTO users"
            + "(email, username, password) VALUES "
            + "(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,email);
            ps.setString(2,username);
            String hashedPassword = hashPassword(password);
            ps.setString(3,hashedPassword);
            ps.executeUpdate();
            
            // Get the last inserted id if there was no issue inserting
            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
              user = new User(
                generatedKeys.getInt(1),
                "member",
                username,
                email,      
                password,
                new Date(),
                true
              );
            }
        }catch (SQLException e) {
           
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
        return user;
      }

    /**
     *  If username and password don't match return 0
     *  If the account is inactive return -1
     * 
     * @param email The username for login.
     * @param password The password for login.
     * @return int Returns the unique ID of the user if Username and password match or -1 for inactive or 0 for bad login
     */
    @Override
    public User validateLogin(String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try{
            con = getConnection();
            String query = "SELECT * FROM users WHERE email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            // Account with that username exists
            if (rs.next()){
                // Hashed pass from database 
                String hashedPass = rs.getString("password");
                // If hashed == the plain text one
                if (checkPassword(password, hashedPass)) {
                  user = new User(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("username"),
                    email,      
                    hashedPass,
                    rs.getDate("dateRegistered"),
                    rs.getBoolean("activeAccount")
                  );
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
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
        return user;
    }
    
     /**
     * Gets the user object by the primary key which is the ID
     * 
     * @param id The id of the user
     * @return <code>User</code> the User object
     */
    @Override
    public User getUserById(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
        try{
            con = getConnection();
            ps = con.prepareStatement("Select * from users where userID = ? ");
            ps.setInt(1,id);
            rs = ps.executeQuery();

            if(rs.next())
            {
                        user = new User(id,
                        rs.getString("type"),
                        rs.getString("username"),
                        rs.getString("email"),      
                        rs.getString("password"),
                        rs.getDate("dateRegistered"),
                        rs.getBoolean("activeAccount")
                );
            }
        }catch (SQLException e) {
            System.out.println("Exception occured in the getUserByID() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getProductByCode() method: " + e.getMessage());
            }
        }
        return user;
    }    
    
     /**
     * Returns true if the password was reset or false if the old password doesn't match the one in the database
     * 
     * @param old_plaintext_password The users current password
     * @param new_plaintext_password The new password to be reset
     * @param username The users username
     * @return boolean True or false
     */
    @Override
    public boolean passwordReset(String old_plaintext_password,String new_plaintext_password,String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;

        try{
            con = getConnection();
            String query = "SELECT password FROM users WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,username);
            rs = ps.executeQuery();

            if (rs.next()){
             // Get the hashed pass from DB   
             String hashedPass = rs.getString("password");
                //Check the current password matches the stored pass in DB
                if (checkPassword(old_plaintext_password,hashedPass)) { 
                    // Good so update with the new password in DB
                    ps = con.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                    String newHashedPass = hashPassword(new_plaintext_password);
                    ps.setString(1,newHashedPass);
                    ps.setString(2,username);
                    ps.executeUpdate();
                    success = true;
                }

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
        return success;
    }
    
     /**
     * This is when the user is forgets his password and knows his username
     * This method will be called when the security answer is correct which is in the security answers dao.
     *  
     * @param new_plaintext_password The users new chosen password
     * @param username The users username
     * @return boolean True or false
     */
    @Override
    public boolean forgotPasswordReset(String new_plaintext_password,String username) {
        Connection con = null;
        PreparedStatement ps = null;
        int success = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            String newHashedPass = hashPassword(new_plaintext_password);
            ps.setString(1,newHashedPass);
            ps.setString(2,username);
            success = ps.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
        finally{
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
        return success != 0;
    }

    /**
     * This method checks if a specified email address exists in the database
     * @param userEmail email address to be found in database
     * @return boolean true if found, false otherwise
     */
    @Override
    public boolean validateEmail(String userEmail) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String email = "";
        
        
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT email FROM users WHERE email = ?;");
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                email = rs.getString("email");
                return email.equalsIgnoreCase(userEmail);
            }
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        
        return false;
    }

    /**
     * This method will send an email to a specified email address
     * @param userEmail the address of recipient
     * @param message message sent in the email
     * @param subject email subject
     * @return boolean true if sent successfully false otherwise
     */
    @Override
    public boolean sendEmailTo(String userEmail, String message, String subject) {
        // Recipient's email ID needs to be mentioned.
        String to = userEmail;

        // Sender's email ID needs to be mentioned
        String from = "MaloGrallSamPonikArnasMontrimas@outlook.com";
        String password = "SoftwareSecurityCA2DemoAccount";
        
        // Assuming you are sending email from localhost
        String host = "smtp.office365.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                }
        };
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(from, "Creative Library LLC"));
            
            msg.setReplyTo(InternetAddress.parse(from, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(message, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            Transport.send(msg);  

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Updates the user's username if that username is available
     *
     * @param newUsername The new username to update
     * @param currentUsername The current username.
     * @return boolean true/false
     */
    @Override
    public boolean changeUsername(String newUsername, String currentUsername) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean changeUsername = false;

        try{
            con = getConnection();
            ps = con.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            ps.setString(1,newUsername);
            ps.setString(2,currentUsername);
            ps.executeUpdate();
            changeUsername = true;
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
        return changeUsername;
    }
}


