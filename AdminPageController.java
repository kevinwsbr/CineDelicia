package cinedelicia;

import cinedelicia.model.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminPageController implements Initializable {

    protected Database con;

    protected ObservableList<Movie> listMovies;
    protected ObservableList<Room> listRooms;
    protected ObservableList<Address> listAddress;
    protected ObservableList<People> listPeoples;

    protected ObservableList<String> listType
            = FXCollections.observableArrayList(
                    "2D",
                    "3D"
            );
    protected ObservableList<String> listClassification
            = FXCollections.observableArrayList(
                    "L",
                    "10",
                    "12",
                    "14",
                    "16",
                    "18"
            );
    protected ObservableList<String> listSex
            = FXCollections.observableArrayList(
                    "M",
                    "F"
            );
    protected ObservableList<String> listState
            = FXCollections.observableArrayList(
                    "AC",
                    "AL",
                    "SP",
                    "RJ",
                    "MG",
                    "RS"
            );
    protected ObservableList<String> listPosition
            = FXCollections.observableArrayList(
                    "Admin",
                    "Caixa"
            );
    
    
    @FXML
    protected ImageView imgCoverMovie;

    @FXML protected Label movieID;    
    @FXML protected Label roomID;    
    @FXML protected Label peopleID;    
    
    //movie
    @FXML
    protected Label movieNameLabel;
    @FXML
    protected Label movieYearLabel;
    @FXML
    protected Label movieTypeLabel;
    @FXML
    protected Label movieDirectorLabel;
    @FXML
    protected Label movieActorsLabel;
    @FXML
    protected Label movieSynopsisLabel;
    @FXML
    protected Label movieGenreLabel;
    @FXML
    protected Label movieCountryLabel;
    @FXML
    protected Label movieRatingLabel;

    //room
    @FXML
    protected Label roomNameLabel;
    @FXML
    protected Label roomMovieNameLabel;
    @FXML
    protected Label roomTypeLabel;
    @FXML
    protected Label roomNumberChairsLabel;
    
    //room
    @FXML
    protected Label peopleNameLabel;
    @FXML
    protected Label peoplePositionLabel;
    @FXML
    protected Label peopleLoginLabel;
    @FXML
    protected Label peopleMailLabel;
    @FXML
    protected Label peopleTelLabel;
    @FXML
    protected Label peopleCPFLabel;
    @FXML
    protected Label peopleAgeLabel;
    @FXML
    protected Label peopleSexLabel;
    @FXML
    protected Label peopleSalaryLabel;
    @FXML
    protected Label peopleAddressLabel;

    
    @FXML protected TableView<Movie> tblViewMovies;
    @FXML protected TableView<Room> tblViewRooms;
    @FXML protected TableView<Address> tblViewAddress;
    @FXML protected TableView<People> tblViewPeoples;

    //movie
    @FXML
    protected TableColumn<Movie, String> clmnName;
    @FXML
    protected TableColumn<Movie, Integer> clmnYear;
    @FXML
    protected TableColumn<Movie, String> clmnType;
    @FXML
    protected TableColumn<Movie, String> clmnDirector;

    //room
    @FXML
    protected TableColumn<Room, String> clmnNameRoom;
    @FXML
    protected TableColumn<Room, String> clmnNameMovie;
    @FXML
    protected TableColumn<Room, Integer> clmnNumberChairsRoom;
    @FXML
    protected TableColumn<Room, String> clmnTypeRoom;

    //people
    @FXML
    protected TableColumn<People, Integer> clmnIDPeople;
    @FXML
    protected TableColumn<People, String> clmnNamePeople;
    @FXML
    protected TableColumn<People, String> clmnPositionPeople;
    @FXML
    protected TableColumn<People, Float> clmnSalaryPeople;

    //movie
    @FXML
    protected TextField txtName;
    @FXML
    protected TextField txtYear;
    @FXML
    protected ComboBox cboType = new ComboBox(listType);
    @FXML
    protected TextField txtActors;
    @FXML
    protected TextField txtGenre;
    @FXML
    protected TextField txtCountry;
    @FXML
    protected TextField txtDirector;
    @FXML
    protected TextArea txtSynopsis;
    @FXML
    protected TextField txtimdbRatio;
    @FXML
    protected TextField txtimgcover;
    @FXML
    protected ComboBox cboClassification = new ComboBox(listClassification);

    //room
    @FXML
    protected TextField txtNameRoom;
    @FXML
    protected TextField txtNumberChairsRoom;
    @FXML
    protected ComboBox cboRoomType = new ComboBox(listType);
    @FXML
    protected ComboBox cboMovieList;
    
    @FXML
    protected TextField txtNamePeople;
    @FXML
    protected TextField txtCPFPeople;
    @FXML
    protected TextField txtAgePeople;
    @FXML
    protected TextField txtUsernamePeople;
    @FXML
    protected PasswordField txtPwdPeople;
    @FXML
    protected TextField txtMailPeople;
    @FXML
    protected TextField txtTelPeople;
    @FXML
    protected TextField txtSalaryPeople;
    @FXML
    protected TextField txtStreetPeople;
    @FXML
    protected TextField txtCEPPeople;
    @FXML
    protected TextField txtNumberPeople;
    @FXML
    protected TextField txtCityPeople;
    @FXML
    protected TextField txtCountryPeople;
    @FXML
    protected ComboBox cboSexPeople = new ComboBox(listSex);
    @FXML
    protected ComboBox cboPositionPeople = new ComboBox(listPosition);
    @FXML
    protected ComboBox cboStatePeople = new ComboBox(listState);
    
    @FXML protected Button regMovie;
    @FXML protected Button regRoom;
    @FXML protected Button regPeople;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        con = new Database();
        con.openConnection();

        listMovies = FXCollections.observableArrayList();
        listRooms = FXCollections.observableArrayList();
        listPeoples = FXCollections.observableArrayList();

        //movie
        Movie.getData(con.getConnection(), listMovies);
        tblViewMovies.setItems(listMovies);

        txtName.setDisable(true);
        txtYear.setDisable(true);
        cboType.setDisable(true);
        txtActors.setDisable(true);
        txtGenre.setDisable(true);
        txtCountry.setDisable(true);
        txtDirector.setDisable(true);
        txtSynopsis.setDisable(true);
        txtimdbRatio.setDisable(true);
        txtimgcover.setDisable(true);
        cboClassification.setDisable(true);
        regMovie.setDisable(true);
        
        txtNameRoom.setDisable(true);
        txtNumberChairsRoom.setDisable(true);
        cboRoomType.setDisable(true);
        cboMovieList.setDisable(true);
        regRoom.setDisable(true);
        
        txtNamePeople.setDisable(true);
        txtCPFPeople.setDisable(true);
        txtAgePeople.setDisable(true);
        txtUsernamePeople.setDisable(true);
        txtPwdPeople.setDisable(true);
        txtMailPeople.setDisable(true);
        txtTelPeople.setDisable(true);
        txtSalaryPeople.setDisable(true);
        txtStreetPeople.setDisable(true);
        txtCEPPeople.setDisable(true);
        txtNumberPeople.setDisable(true);
        txtCityPeople.setDisable(true);
        txtCountryPeople.setDisable(true);
        cboSexPeople.setDisable(true);
        cboPositionPeople.setDisable(true);
        cboStatePeople.setDisable(true);
        regPeople.setDisable(true);

        //room
        Room.getData(con.getConnection(), listRooms);
        tblViewRooms.setItems(listRooms);
        
        People.getData(con.getConnection(), listPeoples);
        tblViewPeoples.setItems(listPeoples);

        //movie cbo
        cboType.getItems().addAll(listType);
        cboClassification.getItems().addAll(listClassification);

        //room cbo
        cboRoomType.getItems().addAll(listType);
        cboMovieList.getItems().addAll(listMovies);
        
        //people cbo
        cboSexPeople.getItems().addAll(listSex);
        cboPositionPeople.getItems().addAll(listPosition);
        cboStatePeople.getItems().addAll(listState);
                
        //movie table
        clmnName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        //clmnName.setCellValueFactory(cellData -> cellData.getValue().getName());
        clmnYear.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        clmnType.setCellValueFactory(new PropertyValueFactory<Movie, String>("type"));
        clmnDirector.setCellValueFactory(new PropertyValueFactory<Movie, String>("director"));

        //room table
        clmnNameRoom.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
        clmnNameMovie.setCellValueFactory(new PropertyValueFactory<Room, String>("movieName"));
        clmnNumberChairsRoom.setCellValueFactory(new PropertyValueFactory<Room, Integer>("numberOfChairs"));
        clmnTypeRoom.setCellValueFactory(new PropertyValueFactory<Room, String>("type"));
        
        //people table
        //clmnIDPeople.setCellValueFactory(cellData -> cellData.getValue().getID());
        clmnIDPeople.setCellValueFactory(new PropertyValueFactory<People, Integer>("idpeople"));
        clmnNamePeople.setCellValueFactory(new PropertyValueFactory<People, String>("name"));
        clmnPositionPeople.setCellValueFactory(new PropertyValueFactory<People, String>("position"));
        clmnSalaryPeople.setCellValueFactory(new PropertyValueFactory<People, Float>("salary"));

        showMovieDetails(null);
        showRoomDetails(null);
        showPeopleDetails(null);

        tblViewMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMovieDetails(newValue));
        tblViewRooms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRoomDetails(newValue));
        tblViewPeoples.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPeopleDetails(newValue));
        
        con.closeConnection();
    }

    public void btnNewMovieClicked() {
        txtName.setDisable(false);
        txtYear.setDisable(false);
        cboType.setDisable(false);
        txtActors.setDisable(false);
        txtGenre.setDisable(false);
        txtCountry.setDisable(false);
        txtDirector.setDisable(false);
        txtSynopsis.setDisable(false);
        txtimdbRatio.setDisable(false);
        txtimgcover.setDisable(false);
        cboClassification.setDisable(false);
        regMovie.setDisable(false);
        
        txtName.setText("");
        cboType.getSelectionModel().select(null);
        txtYear.setText("");
        txtActors.setText("");
        txtGenre.setText("");
        txtCountry.setText("");
        txtDirector.setText("");
        txtSynopsis.setText("");
        txtimdbRatio.setText("");
        txtimgcover.setText("");
        cboClassification.getSelectionModel().select(null);    
    }
    
    public void btnNewRoomClicked() {
        txtNameRoom.setDisable(false);
        txtNumberChairsRoom.setDisable(false);
        cboRoomType.setDisable(false);
        cboMovieList.setDisable(false);
        regRoom.setDisable(false);
        
        txtNameRoom.setText("");
        cboRoomType.getSelectionModel().select(null);
        txtNumberChairsRoom.setText("");
        cboMovieList.getSelectionModel().select(null);
    }
    
    public void btnNewPeopleClicked() {
        txtNamePeople.setDisable(false);
        txtCPFPeople.setDisable(false);
        txtAgePeople.setDisable(false);
        txtUsernamePeople.setDisable(false);
        txtPwdPeople.setDisable(false);
        txtMailPeople.setDisable(false);
        txtTelPeople.setDisable(false);
        txtSalaryPeople.setDisable(false);
        txtStreetPeople.setDisable(false);
        txtCEPPeople.setDisable(false);
        txtNumberPeople.setDisable(false);
        txtCityPeople.setDisable(false);
        txtCountryPeople.setDisable(false);
        cboSexPeople.setDisable(false);
        cboPositionPeople.setDisable(false);
        cboStatePeople.setDisable(false);
        regPeople.setDisable(false);
        
        txtNamePeople.setText("");
        txtCPFPeople.setText("");
        txtAgePeople.setText("");
        txtUsernamePeople.setText("");
        txtPwdPeople.setText("");
        txtMailPeople.setText("");
        txtTelPeople.setText("");
        txtSalaryPeople.setText("");
        txtStreetPeople.setText("");
        txtCEPPeople.setText("");
        txtNumberPeople.setText("");
        txtCityPeople.setText("");
        txtCountryPeople.setText("");
        cboStatePeople.getSelectionModel().select(null);
    }

    private void showMovieDetails(Movie movie) {
        if (movie != null) {
            movieID.setText(movie.getIdmovie().toString());
            
            txtName.setDisable(true);
            txtYear.setDisable(true);
            cboType.setDisable(true);
            txtActors.setDisable(true);
            txtGenre.setDisable(true);
            txtCountry.setDisable(true);
            txtDirector.setDisable(true);
            txtSynopsis.setDisable(true);
            txtimdbRatio.setDisable(true);
            txtimgcover.setDisable(true);
            cboClassification.setDisable(true);
        
            txtName.setText(movie.getName());
            cboType.getSelectionModel().select(movie.getType());
            txtYear.setText(movie.getYear().toString());
            txtActors.setText(movie.getActors());
            txtGenre.setText(movie.getGenre());
            txtCountry.setText(movie.getCountry());
            txtDirector.setText(movie.getDirector());
            txtSynopsis.setText(movie.getSynopsis());
            txtimdbRatio.setText(movie.getImdbRatio().toString());
            txtimgcover.setText(movie.getImgcover());
            cboClassification.getSelectionModel().select(movie.getClassification());

            movieNameLabel.setText(movie.getName());
            movieYearLabel.setText("(" + movie.getYear().toString() + ")");
            movieActorsLabel.setText("Atores: " + movie.getActors());
            movieGenreLabel.setText("Gênero: " + movie.getGenre());
            //movieCountryLabel.setText(movie.getCountry());
            movieDirectorLabel.setText("Diretor: " + movie.getDirector());
            movieSynopsisLabel.setText("Sinopse: " + movie.getSynopsis());
            movieRatingLabel.setText("Nota: " + movie.getImdbRatio().toString());

            Image img = new Image(movie.getImgcover(), 116, 165, false, false);
            imgCoverMovie.setImage(img);

        } else {
            movieNameLabel.setText("Selecione um filme para exibir os dados.");
            movieYearLabel.setText("");
            movieActorsLabel.setText("");
            movieGenreLabel.setText("");
            //movieCountryLabel.setText("");
            movieDirectorLabel.setText("");
            movieSynopsisLabel.setText("");
            movieRatingLabel.setText("");

            Image img = new Image("res/defaultCover.png", 116, 165, false, false);
            imgCoverMovie.setImage(img);
        }
    }
    
    private void showRoomDetails(Room room) {
        if (room != null) {
            roomID.setText(room.getID().toString());
            
            txtNameRoom.setDisable(true);
            txtNumberChairsRoom.setDisable(true);
            cboRoomType.setDisable(true);
            cboMovieList.setDisable(true);
            
            txtNameRoom.setText(room.getName());
            cboRoomType.getSelectionModel().select(room.getType());
            txtNumberChairsRoom.setText(room.getNumberOfChairs().toString());
            cboMovieList.getSelectionModel().select(room.getMovieName());
            roomNameLabel.setText(room.getName());
            roomMovieNameLabel.setText("Filme: " + room.getMovieName());
            roomTypeLabel.setText("Tipo: " + room.getType());
            roomNumberChairsLabel.setText("(" + room.getNumberOfChairs().toString() + " lugares)");
        } else {
            roomNameLabel.setText("Selecione uma sala para exibir os dados.");
            roomMovieNameLabel.setText("");
            roomTypeLabel.setText("");
            roomNumberChairsLabel.setText("");
        }
    }
    
    private void showPeopleDetails(People people) {
        if (people != null) {
            peopleID.setText(people.getIdpeople().toString());
            
            txtNamePeople.setDisable(true);
            txtCPFPeople.setDisable(true);
            txtAgePeople.setDisable(true);
            txtUsernamePeople.setDisable(true);
            txtPwdPeople.setDisable(true);
            txtMailPeople.setDisable(true);
            txtTelPeople.setDisable(true);
            txtSalaryPeople.setDisable(true);
            txtStreetPeople.setDisable(true);
            txtCEPPeople.setDisable(true);
            txtNumberPeople.setDisable(true);
            txtCityPeople.setDisable(true);
            txtCountryPeople.setDisable(true);
            cboSexPeople.setDisable(true);
            cboPositionPeople.setDisable(true);
            cboStatePeople.setDisable(true);
            
            txtNamePeople.setText(people.getName());
            txtCPFPeople.setText(people.getCPF());
            txtAgePeople.setText(people.getAge().toString());
            txtUsernamePeople.setText(people.getFunctionary().getUsername());
            txtPwdPeople.setText(people.getFunctionary().getPwd());
            txtMailPeople.setText(people.getFunctionary().getEmail());
            txtTelPeople.setText(people.getTel());
            txtSalaryPeople.setText(people.getFunctionary().getSalary().toString());
            txtStreetPeople.setText(people.getAddress().getStreet());
            txtCEPPeople.setText(people.getAddress().getCEP());
            txtNumberPeople.setText(people.getAddress().getNumber().toString());
            txtCityPeople.setText(people.getAddress().getCity());
            txtCountryPeople.setText(people.getAddress().getCountry());
            cboStatePeople.getSelectionModel().select(people.getAddress().getState());
            
            peopleNameLabel.setText(people.getName());
            peoplePositionLabel.setText(people.getFunctionary().getPosition());
            peopleLoginLabel.setText(people.getFunctionary().getUsername());
            peopleMailLabel.setText("E-mail: " + people.getFunctionary().getEmail());
            peopleTelLabel.setText("Telefone: " + people.getTel());
            peopleCPFLabel.setText("CPF: " + people.getCPF());
            peopleAgeLabel.setText("Idade: " + people.getAge().toString());
            peopleSexLabel.setText("Sexo: " + people.getSex());
            peopleSalaryLabel.setText("Salário: " + people.getFunctionary().getSalary().toString());
            peopleAddressLabel.setText("Endereço: " + people.getAddress().toString());
            
        } else {
            peopleNameLabel.setText("Selecione um funcionário para exibir os dados.");
            peoplePositionLabel.setText("");
            peopleLoginLabel.setText("");
            peopleMailLabel.setText("");
            peopleTelLabel.setText("");
            peopleCPFLabel.setText("");
            peopleAgeLabel.setText("");
            peopleSexLabel.setText("");
            peopleSalaryLabel.setText("");
            peopleAddressLabel.setText("");
        }
    }

    public int getIDbyName(Connection con, String name) {
        int n = 0;
        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT idmovie FROM cinema.movie WHERE name = (?)");
            stmt.setString(1, name);
            ResultSet res  = stmt.executeQuery();
            if(res.next()){
                n = res.getInt("idmovie");
            }
            return n;
        } catch (SQLException e) {
            return 0;
        }
    }
    
    public int getIDbyPeopleCPF(Connection con, String name) {
        int n = 0;
        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT idpeople FROM cinema.people WHERE CPF = (?)");
            stmt.setString(1, name);
            ResultSet res  = stmt.executeQuery();
            if(res.next()){
                n = res.getInt("idpeople");
            }
            return n;
        } catch (SQLException e) {
            return 0;
        }
    } 
    
    public void addMovie() {
        Movie movie = new Movie(
                txtName.getText(),
                Integer.valueOf(txtYear.getText()),
                cboType.getSelectionModel().getSelectedItem().toString(),
                txtActors.getText(),
                txtGenre.getText(),
                txtCountry.getText(),
                txtDirector.getText(),
                txtSynopsis.getText(),
                Float.valueOf(txtimdbRatio.getText()),
                txtimgcover.getText(),
                cboClassification.getSelectionModel().getSelectedItem().toString()
        );
        con.openConnection();
        if (movie.register(con)) {
            Alert msgbox = new Alert(AlertType.INFORMATION);
            msgbox.setContentText("Cadastro realizado com sucesso!");
            msgbox.setTitle("CineDelícia - Administração");
            msgbox.setHeaderText("Cadastro de Filmes");
            msgbox.showAndWait();
        }
        con.closeConnection();
    }
    
    public void updateMovie() {
        txtName.setDisable(false);
        txtYear.setDisable(false);
        cboType.setDisable(false);
        txtActors.setDisable(false);
        txtGenre.setDisable(false);
        txtCountry.setDisable(false);
        txtDirector.setDisable(false);
        txtSynopsis.setDisable(false);
        txtimdbRatio.setDisable(false);
        txtimgcover.setDisable(false);
        cboClassification.setDisable(false);
        regMovie.setDisable(false);
        
        Movie movie = new Movie(
                txtName.getText(),
                Integer.valueOf(txtYear.getText()),
                cboType.getSelectionModel().getSelectedItem().toString(),
                txtActors.getText(),
                txtGenre.getText(),
                txtCountry.getText(),
                txtDirector.getText(),
                txtSynopsis.getText(),
                Float.valueOf(txtimdbRatio.getText()),
                txtimgcover.getText(),
                cboClassification.getSelectionModel().getSelectedItem().toString(),
                Integer.valueOf(movieID.getText())
        );
        con.openConnection();
        if (movie.update(con)) {
            Alert msgbox = new Alert(AlertType.INFORMATION);
            msgbox.setContentText("Atualização realizada com sucesso!");
            msgbox.setTitle("CineDelícia - Administração");
            msgbox.setHeaderText("Atualização de Filmes");
            msgbox.showAndWait();
        }
        con.closeConnection();
    }    
    
    public void deleteMovie() {
        
        Movie movie = new Movie(
            Integer.valueOf(movieID.getText())
        );
        con.openConnection();
        if (movie.delete(con)) {
            Alert msgbox = new Alert(AlertType.INFORMATION);
            msgbox.setContentText("Filme removido com sucesso!");
            msgbox.setTitle("CineDelícia - Administração");
            msgbox.setHeaderText("Remoção de Filmes");
            msgbox.showAndWait();
        }
        con.closeConnection();
    }      

    public void addRoom() {
        con.openConnection();
        Room room = new Room(
                txtNameRoom.getText(),
                Integer.valueOf(txtNumberChairsRoom.getText()),
                cboRoomType.getSelectionModel().getSelectedItem().toString(),
                getIDbyName(con.getConnection(), cboMovieList.getSelectionModel().getSelectedItem().toString())
        );
        room.register(con);
        con.closeConnection();
    }
    
    public void updateRoom() {
        txtNameRoom.setDisable(false);
        txtNumberChairsRoom.setDisable(false);
        cboRoomType.setDisable(false);
        cboMovieList.setDisable(false);
        regRoom.setDisable(false);
        
        con.openConnection();
        Room room = new Room(
                txtNameRoom.getText(),
                Integer.valueOf(txtNumberChairsRoom.getText()),
                cboRoomType.getSelectionModel().getSelectedItem().toString(),
                getIDbyName(con.getConnection(), cboMovieList.getSelectionModel().getSelectedItem().toString())
        );
        room.update(con, Integer.valueOf(roomID.getText()));
        con.closeConnection();
    }
    
    public void deleteRoom() {
        con.openConnection();
        Room room = new Room(
                getIDbyName(con.getConnection(), cboMovieList.getSelectionModel().getSelectedItem().toString())
        );
        
        if (room.delete(con, Integer.valueOf(roomID.getText()))) {
            Alert msgbox = new Alert(AlertType.INFORMATION);
            msgbox.setContentText("Sala removida com sucesso!");
            msgbox.setTitle("CineDelícia - Administração");
            msgbox.setHeaderText("Remoção de Salas");
            msgbox.showAndWait();
        }
        con.closeConnection();
    }
    
    public void addPeople(Database con) {
        con.getConnection();
        People people = new People(
                txtNamePeople.getText(),
                cboSexPeople.getSelectionModel().getSelectedItem().toString(),
                Integer.valueOf(txtAgePeople.getText()),
                txtTelPeople.getText(),
                txtCPFPeople.getText()
        );
        
        people.register(con);
    }
    
    public void updatePeople(Database con) {
        txtNamePeople.setDisable(false);
        txtCPFPeople.setDisable(false);
        txtAgePeople.setDisable(false);
        txtUsernamePeople.setDisable(false);
        txtPwdPeople.setDisable(false);
        txtMailPeople.setDisable(false);
        txtTelPeople.setDisable(false);
        txtSalaryPeople.setDisable(false);
        txtStreetPeople.setDisable(false);
        txtCEPPeople.setDisable(false);
        txtNumberPeople.setDisable(false);
        txtCityPeople.setDisable(false);
        txtCountryPeople.setDisable(false);
        cboSexPeople.setDisable(false);
        cboPositionPeople.setDisable(false);
        cboStatePeople.setDisable(false);
        regPeople.setDisable(false);
        
        con.getConnection();
        People people = new People(
                txtNamePeople.getText(),
                cboSexPeople.getSelectionModel().getSelectedItem().toString(),
                Integer.valueOf(txtAgePeople.getText()),
                txtTelPeople.getText(),
                txtCPFPeople.getText()
        );
        
        people.update(con, getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText()));
    }    
    
    public void deletePeople(Database con) {
        con.getConnection();
        People people = new People(
                txtCPFPeople.getText()
        );
        
        if (people.delete(con, getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText()))) {
            Alert msgbox = new Alert(AlertType.INFORMATION);
            msgbox.setContentText("Funcionário demitido com sucesso!");
            msgbox.setTitle("CineDelícia - Administração");
            msgbox.setHeaderText("Remoção de Funcionários");
            msgbox.showAndWait();
        }
    }    

    public void addAddress(Database con) {
        con.getConnection();
        Address address = new Address(
                txtStreetPeople.getText(),
                Integer.valueOf(txtNumberPeople.getText()),
                txtCEPPeople.getText(),
                txtCityPeople.getText(),
                cboStatePeople.getSelectionModel().getSelectedItem().toString(),
                txtCountryPeople.getText(),
                getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText())
        );
        
        address.register(con);
    }
    
    public void updateAddress(Database con) {
        con.getConnection();
        Address address = new Address(
                txtStreetPeople.getText(),
                Integer.valueOf(txtNumberPeople.getText()),
                txtCEPPeople.getText(),
                txtCityPeople.getText(),
                cboStatePeople.getSelectionModel().getSelectedItem().toString(),
                txtCountryPeople.getText(),
                getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText())
        );
        
        address.update(con);
    }    
    
    public void deleteAddress(Database con) {
        con.getConnection();
        Address address = new Address(
                getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText())
        );
        
        address.delete(con);
    }    
    
    public void addFunctionary(Database con) {
        con.getConnection();
        Functionary functionary = new Functionary(
                /*txtNamePeople.getText(),
                cboSexPeople.getSelectionModel().getSelectedItem().toString(),
                Integer.valueOf(txtAgePeople.getText()),
                txtTelPeople.getText(),
                txtCPFPeople.getText(),*/
                txtUsernamePeople.getText(),
                txtMailPeople.getText(),
                txtPwdPeople.getText(),
                cboPositionPeople.getSelectionModel().getSelectedItem().toString(),
                Float.valueOf(txtSalaryPeople.getText()),
                getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText())
        );
        
        functionary.register(con);
    }
    
    public void updateFunctionary(Database con) {
        con.getConnection();
        Functionary functionary = new Functionary(
                txtUsernamePeople.getText(),
                txtMailPeople.getText(),
                txtPwdPeople.getText(),
                cboPositionPeople.getSelectionModel().getSelectedItem().toString(),
                Float.valueOf(txtSalaryPeople.getText()),
                getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText())
        );
        
        functionary.update(con);
    }    
    
    public void deleteFunctionary(Database con) {
        con.getConnection();
        Functionary functionary = new Functionary(
            getIDbyPeopleCPF(con.getConnection(), txtCPFPeople.getText())
        );
        
        functionary.delete(con);
    }    
    
    public void registerNewEmployer() {
        con.openConnection();
        addPeople(con);
        addAddress(con);
        addFunctionary(con);
        con.closeConnection();
    }
    
    public void updateSelectedEmployer() {
        con.openConnection();
        updatePeople(con);
        updateAddress(con);
        updateFunctionary(con);
        con.closeConnection();
    }    
    
    public void deleteSelectedEmployer() {
            con.openConnection();
            deleteFunctionary(con);
            deleteAddress(con);
            deletePeople(con);
            con.closeConnection();
        }       
}
