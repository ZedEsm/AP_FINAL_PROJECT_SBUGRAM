
package Controller;

import Model.PageLoader;
import Model.Post;
import View.UPDATE_USER_INFOController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class PostItemController {
      public Label pswn;
      public Label pst;
      public AnchorPane root;
      Post post;
      ObjectOutputStream out;
      ObjectInputStream in ;
      String usn;
   public PostItemController(Post post) throws IOException {
       new PageLoader().load("postItem",this);
       this.post=post;
   }

    public  AnchorPane init(String s) {
        usn=s;
        pswn.setText(post.getWriter());
        pst.setText(post.getTitle());
        return root;
    
    }
    public void repost(ActionEvent a){}
    public void see_more(ActionEvent a){
         try {
//            new PageLoader().load("Menu");
//           ((Node)(act.getSource())).getScene().getWindow().hide();
               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/POST_PAGE.fxml"));
                Parent root=loader.load();
                  POST_PAGEController mt = loader.getController();
                mt.init(post,usn);
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
                   int os =in.readInt();
                     FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/POST_PAGE.fxml"));
                Parent root=loader.load();
                  POST_PAGEController mt = loader.getController();
                mt.init(post,usn);
                mt.init1(os);
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