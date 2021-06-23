
package Controller;

import Model.Post;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


public class View_commentsController  {
public ListView<String> LIST_V;
Post post;
 ObjectOutputStream out;
    ObjectInputStream in;
    ArrayList<String> com = new ArrayList<String>();
public void init(Post p){
    post=p;
     Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("view_comments,"+p.getWriter()+","+p.getTitle());
                 out.flush();
                  Object Ocoment = in.readObject();
                  String comment = Ocoment.toString();
                  String[] comments = comment.split("\n");
                  for (int i = 0; i < comments.length; i++) {
                     String current_comment = comments[i];
                     String t_comment = current_comment.split(",")[0]+"\t"+ current_comment.split(",")[1];
                     com.add(t_comment);
                     
                  }
                   LIST_V.setItems(FXCollections.observableArrayList(com));
               
                 // postList.setCellFactory(postList -> new PostItem());
                 
                  
                
        }catch(Exception ex){
            System.out.println(ex);
        }
}
     
    
}
