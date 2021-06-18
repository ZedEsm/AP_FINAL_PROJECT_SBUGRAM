
package Controller;

import Model.PageLoader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//String users_o =  usersbox.getSelectionModel().getSelectedItem().toString();

public class UserlistController {
    
    @FXML
 public ComboBox usersbox;
    public String usn;
   
    ObjectInputStream in;
    ObjectOutputStream out;
    public void init(String s){
        try {
            usn=s;
            System.out.println(usn);
            Socket clientsocket =new Socket("localhost",2020);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            in=new ObjectInputStream(clientsocket.getInputStream());
            
            
            String str="[get_user_List]," ;
            
            out.writeObject(str); 
            out.flush();
             Object os =in.readObject();
            String from_Server = os.toString();
            String[] fs=from_Server.split(",");
            for (int i = 0; i < fs.length; i++) {
                if(!(fs[i].equals(usn)))
                 usersbox.getItems().add(fs[i]);
            }
           
            //System.out.println(from_Server);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        
    }
    public void Display_Profile(ActionEvent a){
//        try {
//            //      String users_o =  usersbox.getSelectionModel().getSelectedItem().toString();
////        FXMLLoader loader;
////           
////           try {
////               loader = new FXMLLoader(getClass().getResource("/View/profile.fxml"));
////                Parent root=loader.load();
////                    ProfileController tm = loader.getController();
////                    tm.init(users_o);
////                   //tm.init(usn);
////                    Scene scene = new Scene(root);
////                    Stage stage = new Stage();
////                    stage.setScene(scene);
////                    stage.setTitle(users_o);
////                   // stage.setTitle(usn);
////                    Object os = in.readObject();
////                    String from_Server = os.toString();
////                    String pnme=from_Server.split(",")[1];
////                    pnme=pnme.split("///")[1];
////                    pnme=pnme.replaceAll("%20"," ");
////                  
////                   /// stage.getIcons().add(new Image(Paths.get(pnme).toUri().toString()));
////                    stage.show();
////                   //((Node)(a.getSource())).getScene().getWindow().hide();
////           } catch (Exception ex) {
////               System.out.println(ex);
////           }
//new PageLoader().load("profile");
////      
//        } catch (IOException ex) {
//            Logger.getLogger(UserlistController.class.getName()).log(Level.SEVERE, null, ex);
//        }

      FXMLLoader loader;
           String users_o =  usersbox.getSelectionModel().getSelectedItem().toString();
           try {
        loader = new FXMLLoader(getClass().getResource("/View/profile.fxml"));
                Parent root=loader.load();
                   ProfileController tm = loader.getController();
                    tm.init(users_o);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(users_o);
      
                    stage.show();
                   ((Node)(a.getSource())).getScene().getWindow().hide();
           } catch (IOException ex) {
               System.out.println(ex);
           }

    }
    
    
}
