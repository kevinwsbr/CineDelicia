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

public class Room {

    protected IntegerProperty ID, numberOfChairs, movie_idmovie;
    protected StringProperty name, type, movieName;
    
    public Room(Integer movie_idmovie){
        this.movie_idmovie = new SimpleIntegerProperty(movie_idmovie);
    }
    
    public Room(String name, Integer numberOfChairs, String type, Integer movie_idmovie){
        this.name = new SimpleStringProperty(name);
        this.numberOfChairs = new SimpleIntegerProperty(numberOfChairs);
        this.type = new SimpleStringProperty(type);
        this.movie_idmovie = new SimpleIntegerProperty(movie_idmovie);
    }
    
    public Room(String name, Integer numberOfChairs, String type, Integer movie_idmovie, Integer idroom){
        this.name = new SimpleStringProperty(name);
        this.numberOfChairs = new SimpleIntegerProperty(numberOfChairs);
        this.type = new SimpleStringProperty(type);
        this.movie_idmovie = new SimpleIntegerProperty(movie_idmovie);
        this.ID = new SimpleIntegerProperty(idroom);
    }

    public Room(String name, Integer numberOfChairs, String type, String movie){
        this.name = new SimpleStringProperty(name);
        this.numberOfChairs = new SimpleIntegerProperty(numberOfChairs);
        this.type = new SimpleStringProperty(type);
        this.movieName = new SimpleStringProperty(movie);
    }
    
    public Room(String name, Integer numberOfChairs, String type, String movie, Integer idroom){
        this.name = new SimpleStringProperty(name);
        this.numberOfChairs = new SimpleIntegerProperty(numberOfChairs);
        this.type = new SimpleStringProperty(type);
        this.movieName = new SimpleStringProperty(movie);
        this.ID = new SimpleIntegerProperty(idroom);
    }
    
    public Integer getID() {
        return ID.get();
    }

    public Integer getNumberOfChairs() {
        return numberOfChairs.get();
    }

    public Integer getMovie_idmovie() {
        return movie_idmovie.get();
    }

    public String getName() {
        return name.get();
    }
    
    public String getMovieName() {
        return movieName.get();
    }

    public String getType() {
        return type.get();
    }
    
    @Override
    public String toString() {
        return name.get() + " (" + numberOfChairs.get() + ")";
    }
    
    public static void getData(Connection con, ObservableList<Room> list) {
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT A.idroom, A.name, B.name AS movieName, A.type, A.numberOfChairs FROM cinema.room A INNER JOIN cinema.movie B ON (A.movie_idmovie = B.idmovie)");

            while (res.next()) {
                list.add(
                        new Room(
                                res.getString("name"),
                                res.getInt("numberOfChairs"),
                                res.getString("type"),
                                res.getString("movieName"),
                                res.getInt("idroom")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void getDataByID(Connection con, Integer ID, ObservableList<Room> list) {
        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM cinema.room WHERE movie_idmovie = (?)");
            stmt.setInt(1, ID);
            ResultSet res  = stmt.executeQuery();

            while (res.next()) {
                list.add(
                        new Room(
                                res.getString("name"),
                                res.getInt("numberOfChairs"),
                                res.getString("type"),
                                res.getInt("movie_idmovie")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean register(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("INSERT INTO cinema.room (name, numberOfChairs, type, movie_idmovie) VALUES (?, ?, ?, ?)");
            st.setString(1, name.get());
            st.setInt(2, numberOfChairs.get());
            st.setString(3, type.get());
            st.setInt(4, movie_idmovie.get());
            
            st.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Database db, Integer ID){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("UPDATE cinema.room SET name = ?, numberOfChairs = ?, type = ?, movie_idmovie = ? WHERE idroom = ?");
            st.setString(1, name.get());
            st.setInt(2, numberOfChairs.get());
            st.setString(3, type.get());
            st.setInt(4, movie_idmovie.get());
            st.setInt(5, ID);
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(Database db, Integer ID){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("DELETE FROM cinema.room WHERE idroom = ?");
            st.setInt(1, ID);
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
