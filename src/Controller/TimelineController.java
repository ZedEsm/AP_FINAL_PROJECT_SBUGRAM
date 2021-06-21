
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
//   
//    public TextField title_field;
//    public TextArea description_field;
   
    public String usn;
    ArrayList<Post> posts = new ArrayList<Post>();
    Post currentPost = new Post();
    ObjectOutputStream out;
    ObjectInputStream in ;
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
//                  FXMLLoader loader;
//                 loader = new FXMLLoader(getClass().getResource("/View/freee.fxml"));
//                 Parent root=loader.load();
                   //ListView<FXMLLoader> postList;
                  for (int i = 0; i <Length; i++) {
                      
                       Post post= (Post)in.readObject();
                       String s=post.toString();
                        System.out.println(s);
                        
                  }
                  
                   //System.out.println(from_Server+"tm");
                
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void refresh(ActionEvent a){
        iner();
    }
    
   
//   @FXML
//    public void initialize() {
//        //initialize posts array list to be shown in list view
//        for (int i = 1; i <= 5; i++) {
//            Post p = new Post();
//            p.setTitle("post" + i);
//            p.setDescription("description" + i);
//            p.setWriter("user" + i);
//            posts.add(p);
//        }
//
//        //show the post array in list view
//        postList.setItems(FXCollections.observableArrayList(posts));
//
//        //customize each cell of postList with new graphic object PostItem
//        postList.setCellFactory(postList -> new PostItem());
//    }
        
        //set the post features

   
     
//    public  void addPost(ActionEvent actionEvent){
//        currentPost.setWriter("zed esmati");
//        currentPost.setDescription(description_field.getText());
//        currentPost.setTitle(title_field.getText());
//        posts.add(currentPost);
//           
////        postList.setItems(FXCollections.observableArrayList(posts));
////        //postList.setCellFactory(postList -> new PostItem());
//       postList.getItems().add(currentPost.getTitle());
//        currentPost=new Post();
//        title_field.setText("");
//        description_field.setText("");
//    }
    public void publicPost(ActionEvent actionEvent){
        currentPost.setStatus(PrivacyStatus.PUBLIC);
    }
    public void privatePost(ActionEvent actionEvent){
        currentPost.setStatus(PrivacyStatus.PRIVATE);
    }

//    public void showPost(MouseEvent mouseEvent) {
//        Post p = new Post();
//        p.setTitle(postList.getSelectionModel().getSelectedItem());
//        for (int i = 0; i < posts.size(); i++) {
//            if (posts.get(i).equals(p)) {
//                title_field.setText(posts.get(i).getTitle());
//                description_field.setText(posts.get(i).getDescription());
//            }
//        }
//    }
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


