
package Controller;

import Model.PageLoader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Retretive_passController {
 public Label quest;
 public TextField ASWQ;
 public TextField RW;
 public TextField usn;
   String usmn;
   String old_answer;
    ObjectInputStream in;
   ObjectOutputStream out;
    public void loadqa(ActionEvent a) {
        
       usmn=usn.getText();
        try {
                                     
                                     Socket clientsocket =new Socket("localhost",2020);
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());
                                      out.writeObject("check_usn,"+usmn);
                                      out.flush(); 
                                      Object os =in.readObject();
                                      String from_Server = os.toString();
                                      String[] waaa=from_Server.split(",");
                                      quest.setText(waaa[0]);
                                      old_answer=waaa[1];
                                      System.out.println(old_answer);
                                      
                                      
                                      
         }catch(Exception e){
             System.out.println(e);
         }
       
    }   
    public void check_asw(ActionEvent a){
        String as1w=ASWQ.getText();
        if(as1w.equals(old_answer)){
            
        try {
                                     
                                     Socket clientsocket =new Socket("localhost",2020);
                                     out = new ObjectOutputStream(clientsocket.getOutputStream());
                                     in=new ObjectInputStream(clientsocket.getInputStream());
                                      out.writeObject("get_password,"+usmn);
                                      out.flush(); 
                                      Object os =in.readObject();
                                      String from_Server = os.toString();
                                      if(from_Server.equals("usernme is incorrect\n")){
                                          Alert alert = new Alert(Alert.AlertType.ERROR,"user is incorrect");
                                         alert.setTitle("user is incorrect");
                                         alert.showAndWait();
                                      }
                                      else{
                                           RW.setText(from_Server);
                                      }
                                      
                                      
                                      
                                      
         }catch(Exception e){
             System.out.println(e);
         }
       
        }
        else{
              Alert alert = new Alert(Alert.AlertType.ERROR,"incorrect");
                    alert.setTitle("answer is incorrect");
                      alert.showAndWait();
        }
        
    }
    public void loginp(ActionEvent a){
        try {
            new PageLoader().load("FXMLDocument");
            ((Node)(a.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    
}
