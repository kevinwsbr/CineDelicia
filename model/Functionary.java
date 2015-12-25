package cinedelicia.model;

import cinedelicia.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Functionary{
    protected IntegerProperty people_idpeople;
    protected StringProperty username, email, pwd, position;
    protected FloatProperty salary;
    
    public Functionary(Integer people_idpeople) {
        this.people_idpeople = new SimpleIntegerProperty(people_idpeople);
    }
    
    public Functionary(String username, String pwd, String position) {
        this.username = new SimpleStringProperty(username);
        this.pwd = new SimpleStringProperty(pwd);
        this.position = new SimpleStringProperty(position);
    }
    
    public Functionary(String name, String CPF, int age, String sex, String tel, Address address, String username, String email, String pwd, String position, Float salary, Integer people_idpeople) {
        //super(name, sex, age, tel, CPF, address);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.pwd = new SimpleStringProperty(pwd);
        this.position = new SimpleStringProperty(position);
        this.salary = new SimpleFloatProperty(salary);
        this.people_idpeople = new SimpleIntegerProperty(people_idpeople);
    }
    
    public Functionary(String username, String email, String pwd, String position, Float salary, Integer people_idpeople) {
        //super(name, sex, age, tel, CPF);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.pwd = new SimpleStringProperty(pwd);
        this.position = new SimpleStringProperty(position);
        this.salary = new SimpleFloatProperty(salary);
        this.people_idpeople = new SimpleIntegerProperty(people_idpeople);
    }

    public Integer getPeople_idpeople() {
        return people_idpeople.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPwd() {
        return pwd.get();
    }

    public String getPosition() {
        return position.get();
    }

    public Float getSalary() {
        return salary.get();
    }
    
    public boolean register(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("INSERT INTO cinema.functionary(username, email, pwd, position, salary, people_idpeople) VALUES (?, ?, ?, ?, ?, ?)");
            st.setString(1, username.get());
            st.setString(2, email.get());
            st.setString(3, pwd.get());
            st.setString(4, position.get());
            st.setFloat(5, salary.get());
            st.setInt(6, people_idpeople.get());
            
            st.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("UPDATE cinema.functionary SET username = ?, email = ?, pwd = ?, position = ?, salary = ? WHERE people_idpeople = ?");
            st.setString(1, username.get());
            st.setString(2, email.get());
            st.setString(3, pwd.get());
            st.setString(4, position.get());
            st.setFloat(5, salary.get());
            st.setInt(6, people_idpeople.get());
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static String authenticate(Connection con, String name, String pwd) {
        String funcPosition = null;
        try{
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT username, pwd, position FROM cinema.functionary WHERE username = (?) AND pwd = (?)");
            stmt.setString(1, name);
            stmt.setString(2, pwd);
            ResultSet res  = stmt.executeQuery();
            
            if(res.next()){
                 funcPosition = res.getString("position");      
            }
            
            return funcPosition;
            
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean delete(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("DELETE FROM cinema.functionary WHERE people_idpeople = ?");
            st.setInt(1, people_idpeople.get());
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
