
package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProfileController{
    public Label nome_etprenom;
    public Label usnnom;
    public Label brthd;
    public Label followers_conter;
    public Label following_counter;
    public ImageView img;
    ObjectOutputStream out;
    ObjectInputStream in ;
    public String usn;
    
    public void init(String s){
        usn=s;
        System.out.println(usn);
        iner();
        
    }
    public void Follow_action(ActionEvent act){
    }
    public void follower(ActionEvent a){
        
    }
     public void following(ActionEvent a){
    }

    
    public void iner() {
        Socket clientsocket;
        try {
             clientsocket = new Socket("localhost",2020);
             out = new ObjectOutputStream(clientsocket.getOutputStream());
             in = new ObjectInputStream(clientsocket.getInputStream());
                 out.writeObject("get_user_info,"+usn);
                 out.flush();
                  Object os;
                  os = in.readObject();
                  String from_Server = os.toString();
                  System.out.println(from_Server+"f");
                         String[] parameters=from_Server.split(",");
                         nome_etprenom.setText(parameters[0]+" "+parameters[1]);
                         usnnom.setText(usn);
                         brthd.setText(parameters[2]);
                         String pnme=parameters[3];
                         pnme=pnme.split("///")[1];
                         pnme=pnme.replaceAll("%20"," ");
                        img.setImage(new Image(Paths.get(pnme).toUri().toString()));
                        String follower = parameters[4];
                        String following = parameters[5];
                        //System.out.println(parameters[5]+"888");
                        followers_conter.setText(follower);
                       following_counter.setText(following);
                        // Image m =new Image(Paths.get(pnme).toUri().toString()); 
                         
                         System.out.println(pnme);
                        // img.setImage(m);
                         
                         
                              
               
        } catch (Exception ex) {
            System.out.println(ex+"prfex");
        }
   

    }
}
