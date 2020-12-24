/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.AddressDao;
import static Daos.Dao.DEFAULT_DB;
import static Daos.Dao.DEFAULT_JDBC;
import Dtos.Address;
import Dtos.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class InsertAddressDetailsCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            AddressDao udao = new AddressDao(DEFAULT_DB,DEFAULT_JDBC);
            HttpSession session = request.getSession();
            String forwardToJspPage = "AddressDetails.jsp";
            User u = (User)session.getAttribute("User");
            
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            String phonenumber = request.getParameter("phonenumber");
            String address1 = request.getParameter("address1");
            String address2 = request.getParameter("address2");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String postalcode = request.getParameter("postalcode");
            
            // If these optional fields are left empty then make sure they are NULL in DB
            if (address2.isEmpty()) {
            address2 = null;
            }
            if (postalcode.isEmpty()) {
            postalcode = null;
            }
            
             if(u != null && firstname != null && !firstname.isEmpty() &&
                lastname != null && !lastname.isEmpty() && 
                email != null && !email.isEmpty() && 
                phonenumber != null && !phonenumber.isEmpty()  &&  
                address1 != null && !address1.isEmpty() &&
                country != null && !country.isEmpty() &&
                city != null && !city.isEmpty()       
               )
             {
             Address a = new Address(firstname,lastname,email,phonenumber,address1,address2,country,city,postalcode,u.getUserID()); 
                 if (udao.putAddressDetails(a)) {
                     // Inserted only for now 
                   session.setAttribute("Message","Address Details Updated");  
                 } else {
                   session.setAttribute("Message","Address details can't be updated at this time please try again later."); 
                 }          
             } else {
                session.setAttribute("Message","Missing data supplied for the fields !");
            }
            
            return forwardToJspPage;
    }
    
}
