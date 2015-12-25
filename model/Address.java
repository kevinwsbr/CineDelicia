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

public class Address {

    protected IntegerProperty number, people_idpeople;
    protected StringProperty street, CEP, city, state, country;

    public Address(Integer people_idpeople) {
        this.people_idpeople = new SimpleIntegerProperty(people_idpeople);
    }
    
    public Address(String street, int number, String CEP, String city, String state, String country, Integer people_idpeople) {
        this.street = new SimpleStringProperty(street);
        this.number = new SimpleIntegerProperty(number);
        this.CEP = new SimpleStringProperty(CEP);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.country = new SimpleStringProperty(country);
        this.people_idpeople = new SimpleIntegerProperty(people_idpeople);
    }

    public Integer getNumber() {
        return number.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getCEP() {
        return CEP.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public String getCountry() {
        return country.get();
    }
    
    public Integer getPeople_idpeople(){
        return people_idpeople.get();
    }
    
    public String toString() {
        return street.get() + ", " + number.get() + " (" + CEP.get() + ")" + ". " + city.get() + "/" + state.get() + " - " + country.get();
    }
    
    public static void getData(Connection con, ObservableList<Address> list){
        try{
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM cinema.address");
            
            while(res.next()){
                list.add(
                    new Address(
                        res.getString("street"),
                        res.getInt("number"),
                        res.getString("CEP"),
                        res.getString("city"),
                        res.getString("state"),
                        res.getString("country"),
                        res.getInt("people_idpeople")
                    )
                );         
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean register(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("INSERT INTO cinema.address(number, CEP, street, city, state, country, people_idpeople) VALUES (?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, number.get());
            st.setString(2, CEP.get());
            st.setString(3, street.get());
            st.setString(4, city.get());
            st.setString(5, state.get());
            st.setString(6, country.get());
            st.setInt(7, people_idpeople.get());
            
            st.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("UPDATE  cinema.address SET number = ?, CEP = ?, street = ?, city = ?, state = ?, country = ? WHERE people_idpeople = ?");
            st.setInt(1, number.get());
            st.setString(2, CEP.get());
            st.setString(3, street.get());
            st.setString(4, city.get());
            st.setString(5, state.get());
            st.setString(6, country.get());
            st.setInt(7, people_idpeople.get());
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("DELETE FROM cinema.address WHERE people_idpeople = ?");
            st.setInt(1, people_idpeople.get());
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
