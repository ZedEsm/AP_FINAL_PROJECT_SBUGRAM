
package Controller;

import Model.PageLoader;
import Model.Post;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class profile_post_items2Controller {
      public Label pswn;
      public Label pst;
      public AnchorPane root;
      Post post;
      ObjectOutputStream out;
      ObjectInputStream in ;
      String usn;
   public profile_post_items2Controller(Post post) throws IOException {
       new PageLoader().load("profile_post_items2",this);
       this.post=post;
   }

    public  AnchorPane init(String s) {
        usn=s;
        pswn.setText(post.getWriter());
        pst.setText(post.getTitle());
        return root;
    
    }
    public void see_more(ActionEvent a){
         try {

               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/POST_PAGE.fxml"));
                Parent root=loader.load();
                  POST_PAGEController mt = loader.getController();
                mt.init1(post,usn);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(post.getTitle());
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        
    }
    public void like(ActionEvent a){
        String command = "increase_like,"+post.getWriter()+","+post.getTitle();
        Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject(command);
                 out.flush();
                        int LiKe =in.readInt();
                   int Repost = in.readInt();
                     FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/POST_PAGE.fxml"));
                Parent root=loader.load();
                  POST_PAGEController mt = loader.getController();
                mt.init(post,usn,LiKe,Repost);
           //     mt.init1(LiKe,Repost);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(post.getTitle());
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();         
                              

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
      public void repost(ActionEvent a){
          String command = "repost,"+post.getWriter()+","+post.getTitle();
        Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject(command);
                 out.flush();
                   int LiKe =in.readInt();
                   int Repost = in.readInt();
                 //  System.out.println("Controller.PostItemController);
                     FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/POST_PAGE.fxml"));
                Parent root=loader.load();
                POST_PAGEController mt = loader.getController();
                    mt.init(post,usn,LiKe,Repost);
                 //  mt.init1(LiKe,Repost);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(post.getTitle());
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();         
                              

        }
        catch(Exception ex){
            System.out.println(ex);
        }
      }

    
}
