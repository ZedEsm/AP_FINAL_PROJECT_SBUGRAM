
package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

//String users_o =  usersbox.getSelectionModel().getSelectedItem().toString();

public class UserlistController {
    
    @FXML
 public ComboBox usersbox;
    public String usn;
         ObjectInputStream in;
    ObjectOutputStream out;
    public void init(String s){
        try {
            usn=s;
            System.out.println(usn);
            Socket clientsocket =new Socket("localhost",2020);
            out = new ObjectOutputStream(clientsocket.getOutputStream());
            in=new ObjectInputStream(clientsocket.getInputStream());
            
            
            String str="[get_user_List]," ;
            
            out.writeObject(str); 
            out.flush();
             Object os =in.readObject();
            String from_Server = os.toString();
            String[] fs=from_Server.split(",");
            for (int i = 0; i < fs.length; i++) {
                if(!(fs[i].equals(usn)))
                 usersbox.getItems().add(fs[i]);
            }
           
            //System.out.println(from_Server);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        
    }
    public void Display_Profile(ActionEvent a){
      String users_o =  usersbox.getSelectionModel().getSelectedItem().toString();
      
      
    }
    
    
}
