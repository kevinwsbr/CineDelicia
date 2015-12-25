package cinedelicia;

import cinedelicia.model.Movie;
import cinedelicia.model.Room;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class SalePageController implements Initializable {
    
    protected Database con;
    
    protected Float value = 0.f;
    
    protected String movieName;
    protected String roomName;
    
    protected ObservableList<Movie> listMovies;
    protected ObservableList<Room> listRooms;
    
    @FXML protected ImageView imgCoverMovie;
    
    @FXML protected TableView<Movie> tblViewMovies;
    
    //movie
    @FXML protected TableColumn<Movie, String> clmnName;
    //@FXML protected TableColumn<Movie, String> clmnGenre;
    @FXML protected TableColumn<Movie, String> clmnClassification;
    @FXML protected TableColumn<Movie, String> clmnType;
    
    
    
    @FXML protected Label price;
    @FXML protected TextField entire;
    @FXML protected TextField halfPrice;
    
    protected ObservableList<String> listCombos
            = FXCollections.observableArrayList(
                    "Combo Infantil",
                    "Combo Pequeno",
                    "Combo Médio",
                    "Combo Grande",
                    "Combo Gigantesco"
            );
    
    @FXML protected ComboBox cboRooms = new ComboBox(listRooms);
    @FXML protected ComboBox cboCombos = new ComboBox(listCombos);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = new Database();
        con.openConnection();

        listMovies = FXCollections.observableArrayList();
        listRooms = FXCollections.observableArrayList();
        listCombos = FXCollections.observableArrayList();

        Movie.getData(con.getConnection(), listMovies);
        
        tblViewMovies.setItems(listMovies);
        
        cboCombos.getItems().addAll(listCombos);
        
        tblViewMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMovieDetails(newValue));
        
        clmnName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        clmnType.setCellValueFactory(new PropertyValueFactory<Movie, String>("type"));
        clmnClassification.setCellValueFactory(new PropertyValueFactory<Movie, String>("classification"));
    }    
    
    private void showMovieDetails(Movie movie) {
        if (movie != null) {
            javafx.scene.image.Image img = new javafx.scene.image.Image(movie.getImgcover(), 116, 165, false, false);
            imgCoverMovie.setImage(img);
            movieName = movie.getName();
            Room.getDataByID(con.getConnection(), movie.getIdmovie(), listRooms);
            cboRooms.getItems().addAll(listRooms);
            roomName = cboRooms.getSelectionModel().getSelectedItem().toString();
        } else {
            javafx.scene.image.Image img = new javafx.scene.image.Image("@../res/defaultCover.png", 116, 165, false, false);
            imgCoverMovie.setImage(img);
        }
    }
    
    public void updatePrice() {
        Float priceHalf = 0.f, priceEntire = 0.f;
        
        if(halfPrice.getText() != null){
            priceHalf = Float.valueOf(halfPrice.getText())*5;
        }
        if(entire.getText() != null){
            priceEntire = Float.valueOf(entire.getText())*10;
        }
        value = (priceHalf + priceEntire);
        String value_text = "R$ " + value;
        
        price.setText(value_text);
    }
    
    public void updatePriceCombo() {
        
        if(cboCombos.getSelectionModel().getSelectedItem().toString().equals("Combo Infantil")){
            value += 8;
        }else if(cboCombos.getSelectionModel().getSelectedItem().toString().equals("Combo Pequeno")){
            value += 11;
        }else if(cboCombos.getSelectionModel().getSelectedItem().toString().equals("Combo Médio")){
            value += 15;
        }else if(cboCombos.getSelectionModel().getSelectedItem().toString().equals("Combo Grande")){
            value += 18;
        }else if(cboCombos.getSelectionModel().getSelectedItem().toString().equals("Combo Gigantesco")){
            value += 22;
        }
        String value_text = "R$ " + value;
        
        price.setText(value_text);
    }
    
    
    public void buyTicket() throws Exception {
        
        Document doc = null;
        OutputStream os = null;

        try {
            doc = new Document(PageSize.A4, 72, 72, 72, 72);

            os = new FileOutputStream("ticket.pdf");

            PdfWriter.getInstance(doc, os);

            doc.open();

            /*Image logo = Image.getInstance("@../res/LogoCinema.png");
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.setSpacingAfter(2);

            doc.add(logo);*/
            
            Paragraph movie = new Paragraph("Filme adquirido: " + movieName + "\n" + "Sala: " + roomName);
            movie.setSpacingAfter(2);
            doc.add(movie);
            
            Paragraph total = new Paragraph("Valor total: " + value);
            doc.add(total);
            
            Alert msgbox = new Alert(Alert.AlertType.INFORMATION);
            msgbox.setHeaderText("Compra de ingresso efetuada.");
            msgbox.setTitle("CineDelícia - Compra de Ingressos");
            msgbox.setContentText("Ingresso comprado com sucesso!\n\nUma nova janela será aberta com o ticket!");
            msgbox.showAndWait();

        } finally {
            if (doc != null) {
                doc.close();
            }
            
            if (os != null) {
                os.close();
            }
        } 
        Desktop.getDesktop().open(new File("ticket.pdf"));
    }
}
