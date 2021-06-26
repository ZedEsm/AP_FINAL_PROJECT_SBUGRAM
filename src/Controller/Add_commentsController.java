
package Controller;

import Model.Post;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class Add_commentsController {
 public TextArea comments;
     public String usn;
     public Post p;
     ObjectInputStream in;
   ObjectOutputStream out;
    public void init(Post post,String s){
        usn=s;
        p=post;
        System.out.println(usn);
        
        
    }
 

    public void add_comment(ActionEvent a) {
          Socket clientsocket;
     try {
         clientsocket = new Socket("localhost",2020);
     
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());

                                   
                                      String str ="[add_comments],"+usn+","+p.getWriter()+","+p.getTitle()+","+comments.getText();
                                      
                                    
                                      out.writeObject(str);
                                      out.flush(); 
                                      Object os =in.readObject();
                                      String from_Server = os.toString();
                                      if(from_Server.equals("your comment saved successfully")){
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION,"you commented on this post");
                                alert.setTitle("commented!");
                                alert.showAndWait();
                                      }//else bezar error
                                      else{
                                           Alert alert = new Alert(Alert.AlertType.ERROR,"comment is empty");
                                alert.setTitle("comment failed!");
                                alert.showAndWait();
                                      }
                                      
     } catch (Exception ex) {
         System.out.println(ex);
     }
    }    
    
}
