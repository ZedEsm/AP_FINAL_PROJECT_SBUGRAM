package Controller;

import Model.PageLoader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

public class LoginController{
     @FXML
     
    public TextField Username_Field;
    public Button Login_Button;
    public PasswordField Password_Field;
    public Label Wrong_Label;
    public TextField password_visible;
    
    public void login(ActionEvent actionEvent) throws IOException{
        String username = Username_Field.getText();
        String password;
        ObjectInputStream in;
        ObjectOutputStream out;
        
        if(Password_Field.isVisible())
           password = Password_Field.getText();
        else
           password= password_visible.getText();
        if(username!=null){
            if(username.trim().length()>0){
                if(password!=null){
                    if(password.trim().length()>0){
                        Socket clientsocket =new Socket("localhost",2020);
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());
                                      String str ="[Login_user],"+username+","+password;
                                      out.writeObject(str);
                                      out.flush();
                                      Object os;
                        try {
                                    os = in.readObject();
                                    String from_Server = os.toString();
                              
                                    if(from_Server.startsWith("Succeed")){
                                        
            FXMLLoader loader;
           
           try {
               loader = new FXMLLoader(getClass().getResource("/View/timeline.fxml"));
                Parent root=loader.load();
                    TimelineController tm = loader.getController();
                    tm.init(username);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(username);
                    String pnme=from_Server.split(",")[1];
                    System.out.println(pnme);
                    if(pnme.indexOf("///")>0){
                        pnme=pnme.split("///")[1];
                        pnme=pnme.replaceAll("%20"," ");
                    }
                  
                    stage.getIcons().add(new Image(Paths.get(pnme).toUri().toString()));
                    stage.show();
                   ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
           } catch (IOException ex) {
               System.out.println(ex);
           }
                                       //  new PageLoader().load("timeline");
                                          
                                    }
                                    else{
                                            Alert alert = new Alert(Alert.AlertType.ERROR,from_Server);
                                            alert.setTitle("Invilid username or password");
                                            alert.showAndWait();
                                    }
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    else{
                             Alert alert = new Alert(Alert.AlertType.ERROR,"Invlid password");
                             alert.setTitle("Password is empty");
                             alert.showAndWait();
                    }
                }
                else{
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Invlid password");
                        alert.setTitle("Password is empty");
                        alert.showAndWait();
                    }
                
                
            }
            else{
                     Alert alert = new Alert(Alert.AlertType.ERROR,"Invlid username");
                    alert.setTitle("UserName is empty");
                    alert.showAndWait();
            }
            
        }
        else{
              Alert alert = new Alert(Alert.AlertType.ERROR,"Invlid username");
               alert.setTitle("UserName is empty");
            alert.showAndWait();
        }
        
        
//        if(username.equalsIgnoreCase("zed") && password.equalsIgnoreCase("esmati")){
//            Wrong_Label.setVisible(false);  
////            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Welcome!");
////            alert.showAndWait();
//            new PageLoader().load("timeline");
//        }
        
//        else{
//            Wrong_Label.setVisible(true);       
//        }
    }
    public void show_pass(ActionEvent actionEvent){
        if(!password_visible.isVisible()){
            password_visible.setVisible(true);
            Password_Field.setVisible(false);
            password_visible.setText(Password_Field.getText());
        }
        else{
            password_visible.setVisible(false);
            Password_Field.setVisible(true);
            password_visible.setText(password_visible.getText());
        }

        
    }
            
        public void sign_up(ActionEvent actionEvent){
         try {
             //
//             Parent root;
//           try {
//               root = FXMLLoader.load(getClass().getResource("/View/Registe.fxml"));
//                    Scene scene = new Scene(root);
//                    Stage stage = new Stage();
//                     stage.getIcons().add(new Image(Paths.get("87390.png").toUri().toString()));
//
//                    stage.setScene(scene);
//                    stage.show();
//                   ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
//           } catch (IOException ex) {
//               System.out.println(ex);
//           }
               new PageLoader().load("Registe");
//              ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
         } catch (IOException ex) {
             System.out.println(ex);
         }
    }
}



















   // @FXML
//    public void initialize(){
//        TranslateTransition tt = new TranslateTransition(Duration.millis(2000),Login_Button);
//        tt.setToY(-125);
//        System.out.println(Login_Button.getLayoutY());
//        tt.playFromStart();
//    }
//     @FXML
//    //this method is invoked right after the fxml page is loaded
//    public void initialize() {
//        //the login button is at lower position at first so we move it upper with transtion
//        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), login_button);
//        tt.setToY(-125); //the button will come 125 pixels up
//        tt.playFromStart();
//    }


//public class LoginController implements Initializable {
//    
//    @FXML
//    private Label label;
//    
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
//    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }    
//    
//}

