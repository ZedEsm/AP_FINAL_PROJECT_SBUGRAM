
package Model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SBUGRAM extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        
            PageLoader.initStage(stage);
            new PageLoader().load("FXMLDocument");
     
    }

    public static void main(String[] args) {
        launch(args);
    
    }
    
}