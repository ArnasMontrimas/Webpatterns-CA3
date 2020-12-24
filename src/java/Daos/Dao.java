package Daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import static java.lang.System.exit;

public class Dao 
{
    private String databaseName;
    private DataSource datasource;
    
    // Since we will be creating a lot of Dao classes e.g UserDao,BookDao
    // This will make it much cleaner
    public final static String DEFAULT_DB = "creative_library";
    public final static String DEFAULT_JDBC = "jdbc/WebpatternsCA3";
    
    // Used where no parameters are supplied - allows for direct access to a default database
    public Dao(){
        databaseName = DEFAULT_DB;
    }
        
    public Dao(String databaseName)
    {
        this.databaseName = databaseName;
    }
    
    // Constructor for use by pooled connections
    public Dao(String dbName, String datasourceContext){
        this.databaseName = dbName;
        try {
            Context initialContext = new InitialContext();
            DataSource ds = (DataSource)initialContext.lookup("java:comp/env/" 
                    + datasourceContext);
            if(ds != null){
                datasource = ds;
            }
//            else{
//		System.out.println(("Failed to lookup datasource."));
//            }
        }catch (NamingException ex ){
            System.out.println("Cannot get connection: " + ex);
        }
    }
        
    // Direct access to db
    public Connection getDirectConnection()
    {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        String username = "root";
        String password = "";
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex1) {
            System.out.println("Failed to find driver class " + ex1.getMessage());
            exit(1);
        } catch (SQLException ex2) {
            System.out.println("Connection failed " + ex2.getMessage());
        }
        return con;
    }
    
    // If there is a pool set up for us to use, this will pull a connection from there
    // If there was no pool created for us to use, this will go direct to the database 
    public Connection getConnection()
    {
        Connection conn = null;
        try{
            if (datasource != null) {
                conn = datasource.getConnection();
            }else {
                System.out.println(("Failed to lookup datasource. "
                        + "Connecting to database directly."));
                conn = this.getDirectConnection();
            }
        }catch (SQLException ex2){
            System.out.println("Connection failed " + ex2.getMessage());
            System.exit(2); // Abnormal termination
        }
        return conn;
    }
        
    public void freeConnection(Connection con)
    {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            exit(1);
        }
    }

}