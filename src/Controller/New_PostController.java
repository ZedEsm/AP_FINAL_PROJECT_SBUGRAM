
package Controller;

import Model.PageLoader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class New_PostController {
    @FXML
    public TextField post_title;
    public TextArea post_message;
     ObjectInputStream in;
    ObjectOutputStream out;
     public String usn;
    public void init(String s){
        usn=s;
        System.out.println(usn);
        
    }

    
public void Close_form(MouseEvent msv){
    try {
            new PageLoader().load("Menu");
          // ((Node)(msv.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
}
public void Publish_m(ActionEvent act){
      try {
           String pme= post_message.getText();
            String pst=post_title.getText();
            Socket clientsocket =new Socket("localhost",2020);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            in=new ObjectInputStream(clientsocket.getInputStream());
            
            
            String str ="[New_Post],"+pst+","+pme+","+usn;
            
            out.writeObject(str); 
            out.flush();
            Object os =in.readObject();
            String from_Server = os.toString();
            if(from_Server.endsWith("successfuly")){
                  Alert alert = new Alert(Alert.AlertType.INFORMATION,from_Server);
                  alert.setTitle("New Post Added");
                  alert.showAndWait();
                  post_message.setText("");
                  post_title.setText("");
                  
            
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } 
}
    
}
