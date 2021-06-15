
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
//    int Height=600;
//    int Width=400;
    @Override
    public void start(Stage stage) throws IOException {
        
            PageLoader.initStage(stage);
            new PageLoader().load("FXMLDocument");
//        Parent root = FXMLLoader.load(getClass().getResource("/View/FXMLDocument.fxml"));
//      // stage.setTitle("SBU GRAM");
//        stage.setScene(new Scene(root,Width,Height));
//        stage.show();
     
    }

    public static void main(String[] args) {
        launch(args);
    
    }
//        @Override
//        public void stop(){
//            System.out.println("Program Closed");
//        }
    
}



//package Model;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class SBUGRAM extends Application {
//
//    @Override
//    public void start(Stage stage) throws Exception{
//        PageLoader.initStage(stage); //this is only needed when you start program
//        and need a new stage. all scenes will be loaded on this stage
//        new PageLoader().load("FXMLDocument");
//    }
//
//    @Override
//    //this function happens when the program is opened
//    public void init() {
//        System.out.println("program opened");
//    }
//
//    @Override
//    //this function happens when the program is closed
//    public void stop() {
//        System.out.println("program closed");
//    }
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
