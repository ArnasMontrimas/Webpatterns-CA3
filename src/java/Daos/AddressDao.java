
package Daos;

import Daointerfaces.AddressDaoInterface;
import Dtos.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author samue
 */
public class AddressDao extends Dao implements AddressDaoInterface{
    
    public AddressDao() {
        super();
    }
    
    public AddressDao(String databaseName) {
        super(databaseName);
    }
   
    public AddressDao(String databaseName,String poolName) {
        super(databaseName,poolName);
    }
    
     /**
     * Inserts the Address details into the address tables.
     * If already there update them.
     * 
     * @param o The address object  
     * @return boolean true or false true if success false otherwise
     */
    @Override
    public boolean putAddressDetails(Address o) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        int value = 0;
        try{
            con = getConnection();
            
            // No address details in DB so insert 
            if (selectAddressDetails(o.getUserID()) == null) {
                
                ps = con.prepareStatement("INSERT into address VALUES(NULL,?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1,o.getFirstname());
                ps.setString(2,o.getLastname());
                ps.setString(3,o.getEmail());
                ps.setString(4,o.getPhone());
                ps.setString(5,o.getAddress1());
                ps.setString(6,o.getAddress2());
                ps.setString(7,o.getCountry());
                ps.setString(8,o.getCity());
                ps.setString(9,o.getPostalcode());
                ps.setInt(10,o.getUserID());
                value = ps.executeUpdate();
            } else {
            // Details already in DB so update 
                ps = con.prepareStatement("UPDATE address SET firstname=?,lastname=?,email=?,phone=?,address1=?,address2=?,country=?,city=?,postalcode=? WHERE userID = ?");
                ps.setString(1,o.getFirstname());
                ps.setString(2,o.getLastname());
                ps.setString(3,o.getEmail());
                ps.setString(4,o.getPhone());
                ps.setString(5,o.getAddress1());
                ps.setString(6,o.getAddress2());
                ps.setString(7,o.getCountry());
                ps.setString(8,o.getCity());
                ps.setString(9,o.getPostalcode());
                ps.setInt(10,o.getUserID());
                value = ps.executeUpdate();
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
        return value != 0;
    }
    
     /**
     * Selects the address details.
     * 
     * @param userID The userID to be the foreign key
     * @return <code>Address</code> object
     */
    @Override
    public Address selectAddressDetails(int userID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Address a = null;

        try{
            con = getConnection();
            String query = "SELECT * from address where userID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1,userID);
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                a = new Address(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address1"),
                        rs.getString("address2"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("postalcode"),
                        rs.getInt("userID")
                );
                a.setAddressID(rs.getInt("addressID"));
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
        return a;
    }
    
     
}
