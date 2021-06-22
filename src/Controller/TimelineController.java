
package Controller;

import Model.PageLoader;
import Model.Post;
import Model.PrivacyStatus;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class TimelineController {

    
    ArrayList<Post> posts = new ArrayList<>();
    Post currentPost = new Post();
    ObjectOutputStream out;
    ObjectInputStream in ;
    public String usn;
    public ListView<Post> post_list;
    public void init(String s){
        usn=s;
        System.out.println(usn);
        iner();
        
    }
    public void iner(){
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
                   post_list.setItems(FXCollections.observableArrayList(posts));
                       post_list.setCellFactory(post_list -> new PostItem(usn));
                
                
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void refresh(ActionEvent a){
        iner();
    }
    
   

//    public void publicPost(ActionEvent actionEvent){
//        currentPost.setStatus(PrivacyStatus.PUBLIC);
//    }
//    public void privatePost(ActionEvent actionEvent){
//        currentPost.setStatus(PrivacyStatus.PRIVATE);
//    }


    public void showmenu(MouseEvent act){
        try {

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


