
package Controller;

import Model.PageLoader;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.event.ActionEvent;;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class MenuController{
public MenuController(){
        
}
 public String usn;
    public void init(String s){
        usn=s;
        System.out.println(usn);
        
    }

    public void LOGoOUT(ActionEvent act){
       
        try {
            new PageLoader().load("FXMLDocument");
            ((Node)(act.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }

     }
    public void new_post(ActionEvent actev){
//        try {
//            new PageLoader().load("New_Post");
//           //((Node)(actev.getSource())).getScene().getWindow().hide();
                             
            FXMLLoader loader;
             System.out.println("555333");
           
           try {
               
               loader = new FXMLLoader(getClass().getResource("/View/New_Post.fxml"));
                System.out.println("533355");
                Parent root=loader.load();
                  System.out.println("555");
                 New_PostController tm = loader.getController();
                   System.out.println("666");
                    tm.init(usn);
                      System.out.println("9999");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn);
                  
                    stage.show();
                   //((Node)(actev.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
             FXMLLoader loader;
    public void prof(ActionEvent a){
         
           
         try {
//            new PageLoader().load("profile");
//           ((Node)(a.getSource())).getScene().getWindow().hide();
                FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/person_profile.fxml"));
             
                Parent root=loader.load();
                Person_profileController mt = loader.getController();
                mt.init(usn);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn);
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();
                   ((Node)(a.getSource())).getScene().getWindow().hide();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void UserList(ActionEvent actev){
                     FXMLLoader loader;
           
           try {
        loader = new FXMLLoader(getClass().getResource("/View/UserList.fxml"));
                Parent root=loader.load();
                    UserlistController tm = loader.getController();
                    tm.init(usn);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn);
      
                    stage.show();
                   ((Node)(actev.getSource())).getScene().getWindow().hide();
           } catch (IOException ex) {
               System.out.println(ex);
           }
    }
}

     
