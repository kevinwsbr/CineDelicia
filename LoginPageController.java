package cinedelicia;

import cinedelicia.model.Functionary;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController implements Initializable {

    Stage previous = null;
    
    protected Database con;
    
    @FXML protected TextField user;
    @FXML protected PasswordField pwd;

    
    @FXML
    private void login(ActionEvent event){
        con = new Database();
        con.openConnection();
        String position = Functionary.authenticate(con.getConnection(), user.getText(), pwd.getText());
        
        if(position.equals("Admin")){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adminPage.fxml"));
                Parent main = (Parent) fxmlLoader.load();
                
                //open new stage
                Stage n_stage = new Stage();
                n_stage.setScene(new Scene(main));  
                n_stage.show();

                //close current stage
                Node source = (Node)event.getSource(); 
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }else if(position.equals("Caixa")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("salePage.fxml"));
                Parent main = (Parent) fxmlLoader.load();
                
                //open new stage
                Stage n_stage = new Stage();
                n_stage.setScene(new Scene(main));  
                n_stage.show();

                //close current stage
                Node source = (Node)event.getSource(); 
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }else{
            Alert msgbox = new Alert(Alert.AlertType.ERROR);
            msgbox.setContentText("Tente novamente!");
            msgbox.setTitle("CineDelícia - Login");
            msgbox.setHeaderText("Nome de usuário ou senha incorreto(s)");
            msgbox.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
