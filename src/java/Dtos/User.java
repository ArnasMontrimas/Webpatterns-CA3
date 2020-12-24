package Dtos;

import java.util.Date;
import java.util.Objects;

/**
 * This Class Models the User table from database('creative_library')
 */
public class User {
    //Variables which model users table
    private int userID;
    private String type;
    private String username;
    private String email;
    private String password;
    private Date dateRegistered;
    private boolean activeAccount;

    public User(int userID, String type, String username,String email,String password, Date dateRegistered, boolean activeAccount) {
        this.userID = userID;
        this.type = type;
        this.username = username;
        this.password = password;
        this.dateRegistered = dateRegistered;
        this.activeAccount = activeAccount;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID ||
                username.equals(user.username) ||
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, username);
    }
    
      @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dateRegistered='" + dateRegistered + '\'' +
                ", activeAccount=" + activeAccount +
                ", email=" + email +
                '}';
    }
    
    
}
