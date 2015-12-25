package cinedelicia.model;

import cinedelicia.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.ObservableList;

public class Movie {

    protected SimpleStringProperty name, director, synopsis, type, genre, actors, country, imgcover, classification;
    protected SimpleIntegerProperty idmovie, year;
    protected SimpleFloatProperty imdbRatio;
    
    public Movie(Integer idmovie) {
        this.idmovie = new SimpleIntegerProperty(idmovie);
    }

    public Movie(String name, Integer year, String type, String actors, String genre, String country, String director, String synopsis, Float imdbRatio, String imgcover, String classification, Integer idmovie) {
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.type = new SimpleStringProperty(type);
        this.actors = new SimpleStringProperty(actors);
        this.genre = new SimpleStringProperty(genre);
        this.country = new SimpleStringProperty(country);
        this.director = new SimpleStringProperty(director);
        this.synopsis = new SimpleStringProperty(synopsis);
        this.imdbRatio = new SimpleFloatProperty(imdbRatio);
        this.imgcover = new SimpleStringProperty(imgcover);
        this.classification = new SimpleStringProperty(classification);
        this.idmovie = new SimpleIntegerProperty(idmovie);

    }
    
    public Movie(String name, Integer year, String type, String actors, String genre, String country, String director, String synopsis, Float imdbRatio, String imgcover, String classification) {
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.type = new SimpleStringProperty(type);
        this.actors = new SimpleStringProperty(actors);
        this.genre = new SimpleStringProperty(genre);
        this.country = new SimpleStringProperty(country);
        this.director = new SimpleStringProperty(director);
        this.synopsis = new SimpleStringProperty(synopsis);
        this.imdbRatio = new SimpleFloatProperty(imdbRatio);
        this.imgcover = new SimpleStringProperty(imgcover);
        this.classification = new SimpleStringProperty(classification);
    }
    
    @Override
    public String toString(){
        return name.get();
    }

    public String getName() {
        return name.get();
    }

    public String getDirector() {
        return director.get();
    }

    public String getSynopsis() {
        return synopsis.get();
    }

    public String getType() {
        return type.get();
    }

    public String getGenre() {
        return genre.get();
    }

    public String getActors() {
        return actors.get();
    }

    public String getCountry() {
        return country.get();
    }

    public String getImgcover() {
        return imgcover.get();
    }

    public Integer getIdmovie() {
        return idmovie.get();
    }

    public Integer getYear() {
        return year.get();
    }

    public String getClassification() {
        return classification.get();
    }

    public Float getImdbRatio() {
        return imdbRatio.get();
    }

    public static void getData(Connection con, ObservableList<Movie> list) {
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM cinema.movie");

            while (res.next()) {
                list.add(
                        new Movie(
                                res.getString("name"),
                                res.getInt("year"),
                                res.getString("type"),
                                res.getString("actors"),
                                res.getString("genre"),
                                res.getString("country"),
                                res.getString("director"),
                                res.getString("synopsis"),
                                res.getFloat("imdbRatio"),
                                res.getString("imgcover"),
                                res.getString("classification"),
                                res.getInt("idmovie")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean register(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("INSERT INTO cinema.movie (name, year, type, actors, genre, country, director, synopsis, imdbRatio, imgcover, classification) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, name.get());
            st.setInt(2, year.get());
            st.setString(3, type.get());
            st.setString(4, actors.get());
            st.setString(5, genre.get());
            st.setString(6, country.get());
            st.setString(7, director.get());
            st.setString(8, synopsis.get());
            st.setFloat(9, imdbRatio.get());
            st.setString(10, imgcover.get());
            st.setString(11, classification.get());
            
            st.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("UPDATE cinema.movie SET name = ?, year = ?, type = ?, actors = ?, genre = ?, country = ?, director = ?, synopsis = ?, imdbRatio = ?, imgcover = ?, classification = ? WHERE idmovie = ?");
            st.setString(1, name.get());
            st.setInt(2, year.get());
            st.setString(3, type.get());
            st.setString(4, actors.get());
            st.setString(5, genre.get());
            st.setString(6, country.get());
            st.setString(7, director.get());
            st.setString(8, synopsis.get());
            st.setFloat(9, imdbRatio.get());
            st.setString(10, imgcover.get());
            st.setString(11, classification.get());
            st.setInt(12, idmovie.get());
            
            st.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(Database db){
        try {
            PreparedStatement st = db.getConnection().prepareStatement("DELETE FROM cinema.movie WHERE idmovie = ?");
            st.setInt(1, idmovie.get());
            
            st.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
