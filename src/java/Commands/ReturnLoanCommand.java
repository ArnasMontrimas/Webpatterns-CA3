/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arnas
 */
public class ReturnLoanCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        
        
        
        return "loans.jsp";
    }
    
}
