package cinedelicia.model;

import cinedelicia.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class People {

    protected StringProperty name, CPF, tel, sex;
    protected IntegerProperty age, ID;
    protected Address address;
    protected Functionary functionary;

    public People(String CPF) {
        this.CPF = new SimpleStringProperty(CPF);
    }
    
    public People(String name, String sex, Integer age, String tel, String CPF, Address address, Functionary functionary) {
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleIntegerProperty(age);
        this.tel = new SimpleStringProperty(tel);
        this.CPF = new SimpleStringProperty(CPF);
        this.address = address;
        this.functionary = functionary;
    }
    
    public People(String name, String sex, Integer age, String tel, String CPF) {
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleIntegerProperty(age);
        this.tel = new SimpleStringProperty(tel);
        this.CPF = new SimpleStringProperty(CPF);
    }
    
    public People(String name, String sex, Integer age, String tel, String CPF, Integer ID) {
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleIntegerProperty(age);
        this.tel = new SimpleStringProperty(tel);
        this.CPF = new SimpleStringProperty(CPF);
        this.ID = new SimpleIntegerProperty(ID);
    }

    public String getName() {
        return name.get();
    }

    public String getCPF() {
        return CPF.get();
    }

    public String getTel() {
        return tel.get();
    }

    public Integer getAge() {
        return age.get();
    }

    public String getSex() {
        return sex.get();
    }

    public Address getAddress() {
        return address;
    }
    
    public Float getSalary() {
        return functionary.getSalary();
    }
    
    public String getPosition() {
        return functionary.getPosition();
    }
    
    public Integer getIdpeople() {
        return functionary.getPeople_idpeople();
    }
    
    public Functionary getFunctionary() {
        return functionary;
    }
    
    public static void getData(Connection con, ObservableList<People> list){
        try{
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT A.*, B.*, C.* FROM cinema.people A INNER JOIN cinema.address B ON (A.idpeople = B.people_idpeople) INNER JOIN cinema.functionary C ON (A.idpeople = C.people_idpeople)");
            
            while(res.next()){
                list.add(
                    new People(
                        res.getString("name"),
                        res.getString("sex"),
                        res.getInt("age"),
                        res.getString("tel"),
                        res.getString("CPF"),
                        new Address(res.getString("street"),
                                    res.getInt("number"), 
                                    res.getString("CEP"),
                                    res.getString("city"), 
                                    res.getString("state"), 
                                    res.getString("country"), 
                                    res.getInt("people_idpeople")),
                        new Functionary(res.getString("username"),
                                        res.getString("email"),
                                        res.getString("pwd"),
                                        res.getString("position"),
                                        res.getFloat("salary"),
                                        res.getInt("people_idpeople")
                        )
                    )
                );         
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean register(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("INSERT INTO cinema.people(name, CPF, age, sex, tel) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, name.get());
            st.setString(2, CPF.get());
            st.setInt(3, age.get());
            st.setString(4, sex.get());
            st.setString(5, tel.get());
            
            st.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Database db, Integer ID){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("UPDATE cinema.people SET name = ?, CPF = ?, age = ?, sex = ?, tel = ? WHERE idpeople = ?");
            st.setString(1, name.get());
            st.setString(2, CPF.get());
            st.setInt(3, age.get());
            st.setString(4, sex.get());
            st.setString(5, tel.get());
            st.setInt(6, ID);
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(Database db, Integer ID){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("DELETE FROM cinema.people WHERE idpeople = ?");
            st.setInt(1, ID);
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
