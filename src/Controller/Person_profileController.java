
package Controller;

import Model.PageLoader;
import Model.Post;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Person_profileController{
    public Label nam;
    public Label un;
    public Label bd;
    public ImageView img;
    public Label following_counter;
    public Label follower_counter;
    ObjectOutputStream out;
    ObjectInputStream in ;
    public String usn;
   // public String mainusername;
   ArrayList<Post> posts = new ArrayList<>();
    public ListView<Post> post_list;
    public void init(String s){
        usn=s;
        System.out.println(usn);
        iner();
        
    }
     public void init2(String s){
        usn=s;
       // mainusername=mus
        System.out.println(usn);
        iner2();
        
    }
    public void Follow_action(ActionEvent act){
    }
    public void follower(ActionEvent a){
        
    }
     public void following(ActionEvent a){
    }

    
    public void iner() {
        Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("get_user_info,"+usn);
                 out.flush();
                  Object os;
                  os = in.readObject();
                  String from_Server = os.toString();
                  System.out.println(from_Server+"f");
                         String[] parameters=from_Server.split(",");
                         nam.setText(parameters[0]+" "+parameters[1]);
                         un.setText(usn);
                         bd.setText(parameters[2]);
                         String pnme=parameters[3];
                         if(pnme.indexOf("///")>0){
                         pnme=pnme.split("///")[1];
                         
                         pnme=pnme.replaceAll("%20"," ");
                         }
                       //  imagec_prof.setFill(new ImagePattern(new Image(pnme)));
                       img.setImage(new Image(Paths.get(pnme).toUri().toString()));
                        String follower = parameters[4];
                        String following = parameters[5];
                        System.out.println(parameters[4]+" "+ parameters[5]+"888");
                        follower_counter.setText(follower);
                        //System.out.println(parameters[5]+"888");
                       following_counter.setText(following);
                        // Image m =new Image(Paths.get(pnme).toUri().toString()); 
                         
                         System.out.println(pnme);
                        // img.setImage(m);
                         
                         
                              
               
        } catch (Exception ex) {
            System.out.println(ex+"prfex");
        }
   

    }
     public void iner2(){
          Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("select_following_list,"+usn);
                 out.flush();
                  int Length = in.readInt();
              
                   for (int i = 0; i <Length; i++) {
                      
                       Post post= (Post)in.readObject();
                       posts.add(post);
                        
                   }
//                   post_list.setItems(FXCollections.observableArrayList(posts));
//                       post_list.setCellFactory(post_list -> new profile_post_items2(usn));
                    post_list.setItems(FXCollections.observableArrayList(posts));
                    post_list.setCellFactory(post_list -> new PostItem(usn));
                
                
           }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void update_info(ActionEvent a){
        String current_user_name = un.getText();
        if(current_user_name.equals(usn)){
              try {
//            new PageLoader().load("Menu");
//           ((Node)(act.getSource())).getScene().getWindow().hide();
               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/UPDATE_USER_INFO.fxml"));
                Parent root=loader.load();
                  UPDATE_USER_INFOController mt = loader.getController();
                mt.init(usn);
            //    mt.init2(usn);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn);
                    stage.show();
                   ((Node)(a.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }

            
        }
        
    }
    public void delete_account(ActionEvent a){
        Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("delete_account,"+usn);
                 out.flush();
                  Object os;
                  os = in.readObject();
                  String from_Server = os.toString();
                  if(from_Server.equals("your account deleted")){
                       Alert alert = new Alert(Alert.AlertType.INFORMATION,from_Server);
                       alert.setTitle(usn+" deleted account");
                       alert.showAndWait();
                    
                     
                       
                  }
                  else{
                       Alert alert = new Alert(Alert.AlertType.ERROR,"failled");
                       alert.setTitle(usn+" failled to delete");
                       alert.showAndWait();
                       
                  }
                  
        }catch(Exception ex){
            System.out.println(ex);
        }
        
       
        
        
        
    }
    public void showmenu(MouseEvent act){
        try {
//            new PageLoader().load("Menu");
//           ((Node)(act.getSource())).getScene().getWindow().hide();
               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));
                Parent root=loader.load();
                MenuController mt = loader.getController();
                mt.init(usn);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn);
                    stage.show();
                   ((Node)(act.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
 
   
   
    
}
