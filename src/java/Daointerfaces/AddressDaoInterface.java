package Daointerfaces;

import Dtos.Address;

public interface AddressDaoInterface {
    
    /**
     * Inserts the Address details into the address tables.
     * If already there update them.
     * 
     * @param o The address object  
     * @return boolean true or false
     */
    boolean putAddressDetails(Address o);
    
     /**
     * Selects the address details.
     * 
     * @param userID The userID to be the foreign key
     * @return <code>Address</code> object
     */
    Address selectAddressDetails(int userID);
}
