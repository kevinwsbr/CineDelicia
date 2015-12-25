package cinedelicia;

import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Connection con = new Database().getConnection();

        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Login - CineDelícia");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
