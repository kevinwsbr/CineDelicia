package cinedelicia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    protected Connection con;
    
    protected String URL = "jdbc:mysql://localhost/cinema";
    protected String user = "root";
    protected String pwd = "";
    
    public Connection getConnection(){
        return con;
    }
    
    public void setConnection(Connection con){
        this.con = con;
    }
    
    public void openConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL, user, pwd);
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    public void closeConnection(){
        try{
            con.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
