
package daos;

import daointerfaces.OpinionsDaoInterface;
import dtos.Opinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author grallm
 */
public class OpinionsDao extends Dao implements OpinionsDaoInterface {
     
    public OpinionsDao() {
        super();
    }
    
    public OpinionsDao(String databaseName) {
        super(databaseName);
    }

    /**
     * Save an opinion
     * @param userId user that added this opinion
     * @param bookId book commented
     * @param rating rating of book
     * @param comment comment of book
     * @return true if success, false if not
     */
    @Override
    public boolean addOpinion(int userId, int bookId, int rating, String comment) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try{
            con = getConnection();
            ps = con.prepareStatement("INSERT INTO opinions" +
                    "(userId, bookId, rating, comment)" +
                    "VALUES (?, ?, ?, ?)");
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
            success = false;
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
                success = false;
            }
        }
        return success;
    }
    
     /**
     * Get all opinions for a specific book
     * @param bookId Book to fetch opinions
     * @return Returns an ArrayList of Opinions
     */
    @Override
    public ArrayList<Opinion> getBookOpinions(int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Opinion> opinions = new ArrayList<>();

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * from opinions WHERE bookId = ?");
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                opinions.add(new Opinion(
                  rs.getInt("id"),
                  rs.getInt("userId"),
                  rs.getInt("bookId"),
                  rs.getDate("date"),
                  rs.getInt("rating"),
                  rs.getString("comment")
                ));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return opinions;
    }

    /**
     * Check if a user has already an opinion on a book
     * @param userId user to check
     * @param bookId Book to check
     * @return Opinion if found, null if not
     */
    @Override
    public Opinion checkIfUserHasOpinion(int userId, int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Opinion opinion = null;

        try{
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM opinions WHERE bookId = ? AND userId = ?;");
            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();

            if(rs.next()) {
                opinion = new Opinion(
                        rs.getInt("id"),
                        userId,
                        bookId,
                        rs.getDate("date"),
                        rs.getInt("rating"),
                        rs.getString("comment")
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

        return opinion;
    }

    /**
     * Remove an opinion by giving user and book
     * @param userId user id
     * @param bookId book id
     * @return true if successful false otherwise
     */
    @Override
    public boolean removeOpinion(int userId, int bookId) {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;
        boolean success = true;
        
        try {
            con = getConnection();
            ps = con.prepareStatement("DELETE FROM opinions WHERE userId = ? AND bookId = ?");
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            count = ps.executeUpdate();
            
            if(count == 0) success = false;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
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
                success = false;
            }
        }
        
        return success;
    }
}
