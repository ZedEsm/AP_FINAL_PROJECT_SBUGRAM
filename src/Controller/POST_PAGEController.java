
    package Controller;

import Model.PageLoader;
    import Model.Post;
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
import javafx.scene.input.MouseEvent;
    import javafx.stage.Stage;


    public class POST_PAGEController  {
        Post currentPost = new Post();
        ObjectOutputStream out;
        ObjectInputStream in ;
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
        String pwn;
    public void init(Post p,String s,int L,int R){

        this.p=p;
        usn=s;
        System.out.println(L+" "+R);
        post_writer_name.setText(p.getWriter()) ;
        pwn = post_writer_name.getText();
        post_title.setText(p.getTitle());
        post_description.setText(p.getDescription());
        like.setText(L+"");
        p.setNumber_of_like(L);
        repost.setText(R+"");
        p.setNumber_of_repost(R);
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
        pwn = post_writer_name.getText();
        post_title.setText(p.getTitle());
        post_description.setText(p.getDescription());
        post_date.setText(p.getPost_delivered_time().toString());
    }
    public void view_comments(ActionEvent a){
        try {
//          new PageLoader().load("Menu");
//          ((Node)(act.getSource())).getScene().getWindow().hide();
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
        }catch (IOException ex) {
            System.out.println(ex);
        }
          
    }
    public void add_comments(ActionEvent a){
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/add_comments.fxml"));
            Parent root=loader.load();
            Add_commentsController mt = loader.getController();
            mt.init(p,usn);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        // ((Node)(a.getSource())).getScene().getWindow().hide();
        }catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void view_profile(ActionEvent a) throws IOException{
       // new PageLoader().load("profile");
          FXMLLoader loader;
         //  String users_o =  usersbox.getSelectionModel().getSelectedItem().toString();
           try {
        loader = new FXMLLoader(getClass().getResource("/View/profile.fxml"));
                Parent root=loader.load();
                   ProfileController tm = loader.getController();
                   String pswn = post_writer_name.getText();
                   tm.init(pswn,usn);
                    tm.init2(pswn,usn);
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
//    public void Go_back_to_timeline(MouseEvent m){
//         try {
//
//               FXMLLoader loader;
//               loader = new FXMLLoader(getClass().getResource("/View/timeline.fxml"));
//                Parent root=loader.load();
//              TimelineController mt = loader.getController();
//                mt.init(usn);
//                 Scene scene = new Scene(root);
//                    Stage stage = new Stage();
//                    stage.setScene(scene);
//                    stage.setTitle(usn);
//                    stage.show();
//                   ((Node)(m.getSource())).getScene().getWindow().hide();
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//        
//    }
    public void like(ActionEvent a){
        String command = "increase_like,"+p.getWriter()+","+p.getTitle();
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
                mt.init(p,usn,LiKe,Repost);
            //  mt.init1(LiKe,Repost);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(usn);
                    stage.show();
                  ((Node)(a.getSource())).getScene().getWindow().hide();         
                              

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
      public void repost(ActionEvent a){
          String command = "repost,"+p.getWriter()+","+p.getTitle()+","+usn;
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
                   mt.init(p,usn,LiKe,Repost);
               //   mt.init1(LiKe,Repost);
                 Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(p.getTitle());
                    stage.show();
                  // ((Node)(a.getSource())).getScene().getWindow().hide();         
                              

        }
        catch(Exception ex){
            System.out.println(ex);
        }
      }

    
    
}
