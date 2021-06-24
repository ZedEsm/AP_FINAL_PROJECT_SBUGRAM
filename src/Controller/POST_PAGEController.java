
package Controller;

import Model.Post;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class POST_PAGEController  {

    public Label post_writer_name;
    public Label post_title;
    public Label post_description;
   public Label repost;
   public Label like;
    public Post p;
    public String usn;
    public Label post_date;
    int L;
    int R;
     public void init(Post p,String s,int L,int R){
        this.p=p;
        usn=s;
         System.out.println(L+" "+R);
        post_writer_name.setText(p.getWriter()) ;
        post_title.setText(p.getTitle());
        post_description.setText(p.getDescription());
         like.setText(L+"");
         p.setNumber_of_like(L);
          repost.setText(R+"");
       p.setNumber_of_repost(R);
     //  p.getPost_delivered_time();
       post_date.setText(p.getPost_delivered_time().toString());
 
    }
     
        public void init1(int L,int R){
         like.setText(L+"");
         p.setNumber_of_like(L);
          repost.setText(R+"");
       p.setNumber_of_repost(R);
 
    }
     public void init1(Post p,String s){
          this.p=p;
        usn=s;
        post_writer_name.setText(p.getWriter()) ;
        post_title.setText(p.getTitle());
        post_description.setText(p.getDescription());
        post_date.setText(p.getPost_delivered_time().toString());
     }
      public void view_comments(ActionEvent a){
           try {
//            new PageLoader().load("Menu");
//           ((Node)(act.getSource())).getScene().getWindow().hide();
               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/view_comments.fxml"));
                Parent root=loader.load();
               View_commentsController mt = loader.getController();
               mt.init(p);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                //    stage.setTitle(post.getTitle());
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
          
      }
      public void add_comments(ActionEvent a){
            try {
//            new PageLoader().load("Menu");
//           ((Node)(act.getSource())).getScene().getWindow().hide();
               FXMLLoader loader;
               loader = new FXMLLoader(getClass().getResource("/View/add_comments.fxml"));
                Parent root=loader.load();
                 Add_commentsController mt = loader.getController();
               mt.init(p,usn);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                //    stage.setTitle(post.getTitle());
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
      }
    
    
}
