/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtraFunctionalityInterfaces;

/**
 *
 * @author Arnas
 */
public interface SendMailInterface {
    public boolean sendEmailTo(String userEmail, String message, String subject);
}
