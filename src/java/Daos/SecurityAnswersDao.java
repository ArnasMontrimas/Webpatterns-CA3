/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Daointerfaces.SecurityAnswersDaoInterface;


public class SecurityAnswersDao extends Dao implements SecurityAnswersDaoInterface{
    
    public SecurityAnswersDao() {
        super();
    }
    
    public SecurityAnswersDao(String databaseName) {
        super(databaseName);
    }
   
    public SecurityAnswersDao(String databaseName,String poolName) {
        super(databaseName,poolName);
    }
     /**
     * Returns True if inserted the security questions and false otherwise.
     * @param userID The primary key of the user
     * @param schoolAns The answer to the school security question as Plain Text
     * @param foodAns The answer to the food security question as Plain Text
     * @param placeAns The answer to the place security question as Plain Text
     * @return boolean true/false
     */
    @Override
    public boolean insertSecurityAnswers(int userID,String schoolAns,String foodAns,String placeAns) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        UserDao udao = new UserDao(DEFAULT_DB,DEFAULT_JDBC);
        
        try{
            con = getConnection();
            // Simple insert here
            ps = con.prepareStatement("INSERT into security_answers VALUES(NULL,?,1,?,?,?)");
            ps.setInt(1,userID);
            // Same encryption method can be used here
            String hashedSchoolAns = udao.hashPassword(schoolAns);
            String hashedfoodAns = udao.hashPassword(foodAns);
            String hashedplaceAns = udao.hashPassword(placeAns);
            ps.setString(2,hashedSchoolAns);
            ps.setString(3,hashedfoodAns);
            ps.setString(4,hashedplaceAns);           
            rowsAffected = ps.executeUpdate();
            
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
        return rowsAffected != 0;
      }
    
     /**
     * Returns true if the security answer matches that which is in the database corresponding to the appropriate question.
     * False otherwise
     * @param plaintext_answer The answer to any of the 3 security questions
     * @param type The type of the question food, place or school
     * @param userID The users username
     * @return boolean true/false
     */
    @Override
    public boolean validateSecurityQuestion(String plaintext_answer,String type,int userID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        UserDao udao = new UserDao(DEFAULT_DB,DEFAULT_JDBC);
        
        try{
            con = getConnection();
            String query = "SELECT "+ type +" FROM security_answers WHERE userID = ? AND questionsID = 1";
            ps = con.prepareStatement(query);
            ps.setInt(1,userID);
            rs = ps.executeQuery();
            
            if (rs.next()){
                // If the answer matches the answer in DB for the appropriate question
                if (udao.checkPassword(plaintext_answer,rs.getString(type))) {
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
    
}
